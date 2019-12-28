package com.softkit.steps;

import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.softkit.Bot;
import com.softkit.BotMessageSender;
import com.softkit.database.User;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.ApplicationContextProvider;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.hibernate.engine.jdbc.ReaderInputStream;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class SummaryStep extends AbstractStep {

    private final BotMessageSender botMessageSender;

    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = nextStep.getBotMessage();
        BaseRequest<?, ?> baseRequest = new SendMessage(chatId, outgoingMessage);

        if (UpdateUtils.isMessage(update)) {
            Document document = update.message().document();
            if (document != null && document.fileName().contains(".pdf")) {

                GetFileResponse getFileResponse = (GetFileResponse) botMessageSender.send(
                        new GetFile(document.fileId()));

                user.setNameSummary( user.getId() + "_" + document.fileName() );

                if ( !saveFile(getFileResponse, user) ) {
                    //todo if we don't save file
                }
                nextStep = Step.DONE_REGISTRATION;
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));

                return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);

            } else if (UpdateUtils.hasMassageText(update) && update.message().text().contentEquals("Пропустить")) {
                nextStep = Step.DONE_REGISTRATION;
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            }
        }

        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.SUMMARY;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        KeyboardButton[] buttons = new KeyboardButton[]{new KeyboardButton("Пропустить")};
        return ((SendMessage) result.getRequest()).replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }

    private boolean saveFile(GetFileResponse responseFile, User user) {
        if (responseFile.isOk()) {

            File localFile = new File("/home/bogdan/" + user.getNameSummary());
            try {
                InputStream is = new URL("https://api.telegram.org/file/bot" + Bot.getToken()
                        + "/" + responseFile.file().filePath()).openStream();
                FileUtils.copyInputStreamToFile(is, localFile);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
