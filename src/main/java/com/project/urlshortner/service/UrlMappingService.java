package com.project.urlshortner.service;

import com.project.urlshortner.dtos.ClickEventDTO;
import com.project.urlshortner.dtos.UrlMappingDTO;
import com.project.urlshortner.models.UrlMapping;
import com.project.urlshortner.models.User;
import com.project.urlshortner.repositories.ClickEventRepository;
import com.project.urlshortner.repositories.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private UrlMappingRepository urlMappingRepository;
    private ClickEventRepository clickEventRepository;

    public UrlMappingDTO shortenUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);

        return changeToUrlMappingDTOObject(savedUrlMapping);
    }

    private UrlMappingDTO changeToUrlMappingDTOObject(UrlMapping urlMapping) {
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedAt(urlMapping.getCreatedAt());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());

        return urlMappingDTO;
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
//        check against the database if this unique short link already exists or not
        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {

        return urlMappingRepository.findByUser(user).stream()
                .map(this:: changeToUrlMappingDTOObject)
                .toList();
    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping=  urlMappingRepository.findByShortUrl(shortUrl);

        if(urlMapping!=null){
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry ->{
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setCreatedDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }
}
