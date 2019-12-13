package vo;

import com.pengrad.telegrambot.request.BaseRequest;
import database.User;
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
    private Long messageId;

    @NotNull
    private BaseRequest<?, ?> request;

    @Nullable
    private Step nextStep;

    private User updatedUser;

}
