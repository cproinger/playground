package com.example.codgen.app;

import java.util.List;

public class SampleEntity1DtoManualMapper implements GenericDTOMapper<SampleEntity1>{

    @Override
    public GenericDTO toDto(SampleEntity1 e) {
        GenericDTO d = new GenericDTO();
        d.setId(e.getId());
        d.setValues(List.of(
            new GenericValueDTO()
                    .withName("data1")
                    .withStringValue(e.getData1()),
            new GenericValueDTO()
                    .withName("money1")
                    .withBigDecimalValue(e.getMoney1())
        ));
        return d;
    }
}
