package com.example.TalanCDZ.domain;


import lombok.Data;


@Data

public class AdditionalAttributesRequest {
    private String type;
    private String attribute;

    @Override
    public String toString() {
        return String.format("{\"attribute\": \"%s\",\n\"type\": \"%s\"}", attribute, type);
    }
}
