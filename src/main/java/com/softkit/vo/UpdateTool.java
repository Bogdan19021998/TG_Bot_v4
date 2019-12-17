package com.softkit.vo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import java.util.List;

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

    public static boolean isCallback(Update u) {
        return u.callbackQuery() != null;
    }

    public static Integer getUserId(Update u) {
        Message message = UpdateTool.getUpdateMessage(u);
        if (message != null) {
            return message.from().id();
        }
        return null;
    }

    public static Long getChatId(Update u) {
        Message message = UpdateTool.getUpdateMessage(u);
        if (message != null) {
            return message.chat().id();
        }
        return null;
    }

    public static InlineKeyboardButton[][] getButtonArray(List<String> strings, int columns) {

        int rows = (strings.size() + 1) / columns;
        int lastRowLength = strings.size() % columns == 0 ? columns : strings.size() % columns;

        InlineKeyboardButton[][] inlineKeyboardButtons = new InlineKeyboardButton[rows][];

        for (int i = 0; i < rows -1 ; i++) {
            InlineKeyboardButton[] row = new InlineKeyboardButton[columns];
            for (int j = 0; j < columns; j++) {
                row[j] = new InlineKeyboardButton(strings.get(i * columns + j)).callbackData(strings.get(i * columns + j));
            }
            inlineKeyboardButtons[i] = row;
        }

        InlineKeyboardButton[] lastRow = new InlineKeyboardButton[lastRowLength];
        for (int i = 0; i < lastRowLength; i++) {
            lastRow[i] = new InlineKeyboardButton(strings.get(strings.size() - lastRowLength + i)).callbackData(strings.get(strings.size() - lastRowLength + i));
        }
        inlineKeyboardButtons[rows - 1] = lastRow;

        return inlineKeyboardButtons;
    }

}
