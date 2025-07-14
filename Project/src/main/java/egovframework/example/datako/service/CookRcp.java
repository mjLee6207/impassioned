package egovframework.example.datako.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CookRcp {

    @JsonProperty("row")
    private List<DataKOVO> row;
}
