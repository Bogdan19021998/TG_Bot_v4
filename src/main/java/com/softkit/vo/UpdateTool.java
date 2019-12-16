package com.softkit.vo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public class UpdateTool {

    public static Message getUpdateMessage(Update u) {
        Message message = null;
        if (u.callbackQuery() != null) {
            message = u.callbackQuery().message();
        } else if (u.message() != null) {
            message = u.message();
        }
        return message;
    }

    public static Integer getUserId(Update u) {
        Message message = UpdateTool.getUpdateMessage(u);
        if (message != null) {
            return message.from().id();
        }
        return null;
    }
}
