package com.softkit.vo;

import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private UserProfile updatedUserProfile;

}
