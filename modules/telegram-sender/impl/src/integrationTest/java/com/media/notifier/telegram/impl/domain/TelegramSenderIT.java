package com.media.notifier.telegram.impl.domain;

import com.media.notifier.telegram.impl.config.annotation.slices.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
class TelegramSenderIT {
    @Autowired
    private TelegramBotSender telegramBotSender;

    @Test
    void sendMessage() throws InterruptedException {
        //GIVEN
        var message = "Test Message";
        var chatId = "@my_test_channel_for_bot_1989";
        var token = "5262822081:AAH2A89Nt5BLmVSoo18A-6qhcDIcZBkviOo";

        //WHEN
        telegramBotSender.sendMessage(chatId, token, message);

        //GIVEN
        //no exception
    }
}
