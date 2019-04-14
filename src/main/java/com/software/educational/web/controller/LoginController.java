package com.software.educational.web.controller;

import com.software.educational.data.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @ModelAttribute("user")
    User getUser() {
        return new User();
    }

    @GetMapping("")
    ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/login")
    ModelAndView getLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
