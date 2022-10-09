package com.yangdongjue5510.httpclientstudy;

import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.time.Duration;
import java.util.concurrent.Executors;

public class HttpClientSupport {

    // HttpClient는 immutable 객체이므로 멀티 스레드 환경에서도 thread-safe하다.
    public final HttpClient httpClient = HttpClient.newBuilder()
            // 프로토콜 버전을 정한다. 2.0 버전을 지원하지 않으면 1.1 버전으로 재요청한다.
            .version(Version.HTTP_2)
            // 만약 외부 API에서 리다이렉트를 시키려고 할 경우 대응 정책을 정한다.
            .followRedirects(Redirect.NORMAL)
            // 외부 API에서 쿠키를 저장하려고 할 경우 어떻게 저장할 지 정하는 핸들러를 정한다.
            .cookieHandler(CookieHandler.getDefault())
            // 비동기 방식을 사용할 경우 사용되는 스레드를 정한다.
            .executor(Executors.newFixedThreadPool(2))
            // 인증이 필요한 자원에 접근할 때 인증 정보를 Authenticator에 설정할 수 있다.
            .authenticator(Authenticator.getDefault())
            // 요청 처리 시간에 대한 timeout을 설정할 수 있다.
            .connectTimeout(Duration.ofSeconds(10))
            // 프록시에 대한 설정을 할 수 있다.
            .proxy(ProxySelector.getDefault())
            // http 2.0에서만 적용되는 priority를 설정할 수 있다.
            .priority(1)
            .build();

    // 설정을 기본값으로 한 HttpClient를 쉽게 만들 수 있다.
    public final HttpClient defaultClient = HttpClient.newHttpClient();
}
