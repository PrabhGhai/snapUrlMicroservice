package com.SnapUrl.url_service.repositories;

import com.SnapUrl.url_service.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<UrlEntity,Long> {
    Optional<UrlEntity>findByShortUrl(String shortUrl);
}
