package com.wei.demo.rocketmq.integrated.event;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface EventHandler {

    String[] tables();
}
