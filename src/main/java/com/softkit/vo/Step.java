package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Step {
    START(
            "new user",
            "Привет, я могу помочь тебе найти идеальную ИТ вакансию ",
            "Нажми /start, чтобы начать"),
    CANDIDATE(
            "name surname",
            "Для начала напиши мне свое имя и фамилию",
            "Я не могу распознать введенную тобой информацию как имя с фамилией. "
                    + "В этих данных не может быть чисел, ссылок и прочего. Только текст, состоящий "
                    + "из двух или трех отдельно написанных слов. Попробуй еще раз."),
    SPECIALISATIONS(
            "user specialisations",
            "Превосходно, теперь укажи свою специализацию нажав на соответствующие кнопки."
                    + "Ты можешь выбрать несколько, но не больше пяти. По окончанию нажми на кнопку Завершить. ",
            "Обрати внимание, что ты должен выбрать минимум один язык программирования или же несколько, "
                    +"но не больше пяти. По окончанию нажми на кнопку Завершить"),
    TECHNOLOGIES(
            "user technologies",
            "Ты можешь дополнить свою анкету, написав дополнительные технологии, с"
                    + "которыми ты сталкивался на практике. Напиши их и отправь мне сообщением. "
                    + "Также ты можешь пропустить этот шаг нажав на соответствующую кнопку ",
            "Не могу разобрать ввезенную тобой информацию. Учти, что это должен быть текст"
                    + "на английском языке / цифры / символы. Попробуй еще раз и отправь мне "
                    + "сообщением. Также ты можешь пропустить этот шаг нажав на соответствующую "
                    + "кнопку"),
    EXPERIENCE(
            "user experience",
            "Укажи свой опыт работы нажав на соответствующую кнопку",
            ""),
    ENGLISH_LEVEL(
            "user english level",
            "Укажи свой уровень английского нажав на соответствующую кнопку",
            ""),
    CITY_OR_LOCATION(
            "new user",
            "Укажи свой город нажав на соответствующую кнопку с названием города. Если " +
            "твоего города нету в списке - нажми на кнопку Поделиться локацией.",
            ""),
    EMPLOYMENT(
     "user employment",
            "Скажи приемлемый для тебя вид занятости нажав на соответствующие кнопки. Ты " +
            "можешь выбрать один или несколько (все) виды занятости. По окончанию нажми " +
            "на кнопку Завершить",
            "Обрати внимание, что ты должен выбрать минимум один вид занятости. По " +
            "окончанию нажми на кнопку Завершить."),
    MIN_SALARY(
            "user min salary",
            "Теперь мне нужно уточнить у тебя приемлемую зарплатную вилку, для более " +
            "точного поиска идеальной вакансии для тебя. Пришли сначала минимальную " +
            "зарплату в $ от которой ты готов работать",
            "Я не могу распознать введенную тобой информацию как минимальную зарплату в " +
            "$ от которой ты готов работать. Этими данными должно быть только число (не " +
            "менее двузначного и не более пятизначного). Попробуй еще раз.."),
    MAX_SALARY(
            "user max salary",
            "Теперь пришли мне зарплату в $ которая по твоему мнению на 100% соответствует" +
            "уровню твоих умений.",
            "Я не могу распознать введенную тобой информацию как зарплату которая по " +
            "твоему мнению на 100% соответствует уровню твоих умений.. Этими данными " +
            "должно быть только число (не менее двузначного и не более пятизначного). " +
            "Попробуй еще раз."),
    DONE_BASIC_REGISTRATION(
            "user done basic registration",
            "Теперь у меня есть все базовые данные для поиска подходящей вакансии. Ты " +
            "можешь дополнить свою анкету прислав мне свое резюме в pdf формате, а также " +
            "указать свой номер телефона и возраст. Это не обязательно, в любом случае тебе " +
            "уже будут приходить от меня крутые вакансии. Как соберешься с мыслями - " +
            "пришли мне команду " +
            "/profile",
            ""),
    PHONE(
            "user phone",
            "Круто, что ты решил поделиться дополнительной информацией. Для начала мне " +
            "нужно узнать твой номер телефона. Чтобы поделиться ним - просто нажми на " +
            "кнопку под сообщением. Ты можешь пропустить этот шаг.",
            ""),
    AGE(
            "user age",
            "Теперь введи свой возраст. Примечание: тебе должно быть больше 18 лет. Вводи" +
            "данные только цифрами. Ты можешь пропустить этот шаг. ",
            "Я не могу распознать введенную тобой информацию как возраст. Этими данными " +
            "должно быть только двузначное число. Попробуй еще раз."),
    SUMMARY(
            "user summary",
            "Осталось только резюме. Пришли его мне в pdf формате. Для этого слева от " +
            "строки ввода сообщений нажми - файл - и выбери его на своем устройстве. Ты " +
            "можешь пропустить этот шаг. ",
            ""),
    DONE_REGISTRATION(
            "user done registration",
             "Круто! Теперь у меня есть вся необходимая информация для поиска подходящей " +
             "вакансии. В ближайшее время я начну присылать тебе первые вакансии, а пока ты " +
             "можешь пригласить своих друзей воспользоваться мной, отправив им вот эту ссылку &." +
             " За каждого приведенного тобой " +
             "пользователя, который сможет с помощью меня найти работу, ты получишь 10% от " +
             "его первой зарплаты. ",
             ""),
    INVITED_USERS(
            "Get count invited users",
            "По твоему приглашению уже зарегистрировалось @invited соискателей. " +
                    "Из них трудоустроились @working человек. По состоянию на сегодня твой реферальный доход " +
                    "составляет @profit $. Ты можешь пригласить больше друзей отправив им вот эту ссылку ",
            ""
    );

    @Getter
    private final String stepDescription;

    @Getter
    private final String botMessage;

    @Getter
    private final String userMistakeResponse;
}
