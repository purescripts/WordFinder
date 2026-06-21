package com.divvision33.wordfinder.model;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A matching dictionary word and its letter score")
public record WordScoreResponse(
        @Schema(description = "Matching dictionary word", example = "triangle") String key,
        @Schema(description = "Calculated letter score", example = "9") int value) {

    public static WordScoreResponse from(Map.Entry<String, Integer> entry) {
        return new WordScoreResponse(entry.getKey(), entry.getValue());
    }
}
