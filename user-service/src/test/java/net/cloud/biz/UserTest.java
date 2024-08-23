package net.cloud.biz;

import lombok.extern.slf4j.Slf4j;
import net.cloud.UserApplication;
import net.cloud.model.UserDO;
import net.cloud.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserTest {
    @Autowired
    private UserService userService;
    @Test
    public void testUserDetail(){
        UserDO detail = userService.detail(1L);
        log.info(detail.toString());
        Assert.assertNotNull(detail);
    }
}
