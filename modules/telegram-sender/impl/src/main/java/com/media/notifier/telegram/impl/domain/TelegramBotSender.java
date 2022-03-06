package com.media.notifier.telegram.impl.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class TelegramBotSender {
    private final String telegramBotToken;
    private final String telegramChatId;

    public TelegramBotSender(@Value("${telegram.bot.token}") String telegramBotToken,
                             @Value("${telegram.chat.id}") String telegramChatId) {
        this.telegramBotToken = telegramBotToken;
        this.telegramChatId = telegramChatId;
    }

    public void sendMessage(String message) {
        try {
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

            urlString = String.format(urlString, telegramBotToken, telegramChatId, message);

            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
            // Do what you want with response
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to send message", e);
        }
    }
}
