package com.software.educational.service;

import com.software.educational.data.model.Course;
import com.software.educational.data.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;


    public Iterable<Course> getAllCourses(){
        return courseRepository.findAll();
    }


     public List<Course> getCourseByModuleId(Long moduleId) {
        return courseRepository.findCourseByModuleId(moduleId,sortById());

    }

    public Long countAllCourses(){return courseRepository.count();}

    public Course findById(long id){
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find course with id " + id));
    }

    public Integer getNumberofCoursesInModule(Long module_id){
        return courseRepository.countCourseByModuleId(module_id);
    }

    public Sort sortById(){
        return new Sort(Sort.Direction.ASC,"courseId");
    }

    public Course getCourseTheorybyId(Long id){
        return courseRepository.getOne(id);
    }


}
