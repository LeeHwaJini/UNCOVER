package com.shsoftnet.shcommon.rest;

import com.shsoftnet.shcommon.utils.string.JsonUtil;
import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CommonRestClient {

    private final static RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(3, TimeUnit.MINUTES) // Timeout.ofMinutes(3L)
            .setConnectionRequestTimeout(3, TimeUnit.MINUTES) // // Timeout.ofMinutes(3L)
            .setMaxRedirects(50) // 50
//            .setRedirectsEnabled(true)
            .setDefaultKeepAlive(5000, TimeUnit.MILLISECONDS)
            .build();

    private final static HttpRequestRetryStrategy DEFAULT_RETRY_STRATEGY
            = new DefaultHttpRequestRetryStrategy(1, TimeValue.ofSeconds(1L));

    private final static IOReactorConfig DEFAULT_IO_REACTOR_CONFIG = IOReactorConfig.custom()
            .setSoTimeout(Timeout.ofSeconds(5))
            .build();


    public static CloseableHttpAsyncClient CreateAsyncClient(RequestConfig requestConfig, HttpRequestRetryStrategy requestRetryStrategy, Header... httpHeaders) {
        return HttpAsyncClients.custom()
                .setConnectionManager(new PoolingAsyncClientConnectionManager())
                .setDefaultRequestConfig(requestConfig == null ? DEFAULT_REQUEST_CONFIG : requestConfig)
                .setRetryStrategy(requestRetryStrategy == null ? DEFAULT_RETRY_STRATEGY : requestRetryStrategy)
                .setIOReactorConfig(DEFAULT_IO_REACTOR_CONFIG)
                .addRequestInterceptorFirst((httpRequest, entityDetails, httpContext) -> {
                    // 요청시 부가 작업(헤더, 유저 에이 전트, 쿠키, 인증 등)
//                    httpRequest.setHeader(HttpHeaders.AUTHORIZATION, "AUTH_KEY_VALUE");
//                    httpRequest.setHeader(HttpHeaders.USER_AGENT, "AUTH_KEY_VALUE");
                    if(httpHeaders != null) {
                        httpRequest.setHeaders(httpHeaders);

                        for(Header header : httpHeaders) {
                            System.out.println("[HTTP_LOG] HEADER : " + header.getName() + "," + header.getValue());
                        }
                    }

//                    BasicCookieStore cookieStore = new BasicCookieStore();
//                    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
//                    cookie.setDomain(".github.com");
//                    cookie.setPath("/");
//                    cookieStore.addCookie(cookie);
//                    httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);



                })
                .addResponseInterceptorLast((httpResponse, entityDetails, httpContext) -> {
                    // 응답시 부가 작업
                })
                .build()
                ;
    }

    public static SimpleHttpResponse doRequest(CloseableHttpAsyncClient client,  SimpleHttpRequest httpRequest) throws IOException, ExecutionException, InterruptedException {
        CloseableHttpAsyncClient doClient = (client == null ? CreateAsyncClient(null, null) : client);
        doClient.start();
        Future<SimpleHttpResponse> future = doClient.execute(httpRequest, null);
//        doClient.close();
        return future.get();
    }

    public static void doRequest(CloseableHttpAsyncClient client,  SimpleHttpRequest httpRequest, FutureCallback<SimpleHttpResponse> callback) throws IOException {
        CloseableHttpAsyncClient doClient = (client == null ? CreateAsyncClient(null, null) : client);
        doClient.start();
        doClient.execute(httpRequest, callback);
//        doClient.close();
    }

    public static SimpleHttpRequest CreateGetRequest(String url, NameValuePair... params) {
        return SimpleRequestBuilder
                .get(url)
                .addParameters(params)
                .build();
    }

    public static SimpleHttpRequest CreatePostRequestWithJsonBody(String url, String body) {
        return SimpleRequestBuilder
                .post(url)
                .setBody(body, ContentType.APPLICATION_JSON)
                .build();
    }

    public static SimpleHttpRequest CreatePostRequestWithObject(String url, Object body) {
        return SimpleRequestBuilder
                .post(url)
                .setBody(JsonUtil.ToJsonStrFromObj(body), ContentType.APPLICATION_JSON)
                .build();
    }

    public static SimpleHttpRequest CreatePostRequestWithMultipartForm(String url, byte[] body) {
        return SimpleRequestBuilder
                .post(url)
                .setBody(body, ContentType.MULTIPART_FORM_DATA)
                .build();
    }


    public static SimpleHttpRequest CreatePutRequestWithObject(String url, Object body) {
        return SimpleRequestBuilder
                .put(url)
                .setBody(JsonUtil.ToJsonStrFromObj(body), ContentType.APPLICATION_JSON)
                .build();
    }


}
