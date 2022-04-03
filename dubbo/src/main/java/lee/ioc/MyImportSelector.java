package lee.ioc;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
            "lee.ioc.Car",
//            "lee.ioc.MyBeanDefinitionRegistryPostProcessor",
            "lee.ioc.MyBeanFactoryPostProcessor",
            "lee.ioc.MyBeanPostProcessor",
            "lee.ioc.MyImportSelector",
            "lee.ioc.MyInstantiationAwareBeanPostProcessor",
//            "lee.ioc.MyMergedBeanDefinitionPostProcessor",
            "lee.ioc.Person"
        };
    }
}
