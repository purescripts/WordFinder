package com.divvision33.wordfinder.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.divvision33.wordfinder.model.WordFinderModel;
import com.divvision33.wordfinder.model.WordScoreResponse;
import com.divvision33.wordfinder.services.WordFinder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/wordfinder")
@Tag(name = "Word Finder", description = "Find and score dictionary words from a supplied set of letters")
public class WordFinderController {
    @Autowired
    WordFinder wordFinder;

    @PostMapping("letters")
    @Operation(
            summary = "Find words from a set of letters",
            description = "Returns dictionary words that can be constructed without using any supplied letter more than once. Results are ordered by descending score.")
    @ApiResponse(
            responseCode = "202",
            description = "Matching words and scores",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = WordScoreResponse.class)),
                    examples = @ExampleObject(value = "[{\"key\":\"triangle\",\"value\":9},{\"key\":\"altering\",\"value\":9}]")))
    public ResponseEntity<ArrayList<WordScoreResponse>> getPossibleWords(@RequestBody WordFinderModel word) {
        ArrayList<WordScoreResponse> scores = wordFinder.getWordAndScore(word.getLetters()).stream()
                .map(WordScoreResponse::from)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new ResponseEntity<>(scores, HttpStatus.ACCEPTED);
    }

}
