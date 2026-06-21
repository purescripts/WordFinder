package com.divvision33.wordfinder.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divvision33.wordfinder.config.WordValueConfig;

@Service
public class WordFinder {

    private static final int ALPHABET_SIZE = 26;
    private static final int MINIMUM_WORD_LENGTH = 2;

    @Autowired
    WordValueConfig wordConfig;

    @Autowired
    DictionaryLoaderService dictionaryService;

    /**
     * Finds every dictionary word that can be made from the supplied letters.
     *
     * <p>This searches the dictionary instead of generating every permutation of
     * the letters. Its work is proportional to the size of the dictionary rather
     * than factorial in the number of supplied letters.</p>
     *
     * @param letters letters available to build words (each letter can be used once)
     * @return matching words ordered by descending score, then alphabetically
     */
    public ArrayList<Map.Entry<String, Integer>> getWordAndScore(String letters) {
        ArrayList<Map.Entry<String, Integer>> wordsFound = new ArrayList<>();
        if (letters == null) {
            return wordsFound;
        }

        String normalizedLetters = letters.toLowerCase(Locale.ROOT);
        int[] availableLetters = countLetters(normalizedLetters);
        if (availableLetters == null) {
            return wordsFound;
        }

        int maximumWordLength = normalizedLetters.length();
        for (String word : dictionaryService.getDictionary()) {
            if (word.length() >= MINIMUM_WORD_LENGTH
                    && word.length() <= maximumWordLength
                    && canBuildWord(word, availableLetters)) {
                wordsFound.add(Map.entry(word, generatePoint(word)));
            }
        }

        wordsFound.sort(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey()));
        return wordsFound;
    }

    private boolean canBuildWord(String word, int[] availableLetters) {
        int[] remainingLetters = availableLetters.clone();
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (letter < 'a' || letter > 'z' || --remainingLetters[letter - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    private int[] countLetters(String letters) {
        int[] counts = new int[ALPHABET_SIZE];
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            if (letter < 'a' || letter > 'z') {
                return null;
            }
            counts[letter - 'a']++;
        }
        return counts;
    }

    private int generatePoint(String word) {
        int totalPoints = 0;
        Map<String, Integer> letterValues = wordConfig.getLetterMap();
        if (letterValues == null) {
            return totalPoints;
        }

        for (int i = 0; i < word.length(); i++) {
            totalPoints += letterValues.getOrDefault(Character.toString(word.charAt(i)), 0);
        }
        return totalPoints;
    }
}
