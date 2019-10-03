package com.software.educational.web.controller;

import com.software.educational.data.model.User;
import com.software.educational.service.*;
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

    @Autowired
    ModuleTestService moduleTestService;

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
        Double percentageCourses = ((progressService.countCourses(user.getUserId())) / (courseService.countAllCourses())) * 100;
        modelAndView.addObject("percentageCourses", percentageCourses.intValue());
        Double percentageTest=((moduleTestService.countCompletedTests(user.getUserId()))/(moduleService.countModules()))*100;
        modelAndView.addObject("percentageTests", percentageTest.intValue());
        modelAndView.addObject("tests","You have completed "
                +moduleTestService.countCompletedTests(user.getUserId()).intValue()+
                " <b>tests</b> out of " + moduleService.countModules()+".");

        modelAndView.addObject("courses","You have completed "
                +progressService.countCourses(user.getUserId()).intValue()+
                " <b>courses</b> out of " + courseService.countAllCourses()+".");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/help")
    ModelAndView getHelp(ModelAndView modelAndView) {
        modelAndView.setViewName("help");
        return modelAndView;
    }


}
