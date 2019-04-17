package com.software.educational.web.controller;

import com.software.educational.data.model.User;
import com.software.educational.service.ModuleService;
import com.software.educational.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    ModuleService moduleService;

    @ModelAttribute("user")
    User getUser() {
        return new User();
    }

    @GetMapping("/index")
    ModelAndView getIndex(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("welcomeMessage","Welcome " + user.getFirstName() + " " + user.getLastName() + " ");
        modelAndView.setViewName("index");
        return modelAndView;
    }

}
