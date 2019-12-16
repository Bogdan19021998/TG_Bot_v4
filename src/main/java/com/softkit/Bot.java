package com.softkit;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetUpdates;
import com.softkit.repository.UserRepository;
import com.softkit.steps.StepHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("singleton")
public class Bot extends TelegramBot {

    private final GetUpdates getUpdates;
    @Autowired
    private DefaultUpdateProcessor updateProcessor;

    private static String token = "1032578818:AAEcsGFQ46oLcenajdG7vUB_jPyuazzdqIk";

    private Bot() {
        super(token);
        getUpdates = new GetUpdates().limit(1).offset(0).timeout(100);
        start();
    }

    private void start() {
        System.out.println("Bot created");
        // getting userRepository bean
        // UserRepository ur = context.getBean(UserRepository.class);
//        ApplicationContext context = new AnnotationConfigApplicationContext(TgBotApplication.class);
//        updateProcessor = context.getBean(DefaultUpdateProcessor.class);
        setUpdatesListener(Update -> {
            updateProcessor.process(execute(getUpdates).updates().get(0));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
