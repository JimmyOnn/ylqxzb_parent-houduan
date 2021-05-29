package com.SEU.eduService.service;

import com.SEU.eduService.entity.EduTeachers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-23
 */
public interface EduTeachersService extends IService<EduTeachers> {
    List<EduTeachers> listTeachers();
}
