package com.software.educational.web.controller;

import com.software.educational.data.model.Course;
import com.software.educational.data.model.Module;
import com.software.educational.data.model.User;
import com.software.educational.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Comparator;

@Controller
public class ModulesController {

    @Autowired
    ModuleService moduleService;
    @Autowired
    CourseService courseService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    ProgressService progressService;
    @Autowired
    UserService userService;


    @GetMapping("/modules")
    public String getModules(Model model) {
        model.addAttribute("modules", moduleService.getAllModules());
        return "modules";
    }

    @GetMapping(value = "/courses", params = {"id"})
    public ModelAndView getCoursesList(@RequestParam("id") long moduleId, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findByEmail(principal.getName());
        Module module = moduleService.getModulebyId(moduleId);
        modelAndView.addObject("module", module);
        modelAndView.addObject("map", courseService.progressMap(moduleId,user.getUserId()));
        modelAndView.addObject("courses", courseService.getCourseByModuleId(moduleId));
        modelAndView.setViewName("courses");
        return modelAndView;
    }

    @GetMapping(value = "/theory", params = {"courseId"})
    public ModelAndView getTheory(@RequestParam("courseId") long courseId) {
        ModelAndView modelAndView = new ModelAndView();
        Course course = courseService.getCourseTheorybyId(courseId);
        modelAndView.addObject("course", course);
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("theory", courseService.getCourseTheorybyId(courseId));
        modelAndView.setViewName("theory");
        return modelAndView;
    }

    @PostMapping(value = "/theory")
    public ModelAndView postTheory(HttpServletRequest request, Principal principal) {
        String course = request.getParameter("courseId");
        long course_id = Long.parseLong(course);
        User user = userService.findByEmail(principal.getName());
        long moduleId = courseService.findById(course_id).getModuleId();
        String moduleName = moduleService.getModulebyId(moduleId).getModuleName();
        long maxCourse = courseService.getCourseByModuleId(moduleId).stream().max(Comparator.comparing(Course::getCourseId)).get().getCourseId();
        ModelAndView modelAndView = getTheory(course_id);
        Boolean isCourseRead = progressService.getCourseProgress(course_id, user.getUserId());
        try {
            if (!isCourseRead) {
                modelAndView.addObject("errorMessage", "You must complete the test of the course in order to continue");
                modelAndView.addObject("alert", "alert-danger");

                return modelAndView;
            }
        } catch (NullPointerException e) {
            modelAndView.addObject("errorMessage", "You must complete the test of the course in order to continue");
            modelAndView.addObject("alert", "alert-danger");
            return modelAndView;
        }
        if (course_id < maxCourse) {
            return getTheory(course_id + 1);

        }
        ModelAndView md = new ModelAndView();
        md.addObject("successTitle", "Congratulations!");
        md.addObject("successMessage", "You have read all the courses from Module " + moduleId + " : " + moduleName + "." +
                "<br> Take the test for the Module " + moduleId + " and check your knowledge!");
        md.addObject("moduleId", moduleId);
        md.setViewName("success");
        return md;

    }

}
