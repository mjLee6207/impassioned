package egovframework.example.datako.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataKOVO {

	@JsonProperty("RCP_SEQ") 				private String recipeId;
	@JsonProperty("RCP_NM") 				private String titleKr;
	@JsonProperty("RCP_PARTS_DTLS") private String ingredientKr;
	@JsonProperty("ATT_FILE_NO_MK") private String thumbnail;
	
	private String categoryKr;
	private String instructionKr;
//	private String images;
}
