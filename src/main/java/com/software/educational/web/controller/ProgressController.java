package com.software.educational.web.controller;

import com.software.educational.data.model.ModuleTest;
import com.software.educational.data.model.Progress;
import com.software.educational.data.model.User;
import com.software.educational.service.ModuleTestService;
import com.software.educational.service.ProgressService;
import com.software.educational.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;


@Controller
public class ProgressController {

    @Autowired
    ProgressService progressService;
    @Autowired
    UserService userService;
    @Autowired
    ModuleTestService moduleTestService;



    @GetMapping(value = "/myProgress")
    public ModelAndView getProgress(Principal principal, ModelAndView modelAndView, @ModelAttribute("progress")Progress progress, @ModelAttribute("moduleTest")ModuleTest moduleTest){
        User user=userService.findByEmail(principal.getName());
        ArrayList list=progressService.getProgress(user.getUserId());

        modelAndView.addObject("test",moduleTestService.getCompletedTest(user.getUserId()));
        modelAndView.addObject("list",list);

        modelAndView.setViewName("progress");
        return modelAndView;
    }
}
