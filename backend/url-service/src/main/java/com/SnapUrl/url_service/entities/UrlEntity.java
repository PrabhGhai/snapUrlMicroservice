package com.SnapUrl.url_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "urls",
        indexes = {
                @Index(name = "idx_short_url", columnList = "shortUrl"),
                @Index(name = "idx_user_id", columnList = "userId")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_longurl",
                        columnNames = {"userId", "longUrl"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private Long id; // Snowflake ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String shortUrl;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
