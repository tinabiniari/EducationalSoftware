package com.software.educational.data.repository;

import com.software.educational.data.model.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findCourseByModuleId(Long moduleId, Sort sort);


    @Query(nativeQuery = true, value ="SELECT COUNT(course_id) FROM course where module_id=?")
    public Integer countCourseByModuleId(Long module_id);





}
