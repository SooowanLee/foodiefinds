package com.example.foodpick.web.naver;

import com.example.foodpick.web.naver.dto.SearchImageReq;
import com.example.foodpick.web.naver.dto.SearchImageRes;
import com.example.foodpick.web.naver.dto.SearchLocalReq;
import com.example.foodpick.web.naver.dto.SearchLocalRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverClient {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;

    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;

    public SearchLocalRes localSearch(SearchLocalReq searchLocalReq) {

        // uri 생성
        var uri = UriComponentsBuilder.fromUriString(naverLocalSearchUrl)
                .queryParams(searchLocalReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        // header를 정의
        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // header를 담은 entity
        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody();
    }

    public SearchImageRes searchImage(SearchImageReq searchImageReq) {
        URI uri = UriComponentsBuilder
                .fromUriString(naverImageSearchUrl)
                .queryParams(searchImageReq.toMultiValueMap())
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);

        HttpEntity httpEntity = new HttpEntity(headers);

        ParameterizedTypeReference<SearchImageRes> responseType = new ParameterizedTypeReference<>(){};

        ResponseEntity<SearchImageRes> response = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return response.getBody();
    }
}