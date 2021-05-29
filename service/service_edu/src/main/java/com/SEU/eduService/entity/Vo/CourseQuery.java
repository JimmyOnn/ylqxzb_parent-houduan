package com.SEU.eduService.entity.Vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable {
    private String title;

    private String status;


    public CourseQuery() {
    }

    public CourseQuery(String title, String status) {
        this.title = title;
        this.status = status;
    }
}
