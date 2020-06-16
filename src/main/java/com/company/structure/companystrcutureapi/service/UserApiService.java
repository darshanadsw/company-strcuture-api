package com.company.structure.companystrcutureapi.service;

import com.company.structure.companystrcutureapi.command.mappings.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UserApiService {

    @Value("${apifaketory.url}")
    private String url;

    private final RestTemplate restTemplate;

    public UserApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserData getUserData(){
         UriComponents uri = UriComponentsBuilder.fromUriString(url)
                 .path("/api/user")
                 .build();
        return restTemplate.getForObject(uri.toUriString(),UserData.class);
    }
}
