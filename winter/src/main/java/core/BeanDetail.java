package core;

import common.BeanScope;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lixinyang
 * 2020/8/16
 */
public class BeanDetail {

    public String beanClazzName = "";
    public BeanScope beanScope;
    public Class<?> beanClazz;
    public List<Field> fieldsToInject = new ArrayList<>();
}
