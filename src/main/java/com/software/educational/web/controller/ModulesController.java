package com.software.educational.web.controller;

import com.software.educational.data.model.Course;
import com.software.educational.data.model.Module;
import com.software.educational.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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


    @GetMapping("/modules")
    public String getModules(Model model){
        model.addAttribute("modules",moduleService.getAllModules());
        return "modules";
    }

    @GetMapping(value = "/courses", params = {"id"})
    public ModelAndView getCoursesList(@RequestParam("id") long moduleId) {
        ModelAndView modelAndView = new ModelAndView();

        Module module = moduleService.getModulebyId(moduleId);
        modelAndView.addObject("module", module);
        modelAndView.addObject("courses",courseService.getCourseByModuleId(moduleId));
        modelAndView.setViewName("courses");
        return modelAndView;
    }

    @GetMapping(value = "/theory",params = {"courseId"})
    public ModelAndView getTheory(@RequestParam("courseId") long courseId){
        ModelAndView modelAndView = new ModelAndView();

        Course course=courseService.getCourseTheorybyId(courseId);
        modelAndView.addObject("course",course);
        modelAndView.addObject("courseId",courseId);
        modelAndView.addObject("theory",courseService.getCourseTheorybyId(courseId));
        modelAndView.setViewName("theory");
        return modelAndView;
    }

    @PostMapping(value = "/theory")
    public ModelAndView postTheory(HttpServletRequest request){
        String course = request.getParameter("courseId");
        long course_id=Long.parseLong(course);
        ModelAndView modelAndView=getTheory(course_id);
        Boolean isPreviousRead=progressService.getPreviousCourseProgress(course_id);

        if(!isPreviousRead){

            modelAndView.addObject("errorMessage","You must complete the test of the course in order to continue");
            return modelAndView;

        }
        return getTheory(course_id+1);
    }

}
