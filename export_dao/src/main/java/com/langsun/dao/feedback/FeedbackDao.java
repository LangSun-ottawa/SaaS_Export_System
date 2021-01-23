package com.langsun.dao.feedback;

import com.langsun.domain.feedback.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author slang
 * @date 2020-11-08 21:47
 * @Param $
 * @return $
 **/
public interface FeedbackDao {
    void save(Feedback feedback);

    void edit(@Param("state")Integer readState,@Param("id")String id);

    List<Feedback> unread(int state);

    Feedback findByFeedbackId(String feedbackId);

    List<Feedback> findAll(@Param("state") int state,@Param("companyId") String companyId);

    void deleteById(String id);
}
