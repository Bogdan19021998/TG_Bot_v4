package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.service.UserService;
import com.softkit.utils.TextParser;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.softkit.utils.TextParser.FINISH_SELECTION;

@Component
@RequiredArgsConstructor
public class PhoneStep extends AbstractStep{

    private final UserFieldsSetter userFieldsSetter;
    private final UserService userService;

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage;

        BaseRequest<?,?> baseRequest = null;

        if (UpdateUtils.isMessage(update)) {
            if (update.message().contact() != null) {
                userFieldsSetter.setPhone(user, update.message().contact().phoneNumber());

                nextStep = getDefaultNextStep();
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            } else if (UpdateUtils.isContainsIncomingMessage( update, FINISH_SELECTION)) {

                nextStep = getDefaultNextStep();
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            }

        }
        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.PHONE;
    }

    @Override
    public Step getDefaultNextStep() {
        return Step.AGE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        KeyboardButton[] buttons = new KeyboardButton[] {
                new KeyboardButton("Поделиться номером").requestContact(true),
                new KeyboardButton(FINISH_SELECTION)};
        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }
}
