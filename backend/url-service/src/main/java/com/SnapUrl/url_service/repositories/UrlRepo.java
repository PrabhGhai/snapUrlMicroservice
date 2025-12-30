package com.SnapUrl.url_service.repositories;

import com.SnapUrl.url_service.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepo extends JpaRepository<UrlEntity,Long> {
    Optional<UrlEntity>findByShortUrl(String shortUrl);
    Optional<UrlEntity>findByUserIdAndLongUrl(UUID id , String long_url);
    List<UrlEntity> findAllByUserIdOrderByCreatedAtDesc(UUID uuid);
    int deleteByShortUrl(String shortUrl);
}
