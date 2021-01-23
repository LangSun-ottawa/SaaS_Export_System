package com.langsun.service.feedback;

import com.github.pagehelper.PageInfo;
import com.langsun.domain.company.Company;
import com.langsun.domain.feedback.Feedback;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author slang
 * @date 2020-11-08 14:25
 * @Param $
 * @return $
 **/
public interface FeedbackService {

    List<Feedback> findAll(int state,String companyId);

    List<Feedback> unread(int state);

    void save(Feedback feedback);

    void deleteById(String id);

    Feedback messageChecked(String feedbackId);

    PageInfo findByPage(Integer page, Integer size);

    void deleteIdeas(String[] ids);
}
