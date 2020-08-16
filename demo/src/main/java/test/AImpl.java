package test;

import common.AutoInject;
import common.Bean;

/**
 * @author lixinyang
 * 2020/8/16
 */
@Bean
public class AImpl implements IA {

    @AutoInject
    private B b;

    public void show() {
        System.out.println(b.getS());
    }
}
