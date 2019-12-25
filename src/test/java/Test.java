import com.softkit.TgBotApplication;
import com.softkit.database.User;
import com.softkit.database.UserSpecialization;
import com.softkit.repository.UserRepository;
import com.softkit.repository.UserSpecialisationsRepository;
import com.softkit.service.SpecializationService;
import com.softkit.vo.ApplicationContextProvider;
import com.softkit.vo.Specialization;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgBotApplication.class)
public class Test {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecialisationsRepository userSpecialisationsRepository;

    @org.junit.Test
    public void test() {
        userRepository.deleteAll();



    }
//
//    public void checkSpecializations()
//    {
//        User user = userRepository.save(new User(123123123));
//
//        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
//
//        SpecializationService spcService = ctx.getBean(SpecializationService.class);
//
//        int sizeEmpty = spcService.findAllUserSpecialization(user).size();
//
//        spcService.addUserSpecialisation(user, Specialization.JAVA.name() );
//        spcService.addUserSpecialisation(user, Specialization.ANDROID.name() );
//        spcService.addUserSpecialisation(user, Specialization.IOS.name() );
//
//        System.out.println();
//
//        spcService.removeUserSpecialisation(user, Specialization.JAVA.name() );
//        spcService.removeUserSpecialisation(user, Specialization.JAVA.name() );
//        spcService.removeUserSpecialisation(user, "not found sdfsfs" );
//
//        int sizeFill = spcService.findAllUserSpecialization(user).size();
//
//        System.out.println();
//    }
}
