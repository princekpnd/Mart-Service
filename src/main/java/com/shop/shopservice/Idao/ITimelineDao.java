package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.TimeLine;
import com.shop.shopservice.entity.User;

public interface ITimelineDao {

	List<TimeLine> getAllTimeLine();

	List<TimeLine> getAllTimeLineByuser(int userId);

	List<TimeLine> getAllTimeLineByCatagory(int catagoryId);

	List<TimeLine> getAllTimeLineByConsultant(int consultantId);

	List<TimeLine> getAllTimeLineByUserConsultant(int userId, int consultantId, boolean isAnswered);

	void createTimeline(TimeLine timeline);

	List<TimeLine> findTimeline(String keyword);

	void likeTimeline(TimeLine timeline, int userId);

	void disLikeTimeline(TimeLine timeline, int userId);

	List<TimeLine> getAllTimeLineByuserAndCatagory(int userId, int catagoryId);

	TimeLine getTimeLineByTwitterId(Long twitterId);

	TimeLine getTimeLineById(Integer timeLineid);

	public List<TimeLine> searchTimeLine(String keyword);

	public void indexTime();

	public void updateTimeLine(TimeLine timeLine);
}
