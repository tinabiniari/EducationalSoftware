package com.software.educational.data.repository;

import com.software.educational.data.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {

    @Query(nativeQuery = true,value = "SELECT answer_id from answer where is_correct=false")
    public  List<Long> findFalseAnswersByIsCorrect();


    @Query(nativeQuery = true,value = "SELECT answer_id from answer where is_correct=true")
    public  List<Long> findCorrectAnswersByIsCorrect();



}
