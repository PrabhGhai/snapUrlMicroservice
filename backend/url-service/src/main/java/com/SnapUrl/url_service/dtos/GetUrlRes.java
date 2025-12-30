package com.SnapUrl.url_service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetUrlRes {

    private String long_url;
    private String short_url;
    private String time;
}
