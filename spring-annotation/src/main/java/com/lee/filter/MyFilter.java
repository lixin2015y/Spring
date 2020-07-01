package com.lee.filter;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyFilter implements TypeFilter {

    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        final String className = metadataReader.getClassMetadata().getClassName();
        System.out.println("------<<<<" + className);
        if (className.contains("Dao")) {
            return true;
        }
        return false;
    }
}
