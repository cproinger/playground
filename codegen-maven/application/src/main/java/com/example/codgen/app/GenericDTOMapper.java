package com.example.codgen.app;

public interface GenericDTOMapper<T> {
    GenericDTO toDto(T e);
}
