package com.software.educational.data.repository;

import com.software.educational.data.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

     Progress findByUserIdAndCourseId(Long userId, Long courseId);

    @Query(nativeQuery = true, value = "SELECT COUNT(user_id) FROM progress WHERE user_id=? AND course_id=?")
     Integer countByUserIdAndCourseId(Long user_id, Long course_id);


    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM progress where user_id=?;")
     Integer countReadCourses(Long user_id);

    @Query(nativeQuery = true,value = "SELECT is_course_read FROM progress WHERE course_id=?")
     Boolean checkPreviousCourseByCourseId(Long course_id);


    @Query(nativeQuery = true,value = "SELECT progress.course_id as courseId,course_name as courseName from progress inner join course on course.course_id=progress.course_id where user_id=? and is_course_read=true order by courseId")
    ArrayList findMyProgress(Long userId);








}
