package com.software.educational.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Progress {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long Id;


        private Long userId;


        private Long moduleId;


        private Long courseId;


        private boolean isCourseRead=false;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public boolean isCourseRead() {
        return isCourseRead;
    }

    public void setCourseRead(boolean courseRead) {
        isCourseRead = courseRead;
    }
}
