package org.eco.mubisoft.good_and_cheap.user.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class UserConsumer implements Callable<List<Long>> {

    private UserService userService;

    public UserConsumer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<Long> call() throws Exception {
        List<Long> list = null;
        list = userService.getIdListFromBuffer();
        Thread.sleep(200);
        return list;
    }
}
