package com.project.tracc.utils;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor{
    private final String headerName;
    private final String headerValue;
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpRequest wrapper = new HttpRequestWrapper(request);
        wrapper.getHeaders().set(headerName, headerValue);

        return execution.execute(wrapper, body);
    }

    
}
