package com.shop.shopservice.controller;
/**
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.shopservice.Idao.ITimelineDao;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.TimeLine;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.service.ArticleFileService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.twitter.api.TwitterApi;
import com.shop.shopservice.utils.PropertyBundle;

@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

	private final Logger log = LoggerFactory.getLogger(TimelineController.class);
	@Autowired
	ITimelineDao timelineDao;
	
	@Autowired
	private IUserService UserService;
	
	@Autowired
	TwitterApi twitterApi;
	
	@Autowired
	ArticleFileService fileService;
	
	@GetMapping("getalltimeline")
	public ResponseEntity<List<TimeLine>> findAllTimeline() {
		List<TimeLine> result = new ArrayList<TimeLine>();
		List<TimeLine> listTimeline = timelineDao.getAllTimeLine();
		for(TimeLine t : listTimeline) {
			if(t.getUserId() != 0 ) {
				User u = UserService.getUser(t.getUserId());
				t.setUserName(u.getLastName()+ " , " +u.getFirstName());
			}
			if(t.getConsultantId() != 0 ) {
				User u = UserService.getUser(t.getConsultantId());
				t.setConsultantName(u.getLastName()+", "+u.getFirstName());
			}
			result.add(t);
		}
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTimeline(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create Timeline: {}", json.get(ServiceConstants.ARTICLE_TITLE));
		Map<String, String> response = new HashMap<String, String>();
		if (Strings.isNotEmpty(json.get(ServiceConstants.ARTICLE_TITLE))
				&& Strings.isNotEmpty(json.get(ServiceConstants.ARTICLE_TYPE))) {
			LookUp objectTypeLookUp = PropertyBundle.systemMap.get("MANAGED_OBJECT_TYPE").stream()
					.filter(l -> l.getLookUpName().equals("ARTICLE")).findFirst().orElse(null);
			LookUp objectCreatedByLookUp = PropertyBundle.systemMap.get("MANAGED_OBJECT_CREAT").stream()
					.filter(l -> l.getLookUpName().equals("SELF")).findFirst().orElse(null);
			ManagedObject mo = new ManagedObject(UUID.randomUUID().toString(), new Date(),
					objectCreatedByLookUp.getLookUpId(), objectTypeLookUp.getLookUpId());
			managedObject.createManagedObject(mo);
			Article article = new Article(json.get(ServiceConstants.ARTICLE_TITLE),
					Integer.parseInt(json.get(ServiceConstants.ARTICLE_TYPE)), mo.getID());

			if (Strings.isNotBlank(json.get(ServiceConstants.ARTICLE_TEASER))) {
				article.setTeaser(json.get(ServiceConstants.ARTICLE_TEASER));
			}
			if (Strings.isNotBlank(json.get(ServiceConstants.ARTICLE_BODY))) {
				article.setTeaser(json.get(ServiceConstants.ARTICLE_BODY));
			}

			article.setIsPublished(Boolean.FALSE);

			response.put("articleTitle", json.get(ServiceConstants.ARTICLE_TITLE));
			articleService.addArticle(article);
			response.put("status", Strings.EMPTY + "SUCCESS");
			response.put("description",
					"Article created successfully with given Title, Kindly publish to make it usable");
		}
		return ResponseEntity.ok().body(response); // return
		ResponseEntity.created(new URI("/api/user/create/" + result)).body(result);
	} 
	
	@GetMapping("getalltimeline/byconsultant/{consultantId}")
	public ResponseEntity<List<TimeLine>> findAllTimeLineByConsultantId(@PathVariable("consultantId") int consultantId) {
		List<TimeLine> result = new ArrayList<TimeLine>();
		List<TimeLine> listTimeLine = timelineDao.getAllTimeLineByConsultant(consultantId);
		for(TimeLine t : listTimeLine) {
			if(t.getUserId() != 0 ) {
				User u = UserService.getUser(t.getUserId());
				t.setUserName(u.getLastName()+ " , " +u.getFirstName());
			}
			if(t.getConsultantId() != 0 ) {
				User u = UserService.getUser(t.getConsultantId());
				t.setConsultantName(u.getLastName()+", "+u.getFirstName());
			}
			result.add(t);
		}
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@GetMapping("getalltimeline/byuser/{userId}")
	public ResponseEntity<List<TimeLine>> findAllTimeLineByUserId(@PathVariable("userId") int userId) {
		List<TimeLine> result = new ArrayList<TimeLine>();
		List<TimeLine> listTimeLine = timelineDao.getAllTimeLineByuser(userId);
		for(TimeLine t : listTimeLine) {
			if(t.getUserId() != 0 ) {
				User u = UserService.getUser(t.getUserId());
				t.setUserName(u.getLastName()+ " , " +u.getFirstName());
			}
			if(t.getConsultantId() != 0 ) {
				User u = UserService.getUser(t.getConsultantId());
				t.setConsultantName(u.getLastName()+", "+u.getFirstName());
			}
			result.add(t);
		}
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@GetMapping("getalltimeline/byuser/{userId}/{catagoryId}")
	public ResponseEntity<List<TimeLine>> findAllTimeLineByUserAndCatagoryId(@PathVariable("userId") String userId, @PathVariable("catagoryId") String catagoryId) {
		int userid = Integer.parseInt(userId);
		int catagoryid = Integer.parseInt(catagoryId);
		List<TimeLine> result = new ArrayList<TimeLine>();
		List<TimeLine> listTimeLine = timelineDao.getAllTimeLineByuserAndCatagory(userid,catagoryid);
		for(TimeLine t : listTimeLine) {
			if(t.getUserId() != 0 ) {
				User u = UserService.getUser(t.getUserId());
				t.setUserName(u.getLastName()+ " , " +u.getFirstName());
			}
			if(t.getConsultantId() != 0 ) {
				User u = UserService.getUser(t.getConsultantId());
				t.setConsultantName(u.getLastName()+", "+u.getFirstName());
			}
			result.add(t);
		}
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@GetMapping("getalltimeline/bycatagory/{catagoryId}")
	public ResponseEntity<List<TimeLine>> findAllTimeLineByCatagoryId(@PathVariable("catagoryId") String catagoryId) {
		List<TimeLine> result = new ArrayList<TimeLine>();
		List<TimeLine> listTimeLine = null;
		int catagoryid = Integer.parseInt(catagoryId);
		if (catagoryid == 33) {
			listTimeLine = timelineDao.getAllTimeLine();
		} else {
			listTimeLine = timelineDao.getAllTimeLineByCatagory(catagoryid);
		}
		for (TimeLine t : listTimeLine) {
			if (t.getUserId() != 0) {
				User u = UserService.getUser(t.getUserId());
				t.setUserName(u.getLastName() + " , " + u.getFirstName());
			}
			if (t.getConsultantId() != 0) {
				User u = UserService.getUser(t.getConsultantId());
				t.setConsultantName(u.getLastName() + ", " + u.getFirstName());
			}
			result.add(t);
		}
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTimeline(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) {
		log.info("Request to create timeline for : {}", json.get(ServiceConstants.CONSULTANTID));
		Map<String, String> response = new HashMap<String, String>();
		    String uuid = UUID.randomUUID().toString();
			Integer consultantId = Integer.parseInt(json.get(ServiceConstants.CONSULTANTID));
			Integer catagoryId = Integer.parseInt(json.get(ServiceConstants.PROFILE_CATAGORY));
			String qArticle = json.get(ServiceConstants.TIMELINE_BODY);
			Integer timeLineType = Integer.parseInt(json.get(ServiceConstants.TIMELINE_TYPE));
			Date nowDate = new Date();
			TimeLine timeline = new TimeLine(consultantId,qArticle,timeLineType,nowDate);
			timeline.setCatagoryId(catagoryId);
			timelineDao.createTimeline(timeline);
			response.put(ServiceConstants.TIMELINE_BODY, timeline.getqArticle());
			response.put(ServiceConstants.SUCCESS, Boolean.TRUE.toString());
			timeline = null;
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/create/file/{consultantId}/{timelineType}/{catagoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<Map<String, String>> createFileTimeline(@PathVariable("consultantId") Integer consultantId,
			@PathVariable("catagoryId") Integer catagoryId, @PathVariable("timelineType") Integer timelineType,
			@RequestParam("file") MultipartFile file) {
		log.info("Request to create timeline for : {}", consultantId);
		Map<String, String> response = new HashMap<String, String>();
		String uuid = UUID.randomUUID().toString();
//			Integer consultantId = Integer.parseInt(consultantId);
//			Integer timeLineType = Integer.parseInt(json.get(ServiceConstants.TIMELINE_TYPE));
//			Integer catagoryId = Integer.parseInt(json.get(ServiceConstants.PROFILE_CATAGORY));
		String qArticle = Strings.EMPTY;
		List<LookUp> lookupList = PropertyBundle.systemMap.get("TIMELINE_TYPE");
		LookUp timeLineLookup = null;
		for (LookUp lkp : lookupList) {
			if (lkp.getLookUpId() == timelineType) {
				timeLineLookup = lkp;
				break;
			}
		}
		if (timeLineLookup.getLookUpLabel().equalsIgnoreCase("VIDEO")
				|| timeLineLookup.getLookUpLabel().equalsIgnoreCase("AUDIO")
				|| timeLineLookup.getLookUpLabel().equalsIgnoreCase("IMAGE")) {
			String fName = consultantId.toString() + "_" + timelineType + "_" + UUID.randomUUID().toString() + "-"
					+ file.getOriginalFilename();
			if (!file.isEmpty()) {
				try {
					fileService.saveUploadedFiles(Arrays.asList(file), fName, timeLineLookup.getLookUpLabel());
					qArticle = fName;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Date nowDate = new Date();
		TimeLine timeline = new TimeLine(consultantId, qArticle, timelineType, nowDate);
		timeline.setCatagoryId(catagoryId);
		timelineDao.createTimeline(timeline);
		response.put(ServiceConstants.TIMELINE_BODY, timeline.getqArticle());
		response.put(ServiceConstants.SUCCESS, Boolean.TRUE.toString());
		timeline = null;
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/askquestion")
	ResponseEntity<Map<String, String>> createQuestion(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) {
		log.info("Request to create timeline for : {}", json.get(ServiceConstants.CONSULTANTID));
		Map<String, String> response = new HashMap<String, String>();
		    String uuid = UUID.randomUUID().toString();
		    Integer userId = Integer.parseInt(json.get(ServiceConstants.USERID));
			Integer consultantId = Integer.parseInt(json.get(ServiceConstants.CONSULTANTID));
			Integer catagoryId = Integer.parseInt(json.get(ServiceConstants.PROFILE_CATAGORY));
			String qArticle = json.get(ServiceConstants.TIMELINE_BODY);
			Integer timeLineType = Integer.parseInt(json.get(ServiceConstants.TIMELINE_TYPE));
			Date nowDate = new Date();
			TimeLine timeline = new TimeLine(userId ,consultantId ,qArticle,timeLineType,nowDate);
			timeline.setCatagoryId(catagoryId);
			timelineDao.createTimeline(timeline);
			response.put(ServiceConstants.TIMELINE_BODY, timeline.getqArticle());
			response.put(ServiceConstants.SUCCESS, Boolean.TRUE.toString());
			timeline = null;
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/giveanswer")
	ResponseEntity<Map<String, String>> createAnswer(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) {
		log.info("Request to answer timeline for : {}", json.get(ServiceConstants.TIMELINE_ID));
		Map<String, String> response = new HashMap<String, String>();
		TimeLine t = timelineDao.getTimeLineById(Integer.parseInt(json.get(ServiceConstants.TIMELINE_ID)));
		String aArticle = json.get(ServiceConstants.TIMELINE_BODY);
		if (null != t && null != aArticle) {
			t.setaArticle(aArticle);
			timelineDao.updateTimeLine(t);
			response.put(ServiceConstants.TIMELINE_ID, json.get(ServiceConstants.TIMELINE_ID));
			response.put(ServiceConstants.SUCCESS, Boolean.TRUE.toString());
			t = null;
		} else {
			response.put(ServiceConstants.TIMELINE_ID, json.get(ServiceConstants.TIMELINE_ID));
			response.put(ServiceConstants.FAILURE, Boolean.FALSE.toString());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("addtwittertimeline")
	public ResponseEntity<Map<String,String>> addtwittertimeline() {
		Map<String, String> response = new HashMap<String, String>();
		twitterApi.addTwitterTimeLine();
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("like/byuser/{timeLineId}/{userId}")
	public ResponseEntity<TimeLine> liketimeLineByUserId(@PathVariable("userId") Integer userId, @PathVariable("timeLineId") Integer timeLineId) {
		TimeLine timeLine = timelineDao.getTimeLineById(timeLineId);
		timelineDao.likeTimeline(timeLine, userId);
		return new ResponseEntity<TimeLine>(timeLine, HttpStatus.OK);
	}
	
	@GetMapping("dislike/byuser/{timeLineId}/{userId}")
	public ResponseEntity<TimeLine> disliketimeLineByUserId(@PathVariable("userId") Integer userId, @PathVariable("timeLineId") Integer timeLineId) {
		TimeLine timeLine = timelineDao.getTimeLineById(timeLineId);
		timelineDao.disLikeTimeline(timeLine, userId);
		return new ResponseEntity<TimeLine>(timeLine, HttpStatus.OK);
	}
	
	@GetMapping("search/bycatagory/{catagoryId}/{keyword}")
	public ResponseEntity<List<TimeLine>> searchAllTimeLineByCatagoryId(@PathVariable("catagoryId") String catagoryId, @PathVariable("keyword") String keyword) {
		List<TimeLine> result = new ArrayList<TimeLine>();
		List<TimeLine> listTimeLine = null;
		int catagoryid = Integer.parseInt(catagoryId);
		if (catagoryid == 33) {
			listTimeLine = timelineDao.getAllTimeLine();
		} else {
			listTimeLine = timelineDao.getAllTimeLineByCatagory(catagoryid);
		}
		for (TimeLine t : listTimeLine) {
			if (t.getUserId() != 0) {
				User u = UserService.getUser(t.getUserId());
				t.setUserName(u.getLastName() + " , " + u.getFirstName());
			}
			if (t.getConsultantId() != 0) {
				User u = UserService.getUser(t.getConsultantId());
				t.setConsultantName(u.getLastName() + ", " + u.getFirstName());
			}
			result.add(t);
		}
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@GetMapping("search/{keyword}")
	public ResponseEntity<List<TimeLine>> searchAllTimeLineBykeyword(@PathVariable("keyword") String keyword) {
		List<TimeLine> result = new ArrayList<TimeLine>();
		result = timelineDao.searchTimeLine(keyword);
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		timelineDao.indexTime();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("search/consultantid/{consultantId}/{keyword}")
	public ResponseEntity<List<TimeLine>> searchAllTimeLineByConsultantkeyword(@PathVariable("keyword") String keyword, @PathVariable("consultantId") Integer consultantId) {
		List<TimeLine> result = new ArrayList<TimeLine>();
		result = timelineDao.searchTimeLine(keyword);
		result = result.stream().filter(t -> t.getConsultantId() == consultantId).collect(Collectors.toList());
		return new ResponseEntity<List<TimeLine>>(result, HttpStatus.OK);
	}
	
	@GetMapping("gettimeline/{timelineId}")
	public ResponseEntity<TimeLine> findTimeline(@PathVariable("timelineId") Integer timelineId) {
		TimeLine timeline = timelineDao.getTimeLineById(timelineId);
		return new ResponseEntity<TimeLine>(timeline, HttpStatus.OK);
	}
	
}
**/
