package com.softkit.vo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
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
        if (u.callbackQuery() != null) {
            return u.callbackQuery().from().id();
        } else if (u.message() != null) {
            return u.message().from().id();
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

    public static InlineKeyboardButton[][] getButtonArray(List<String> strings) {
        return getButtonArray(strings, strings,1, false);
    }

    public static InlineKeyboardButton[][] getButtonArrayWithExitButton(List<String> strings) {
        return getButtonArray(strings, strings,1, true);
    }

    public static InlineKeyboardButton[][] getButtonArray(List<String> strings, int columns, boolean exitButton) {
        return getButtonArray(strings, strings, columns, exitButton);
    }

    public static InlineKeyboardButton[][] getButtonArray(List<String> strings, List<String> callbacks, int columns, boolean exitButton) {

        if (callbacks.size() != strings.size()) {
            System.out.println("NOT EQUALS SISES!!!");
            callbacks = strings;
        }

        int buttons_count;

        if (exitButton) {
            buttons_count = strings.size() + 1;   // +1 for exit button
        } else {
            buttons_count = strings.size();
        }

        if (buttons_count == 0) {
            return new InlineKeyboardButton[0][0];
        }

        int rows = (buttons_count + (columns - 1)) / columns; // rows count ( + (columns - 1) for odd buttons_count )
        int lastRowLength = buttons_count % columns == 0 ? columns : buttons_count % columns;

        // 2D button array
        InlineKeyboardButton[][] inlineKeyboardButtons = new InlineKeyboardButton[rows][];
        // rows (except last) initialization
        for (int i = 0; i < rows - 1; i++) {
            InlineKeyboardButton[] row = new InlineKeyboardButton[columns];
            for (int j = 0; j < columns; j++) {
                row[j] = new InlineKeyboardButton(strings.get(i * columns + j)).callbackData(callbacks.get(i * columns + j));
            }
            inlineKeyboardButtons[i] = row;
        }
        // last row initialisation
        InlineKeyboardButton[] lastRow = new InlineKeyboardButton[lastRowLength];
        for (int i = 0; i < lastRowLength - ( exitButton ? 1 : 0 ); i++) { // ex- or including exit button place
            lastRow[i] = new InlineKeyboardButton(strings.get(buttons_count - lastRowLength + i)).callbackData(callbacks.get(buttons_count - lastRowLength + i));
        }

        if (exitButton)
            lastRow[lastRowLength - 1] = new InlineKeyboardButton("Завершить").callbackData(FINISH_SELECTION); // adding exit button

        inlineKeyboardButtons[rows - 1] = lastRow;
        return inlineKeyboardButtons;

    }

    public static InlineKeyboardButton findButtonByText(InlineKeyboardButton[][] buttons, String buttonText) {
        for (InlineKeyboardButton[] button : buttons) {
            for (InlineKeyboardButton inlineKeyboardButton : button) {
                if (inlineKeyboardButton.text().contentEquals(buttonText)) {
                    return inlineKeyboardButton;
                }
            }
        }
        return null;
    }

    public static void changeButtonByText(InlineKeyboardButton[][] inlineKeyboard, String buttonText, InlineKeyboardButton newButton) {
        for (InlineKeyboardButton[] inlineKeyboardButtons : inlineKeyboard) {
            for (int j = 0; j < inlineKeyboardButtons.length; j++) {
                InlineKeyboardButton inlineKeyboardButton = inlineKeyboardButtons[j];
                if (inlineKeyboardButton.text().contentEquals(buttonText)) {
                    inlineKeyboardButtons[j] = newButton;
                    return;
                }
            }
        }
    }

    public static String createMarker() {
        return new String(
                new byte[]{(byte) (' '), (byte) 0xE2, (byte) 0x9C, (byte) 0x85},
                StandardCharsets.UTF_8);
    }

    public static boolean hasMarker(String data) {
        return data.contains(createMarker());
    }

    public static InlineKeyboardButton addMarkerToButton(InlineKeyboardButton inlineKeyboardButton) {

        String text = inlineKeyboardButton.text();
        String newText = text.contains(createMarker()) ? text : text + createMarker();

        return new InlineKeyboardButton(newText).callbackData(inlineKeyboardButton.callbackData() + createMarker());
    }

    public static InlineKeyboardButton removeMarkerFromButton(InlineKeyboardButton inlineKeyboardButton) {

        int endIndex = inlineKeyboardButton.text().indexOf(createMarker());
        String newText = (endIndex != -1) ? inlineKeyboardButton.text().substring(0, endIndex) : inlineKeyboardButton.text();

        return new InlineKeyboardButton(newText).callbackData(newText);
    }
}
