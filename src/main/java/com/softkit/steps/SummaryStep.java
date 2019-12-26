package com.softkit.steps;

import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.stereotype.Component;

@Component
public class SummaryStep extends AbstractStep {

    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = nextStep.getBotMessage();
        BaseRequest<?,?> baseRequest = new SendMessage(chatId, outgoingMessage);

        if (UpdateUtils.isMessage(update)) {
            Document document = update.message().document();
            if (document != null && document.fileName().contains(".pdf")) {
                // save file !
                nextStep = Step.DONE_REGISTRATION;
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            } else if (UpdateUtils.hasMassageText(update) && update.message().text().contentEquals("Пропустить")) {
                nextStep = Step.DONE_REGISTRATION;
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            }
        }

        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    public Step getCurrentStepId() {
        return Step.SUMMARY;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        KeyboardButton[] buttons = new KeyboardButton[]{new KeyboardButton("Пропустить")};
        return ((SendMessage) result.getRequest()).replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }

}
