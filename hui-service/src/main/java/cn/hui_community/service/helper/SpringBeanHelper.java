package cn.hui_community.service.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringBeanHelper implements BeanFactoryPostProcessor, ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanHelper.applicationContext = applicationContext;
    }

    public static ListableBeanFactory getBeanFactory() {
        ListableBeanFactory factory = null == beanFactory ? applicationContext : beanFactory;
        if (null == factory) {
            throw new IllegalArgumentException("No ConfigurableListableBeanFactory or ApplicationContext injected, maybe not in the Spring environment?");
        } else {
            return factory;
        }
    }


    public static <T> T getBean(String beanName) {
        return (T) getBeanFactory().getBean(beanName);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {

        return getBeanFactory().getBean(clazz);
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringBeanHelper.beanFactory = beanFactory;
    }
}