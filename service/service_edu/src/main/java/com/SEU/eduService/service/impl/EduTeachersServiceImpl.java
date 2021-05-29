package com.SEU.eduService.service.impl;

import com.SEU.eduService.entity.EduTeachers;
import com.SEU.eduService.mapper.EduTeachersMapper;
import com.SEU.eduService.service.EduTeachersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-23
 */
@Service
public class EduTeachersServiceImpl extends ServiceImpl<EduTeachersMapper, EduTeachers> implements EduTeachersService {
    @Override
    public List<EduTeachers> listTeachers() {
        QueryWrapper<EduTeachers> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeachers> eduTeachers = baseMapper.selectList(wrapper);
        return eduTeachers;
    }
}
