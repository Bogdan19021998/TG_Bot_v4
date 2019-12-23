package com.softkit;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetUpdates;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Bot
//        extends TelegramBot
{

    private GetUpdates getUpdates;
    private DefaultUpdateProcessor updateProcessor;

//    todo add to application properties
    private static String token = "1032578818:AAEcsGFQ46oLcenajdG7vUB_jPyuazzdqIk";

    private Bot(DefaultUpdateProcessor updateProcessor) {
//        super("1");
//        getUpdates = new GetUpdates().limit(1).offset(0).timeout(1000);
//        start();
//        this.updateProcessor = updateProcessor;
    }

    private void start() {
        System.out.println("Bot created");
//        Update - todo rename
//        setUpdatesListener(Update -> {
//            updateProcessor.process(execute(getUpdates).updates().get(0));
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        });
    }
}
