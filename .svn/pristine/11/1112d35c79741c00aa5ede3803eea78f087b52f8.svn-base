package com.cmcc.seed.controller;

import com.cmcc.seed.dao.UserMapper;
import com.cmcc.seed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmcc on 16/1/7.
 */
@Controller
@RequestMapping("/seed/")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("index")
    public String test(Model model){
        model.addAttribute("message","test");
        return "index";
    }

    @RequestMapping(value = "index1",method = RequestMethod.GET)
    @ResponseBody
    public Map test1(){
        Map m = new HashMap();
        User u = userMapper.selectByPrimaryKey(1l);
        m.put("a",u);
        return m;
    }
}
