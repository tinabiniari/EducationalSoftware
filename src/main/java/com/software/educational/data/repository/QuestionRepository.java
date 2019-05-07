package com.software.educational.data.repository;

import com.software.educational.data.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query(nativeQuery = true, value ="select * from question where module_id=?")
    List<Question> findQuestionsByModuleId(Long module_id);

    Question findQuestionByCourse_CourseId(Long courseId);


}
