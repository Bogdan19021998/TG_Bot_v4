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
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;

import static com.softkit.utils.TextParser.FINISH_SELECTION;

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

                user.setNameSummary(user.getId() + "_" + document.fileName());

                if (!saveFile(getFileResponse, user)) {
                    //todo if we don't save file
                }
                nextStep = getDefaultNextStep();
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));

                return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);

            } else if (UpdateUtils.isContainsIncomingMessage(update, FINISH_SELECTION)) {
                nextStep = getDefaultNextStep();
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
    public Step getDefaultNextStep() {
        return Step.DONE_REGISTRATION;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        KeyboardButton[] buttons = new KeyboardButton[]{new KeyboardButton(FINISH_SELECTION)};
        return ((SendMessage) result.getRequest()).replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }

    private boolean saveFile(GetFileResponse responseFile, User user) {
        if (responseFile.isOk()) {

            String pathName = System.getProperty("user.home") + File.separator + "HRBot" +
                    File.separator + "summaries";

            File filePath = new File(pathName);
            if (filePath.exists() || filePath.mkdirs()) {
                try {
                    File summaryFile = new File(pathName  + File.separator + user.getNameSummary());
                    InputStream is = new URL("https://api.telegram.org/file/bot" + Bot.getToken()
                            + "/" + responseFile.file().filePath()).openStream();
                    FileUtils.copyInputStreamToFile(is, summaryFile);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
