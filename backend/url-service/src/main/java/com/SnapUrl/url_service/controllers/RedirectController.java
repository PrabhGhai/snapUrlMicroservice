package com.SnapUrl.url_service.controllers;

import com.SnapUrl.url_service.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {
    private final UrlService urlService;

    @GetMapping("/{id}")
    public ResponseEntity<Void> getRedirectionUrl(@PathVariable String id) {

        String longUrl = urlService.getLongUrl(id);

        return ResponseEntity
                .status(HttpStatus.FOUND) // 302
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .location(URI.create(longUrl))
                .build();
    }

}
