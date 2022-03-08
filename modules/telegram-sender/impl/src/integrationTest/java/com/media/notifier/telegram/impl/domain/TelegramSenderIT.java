package com.media.notifier.telegram.impl.domain;

import com.github.database.rider.core.api.dataset.DataSet;
import com.media.notifier.telegram.impl.config.annotation.slices.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
class TelegramSenderIT {
    @Autowired
    private TelegramBotSender telegramBotSender;

    @Test
    @DataSet("dataset/air_alarm.xml")
    void sendMessage() throws InterruptedException {
        //GIVEN
        var message = "Test Message";

        //WHEN
        telegramBotSender.sendMessage(message);

        //GIVEN
        //no exception
    }
}
