import com.pengrad.telegrambot.model.Update;

public interface UpdateProcessor {

    void process(Update update);
}
