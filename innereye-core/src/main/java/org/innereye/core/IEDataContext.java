package org.innereye.core;

import org.springframework.context.ApplicationContext;

public class IEDataContext {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
