package com.divvision33.wordfinder.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DictionaryLoaderService {

    @Value("classpath:enable1.txt")
    private Resource resource;

    private Set<String> dataSet = Collections.emptySet();

    @EventListener(ApplicationReadyEvent.class)
    public void loadFileOnStartup() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            
            this.dataSet = reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toUnmodifiableSet());
            
        } catch (Exception e) {
            // Log the error or throw a runtime exception to stop startup if the file is critical
            throw new IllegalStateException("Failed to read startup file enable1.txt", e);
        }
    }

    public Set<String> getDictionary() {
        return this.dataSet;
    }
}
