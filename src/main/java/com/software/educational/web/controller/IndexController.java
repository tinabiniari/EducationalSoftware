package com.software.educational.web.controller;

import com.software.educational.data.model.User;
import com.software.educational.service.CourseService;
import com.software.educational.service.ModuleService;
import com.software.educational.service.ProgressService;
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

    @Autowired
    CourseService courseService;

    @Autowired
    ProgressService progressService;


    @ModelAttribute("user")
    User getUser() {
        return new User();
    }

    @GetMapping("/index")
    ModelAndView getIndex(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("welcomeMessage", "Welcome " + user.getFirstName() + " ");
        modelAndView.addObject("modules", moduleService.getAllModules());
        modelAndView.addObject("courses","You have completed " +progressService.countCourses(user.getUserId())+ " courses out of " + courseService.countAllCourses()+".");
        modelAndView.setViewName("index");
        return modelAndView;
    }



}
