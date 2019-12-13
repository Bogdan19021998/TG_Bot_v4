import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

public interface IMessageSender {
    <T extends BaseRequest, R extends BaseResponse> boolean send(BaseRequest<T, R> r);
}
