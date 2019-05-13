package com.software.educational.service;

import com.software.educational.data.model.Progress;
import com.software.educational.data.repository.ProgressRepository;
import com.software.educational.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProgressService {

    @Autowired
    ProgressRepository progressRepository;
    @Autowired
    UserRepository userRepository;

    public Progress saveProgress(Progress progress) {

        if (progressRepository.countByUserIdAndCourseId(progress.getUserId(), progress.getCourseId()) > 0) {
            Progress oldProgress = progressRepository.findByUserIdAndCourseId(progress.getUserId(), progress.getCourseId());
            progress.Id = oldProgress.getId();

        }
        return progressRepository.save(progress);
    }

    public Integer countCourses(Long user_id){
        return progressRepository.countReadCourses(user_id);
    }

    public Boolean getPreviousCourseProgress(Long course_id){
        return progressRepository.checkPreviousCourseByCourseId(course_id);
    }

    public ArrayList getProgress(Long userId){
        return progressRepository.findMyProgress(userId);
    }







}
