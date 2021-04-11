package uk.co.ractf.yakrazor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    protected ApplicationContextProvider() {
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        ApplicationContextProvider.context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.context;
    }

}
