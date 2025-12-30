package com.SnapUrl.url_service.controllers;

import com.SnapUrl.url_service.dtos.CreateUrlReq;
import com.SnapUrl.url_service.dtos.GetUrlRes;
import com.SnapUrl.url_service.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/create-url")
    public ResponseEntity<?> createUrl(@RequestHeader("X-User-Id") String userId , @RequestBody CreateUrlReq urlReq)
    {

        String url =  urlService.createUrlService(userId,urlReq.getLong_url());
        String shortUrl = "http://localhost:8080/";
        Map<String , Object> res = new HashMap<>();
        res.put("success", "Your snap url is ready");
        res.put("short_url", shortUrl+url);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/getProfileUrls")
    public ResponseEntity<?> getProfileUrls(@RequestHeader("X-User-Id") String userId)
    {
        List<GetUrlRes> data = urlService.getAllUrlsOfProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping("/delShortUrl/{shortUrl}")
    public ResponseEntity<?> delUrl(@PathVariable() String shortUrl)
    {
         urlService.deleteUrl(shortUrl);
         return ResponseEntity.status(HttpStatus.OK).build();
    }

}
