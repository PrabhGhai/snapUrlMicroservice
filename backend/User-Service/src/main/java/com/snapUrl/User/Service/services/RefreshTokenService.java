package com.snapUrl.User.Service.services;
import com.snapUrl.User.Service.entities.RefreshToken;
import com.snapUrl.User.Service.entities.UserEntity;
import com.snapUrl.User.Service.repositories.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    //db entry for refresh token
    @Transactional
    public void save(UserEntity user, String refreshToken) throws NoSuchAlgorithmException {
        refreshTokenRepo.revokeAllActiveByUser(user);
        RefreshToken refToken = new RefreshToken();
        refToken.setUser(user);
        /*Encoding token:
          Earlier we used password Encoder for encoding the token, However this encoding has limit that
            the data you want to encode should be of 72 characters so that's why we are going to use
             SHA 256 algo*/

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
        String tokenHash = Base64.getEncoder().encodeToString(hash);

        refToken.setTokenHash(tokenHash);
        refToken.setExpiresAt(LocalDateTime.now().plusDays(30));
        refToken.setRevoked(false);
        refToken.setCreatedAt(LocalDateTime.now());
        refreshTokenRepo.save(refToken);
    }

    public Optional<RefreshToken> findValidToken(String token) {
        return refreshTokenRepo.findByTokenHashAndExpiresAtAfterAndRevokedFalse(token, LocalDateTime.now());
    }

    @Transactional
    public void revokeByTokenHash(String tokenHash) {
        refreshTokenRepo.revokeSingleUserToken(tokenHash);
    }
}
