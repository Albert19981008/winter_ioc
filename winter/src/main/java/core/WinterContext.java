package core;

import common.AutoInject;
import common.Bean;
import common.BeanScope;
import util.PackageScanner;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author lixinyang
 * 2020/8/16
 */
public class WinterContext implements BeanFactory {

    private final ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, BeanDetail> details = new ConcurrentHashMap<>();

    public Object getBean(String clzName) {
        try {
            Class<?> clazz = Class.forName(clzName);
            return getBean(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getBean(Class<T> clazz) {
        String clzName = clazz.getCanonicalName();
        if (beans.containsKey(clzName)) {
            return (T) beans.get(clzName);
        }
        if (details.containsKey(clzName)) {
            BeanDetail detail = details.get(clzName);
            return (T) createBean(detail);
        }
        for (BeanDetail detail : details.values()) {
            Class<?> detailClazz = detail.beanClazz;
            if (clazz.isAssignableFrom(detailClazz)) {
                return (T) createBean(detail);
            }
        }
        return null;
    }

    public void registerBean(Class<?> clazz) {
        if (details.containsKey(clazz.getCanonicalName())) {
            return;
        }
        Bean beanAnnotation = clazz.getAnnotation(Bean.class);
        BeanDetail detail = new BeanDetail();
        detail.beanScope = beanAnnotation.scope();
        detail.beanClazz = clazz;
        detail.beanClazzName = clazz.getCanonicalName();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
//                f.setAccessible(true);
                AutoInject autoInject = f.getAnnotation(AutoInject.class);
                if (autoInject != null) {
                    detail.fieldsToInject.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        details.put(clazz.getCanonicalName(), detail);
    }

    private Object createBean(BeanDetail detail) {
        Class<?> clazz = detail.beanClazz;
        try {
            Object instance = clazz.newInstance();
            for (Field field : detail.fieldsToInject) {
                field.setAccessible(true);
                Class<?> clazzToInject = field.getType();
                field.set(instance, getBean(clazzToInject));
            }
            if (detail.beanScope == BeanScope.SINGLETON) {
                beans.put(clazz.getCanonicalName(), instance);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WinterContext(Class<?> clazz) {
        this(clazz.getPackage().getName());
    }

    public WinterContext(String pkgName) {
        Set<Class<?>> classes = PackageScanner.getClasses(pkgName);
        classes.stream()
                .filter(clz -> clz.getAnnotation(Bean.class) != null)
                .forEach(this::registerBean);
    }

    public WinterContext() {
    }
}
