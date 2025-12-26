package com.SnapUrl.url_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urls", indexes = {
        @Index(name = "idx_short_url", columnList = "shortUrl"),
        @Index(name = "idx_user_id", columnList = "userId")
})
@Getter
@Setter
public class UrlEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id; // Snowflake ID, generated externally

    @Column(name = "longUrl", nullable = false, columnDefinition = "TEXT") // to handle data integrity exception by default VARCHAR 255
    private String longUrl;

    @Column(name = "shortUrl", nullable = false, unique = true, columnDefinition = "TEXT")
    private String shortUrl;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
