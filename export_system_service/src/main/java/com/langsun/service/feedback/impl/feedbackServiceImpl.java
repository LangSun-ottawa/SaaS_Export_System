package com.langsun.service.feedback.impl;

import com.github.pagehelper.PageInfo;
import com.langsun.dao.feedback.FeedbackDao;
import com.langsun.domain.feedback.Feedback;
import com.langsun.service.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author slang
 * @date 2020-11-08 15:13
 * @Param $
 * @return $
 **/
@Service
public class feedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;


    @Override
    public List<Feedback> findAll(int state, String companyId) {
        List<Feedback> list = feedbackDao.findAll(state, companyId);
        return list;
    }

    @Override
    public List<Feedback> unread(int state) {
        List<Feedback> unreadIdea = feedbackDao.unread(state);
        return unreadIdea;
    }

    @Override
    public void save(Feedback feedback) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        feedback.setDate(simpleDateFormat.format(new Date()));
        feedbackDao.save(feedback);

    }

    @Override
    public void deleteById(String id) {
        feedbackDao.deleteById(id);
    }

    @Override
    public Feedback messageChecked(String feedbackId) {
        Feedback feedback = feedbackDao.findByFeedbackId(feedbackId);
        feedbackDao.edit(1,feedbackId);
        return feedback;
    }

    @Override
    public PageInfo findByPage(Integer page, Integer size) {
        return null;
    }

    @Override
    public void deleteIdeas(String[] ids) {
        if (ids != null && ids.length > 0)
        for (String id : ids) {
            feedbackDao.deleteById(id);
        }
    }
}
