package test;

import core.BeanFactory;
import core.WinterContext;

/**
 * @author lixinyang
 * 2020/8/16
 */
public class Main {

    public static void main(String[] args) {
        BeanFactory factory = new WinterContext(Config.class);

        C c = factory.getBean(C.class);
        c.ia.show();
        c.getK().uu();
    }
}
