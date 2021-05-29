package com.SEU.eduService.service.impl;

import com.SEU.eduService.entity.EduCourse;
import com.SEU.eduService.entity.EduCourseDescription;
import com.SEU.eduService.entity.Vo.CourseInfoVo;
import com.SEU.eduService.entity.Vo.CoursePublishVo;
import com.SEU.eduService.mapper.EduCourseMapper;
import com.SEU.eduService.service.EduChapterService;
import com.SEU.eduService.service.EduCourseDescriptionService;
import com.SEU.eduService.service.EduCourseService;
import com.SEU.eduService.service.EduVideoService;
import com.SEU.serviceBase.exceptionHandler.SEUException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;



    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new SEUException(20001, "添加商品信息失败");
        }

        String cid = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);

        eduCourseDescriptionService.save(courseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new SEUException(20001,"修改商品信息失败！");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        //根据id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //删除描述
        eduCourseDescriptionService.removeById(courseId);
        //删除课程
        int result = baseMapper.deleteById(courseId);
        if(result == 0){
            throw new SEUException(20001,"删除失败！");
        }
    }
}
