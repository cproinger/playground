package com.example.codgen.app;


import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Arrays;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

@SpringBootApplication

public class CodegenUsage implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CodegenUsage.class);

    public void doSomething() {
        printInfo(SampleEntity1_.class);

        Reflections reflections = new Reflections(getClass().getPackage().getName());
        Set<Class<?>> metamodelClasses = reflections.get(SubTypes.of(TypesAnnotated.with(StaticMetamodel.class)).asClass());
        metamodelClasses.forEach(CodegenUsage::printInfo);
    }

    private static void printInfo(Class<?> clazz) {
        // hibernate6 has a class_ field which provides other info, but for now ok
        var nonStringFields = Arrays.stream(clazz.getFields()).filter(f -> !f.getType().equals(String.class)).toList();
        LOG.info("{} -> {}", clazz.getAnnotation(StaticMetamodel.class).value(),
                nonStringFields.stream().map(f -> {
                    try {
                        Object value = f.get(null);
                        if (value instanceof SingularAttribute sa) {
                            LOG.info("singularAttribute {}: {}", sa.getName(), sa.getType().getJavaType());
                        } else if (value instanceof PluralAttribute pa) {
                            LOG.info("pluralrAttribute {}: {}", pa.getName(), pa.getElementType());
                        }
                        return new FieldInfo(f.getName(), value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).toList());
    }

    record FieldInfo(String name, Object value) {}

    public static void main(String[] args) {
        SpringApplication.run(CodegenUsage.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        doSomething();
    }
}
