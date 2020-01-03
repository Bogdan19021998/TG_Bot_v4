package com.softkit.vo;

import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProcessorResult {
    @Nullable
    private Long chatId;

    @NotNull
    private BaseRequest<?, ?> request;

    private Step nextStep;

    private User updatedUser;

    private BaseRequest<?, ?> optionalRequest;

    public UpdateProcessorResult(@Nullable Long chatId, @NotNull BaseRequest<?, ?> request, Step nextStep, User updatedUser) {
        this.chatId = chatId;
        this.request = request;
        this.nextStep = nextStep;
        this.updatedUser = updatedUser;
    }
}
