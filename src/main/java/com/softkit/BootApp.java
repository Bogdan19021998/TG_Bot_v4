package com.softkit;

import com.softkit.database.User;
import com.softkit.database.UserStatus;
import com.softkit.repository.UserRepository;
import com.softkit.repository.UserStatusRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootApp implements ApplicationListener<ContextRefreshedEvent> {

    UserRepository userRepository;
    UserStatusRepository userStatusRepository;


    public BootApp(UserRepository userRepository, UserStatusRepository userStatusRepository) {
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
        UserStatus userStatusOne = new UserStatus(
                "new user",
                "Привет, я могу помочь тебе найти идеальную ИТ вакансию ",
                "");
        UserStatus userStatusTwo = new UserStatus(
                "напиши мне свое имя и фамилию",
                "Для начала напиши мне свое имя и фамилию",
                "Я не могу распознать введенную тобой информацию как имя с фамилией. " +
                        "В этих данных не может быть чисел, ссылок и прочего. Только текст, состоящий " +
                        "из двух или трех отдельно написанных слов. Попробуй еще раз.\n");

        userStatusRepository.save( userStatusOne );
        userStatusRepository.save( userStatusTwo );

//        Iterable userStatuses = userStatusRepository.findAll();

        User userOne = new User(1, userStatusRepository.findById(1).get() );
        User userTwo = new User(2, userStatusRepository.findById(2).get() );

        userRepository.save( userOne );
        userRepository.save( userTwo );

        System.out.println("User(one) status is : " + userRepository.findUserByUserId(1).get().getUserStatus().getBotMessage());
        System.out.println("User(two) status is : " + userRepository.findUserByUserId(2).get().getUserStatus().getBotMessage());



    }
}
