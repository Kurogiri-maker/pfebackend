package com.example.TalanCDZ.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AdditionalAttributesDTO {
    private String field;
    private String header;
}
