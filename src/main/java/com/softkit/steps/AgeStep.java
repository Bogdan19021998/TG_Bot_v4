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

@Component
@RequiredArgsConstructor
public class AgeStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    private final UserService userService;

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();

        String outgoingMessage = nextStep.getUserMistakeResponse();
        BaseRequest<?,?> baseRequest = new SendMessage(chatId, outgoingMessage);

        if (UpdateUtils.hasMassageText(update)) {
            String userText = UpdateUtils.getMessage(update).text();
            if (TextParser.isIntegerText(userText)) {
                int age = Integer.parseInt(userText);
                if (age >= 18 && age <= 99) {

                    nextStep = Step.SUMMARY;

//                    nextStep = userService.getNextStepInProfile( user  );


                    userFieldsSetter.setAge(user, age);
                    outgoingMessage = nextStep.getBotMessage();
                    baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
                }
            } else if (userText.contentEquals("Пропустить")) {

                nextStep = Step.SUMMARY;

//                nextStep = userService.getNextStepInProfile( user );


                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            }
        }
        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.AGE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        KeyboardButton[] buttons = new KeyboardButton[] {new KeyboardButton("Пропустить")};
        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }
}
