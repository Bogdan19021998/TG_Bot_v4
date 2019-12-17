package com.softkit;

import com.softkit.database.UserStatus;
import com.softkit.repository.UserRepository;
import com.softkit.repository.UserStatusRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DbTest implements ApplicationListener<ContextRefreshedEvent> {

    UserRepository userRepository;
    UserStatusRepository userStatusRepository;


    public DbTest(UserRepository userRepository, UserStatusRepository userStatusRepository) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData()
    {
        // заполняю таблицы
        UserStatus defStatus = new UserStatus(
                "new user",
                "Привет, я могу помочь тебе найти идеальную ИТ вакансию ",
                "Нажми /start, чтобы начать");
        UserStatus candidateStatus = new UserStatus(
                "name surname",
                "Для начала напиши мне свое имя и фамилию",
                "Я не могу распознать введенную тобой информацию как имя с фамилией. " +
                        "В этих данных не может быть чисел, ссылок и прочего. Только текст, состоящий " +
                        "из двух или трех отдельно написанных слов. Попробуй еще раз.\n");

        UserStatus specStatus = new UserStatus(
                "user specialisations",
                "Превосходно, теперь укажи свою специализацию нажав на соответствующие кнопки. " +
                        "Ты можешь выбрать несколько, но не больше пяти. По окончанию нажми на кнопку Завершить.",
//                "Круто, ты выбрал такие специализации: &. Можешь выбрать еще несколько или завершить этот этап.");
                "Обрати внимание, что ты должен выбрать минимум один язык программирования или же несколько, " +
                        "но не больше пяти. По окончанию нажми на кнопку Завершить");

        userStatusRepository.save( defStatus );
        userStatusRepository.save( candidateStatus );
        userStatusRepository.save( specStatus );

//        Iterable userStatuses = userStatusRepository.findAll();

//        User userOne = new User(1, userStatusRepository.findById(1).get() );
//        User userTwo = new User(2, userStatusRepository.findById(2).get() );
//
//        userRepository.save( userOne );
//        userRepository.save( userTwo );

    }
}
