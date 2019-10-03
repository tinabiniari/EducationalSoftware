package com.software.educational.web.controller;

import com.software.educational.data.model.User;
import com.software.educational.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @ModelAttribute("user")
    User getUser() {
        return new User();
    }


    //show registration page
    @GetMapping
    ModelAndView getRegistration(ModelAndView modelAndView) {
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    // save user if does not exist
    @PostMapping
    ModelAndView saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelAndView modelAndView) {
        if (userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.user", "This email already exists");
        }
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        User regUser = userService.saveUser(user);
        modelAndView.setViewName("registration");
        modelAndView.addObject("successMessage", "User " + regUser.getEmail() + " has being successfully registered");
        modelAndView.addObject("alert", "alert-success");

        return modelAndView;
    }
}
