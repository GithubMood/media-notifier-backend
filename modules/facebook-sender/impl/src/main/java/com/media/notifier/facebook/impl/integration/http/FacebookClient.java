package com.media.notifier.facebook.impl.integration.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "facebook", url = "https://graph.facebook.com")
public interface FacebookClient {
    @PostMapping(value = "{pageId}/feed")
    Object publishPost(@PathVariable("pageId") String pageId,
                       @RequestParam("message") String message,
                       @RequestParam("access_token") String pageAccessToken);
}
