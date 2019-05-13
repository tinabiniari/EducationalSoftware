package com.software.educational.service;

import com.software.educational.data.model.ModuleTest;
import com.software.educational.data.repository.AnswerRepository;
import com.software.educational.data.repository.ModuleTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ModuleTestService {

    @Autowired
    ModuleTestRepository moduleTestRepository;
    @Autowired
    AnswerRepository answerRepository;

    public ModuleTest saveResults(ModuleTest moduleTest){
        return moduleTestRepository.save(moduleTest);
    }

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

//        correctAnswersString.retainAll(getUserInputValues(httpServletRequest));
        return correctAnswersString;
    }

    public Double getScore(HttpServletRequest httpServletRequest){
        List<Object> trueAnswersStrings = new ArrayList<Object>(getFalseAnswers().size());
        for (Long myInt : getCorrectAnswers()) {
            trueAnswersStrings.add(String.valueOf(myInt));
        }

        trueAnswersStrings.retainAll(getUserInputValues(httpServletRequest));
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");

        return ((double)((trueAnswersStrings.size()*100)/(getUserInputValues(httpServletRequest).size()-1)));
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



}
