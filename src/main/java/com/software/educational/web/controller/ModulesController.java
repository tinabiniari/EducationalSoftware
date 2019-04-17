package com.software.educational.web.controller;

import com.software.educational.data.model.Course;
import com.software.educational.data.model.Module;
import com.software.educational.service.CourseService;
import com.software.educational.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModulesController {

    @Autowired
    ModuleService moduleService;
    @Autowired
    CourseService courseService;


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

    @GetMapping(value = "/theory",params = {"id"})
    public ModelAndView getTheory(@RequestParam("id") long courseId){
        ModelAndView modelAndView = new ModelAndView();

        Course course=courseService.getCourseTheorybyId(courseId);
        modelAndView.addObject("course",course);
        modelAndView.addObject("theory",courseService.getCourseTheorybyId(courseId));
        modelAndView.setViewName("theory");
        return modelAndView;
    }
}
