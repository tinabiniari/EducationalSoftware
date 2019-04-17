package com.software.educational.data.repository;

import com.software.educational.data.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findCourseByModuleId(Long moduleId);



//    @Query(nativeQuery = true, value = "SELECT  *  FROM course WHERE  module_id=?")
//    public abstract Iterable<Course> findCourseByModuleId();

}
