package com.software.educational.data.repository;

import com.software.educational.data.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    public Progress findByUserIdAndCourseId(Long userId, Long courseId);

    @Query(nativeQuery = true, value = "SELECT COUNT(user_id) FROM progress WHERE user_id=? AND course_id=?")
    public Integer countByUserIdAndCourseId(Long user_id, Long course_id);


    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM progress where user_id=?;")
    public Integer countReadCourses(Long user_id);

    @Query(nativeQuery = true,value = "SELECT is_course_read FROM progress WHERE course_id=?")
    public Boolean checkPreviousCourseByCourseId(Long course_id);



}
