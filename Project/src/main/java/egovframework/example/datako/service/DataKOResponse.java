package egovframework.example.datako.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DataKOResponse {
    @JsonProperty("COOKRCP01")
    private CookRcp cookRcp;
}
