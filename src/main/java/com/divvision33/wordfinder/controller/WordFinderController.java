package com.divvision33.wordfinder.controller;

import java.util.ArrayList;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.divvision33.wordfinder.model.WordFinderModel;
import com.divvision33.wordfinder.services.WordFinder;

@RestController
@RequestMapping("/api/v1/wordfinder")
public class WordFinderController {
    @Autowired
    WordFinder wordFinder;

    @PostMapping("letters")
    public ResponseEntity<ArrayList<Entry<String, Integer>>> getPossibleWords(@RequestBody WordFinderModel word) {
        ArrayList<Entry<String, Integer>> scores = wordFinder.getWordAndScore(word.getLetters());
        return new ResponseEntity<>(scores, HttpStatus.ACCEPTED);
    }

}