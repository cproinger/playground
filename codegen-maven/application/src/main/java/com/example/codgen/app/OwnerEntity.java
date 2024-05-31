package com.example.codgen.app;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class OwnerEntity {
    @Id
    private Long id;

    private String name;
}
