package com.cmcc.seed.controller;

import com.cmcc.seed.model.Land;
import com.cmcc.seed.model.User;
import com.cmcc.seed.service.LandService;
import com.cmcc.seed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by cmcc on 16/1/11.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LandService landService;

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return user;
    }

    @RequestMapping(value = "/{userId}/lands", method = RequestMethod.GET)
    @ResponseBody
    public List<Land> getUserLandsInfo(@PathVariable long userId) {
        List<Land> lands = landService.getLandsByUserId(userId);
        return lands;
    }
}
