package com.yangdongjue5510.httpclientstudy.async;

import static com.yangdongjue5510.httpclientstudy.HttpClientSupport.httpClient;
import static com.yangdongjue5510.httpclientstudy.RequestTest.EXTERNAL_API_ORIGIN;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 테스트를 실행하기 전에 main 패키지의 main 메서드를 실행시켜야 한다.
 */
class AsyncRequestTest {

    private final Logger logger = LoggerFactory.getLogger(AsyncRequestTest.class);

    @Test
    @DisplayName("비동기 방식으로 GET 요청을 여러개 보내서 로그를 확인해본다.")
    void asyncGetRequest() throws InterruptedException {
        // given
        // URI 리스트를 만들고 요청 빌더를 만들어둔다.
        final List<URI> requestURIs = createURIS(10);
        final Builder requestBuilder = HttpRequest.newBuilder().GET();

        // when, then
        // URI 루프를 돌면서 비동기 요청을 보내고, 요청에 대한 응답이 오면 로그를 찍도록 한다.
        requestURIs.forEach(uri ->
                httpClient.sendAsync(requestBuilder.uri(uri).build(), BodyHandlers.ofString())
                        .thenAccept(response -> logger.info("비동기 응답 완료!!!!!! {}", response.statusCode()))
        );
        // 테스트 코드가 더 진행되는지 확인하는 로그를 찍는다.
        logger.info("코드 실행 완료");
        // 비동기 요청이 모두 종료될 때까지 기다린다.
        Thread.sleep(5000);
    }

    private List<URI> createURIS(final int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(id -> URI.create(EXTERNAL_API_ORIGIN + "/slow-task?id=" + id))
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("동기 방식과 비동기 방식의 차이를 체감해보자")
    void compareSyncAsync() throws InterruptedException {
        // given
        // 동기 방식은 스레드가 10개인 스레드풀을 통해 요청을 날리고,
        // 비동기 방식은 스레드가 2개인 스레드풀을 통해 요청을 날린다.
        // 총 100개의 URI를 만들어서 처리할 수 있도록 한다.
        final ExecutorService executorService = Executors.newFixedThreadPool(50);
        final List<URI> uris = createURIS(100);

        // when, then
        // 테스트를 실행해보고 동기 요청과 비동기 요청의 로그가 얼마나 생겼는지 살펴보면서 차이를 체감해보자.
        // executorService의 스레드 갯수를 바꾸면서 차이를 살펴보자.
        uris.forEach(uri -> runSync(executorService, uri));
        uris.forEach(this::runAsync);
        Thread.sleep(5000);
    }

    private void runSync(final ExecutorService executorService, final URI uri) {
        executorService.execute(() -> requestSync(uri));
    }

    private void requestSync(final URI uri) {
        try {
            httpClient.send(HttpRequest.newBuilder(uri).GET().build(), BodyHandlers.ofString());
            logger.info("[SYNC ID = {}] DONE", uri.getQuery());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void runAsync(final URI uri) {
        httpClient.sendAsync(HttpRequest.newBuilder(uri).GET().build(), BodyHandlers.ofString())
                .thenAccept(response -> logger.info("[ASYNC ID = {}] DONE", uri.getQuery()));
    }
}
