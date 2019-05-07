package com.software.educational.service;

import com.software.educational.data.model.ModuleTest;
import com.software.educational.data.repository.ModuleTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ModuleTestService {

    @Autowired
    ModuleTestRepository moduleTestRepository;

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
//
//    public Object getAnswer(HttpServletRequest httpServletRequest){
//        Set<String> keys=httpServletRequest.getParameterMap().keySet();
//        Object[] keysArray=keys.toArray();
//        Collection<String[]> values = httpServletRequest.getParameterMap().values();
//        Object[][] valuesArray = values.toArray(new Object[0][]);
//        Map<Object,Object> answerMap=new HashMap<>();
//
//        for (Object[] :myValue :valuesArray){
//            for (Object:myKey:keysArray) {
//                answerMap.put()
//            }
//        }
//        answerMap.put(keysArray,valuesArray);
//
//    }


    public static Map<String,String> getAnswerMap(HttpServletRequest httpServletRequest){
        Collection<String[]> values = httpServletRequest.getParameterMap().values();
        Set<String> keys=httpServletRequest.getParameterMap().keySet();
        Object[] keysArray=keys.toArray();
        Map<String,String> answerMap=new HashMap<>();

        int total=0;

        for (String[] myValue : values) {
            answerMap.put(String.valueOf(keysArray[total]),myValue[0]);

            total++;
        }

        return answerMap;
    }

    public static ArrayList<Object> getKeysFromValue(Map hm, Object value) {
        ArrayList<Object> myList=new ArrayList();
        myList.add("list");
        for (Object o : hm.keySet()) {
            if (hm.get(o.toString()).equals(value)) {
                myList.add(o);

            }
        }
        return myList;
    }



    public static Double getScore(HttpServletRequest httpServletRequest){
        Collection<String[]> values = httpServletRequest.getParameterMap().values();
        Object[][] valuesArray = values.toArray(new Object[0][]);
        Set<String> keys=httpServletRequest.getParameterMap().keySet();
        Object[] keysArray=keys.toArray();
        Map<Object,Object> answerMap=new HashMap<>();

        int count=0;
        int total=0;
        for (Object[] myValue : valuesArray) {

            if (myValue[0].equals("true")) {
                count++;

            }
            total++;
        }
        getAnswerMap(httpServletRequest);
        total--;
        return (double) ((count*100)/total);
    }
}
