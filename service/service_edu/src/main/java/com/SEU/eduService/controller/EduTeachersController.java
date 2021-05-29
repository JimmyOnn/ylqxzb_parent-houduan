package com.SEU.eduService.controller;


import com.SEU.commonUtils.R;
import com.SEU.eduService.entity.EduTeachers;
import com.SEU.eduService.entity.Vo.TeacherQuery;
import com.SEU.eduService.service.EduTeachersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/eduService/teachers")
@CrossOrigin
public class EduTeachersController {

    @Autowired
    private EduTeachersService teachersService;

    //查所有
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeachers> teachers = teachersService.list(null);
        return R.ok().data("items",teachers);
    }

    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teachersService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeachers> pageTeacher = new Page<>(current,limit);
        teachersService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeachers> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeachers> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduTeachers> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(level!=null){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.eq("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_create");
        teachersService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeachers> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeachers eduTeachers){
        boolean save = teachersService.save(eduTeachers);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeachers eduTeacher = teachersService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeachers eduTeachers){
        boolean flag = teachersService.updateById(eduTeachers);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

