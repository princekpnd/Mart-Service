package com.shop.shopservice.twitter.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ITimelineDao;
//import com.shop.shopservice.controller.ProfileController;
import com.shop.shopservice.entity.Profile;
import com.shop.shopservice.entity.TimeLine;
import com.shop.shopservice.service.IProfileService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.twitter.api.TwitterApi;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

@Repository
@Transactional
public class TwitterImpl implements TwitterApi {

	private final Logger log = LoggerFactory.getLogger(TwitterImpl.class);
	@Autowired
	IProfileService profileService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ITimelineDao timelineDao;
	
	public void loginTwitter() {}
	
	static {
		File file = new File("twitter4j.properties");
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
            }
             
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignore) {
                }
            }
        }

	}

	
	public void addTwitterTimeLine() {
		try {
			// gets Twitter instance with default credentials
			Twitter twitter = new TwitterFactory().getInstance();
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();
			log.info("Showing @" + user.getScreenName() + "'s home timeline.");
			for (Status status : statuses) {
				String twitterHandle = status.getUser().getScreenName();
				Profile profile = profileService.getProfileByTwitterHandle(twitterHandle);
				if (null != profile) {
					System.out.println(profile.getDisplayName());
				}
				TimeLine t = timelineDao.getTimeLineByTwitterId(status.getId());
				if (null != profile && null == t) {
					System.out.println(status.getText());
					TimeLine timeline = new TimeLine(profile.getUserId(), status.getText(), 1, status.getCreatedAt());
					String sString = Long.toString(status.getId());
					timeline.setTwitterId(sString);
					timeline.setCatagoryId(profile.getCatagory());
					timelineDao.createTimeline(timeline);
				}
				log.info("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}

	}
}