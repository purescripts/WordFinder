package com.divvision33.wordfinder.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "word")
public class WordValueConfig {

    Map<String,Integer> letterMap;

    public Map<String, Integer> getLetterMap() {
        return letterMap;
    }

    public void setLetterMap(Map<String, Integer> letterMap) {
        this.letterMap = letterMap;
    }
    
}
