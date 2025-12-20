package com.snapUrl.User.Service.controllers;
import com.snapUrl.User.Service.dtos.AuthResponse;
import com.snapUrl.User.Service.dtos.LoginReq;
import com.snapUrl.User.Service.dtos.SignUpReq;
import com.snapUrl.User.Service.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReq signUpReq)
    {
        authService.signup(signUpReq);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req)
    {
       AuthResponse res = authService.login(req);
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", res.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(15 * 60)
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", res.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(30L*24*60*60)
                .build();

        Map<String,Object> body = new HashMap<>();
        body.put("success", true);
        body.put("username", res.getUsername());
        body.put("email", res.getEmail());
        body.put("role", res.getRole());
       return ResponseEntity.status(HttpStatus.OK)
               .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
               .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
               .body(body);

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshTokenCookie) {
        if (refreshTokenCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Refresh token is missing"));
        }
        try {
            AuthResponse res = authService.refreshAccessToken(refreshTokenCookie);

            // Set new access token cookie
            ResponseCookie accessCookie = ResponseCookie.from("accessToken", res.getAccessToken())
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(15 * 60)
                    .build();
            Map<String, Object> body = new HashMap<>();
            body.put("success", true);
            body.put("username", res.getUsername());
            body.put("email", res.getEmail());
            body.put("role", res.getRole());
            body.put("accessToken", res.getAccessToken());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .body(body);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshToken) {

        if (refreshToken != null) {
            authService.logout(refreshToken);
        }

        // delete cookies
        ResponseCookie deleteAccessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false) // true in prod
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie deleteRefreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshCookie.toString())
                .body(Map.of("success", true, "message", "Logged out successfully"));
    }

}

