package com.SnapUrl.url_service.controllers;

import com.SnapUrl.url_service.dtos.CreateUrlReq;
import com.SnapUrl.url_service.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/create-url")
    public ResponseEntity<?> createUrl(@RequestBody CreateUrlReq urlReq)
    {
        String url =  urlService.createUrlService(urlReq.getLong_url());
        String shortUrl = "http://localhost:8082/";
        Map<String , Object> res = new HashMap<>();
        res.put("success", "Your snap url is ready");
        res.put("short-url", shortUrl+url);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


}
