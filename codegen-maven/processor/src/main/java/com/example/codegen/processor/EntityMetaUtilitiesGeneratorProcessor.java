package com.example.codegen.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.util.ElementFilter;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

@SupportedAnnotationTypes({"javax.persistence.metamodel.StaticMetamodel"
//        "javax.persistence.Entity", "javax.persistence.MappedSuperclass", "javax.persistence.Embeddable"
})
@SupportedOptions({
//        "debug",
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class EntityMetaUtilitiesGeneratorProcessor extends javax.annotation.processing.AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        processingEnv.getMessager()
                .printMessage(Diagnostic.Kind.NOTE, getClass().getSimpleName() + " initializing");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.errorRaised() || roundEnv.processingOver() || annotations.isEmpty()) {
            return false;
        }
        processingEnv.getMessager()
                .printMessage(Diagnostic.Kind.NOTE, getClass().getSimpleName() + " processing ...");

        for (var anno : annotations) {
            for (Element tated : roundEnv.getElementsAnnotatedWith(anno)) {
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.NOTE,
                                getClass().getSimpleName() + " processing " + tated);

                if (tated.getKind().isClass()) {
                    TypeElement typeElement = (TypeElement) tated;
                    try {
//                        typeElement.asType().accept(new TypeVisitor<Object, Object>() {
//                        })
                        String genTypeSimpleName = typeElement.getSimpleName().toString() + "MM";
                        String packageName = extractPackageName(typeElement);
                        String genTypeFqn = packageName + "." + genTypeSimpleName;
                        var javaFile = processingEnv.getFiler().createSourceFile(genTypeFqn);
                        try (var writer = javaFile.openWriter(); var pw = new PrintWriter(writer)) {
                            pw.println("package %s;".formatted(packageName));
                            pw.println();

                            var imports = new Imports();
                            imports.add("javax.annotation.processing.Generated");
                            imports.add("java.util.function.Function");
                            imports.add(MMGetter.class.getName());

                            imports.generate(pw);
                            pw.println();

                            pw.println("@Generated(value = \"%s\")".formatted(
                                    EntityMetaUtilitiesGeneratorProcessor.class.getName()));
                            pw.println("public class %s {".formatted(genTypeSimpleName));
                            pw.println();

                            var attributes = ElementFilter.fieldsIn(typeElement.getEnclosedElements())
                                    .stream()
                                    .filter(e -> e.asType()
                                            .toString()
                                            .startsWith("javax.persistence.metamodel."))
//                                    .map(e -> StaticMetamodelAttribute.of(context, e))
                                    .filter(e -> !e.getSimpleName().toString().equals("id"))
                                    .sorted((e1, e2) -> e1.getSimpleName().toString().compareTo(e2.getSimpleName().toString()))
                                    .toList();
                            pw.println("// " + attributes + ", " + attributes.stream().map(e -> e.asType()).toList());
                            for (VariableElement attr : attributes) {
                                TypeMirror attrType = attr.asType();
                                DeclaredType declaredType = (DeclaredType) attrType;
//                                processingEnv.getTypeUtils().getDeclaredType(attr., attrType);
                                processingEnv.getMessager()
                                        .printMessage(Diagnostic.Kind.NOTE,
                                                getClass().getSimpleName() + " processing " + tated + ": " + declaredType);

                                if (!declaredType.toString().substring(0, declaredType.toString().indexOf('<')).equals(SingularAttribute.class.getName())) {
                                    continue;
                                }

                                pw.println(("""
                                            public static final MMGetter<%1$s, %2$s> GET_%3$s
                                                = new MMGetter<>(\"%3$s\", %1$s.class, %2$s.class , %1$s::get%5$s);
                                        """).formatted(
                                        declaredType.getTypeArguments().get(0),
                                        declaredType.getTypeArguments().get(1),
                                        attr.getSimpleName().toString(),
//                                        attr.getSimpleName().toString()
                                        declaredType.getTypeArguments().get(1),
                                        Character.toUpperCase(attr.getSimpleName().toString().charAt(0)) + attr.getSimpleName().toString().substring(1)
                                ));
                            }

                            pw.println();
                            pw.println("}");
                            // needed?
                            pw.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return false;
    }

    private String extractPackageName(TypeElement typeElement) {
        String packageName = typeElement.getQualifiedName().toString();
        return packageName.substring(0, packageName.lastIndexOf("."));
    }
}
