package com.example.hrm_game.service.utils;

import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;

import static com.example.hrm_game.data.URLConst.HTTP;
@Service
public class HttpBuilder {
    private final HttpUrl.Builder builder = new HttpUrl.Builder();

    public HttpUrl.Builder createHttpHost(String host, Integer port){
        return builder
                .scheme(HTTP)
                .host(host)
                .port(port);
    }

    public HttpUrl.Builder addPathSegment(String path){
        return builder.addPathSegment(path);
    }
}
