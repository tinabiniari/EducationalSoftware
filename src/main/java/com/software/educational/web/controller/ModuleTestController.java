package com.software.educational.web.controller;

import com.software.educational.service.AnswerService;
import com.software.educational.service.ModuleService;
import com.software.educational.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ModuleTestController {

    @Autowired
    QuestionService questionService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    AnswerService answerService;
//    @GetMapping("/modulesList")
//    public String getModules(Model model){
//        model.addAttribute("modules",moduleService.getAllModules());
//        return "modules";
//    }

//    @GetMapping(value = "/moduleTest", params = {"moduleId"})
//    public ModelAndView getQuestionPerModule(@RequestParam("moduleId") long moduleId, ModelAndView modelAndView) {
//        Module module = moduleService.getModulebyId(moduleId);
//        modelAndView.addObject("question", questionService.getQuestionbyModuleId(module.getModuleId()));
//        modelAndView.setViewName("module_test");
//        return modelAndView;
//    }
}

//    @PostMapping
//    public String submitAnswers()
//}
