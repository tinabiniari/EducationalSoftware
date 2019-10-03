package com.software.educational.service;

import com.software.educational.data.model.ModuleTest;
import com.software.educational.data.repository.AnswerRepository;
import com.software.educational.data.repository.ModuleTestRepository;
import com.software.educational.data.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ModuleTestService {

    @Autowired
    ModuleTestRepository moduleTestRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ProgressRepository progressRepository;


    public ModuleTest saveTestResults(ModuleTest moduleTest) {

        if (moduleTestRepository.countByUserIdAndModuleId(moduleTest.getUserId(), moduleTest.getModuleId()) > 0) {
            ModuleTest oldTestResults = moduleTestRepository.findByUserIdAndModuleId(moduleTest.getUserId(), moduleTest.getModuleId());
            moduleTest.Id = oldTestResults.getId();

        }
        return moduleTestRepository.save(moduleTest);
    }


    public static List<Object> getUserInputValues(HttpServletRequest httpServletRequest){
        Collection<String[]> values = httpServletRequest.getParameterMap().values();
        Set<String> keys=httpServletRequest.getParameterMap().keySet();
        Object[] keysArray=keys.toArray();
        Map<String,String> answerMap=new HashMap<>();

        int total=0;

        for (String[] myValue : values) {
            answerMap.put(String.valueOf(keysArray[total]),myValue[0]);

            total++;
        }

        Collection<String> mapValues = answerMap.values();
        List<Object> userInput = new ArrayList<>(mapValues);

        return userInput;
    }

    public List<Object> getWrongAnswers(HttpServletRequest httpServletRequest){
        List<Object> falseAnswersStrings = new ArrayList<Object>(getFalseAnswers().size());
        for (Long myInt : getFalseAnswers()) {
            falseAnswersStrings.add(String.valueOf(myInt));
        }

        falseAnswersStrings.retainAll(getUserInputValues(httpServletRequest));
        return falseAnswersStrings;
    }

    public List<Object> getCorrect(HttpServletRequest httpServletRequest){
        List<Object> correctAnswersString = new ArrayList<Object>(getCorrectAnswers().size());
        for (Long myInt : getCorrectAnswers()) {
            correctAnswersString.add(String.valueOf(myInt));
        }

        return correctAnswersString;
    }

    public Double getScore(HttpServletRequest httpServletRequest){
        List<Object> trueAnswersStrings = new ArrayList<Object>(getFalseAnswers().size());
        for (Long myInt : getCorrectAnswers()) {
            trueAnswersStrings.add(String.valueOf(myInt));
        }

        trueAnswersStrings.retainAll(getUserInputValues(httpServletRequest));

        return ((double)((trueAnswersStrings.size()*100)/(getUserInputValues(httpServletRequest).size()-1)));
    }

    public ModelAndView handleResults(Long userId,Double myscore,Long module_id, ModelAndView modelAndView) {
        if (myscore < 50) {
            modelAndView.addObject("resultMessage", "You failed Test " + module_id.intValue() + " with score " + myscore.intValue() + "%.<br> Please read again the theory of the module <br> (The courses of this module will be set to non-read)<br>Below you can see the answers");
            progressRepository.setProgressFalse(false,userId,module_id);
            modelAndView.addObject("moduleId", module_id);
            modelAndView.addObject("alert", "alert-danger");
            modelAndView.addObject("buttonMessage", "Check again the courses");
            return modelAndView;
        }
        if (myscore >= 50) {
            modelAndView.addObject("resultMessage", "Congratulations! <br> You passed Test " + module_id.intValue() + " with score " + myscore.intValue() + "%. <br> Keep up the good work!<br>Below you can see the answers");
            modelAndView.addObject("moduleId", module_id + 1);
            modelAndView.addObject("alert", "alert-success");

            modelAndView.addObject("buttonMessage", "Continue");
            return modelAndView;
        }
        return modelAndView;
    }

    public  List<Long> getFalseAnswers(){
        return answerRepository.findFalseAnswersByIsCorrect();
    }
    public  List<Long> getCorrectAnswers(){
        return answerRepository.findCorrectAnswersByIsCorrect();
    }

    public Iterable<ModuleTest> getCompletedTest(Long userId){
        return moduleTestRepository.findByUserId(userId);
    }

    public Double countCompletedTests(Long user_id){
        return moduleTestRepository.countCompletedTests(user_id);
    }

}
