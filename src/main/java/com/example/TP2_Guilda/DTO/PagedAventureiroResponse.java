package com.example.TP2_Guilda.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class PagedAventureiroResponse<T> {
    private final int page;
    private final int size;
    private final int total;
    private final int totalPages;
    private List<T> content;

    public PagedAventureiroResponse(List<T> content, int page, int size, int total) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = size <= 0 ? 0 : (int) Math.ceil( (double) total / size) ;
        this.content = content;
    }
}
