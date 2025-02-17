package com.project.urlshortner.dtos;

import com.project.urlshortner.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDTO {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount;
    private LocalDateTime createdAt;
    private String username;
}
