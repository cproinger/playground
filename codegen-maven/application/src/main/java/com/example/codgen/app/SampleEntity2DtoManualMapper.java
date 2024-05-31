package com.example.codgen.app;

import java.util.List;

public class SampleEntity2DtoManualMapper implements GenericDTOMapper<SampleEntity2> {


    public GenericDTO toDto(SampleEntity2 e) {
        GenericDTO d = new GenericDTO();
        d.setId(e.getId());
        d.setValues(List.of(
                new GenericValueDTO()
                        .withName("data2")
                        .withStringValue(e.getData2()),
                new GenericValueDTO()
                        .withName("money2")
                        .withBigDecimalValue(e.getMoney2())
        ));
        return d;
    }
}
