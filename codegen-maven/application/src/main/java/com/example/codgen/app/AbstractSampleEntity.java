package com.example.codgen.app;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractSampleEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private OwnerEntity owner;

}
