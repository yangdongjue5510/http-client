package com.yangdongjue5510.httpclientstudy.sync;

import static com.yangdongjue5510.httpclientstudy.HttpClientSupport.httpClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangdongjue5510.httpclientstudy.dto.RequestDto;
import com.yangdongjue5510.httpclientstudy.dto.ResponseDto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * 테스트를 실행하기 전에 main 패키지의 main 메서드를 실행시켜야 한다.
 */
class SyncRequestTest {

    private final String externalApiOrigin = "http://localhost:8080";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("동기 방식으로 GET 요청을 보낸다.")
    void requestGetSync() throws IOException, InterruptedException {
        // given
        // 예시 컨트롤러에서 요청이 갈 수 있도록 request를 만들어본다.
        final HttpRequest request = HttpRequest.newBuilder(URI.create(externalApiOrigin + "/sync"))
                .GET()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // when
        // HttpClient 객체를 통해 동기 방식으로 요청을 보낸다.
        // BodyHandlers는 Body 데이터를 어떤 형식으로 직렬화/역직렬화 할 지를 정한다.
        final HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(objectMapper.readValue(response.body(), ResponseDto.class))
                        .usingRecursiveComparison().isEqualTo(new ResponseDto("GET sync request success"))
        );
    }

    @Test
    @DisplayName("동기 방식으로 POST 요청을 보낸다.")
    void requestPostSync() throws IOException, InterruptedException {
        // given
        // 예시 컨트롤러에 POST 요청이 갈 수 있도록 HttpRequest를 만들어보자.
        // BodyPublishers는 RequestBody를 어떤 방식으로 직렬화 할 것인지 정의한다.
        final String requestDtoString = objectMapper.writeValueAsString(new RequestDto("Hello"));
        final HttpRequest request = HttpRequest.newBuilder(URI.create(externalApiOrigin + "/sync"))
                .POST(BodyPublishers.ofString(requestDtoString))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // when
        // httpClient 객체를 통해 요청을 보낼 수 있다.
        final HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.body()).isEqualTo(objectMapper.writeValueAsString(new ResponseDto("Hello success")))
        );
    }
}
