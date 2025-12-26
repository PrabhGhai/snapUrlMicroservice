package com.SnapUrl.url_service.services;

import com.SnapUrl.url_service.entities.UrlEntity;
import com.SnapUrl.url_service.exceptions.UrlInactiveException;
import com.SnapUrl.url_service.exceptions.UrlNotFoundException;
import com.SnapUrl.url_service.repositories.UrlRepo;
import com.SnapUrl.url_service.utils.Base62Encoder;
import com.SnapUrl.url_service.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo urlRepo;
    SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1);

    public String createUrlService(String long_url)
    {
        long snowflakeId = idGenerator.nextId();
        String shortUrl = Base62Encoder.encode(snowflakeId);

        //Removing uniqueness check bcz snowflake guarantees to have always generate unique id
        UrlEntity saveUrl = new UrlEntity();
        saveUrl.setId(snowflakeId);
        saveUrl.setLongUrl(long_url);
        saveUrl.setShortUrl(shortUrl);
        UUID userId = UUID.fromString("375a904c-5565-406d-9b86-7e6917e7099f");
        saveUrl.setUserId(userId);
        saveUrl.setActive(true);
        urlRepo.save(saveUrl);
        return shortUrl ;
    }

    public String getLongUrl(String shortUrl) {

        UrlEntity urlEntity = urlRepo.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL does not exist"));

        if (!urlEntity.getActive()) {
            throw new UrlInactiveException("Short URL is inactive");
        }

        return urlEntity.getLongUrl();
    }
}
