package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Article;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.ManagedObject;
import com.shop.shopservice.service.ArticleService;
import com.shop.shopservice.utils.PropertyBundle;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

	private final Logger log = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	ArticleService articleService;

	@Autowired
	IManagedObject managedObject;

	@GetMapping("getallarticle")
	public ResponseEntity<List<Article>> findAllArticle() {
		List<Article> listArticle = articleService.getAllArticle();
		return new ResponseEntity<List<Article>>(listArticle, HttpStatus.OK);
	}

	@GetMapping("getdistance")
	public ResponseEntity<?> findDistance() {
		double origLat = 25.594095, origLon = 85.137566, desLat = 37.090240, desLon = -95.712891;
		 DistanceController distance = new DistanceController();
		double result = distance.Distance(origLat, origLon, desLat, desLon);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createArticle(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create Article: {}", json.get(ServiceConstants.ARTICLE_TITLE));
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
		return ResponseEntity.ok().body(response);
//		return ResponseEntity.created(new URI("/api/user/create/" + result)).body(result);
	}

	@GetMapping("getallarticle/Type/{articleType}")
	public ResponseEntity<List<Article>> findAllArticleByType(@PathVariable("articleType") int articleType) {
		List<Article> listArticle = articleService.findArticleByType(articleType);
		return new ResponseEntity<List<Article>>(listArticle, HttpStatus.OK);
	}
}
