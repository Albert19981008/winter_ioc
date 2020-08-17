package test2;

import common.AutoInject;
import common.Bean;

/**
 * @author lixinyang
 * 2020/8/17
 */
@Bean
public class AB {

    @AutoInject
    private AA a;
}
