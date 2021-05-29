package com.SEU.eduService.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称")
    private String name;

    @ApiModelProperty(value = "头衔 1高级 2首席")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2021-05-22 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2021-05-22 10:10:10")
    private String end;
}
