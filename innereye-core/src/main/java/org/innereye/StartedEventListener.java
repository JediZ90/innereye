package org.innereye;

import org.innereye.core.IEDataContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (IEDataContext.getContext() == null) {
            IEDataContext.setApplicationContext(event.getApplicationContext());
        }
        // TODO 加载系统全局配置
    }
}
