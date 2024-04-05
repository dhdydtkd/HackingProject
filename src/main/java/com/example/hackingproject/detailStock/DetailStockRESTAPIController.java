package com.example.hackingproject.detailStock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.NumberFormat;

import com.example.hackingproject.mypage.dto.MyUserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.detailStock.service.DetailStockService;
import com.example.hackingproject.detailStock.vo.DetailStockVO;

import java.security.PrivateKey;

@Controller
public class DetailStockRESTAPIController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private DetailStockService detailStockService;
	
    @Value("${rsa_modulus_key}")
    private String RSAModulus ; // 개인키 session key
    @Value("${rsa_exponent_key}")
    private String RSAExponent ; // 개인키 session key
	
    @RequestMapping(value = "/detailstock", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response
    , @RequestParam(value = "stockCode")String stockCode
    , @RequestParam(value = "stockName")String stockName) {
    	
    	MyUserData user = new MyUserData();
    	DetailStockVO detailStockVO = new DetailStockVO();
        ModelAndView mav = new ModelAndView();
        String JWTToken = jwtTokenUtil.GetJWTCookie(request);
        int unit = 0;
        HttpSession session = request.getSession();
        String RSA_Modulus = (String)session.getAttribute(RSAModulus);
        String RSA_Exponent = (String)session.getAttribute(RSAExponent);
        String user_id = jwtTokenUtil.getUserIdFromToken(JWTToken);
        request.setAttribute(RSAModulus, RSA_Modulus); // rsa modulus 를 request 에 추가
        request.setAttribute(RSAExponent, RSA_Exponent); // rsa exponent 를 request 에 추가
        System.out.println("detailstock");
        System.out.println(stockCode);
        System.out.println(stockName);
        
        user.setUSER_ID(user_id);
        detailStockVO.setUserId(user_id);
        detailStockVO = detailStockService.haveStock(detailStockVO);
        user = detailStockService.getUserData(user);
        System.out.println("컨트롤러 : "+user.getUSER_ID()+"\nBalance : "+user.getACCOUNT_BALANCE()+"Stock Code : "+ stockCode);
        
        if (stockCode != null) {
            switch (stockCode) {
                case "AAPL":
                    unit = detailStockVO.getAAPL();
                    break;
                case "AMZN":
                    unit = detailStockVO.getAMZN();
                    break;
                case "FB":
                    unit = detailStockVO.getFB();
                    break;
                case "GOOGL":
                    unit = detailStockVO.getGOOGL();
                    break;
                default:
                    unit = detailStockVO.getMSFT();
                    break;
            }
        }

        String user_balance;// = String.valueOf(user.getACCOUNT_BALANCE());
        user_balance = NumberFormat.getNumberInstance().format(user.getACCOUNT_BALANCE());
        System.out.println("주식 : "+stockCode + " 개수 :"+unit);
        mav.addObject("haveStock", unit);
        mav.addObject("id", user.getUSER_ID());
        mav.addObject("balance", user_balance);
        mav.addObject("nm", user.getUSER_NM());
        mav.addObject("stockCode", stockCode);
        mav.addObject("stockName", stockName);
        
        mav.setViewName("detail_stock");
        return mav;
    }
}
