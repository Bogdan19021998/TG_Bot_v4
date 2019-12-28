package com.softkit;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetUpdates;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Bot extends TelegramBot {

    private GetUpdates getUpdates;
    private DefaultUpdateProcessor updateProcessor;

    @Getter
    private static final String token = "1032578818:AAEcsGFQ46oLcenajdG7vUB_jPyuazzdqIk";


    private Bot(DefaultUpdateProcessor updateProcessor) {
        super(token);
        getUpdates = new GetUpdates().limit(1).offset(0).timeout(100);
        this.updateProcessor = updateProcessor;
        start();
    }

    private void start() {
        System.out.println("Bot created");
        setUpdatesListener(Update -> {
            updateProcessor.process(execute(getUpdates).updates().get(0));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
