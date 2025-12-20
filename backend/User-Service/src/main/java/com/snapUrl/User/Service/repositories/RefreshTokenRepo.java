package com.snapUrl.User.Service.repositories;

import com.snapUrl.User.Service.entities.RefreshToken;
import com.snapUrl.User.Service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, UUID> {

    @Modifying
    @Query("""
            UPDATE RefreshToken r
            SET r.revoked = true
            WHERE r.user = :user AND r.revoked = false
            """)
    void revokeAllActiveByUser(@Param("user") UserEntity user);

    Optional<RefreshToken> findByTokenHashAndExpiresAtAfterAndRevokedFalse(String token, LocalDateTime now);

    @Modifying
    @Query("""
            UPDATE RefreshToken r SET r.revoked = true WHERE r.tokenHash = :tokenHash
            """)
    void revokeSingleUserToken(@Param("tokenHash") String tokenHash);
}
