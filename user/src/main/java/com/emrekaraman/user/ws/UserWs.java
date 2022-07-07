package com.emrekaraman.user.ws;

import com.emrekaraman.user.business.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userws")
public class UserWs {

    private final UserService userService;

    public UserWs(UserService userService) {
        this.userService = userService;
    }
}
