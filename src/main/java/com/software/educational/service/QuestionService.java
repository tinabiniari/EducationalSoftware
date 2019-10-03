package com.software.educational.service;

import com.software.educational.data.model.Question;
import com.software.educational.data.repository.AnswerRepository;
import com.software.educational.data.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;


    public Question getQuestionByCourseId(Long id){ //returns question from each course
        return questionRepository.findQuestionByCourse_CourseId(id);
    }


    public Question getQuestionById(Long questionId){
        return questionRepository.findByQuestionId(questionId);
    }

    public List<Question> getQuestionbyModuleId(Long moduleId){
        return questionRepository.findQuestionsByModuleId(moduleId);
    }

    public List<Question> getRandomQuestions(HttpServletRequest httpServletRequest) {
        Set<String> strings = httpServletRequest.getParameterMap().keySet();
        List<Question> questionList = new ArrayList<>();
        List<String> targetList = new ArrayList<>(strings);
        targetList.remove(0);
        for (String str : targetList) {
            questionList.add(getQuestionById(Long.valueOf(str)));
        }
        return questionList;
    }
}
