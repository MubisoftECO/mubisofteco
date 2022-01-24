package org.eco.mubisoft.good_and_cheap.user.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;

import java.util.List;

@Slf4j
public class UserProducer implements Runnable {
    private UserService userService;
    private String city;

    public UserProducer(String city, UserService userService) {
        this.userService = userService;
        this.city = city;
    }

    @Override
    public void run() {
        List<Long> list = userService.getIdListFromDB(city);
        list.forEach(v -> userService.setIdListToBuffer(v));
    }
}
