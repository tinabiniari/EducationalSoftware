package com.software.educational.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Progress {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long Id;

        @NotNull
        @Column(name = "user_id")
        private Long userId;

        @NotNull
        @Column(name = "module_id")
        private Long moduleId;

        @NotNull
        @Column(name = "course_id")
        private Long courseId;

        @NotNull
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
