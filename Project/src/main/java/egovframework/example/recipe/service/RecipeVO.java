package egovframework.example.recipe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class RecipeVO {
	private String recipeId;
	private String titleKr;
	private String categoryKr;
	private String instructionKr;
	private String ingredientKr;
	private String measureKr;
	private String thumbnail;
//	7월21일 메인페이지 인기 레시피 조회를 위해 추가
	private Integer likeCount; 
	
	public List<String> getIngredientDisplayList() {
	    if (ingredientKr == null || ingredientKr.trim().isEmpty()) return Collections.emptyList();

	    String[] ingArr = ingredientKr.split(",");
	    String[] meaArr = (measureKr != null && !measureKr.trim().isEmpty())
	                      ? measureKr.split(",")
	                      : new String[0];

	    List<String> result = new ArrayList<>();

	    for (int i = 0; i < ingArr.length; i++) {
	        String ing = ingArr[i].trim();
	        String mea = (i < meaArr.length) ? meaArr[i].trim() : null;

	        if (mea != null && !mea.isEmpty()) {
	            result.add(ing + " (" + mea + ")");
	        } else {
	            result.add(ing);  // 계량 없으면 재료만 출력
	        }
	    }

	    return result;
	}
}
