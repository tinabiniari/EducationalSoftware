package com.software.educational.web.controller;

import com.software.educational.data.model.*;
import com.software.educational.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/test")
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
                return new ModelAndView("redirect:/theory?courseId=" + newCourseId);

            }
            ModelAndView md = new ModelAndView();
            md.addObject("successTitle", "Congratulations!");
            md.addObject("successMessage", "You have read all the courses from Module "
                    + moduleId + " . "  +
                    "<br> Take the test for the Module " + moduleId + " and check your knowledge!");
            md.addObject("moduleId", moduleId);
            md.setViewName("success");
            return md;

        }
        ModelAndView modelAndView = getTest(courseId);
        modelAndView.addObject("errorMessage", "Wrong answer! Please try again");
        modelAndView.addObject("alert", "alert alert-danger");

        return modelAndView;

    }

    @GetMapping(value = "/moduleTest", params = {"moduleId"})
    public ModelAndView getQuestionPerModule(@RequestParam("moduleId") long moduleId) {
        ModelAndView modelAndView = new ModelAndView();
        Module module = moduleService.getModulebyId(moduleId);
        List<Question> questionList=questionService.getQuestionbyModuleId(moduleId); //returns 10 random question from this module
        modelAndView.addObject("question", questionList);
        modelAndView.addObject("moduleId", moduleId);
        List<Long> falseAnswers = moduleTestService.getFalseAnswers();
        List<Object> newList = new ArrayList<Object>(falseAnswers.size());
        List<Long> correctAnswers = moduleTestService.getCorrectAnswers();
        List<Object> trueAnswers = new ArrayList<Object>(correctAnswers.size());
        modelAndView.addObject("trueList", trueAnswers);
        modelAndView.addObject("newList", newList);
        modelAndView.addObject("type", "radio");
        modelAndView.addObject("visibility", "submit-btn btn btn-info");
        modelAndView.addObject("moduleName", module.getModuleName());
        modelAndView.setViewName("module_test");
        return modelAndView;
    }
    @PostMapping(value = "/moduleTest")
    public ModelAndView submitTest(Principal principal, HttpServletRequest httpServletRequest,ModelAndView modelAndView) {
        User user = userService.findByEmail(principal.getName());
        String moduleId = httpServletRequest.getParameter("moduleId");

        long module_id = Long.parseLong(moduleId);
        Module module = moduleService.getModulebyId(module_id);
        String moduleName=module.getModuleName();
        Double myscore = moduleTestService.getScore(httpServletRequest);

        ModuleTest moduleTest = new ModuleTest();
        moduleTest.setUserId(user.getUserId());
        moduleTest.setModuleId(module_id);
        moduleTest.setScore(myscore);
        moduleTestService.saveTestResults(moduleTest);

        modelAndView.addObject("question",questionService.getRandomQuestions(httpServletRequest));
        modelAndView.addObject("newList", moduleTestService.getWrongAnswers(httpServletRequest));
        modelAndView.addObject("trueList", moduleTestService.getCorrect(httpServletRequest));
        modelAndView.addObject("moduleName",moduleName);

        modelAndView.addObject("type", "hidden");
        modelAndView.addObject("visibility", "hide");
        modelAndView.setViewName("module_test");
        return moduleTestService.handleResults(user.getUserId(),myscore,module_id,modelAndView);




    }

    @GetMapping(value = "/testList")
    public ModelAndView getTestList(ModelAndView modelAndView) {
        modelAndView.addObject("modules", moduleService.getAllModules());
        modelAndView.setViewName("test_list");
        return modelAndView;
    }




}
