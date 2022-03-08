package com.media.notifier.telegram.impl.integration.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "telegram", url = "https://api.telegram.org")
public interface TelegramClient {
    @GetMapping(value = "bot{botToken}/sendMessage")
    Object publishMessage(@PathVariable("botToken") String botToken,
                          @RequestParam("chat_id") String chatId,
                          @RequestParam("text") String message);
}
