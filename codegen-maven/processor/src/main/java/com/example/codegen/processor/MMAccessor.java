package com.example.codegen.processor;

import java.util.function.Function;

public record MMAccessor<O, R>(String name, Class<O> ownerType, Class<R> resultType, Function<O, R> getterFunc) {
}
