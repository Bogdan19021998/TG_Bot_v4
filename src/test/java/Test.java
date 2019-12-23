import com.softkit.TgBotApplication;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgBotApplication.class)
public class Test {

    @Autowired
    private UserRepository userRepository;

    @org.junit.Test
    public void test() {
        userRepository.deleteAll();

        User user = userRepository.save(new User(123123123));

        System.out.println();
    }
}
