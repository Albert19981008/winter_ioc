package test2;

import core.WinterContext;

/**
 * @author lixinyang
 * 2020/8/17
 */
public class Main {

    public static void main(String[] args) {
        WinterContext context = new WinterContext(Scanner.class);
        AA a = context.getBean(AA.class);
        AB b = context.getBean(AB.class);
    }
}
