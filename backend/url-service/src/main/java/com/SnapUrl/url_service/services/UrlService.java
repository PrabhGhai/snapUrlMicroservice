package com.SnapUrl.url_service.services;

import com.SnapUrl.url_service.dtos.GetUrlRes;
import com.SnapUrl.url_service.entities.UrlEntity;
import com.SnapUrl.url_service.exceptions.NoSuchUrlFound;
import com.SnapUrl.url_service.exceptions.UrlAlreadyExistException;
import com.SnapUrl.url_service.exceptions.UrlInactiveException;
import com.SnapUrl.url_service.exceptions.UrlNotFoundException;
import com.SnapUrl.url_service.repositories.UrlRepo;
import com.SnapUrl.url_service.utils.Base62Encoder;
import com.SnapUrl.url_service.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo urlRepo;
    SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1);

    //create short url
    public String createUrlService(String user,String long_url)
    {

        UUID userId = UUID.fromString(user);

        // This is where idempotency is implemented:
        // Same input (user + long URL) will always return the same short URL - 26-12-2025
//        Optional<UrlEntity> existing = urlRepo.findByUserIdAndLongUrl(userId, long_url);
//        if (existing.isPresent()) {
//            return existing.get().getShortUrl(); // Return existing short URL
//        }
        long snowflakeId = idGenerator.nextId();
        String shortUrl = Base62Encoder.encode(snowflakeId);

        //Removing uniqueness check bcz snowflake guarantees to have always generate unique id
       try{
           UrlEntity saveUrl = new UrlEntity();
           saveUrl.setId(snowflakeId);
           saveUrl.setLongUrl(long_url);
           saveUrl.setShortUrl(shortUrl);
           saveUrl.setUserId(userId);
           saveUrl.setActive(true);
           urlRepo.save(saveUrl);
           return shortUrl ;
       }catch (Exception e)
       {
           throw new UrlAlreadyExistException("Short url already exists");
       }
    }

    public String getLongUrl(String shortUrl) {

        UrlEntity urlEntity = urlRepo.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL does not exist"));

        if (!urlEntity.getActive()) {
            throw new UrlInactiveException("Short URL is inactive");
        }

        return urlEntity.getLongUrl();
    }

    public List<GetUrlRes> getAllUrlsOfProfile(String userId) {
        List<UrlEntity> res = urlRepo.findAllByUserIdOrderByCreatedAtDesc(UUID.fromString(userId));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        List<GetUrlRes> data = new ArrayList<>();

        for (UrlEntity a : res) {
            GetUrlRes getUrlRes = new GetUrlRes();

            getUrlRes.setShort_url("localhost:8080/" + a.getShortUrl());
            getUrlRes.setLong_url(a.getLongUrl());
            getUrlRes.setTime(a.getCreatedAt().format(formatter));

            data.add(getUrlRes);
        }
        return data;
    }

    @Modifying
    @Transactional
    public void deleteUrl(String shortUrl) {
        int deleted = urlRepo.deleteByShortUrl(shortUrl);
        System.out.println(deleted);

        if (deleted == 0) {
            throw new NoSuchUrlFound("No such url found");
        }
    }


}
