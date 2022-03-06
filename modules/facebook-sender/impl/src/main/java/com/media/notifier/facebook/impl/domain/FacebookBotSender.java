package com.media.notifier.facebook.impl.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
@RequiredArgsConstructor
public class FacebookBotSender {
    public void sendMessage(String message) {

    }
}
