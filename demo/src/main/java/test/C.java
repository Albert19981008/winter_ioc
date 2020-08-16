package test;

import common.AutoInject;
import common.Bean;
import test.otherpkg.IK;

/**
 * @author lixinyang
 * 2020/8/16
 */
@Bean
public class C {

    @AutoInject
    public IA ia;

    @AutoInject
    private IK k;

    public IK getK() {
        return k;
    }

    public void setK(IK k) {
        this.k = k;
    }


}
