package com.software.educational.service;

import com.software.educational.data.model.Answer;
import com.software.educational.data.model.Question;
import com.software.educational.data.repository.AnswerRepository;
import com.software.educational.data.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;


    public Question getQuestionByCourseId(Long id){ //returns question from each course
        return questionRepository.findQuestionByCourse_CourseId(id);
    }

    public  List<Answer> getAnswersByQuestionId(Long id) {
        return answerRepository.findAnswerByQuestionId(id);
    }

    public List<Question> getQuestionbyModuleId(Long moduleId){
        return questionRepository.findQuestionsByModuleId(moduleId);
    }
}
