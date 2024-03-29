package com.example.hackingproject.detailStock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DetailStockRESTAPIController {
    @RequestMapping(value = "/detailstock", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response
    , @RequestParam(value = "stockCode")String stockCode
    , @RequestParam(value = "stockName")String stockName) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("stockCode", stockCode);
        mav.addObject("stockName", stockName);

        mav.setViewName("detail_stock");
        return mav;
    }
}
