import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.softkit.TgBotApplication;
import com.softkit.database.*;
import com.softkit.repository.LocationRepository;
import com.softkit.repository.UserRepository;
import com.softkit.repository.UserSpecialisationsRepository;
import com.softkit.service.*;
import com.softkit.vo.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgBotApplication.class)
public class Test {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecialisationsRepository userSpecialisationsRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmploymentsService employmentsService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private TechnologiesService technologiesService;



    @org.junit.Test
    public void test() {
        userRepository.deleteAll();

//        checkSpecializations();

//        checkEmployment();

//        checkTechnologies();

//        checkReferrals();

        checkUser();
    }

    private void checkUser() {

        User user = userService.addUserAndSetFirstStep(5);

        userRepository.save( user );

        userService.setCandidate( user, "Bogdan" );
        userService.setCity( user, City.LVIV );
        userService.setEnglishLevel( user, EnglishLevel.INTERMEDIATE);
        userService.setExperience( user, Experience.WITHOUT_EXPERIENCE );
        userService.setSalaryFrom( user, 100 );
        userService.setSalaryUpTo( user, 100 );

        employmentsService.addUserEmployment(user, Employment.ONLY_OFFICE);
        employmentsService.addUserEmployment(user, Employment.ONLY_REMOTE);

        specializationService.addUserSpecialisation(user, Specialization.JAVA);
        specializationService.addUserSpecialisation(user, Specialization.GOLANG);
        specializationService.addUserSpecialisation(user, Specialization.IOS);

        referralService.addUserReferral(user,444);
        referralService.addUserReferral(user,2323);

        technologiesService.addAllTechnologies( user, new String[]{"A","B","C"});

        locationRepository.save( new UserLocation(user.getId(), 30f, 342f));

        userRepository.save( user );

        System.out.println();

        userService.removeUser( user );

        System.out.println();



    }


    public void checkReferrals()
    {
        User user = userRepository.save(new User(1));

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();

        ReferralService service = ctx.getBean(ReferralService.class);

        Integer sizeEmpty = service.findAllUserReferrals(user).size();

        service.removeAllUserReferrals(user);


        service.addUserReferral(user, 242342 );
        service.addUserReferral(user, 10000001 );
        service.addUserReferral(user, 99977999 );


        System.out.println();

        int sizeFill = service.findAllUserReferrals( user ).size();

        Date date = service.findAllUserReferrals(user).iterator().next().getDateCreated();

        int sizeRemoved = service.findAllUserReferrals( user ).size();



        System.out.println();
    }

    public void checkTechnologies()
    {
        User user = userRepository.save(new User(14));

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();

        TechnologiesService service = ctx.getBean(TechnologiesService.class);

        Integer sizeEmpty = service.findAllUserTechnologies(user).size();

        service.removeAllTechnologies(user);


//        service.addAllTechnologies(user, null );
        service.addAllTechnologies(user, new String[]{} );
        service.addAllTechnologies(user, new String[]{"ppp"} );
        service.addAllTechnologies(user, new String[]{"aaa","bbb","ccc"} );


        System.out.println();

        service.removeAllTechnologies( user );

        int sizeFill = service.findAllUserTechnologies( user ).size();

        System.out.println();
    }

    public void checkEmployment()
    {
        User user = userRepository.save(new User(2));

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();

        EmploymentsService service = ctx.getBean(EmploymentsService.class);

        Integer sizeEmpty = service.findAllUserEmployments(user).size();

        service.removeUserEmployment(user, Employment.ONLY_OFFICE );


        service.addUserEmployment(user, Employment.OFFICE_RELOCATION);
        service.addUserEmployment(user, Employment.ONLY_REMOTE );

        System.out.println();

        service.removeUserEmployment(user, Employment.ONLY_REMOTE );
        service.removeUserEmployment(user, Employment.ONLY_REMOTE );

        int sizeFill = service.findAllUserEmployments(user).size();

        System.out.println();
    }
    public void checkSpecializations()
    {
        User user = userRepository.save(new User(3));

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();

        SpecializationService spcService = ctx.getBean(SpecializationService.class);

        Integer sizeEmpty = spcService.findAllUserSpecialization(user).size();

        spcService.removeUserSpecialisation(user, Specialization.JAVA );


        spcService.addUserSpecialisation(user, Specialization.JAVA);
        spcService.addUserSpecialisation(user, Specialization.ANDROID );
        spcService.addUserSpecialisation(user, Specialization.IOS );

        System.out.println();

        spcService.removeUserSpecialisation(user, Specialization.JAVA );
        spcService.removeUserSpecialisation(user, Specialization.JAVA );

        int sizeFill = spcService.findAllUserSpecialization(user).size();

        System.out.println();
    }
}
