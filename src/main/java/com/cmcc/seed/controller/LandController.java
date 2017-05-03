package com.cmcc.seed.controller;

import com.cmcc.seed.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cmcc on 16/1/11.
 */
@Controller
@RequestMapping("/land")
public class LandController {

    @Autowired
    private LandService landService;

}
