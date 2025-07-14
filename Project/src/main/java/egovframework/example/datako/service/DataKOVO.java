package egovframework.example.datako.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataKOVO {

	@JsonProperty("RCP_SEQ") 				private String recipeId;
	@JsonProperty("RCP_NM") 				private String title;
	@JsonProperty("RCP_PARTS_DTLS") private String ingredient;
	@JsonProperty("ATT_FILE_NO_MK") private String thumbnail;
	private String category;
	private String instruction;
	private String images;
}
