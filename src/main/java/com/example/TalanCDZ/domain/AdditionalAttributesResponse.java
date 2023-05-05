package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalAttributesResponse {
    private String type;
    private String numero;
    private String attributeName;
    private String attributeValue;
}
