package com.example.codegen.processor;

import java.io.PrintWriter;
import java.util.TreeSet;

public class Imports {
    private TreeSet<String> packages = new TreeSet<>();

    public void generate(PrintWriter pw) {
        packages.forEach(p -> pw.println("import " + p + ";"));
    }

    public void add(String packageQualifiedName) {
        packages.add(packageQualifiedName);
    }
}
