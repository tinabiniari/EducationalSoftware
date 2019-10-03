package com.software.educational.service;

import com.software.educational.data.model.Course;
import com.software.educational.data.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ProgressService progressService;


     public List<Course> getCourseByModuleId(Long moduleId) {
        return courseRepository.findCourseByModuleId(moduleId,sortById());

    }

    public Long countAllCourses(){return courseRepository.count();}

    public Course findById(long id){
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find course with id " + id));
    }



    public Sort sortById(){
        return new Sort(Sort.Direction.ASC,"courseId");
    }

    public Course getCourseTheorybyId(Long id){
        return courseRepository.getOne(id);
    }


    public Map<Long,String> progressMap(Long moduleId,Long userId){
        long minCourse = getCourseByModuleId(moduleId).stream().min(Comparator.comparing(Course::getCourseId)).get().getCourseId();
        List<Course> courses = getCourseByModuleId(moduleId);
        Map<Long, String> map = new HashMap<>();
        map.put(minCourse, "isAvailable");
        for (Course c : courses) {
            Long id = c.getCourseId();
            id++;
            try {

                if (progressService.getCourseProgress(id-1,userId)) {
                    map.put(id, "isAvailable");

                } else {
                    if (!progressService.getCourseProgress(id-1, userId))
                        if (id > minCourse) {
                            map.put(id, "isDisabled");
                        }

                }

            } catch (NullPointerException e) {

                    map.put(id , "isDisabled");



            }
        }
        return map;
    }


}
