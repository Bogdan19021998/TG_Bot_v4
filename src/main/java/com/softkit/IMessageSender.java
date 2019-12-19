package com.softkit;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface IMessageSender {

    <T extends BaseRequest<?,?>, R extends BaseResponse> boolean send(BaseRequest<T, R> r);
}
