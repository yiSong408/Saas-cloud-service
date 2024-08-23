package net.cloud.biz;

import lombok.extern.slf4j.Slf4j;
import net.cloud.UserApplication;
import net.cloud.component.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSend(){
        mailService.sendMail("1027881714@qq.com","hello","hello world!");
    }
}
