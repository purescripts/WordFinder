package com.divvision33.wordfinder.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Letters available for constructing dictionary words")
public class WordFinderModel {

    @Schema(description = "Alphabetic letters that may each be used once", example = "triangle", requiredMode = Schema.RequiredMode.REQUIRED)
    String letters;

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
    
}
