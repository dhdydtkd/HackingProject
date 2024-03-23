package com.example.hackingproject.TestController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

//    @RequestMapping(value = "/scheduled", method = RequestMethod.GET)
//    public String scheduled() {
//        return "scheduled";
//    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String map() {
        return "map";
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public String sample() {
        return "sample";
    }

}
