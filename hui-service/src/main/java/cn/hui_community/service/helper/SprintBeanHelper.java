package cn.hui_community.service.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintBeanHelper implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SprintBeanHelper.applicationContext = applicationContext;
    }

    private static ApplicationContext applicationContext;

    public static <T> T getBean(String beanName) {
        if (applicationContext.containsBean(beanName)) {
            return (T) applicationContext.getBean(beanName);
        } else {
            return null;
        }
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }


}
