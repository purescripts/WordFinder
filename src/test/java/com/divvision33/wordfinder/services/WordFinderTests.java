package com.divvision33.wordfinder.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divvision33.wordfinder.config.WordValueConfig;

class WordFinderTests {

    private WordFinder wordFinder;

    @BeforeEach
    void setUp() {
        WordValueConfig wordConfig = new WordValueConfig();
        wordConfig.setLetterMap(Map.of("a", 1, "c", 3, "t", 1));

        DictionaryLoaderService dictionaryService = mock(DictionaryLoaderService.class);
        when(dictionaryService.getDictionary()).thenReturn(Set.of("a", "at", "cat", "tact", "attack"));

        wordFinder = new WordFinder();
        wordFinder.wordConfig = wordConfig;
        wordFinder.dictionaryService = dictionaryService;
    }

    @Test
    void findsWordsWithoutGeneratingPermutations() {
        assertThat(wordFinder.getWordAndScore("TACT"))
                .extracting(Map.Entry::getKey)
                .containsExactly("tact", "cat", "at");
    }

    @Test
    void respectsTheNumberOfTimesEachLetterIsAvailable() {
        assertThat(wordFinder.getWordAndScore("tac"))
                .extracting(Map.Entry::getKey)
                .containsExactly("cat", "at");
    }

    @Test
    void safelyHandlesInvalidInput() {
        assertThat(wordFinder.getWordAndScore(null)).isEmpty();
        assertThat(wordFinder.getWordAndScore("cat!")).isEmpty();
    }
}
