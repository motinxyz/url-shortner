package com.project.urlshortner.controllers;

import com.project.urlshortner.models.UrlMapping;
import com.project.urlshortner.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedirectController {

    private UrlMappingService urlMappingService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        UrlMapping urlMapping = urlMappingService.getOriginalurl(shortUrl);
        if(urlMapping != null){
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, urlMapping.getOriginalUrl());
            return ResponseEntity.status(302).headers(headers).build();
        }
        return ResponseEntity.notFound().build();
    }
}
