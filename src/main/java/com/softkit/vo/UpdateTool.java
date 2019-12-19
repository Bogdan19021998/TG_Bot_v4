package com.softkit.vo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import java.util.List;

import static com.softkit.steps.StepHolder.FINISH_SELECTION;

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

        int buttons_count = strings.size() + 1;   // +1 for exit button
        int rows = (buttons_count + (columns - 1) ) / columns; // rows count ( + (columns - 1) for odd buttons_count )
        // last row length including exit button
        int lastRowLength = buttons_count % columns == 0 ? columns : buttons_count % columns;
        // 2D button array
        InlineKeyboardButton[][] inlineKeyboardButtons = new InlineKeyboardButton[rows][];
        // rows (except last) initialization
        for (int i = 0; i < rows -1 ; i++) {
            InlineKeyboardButton[] row = new InlineKeyboardButton[columns];
            for (int j = 0; j < columns; j++) {
                row[j] = new InlineKeyboardButton(strings.get(i * columns + j)).callbackData(strings.get(i * columns + j));
            }
            inlineKeyboardButtons[i] = row;
        }
        // last row initialisation
        InlineKeyboardButton[] lastRow = new InlineKeyboardButton[lastRowLength];
        for (int i = 0; i < lastRowLength - 1; i++) { // excluding exit button place
            lastRow[i] = new InlineKeyboardButton(strings.get(buttons_count - lastRowLength + i)).callbackData(strings.get(buttons_count - lastRowLength + i));
        }
        lastRow[lastRowLength - 1] = new InlineKeyboardButton("Завершить").callbackData(FINISH_SELECTION); // adding exit button
        inlineKeyboardButtons[rows - 1] = lastRow;

        return inlineKeyboardButtons;
    }

}
