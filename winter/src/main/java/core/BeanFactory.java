package core;


public interface BeanFactory {

    Object getBean(String clzName);

    <T> T getBean(Class<T> clazz);

    void registerBean(Class<?> clazz);
}
