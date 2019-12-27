package com.softkit;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public class BotMessageSender implements IMessageSender {

    // todo first version
//    @Override
//    public <T extends BaseRequest<?,?>, R extends BaseResponse> boolean send(BaseRequest<T, R> r) {
//        return TgBotApplication.applicationContext.getBean(Bot.class).execute( r ).isOk();
//    }

    public <T extends BaseRequest<?,?>, R extends BaseResponse> BaseResponse send(BaseRequest<T, R> r) {
        return TgBotApplication.applicationContext.getBean(Bot.class).execute( r );
    }
}
