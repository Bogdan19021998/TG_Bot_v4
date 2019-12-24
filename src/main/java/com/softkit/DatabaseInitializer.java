package com.softkit.vo;

import com.softkit.database.*;
import com.softkit.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    UserRepository userRepository;
    UserStatusRepository userStatusRepository;
    SpecialisationRepository specializationRepository;
    EmploymentRepository employmentRepository;
    ExperienceRepository experienceRepository;
    EnglishLevelRepository englishLevelRepository;
    CityRepository cityRepository;

////    UserSpecialisationsRepository usr;
////

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        initStatuses();
        initSpecialisations();
        initCities();
        initExperience();
        initEnglishLevel();
        initEmployment();

//        userMappingTest();
    }

    private void initStatuses() {
        // 1
        Status defStatus = new Status(
                Step.START,
                "new user",
                "Привет, я могу помочь тебе найти идеальную ИТ вакансию ",
                "Нажми /start, чтобы начать");
        // 2
        Status candidateStatus = new Status(
                Step.CANDIDATE,
                "name surname",
                "Для начала напиши мне свое имя и фамилию",
                "Я не могу распознать введенную тобой информацию как имя с фамилией. " +
                        "В этих данных не может быть чисел, ссылок и прочего. Только текст, состоящий " +
                        "из двух или трех отдельно написанных слов. Попробуй еще раз.\n");
        // 3
        Status specStatus = new Status(
                Step.SPECIALISATIONS,
                "user specialisations",
                "Превосходно, теперь укажи свою специализацию нажав на соответствующие кнопки. " +
                        "Ты можешь выбрать несколько, но не больше пяти. По окончанию нажми на кнопку Завершить.",
//                "Круто, ты выбрал такие специализации: &. Можешь выбрать еще несколько или завершить этот этап.");
                "Обрати внимание, что ты должен выбрать минимум один язык программирования или же несколько, " +
                        "но не больше пяти. По окончанию нажми на кнопку Завершить");
        // 4
        Status techStatus = new Status(
                Step.TECHNOLOGIES,
                "user technologies",
                "Ты можешь дополнить свою анкету, написав дополнительные технологии, с \n" +
                        "которыми ты сталкивался на практике. Напиши их и отправь мне сообщением. \n" +
                        "Также ты можешь пропустить этот шаг нажав на соответствующую кнопку ",
                "Не могу разобрать ввезенную тобой информацию. Учти, что это должен быть текст \n" +
                        "на английском языке / цифры / символы. Попробуй еще раз и отправь мне \n" +
                        "сообщением. Также ты можешь пропустить этот шаг нажав на соответствующую \n" +
                        "кнопку ");
        // 5
        Status userExperience = new Status(
                Step.EXPERIENCE,
                "user experience",
                "Укажи свой опыт работы нажав на соответствующую кнопку",
                "");
        // 6
        Status userEnglishLevel = new Status(
                Step.ENGLISH_LEVEL,
                "user english level",
                "Укажи свой уровень английского нажав на соответствующую кнопку",
                "");
        // 7
        Status userCity = new Status(
                Step.CITY_OR_LOCATION,
                "new user",
                "Укажи свой город нажав на соответствующую кнопку с названием города. Если \n" +
                        "твоего города нету в списке - нажми на кнопку Поделиться локацией.",
                "");
        // 8
        Status userEmployment = new Status(
                Step.EMPLOYMENT,
                "user employment",
                "Скажи приемлемый для тебя вид занятости нажав на соответствующие кнопки. Ты \n" +
                        "можешь выбрать один или несколько (все) виды занятости. По окончанию нажми \n" +
                        "на кнопку Завершить",
                "Обрати внимание, что ты должен выбрать минимум один вид занятости. По \n" +
                        "окончанию нажми на кнопку Завершить.");
        // 9
        Status userMinMoney = new Status(
                Step.MIN_SALARY,
                "user min salary",
                "СТеперь мне нужно уточнить у тебя приемлемую зарплатную вилку, для более \n" +
                        "точного поиска идеальной вакансии для тебя. Пришли сначала минимальную \n" +
                        "зарплату в $ от которой ты готов работать",
                "Я не могу распознать введенную тобой информацию как минимальную зарплату в \n" +
                        "$ от которой ты готов работать. Этими данными должно быть только число (не \n" +
                        "менее двузначного и не более пятизначного). Попробуй еще раз..");
        // 10
        Status userMaxMoney = new Status(
                Step.MAX_SALARY,
                "user max salary",
                "Теперь пришли мне зарплату в $ которая по твоему мнению на 100% соответствует\n" +
                        "уровню твоих умений.",
                "Я не могу распознать введенную тобой информацию как зарплату которая по \n" +
                        "твоему мнению на 100% соответствует уровню твоих умений.. Этими данными \n" +
                        "должно быть только число (не менее двузначного и не более пятизначного). \n" +
                        "Попробуй еще раз.");
        // 11
        Status userDoneBasicRegistration = new Status(
                Step.DONE_BASIC_REGISTRATION,
                "user done basic registration",
                "Теперь у меня есть все базовые данные для поиска подходящей вакансии. Ты \n" +
                        "можешь дополнить свою анкету прислав мне свое резюме в pdf формате, а также \n" +
                        "указать свой номер телефона и возраст. Это не обязательно, в любом случае тебе \n" +
                        "уже будут приходить от меня крутые вакансии. Как соберешься с мыслями - \n" +
                        "пришли мне команду \n" +
                        "/profile",
                "");
        // 12
        Status userPhone = new Status(
                Step.PHONE,
                "user phone",
                "Круто, что ты решил поделиться дополнительной информацией. Для начала мне \n" +
                        "нужно узнать твой номер телефона. Чтобы поделиться ним - просто нажми на \n" +
                        "кнопку под сообщением. Ты можешь пропустить этот шаг.",
                "");
        // 13
        Status userAge = new Status(
                Step.AGE,
                "user age",
                "Теперь введи свой возраст. Примечание: тебе должно быть больше 18 лет. Вводи" +
                "данные только цифрами. Ты можешь пропустить этот шаг. ",
                "Я не могу распознать введенную тобой информацию как возраст. Этими данными \n" +
                        "должно быть только двузначное число. Попробуй еще раз.");
        // 14
        Status userResume = new Status(
                Step.SUMMARY,
                "user summary",
                "Осталось только резюме. Пришли его мне в pdf формате. Для этого слева от \n" +
                        "строки ввода сообщений нажми - файл - и выбери его на своем устройстве. Ты \n" +
                        "можешь пропустить этот шаг. ",
                "");
        // 15
        Status userDoneRegistration = new Status(
                Step.DONE_REGISTRATION,
                "user done registration",
                "Круто! Теперь у меня есть вся необходимая информация для поиска подходящей \n" +
                        "вакансии. В ближайшее время я начну присылать тебе первые вакансии, а пока ты \n" +
                        "можешь пригласить своих друзей воспользоваться мной, отправив им вот эту \n" +
                        "ссылку &. " +
                        "За каждого приведенного тобой \n" +
                        "пользователя, который сможет с помощью меня найти работу, ты получишь 10% от \n" +
                        "его первой зарплаты. ",
                "");

        userStatusRepository.save( defStatus );
        userStatusRepository.save( candidateStatus );
        userStatusRepository.save( userExperience );
        userStatusRepository.save( specStatus );
        userStatusRepository.save( techStatus );
        userStatusRepository.save( userEnglishLevel );
        userStatusRepository.save( userCity );
        userStatusRepository.save( userEmployment );
        userStatusRepository.save( userMinMoney );
        userStatusRepository.save( userMaxMoney );
        userStatusRepository.save( userDoneBasicRegistration );
        userStatusRepository.save( userPhone );
        userStatusRepository.save( userAge );
        userStatusRepository.save( userResume );
        userStatusRepository.save( userDoneRegistration );
    }

    private void initSpecialisations() {
        specializationRepository.save( new Specialization(".NET"));
        specializationRepository.save( new Specialization("Front-End / JS"));
        specializationRepository.save( new Specialization("Android"));
        specializationRepository.save( new Specialization("Node.js"));
        specializationRepository.save( new Specialization("C/C++"));

        specializationRepository.save( new Specialization("PHP"));
        specializationRepository.save( new Specialization("Golang"));
        specializationRepository.save( new Specialization("Python"));
        specializationRepository.save( new Specialization("iOS"));
        specializationRepository.save( new Specialization("Ruby / Rails"));

        specializationRepository.save( new Specialization("Java"));
        specializationRepository.save( new Specialization("Scala"));
    }

    private void initExperience() {
        experienceRepository.save( new Experience("Без опыта") );
        experienceRepository.save( new Experience("Меньше года") );
        experienceRepository.save( new Experience("Минимум год") );
        experienceRepository.save( new Experience("Более 2-х лет") );
        experienceRepository.save( new Experience("Более 3-х лет") );
        experienceRepository.save( new Experience("Более 5-ти лет") );
    }

    private void initEnglishLevel() {
        englishLevelRepository.save( new EnglishLevel("Beginner/Elementary"));
        englishLevelRepository.save( new EnglishLevel("Pre-Intermediate"));
        englishLevelRepository.save( new EnglishLevel("Intermediate"));
        englishLevelRepository.save( new EnglishLevel("Upper Intermediate"));
        englishLevelRepository.save( new EnglishLevel("Advanced/Fluent"));
    }

    private void initEmployment() {
        employmentRepository.save( new Employment("Только офис в моем городе") );
        employmentRepository.save( new Employment("Переезд для работы в офисе") );
        employmentRepository.save( new Employment("Только удаленка") );
    }

    private void initCities() {
        cityRepository.save( new City("Киев") );
        cityRepository.save( new City("Львов") );
        cityRepository.save( new City("Харьков") );
        cityRepository.save( new City("Одесса") );
        cityRepository.save( new City("Днепр") );
        cityRepository.save( new City("Запорожье") );
    }

//    private void userMappingTest() {
//
//        User user = userRepository.save(new User(3));
//        Specialization specialization = specializationRepository.findById(3).get();
//        usr.save( new UserSpecialization(user, specialization ) );
//        usr.save(new UserSpecialization(user, specializationRepository.findById(4).get()));
//        usr.save(new UserSpecialization(user, specializationRepository.findById(2).get()));
//        usr.save(new UserSpecialization(user, specializationRepository.findById(5).get()));
//        userRepository.save(user);
//        System.out.println(Arrays.toString(userRepository.findUserByUserId(3).get().getSpecializations().toArray()));
//    }

}
