package com.software.educational.web.controller;

import com.software.educational.data.model.*;
import com.software.educational.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    CourseService courseService;
    @Autowired
    ProgressService progressService;
    @Autowired
    UserService userService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ModuleTestService moduleTestService;

    @GetMapping(value = "/test", params = {"courseId"})
    public ModelAndView getTest(@RequestParam("courseId") long courseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("question", questionService.getQuestionByCourseId(courseId));
        modelAndView.addObject("courseId", courseId);
        modelAndView.setViewName("test");
        return modelAndView;
    }

    @GetMapping(value = "/moduleTest", params = {"moduleId"})
    public ModelAndView getQuestionPerModule(@RequestParam("moduleId") long moduleId) {
        ModelAndView modelAndView=new ModelAndView();
        Module module = moduleService.getModulebyId(moduleId);
        modelAndView.addObject("question", questionService.getQuestionbyModuleId(module.getModuleId()));
        modelAndView.addObject("moduleId",moduleId);
        List<Long> falseAnswers=moduleTestService.getFalseAnswers();
        List<Object> newList = new ArrayList<Object>(falseAnswers.size());
        List<Long> correctAnswers=moduleTestService.getCorrectAnswers();
        List<Object> trueAnswers = new ArrayList<Object>(correctAnswers.size());


        modelAndView.addObject("trueList",trueAnswers);
        modelAndView.addObject("newList",newList);
        modelAndView.addObject("moduleName",module.getModuleName());
        modelAndView.setViewName("module_test");
        return modelAndView;
    }

    @GetMapping(value = "/testList")
    public ModelAndView getTestList(ModelAndView modelAndView){
        modelAndView.addObject("modules",moduleService.getAllModules());
        modelAndView.setViewName("test_list");
        return modelAndView;
    }

    @PostMapping(value = "/moduleTest")
    public ModelAndView submitTest(@Valid @ModelAttribute("answer")Answer answer, Principal principal, HttpServletRequest httpServletRequest, BindingResult bindingResult){
        User user=userService.findByEmail(principal.getName());
        String moduleId = httpServletRequest.getParameter("moduleId");
        long module_id=Long.parseLong(moduleId);
        ModuleTest moduleTest=new ModuleTest();

        Double myscore=moduleTestService.getScore(httpServletRequest);
        moduleTest.setUserId(user.getUserId());
        moduleTest.setModuleId(module_id);
        moduleTest.setScore(myscore);
        moduleTestService.saveTestResults(moduleTest);
        ModelAndView modelAndView=getQuestionPerModule(module_id);
        modelAndView.addObject("newList",moduleTestService.getWrongAnswers(httpServletRequest));
        modelAndView.addObject("trueList",moduleTestService.getCorrect(httpServletRequest));
        modelAndView.setViewName("module_test");
        if(bindingResult.hasErrors()){
            return modelAndView;
        }


        if(myscore < 50){
            modelAndView.addObject("resultMessage","You failed Test " +moduleId+ "with score "+myscore+".<br> Please read again the theory of the module <br> (The courses of this module will be set to non-read)");
            modelAndView.addObject("moduleId",module_id);
            modelAndView.addObject("buttonMessage","Check again the courses");
            return modelAndView;
        }
        if(myscore>50){
            modelAndView.addObject("resultMessage","Congratulations! <br> You passed Test " +moduleId+ " with score " +myscore+ "% <br> Keep up the good work!");
            modelAndView.addObject("moduleId",module_id+1);
            modelAndView.addObject("buttonMessage","Continue");
            return modelAndView;
        }


        return modelAndView;


    }


    @PostMapping(value="/test")
    public ModelAndView submitAnswer(@RequestParam("courseId") long courseId, @RequestParam("questionId") long questionId, @ModelAttribute("question") Question question, HttpServletRequest httpServletRequest, Principal principal) {
        String value = httpServletRequest.getParameter(String.valueOf(questionId));
        long moduleId = courseService.findById(courseId).getModuleId();
        long maxCourse = courseService.getCourseByModuleId(moduleId).stream().max(Comparator.comparing(Course::getCourseId)).get().getCourseId();
        User user = userService.findByEmail(principal.getName());
        Progress progress = new Progress();
        progress.setUserId(user.getUserId());
        progress.setModuleId(moduleId);
        progress.setCourseId(courseId);
        progress.setCourseRead(true);



        if ((value.equals("true"))) {
            progressService.saveProgress(progress);
            if (courseId < maxCourse) {
                long newCourseId = courseId + 1;
                return new ModelAndView("redirect:/theory?id=" + newCourseId);

            }
            long nextModule = moduleId + 1;
            return new ModelAndView("redirect:/courses?id="+nextModule);

        }
        ModelAndView modelAndView=getTest(courseId);
        modelAndView.addObject("errorMessage","Wrong answer! Please try again");
        return modelAndView;

    }
}
