package com.example.hackingproject.TestController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String index() {
        return "test_index";
    }

//    @RequestMapping(value = "/scheduled", method = RequestMethod.GET)
//    public String scheduled() {
//        return "scheduled";
//    }

    @RequestMapping(value = "/test_map", method = RequestMethod.GET)
    public String map() {
        return "test_map";
    }

    @RequestMapping(value = "/test_sample", method = RequestMethod.GET)
    public String sample() {
        return "test_sample";
    }
    
    @RequestMapping(value = "/test_login", method = RequestMethod.GET)
    public String login() {
        return "test_login";
    }
    
    @RequestMapping(value = "/test_signup", method = RequestMethod.GET)
    public String signup() {
        return "test_signup";
    }

    @RequestMapping(value = "/test_chart", method = RequestMethod.GET)
    public String chart() {
        return "test_chart";
    }

}
