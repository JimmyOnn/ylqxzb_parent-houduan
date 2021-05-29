package com.SEU.eduService.service.impl;

import com.SEU.eduService.entity.EduChapter;
import com.SEU.eduService.entity.EduVideo;
import com.SEU.eduService.entity.chapter.ChapterVo;
import com.SEU.eduService.entity.chapter.VideoVO;
import com.SEU.eduService.mapper.EduChapterMapper;
import com.SEU.eduService.service.EduChapterService;
import com.SEU.eduService.service.EduVideoService;
import com.SEU.serviceBase.exceptionHandler.SEUException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据id查所有大标题
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //查视频
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        List<ChapterVo> finalList = new ArrayList<>();
        //封装大标题
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            //遍历小标题
            List<VideoVO> videoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    videoList.add(videoVO);
                }
            }
            chapterVo.setChildren(videoList);
        }
        //封装视频
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if (count > 0) {//有视频，不删除
            throw new SEUException(20001, "不能删除");
        } else {//没视频就删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
