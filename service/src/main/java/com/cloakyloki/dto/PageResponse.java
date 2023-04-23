package com.cloakyloki.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class PageResponse<T> {

    List<T> content;
    Metadata metadata;


    public static <T> PageResponse<T> of(Page<T> page) {
        List<Integer> pageNumbers;
        int PAGE_SIZE = 20;
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        } else {
            pageNumbers = Collections.emptyList();
        }
        var metadata = new Metadata(page.getNumber(), PAGE_SIZE, page.getTotalPages(), pageNumbers);
        return new PageResponse<>(page.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalPages;
        List<Integer> pageNumbers;
    }
}
