package com.project.urlshortner.repositories;

import com.project.urlshortner.models.UrlMapping;
import com.project.urlshortner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping> findByUser(User user);
}
