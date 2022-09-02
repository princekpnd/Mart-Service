package com.shop.shopservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.model.UploadModel;
import com.shop.shopservice.service.ArticleFileService;
import com.shop.shopservice.service.IUserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Avinash
 *
 */
@RestController
@RequestMapping("/api/file")
public class RestUploadController {

	private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

	// Save the uploaded file to this folder
	@Autowired
	ArticleFileService fileService;

	@Autowired
	IUserService userService;

	@Autowired
	IManagedObject ManagedObjectService;

	// 3.1.1 Single file upload
	@PostMapping(value = "/upload/avatar/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// If not @RestController, uncomment this
	// @ResponseBody
	public ResponseEntity<?> uploadavatarFile(@RequestParam("file") MultipartFile file,
			@PathVariable("userId") String userId) {

		// int moid =
		// userService.getUser(Integer.parseInt(userId)).getManagementObject();
		// ManagedObject mo = (ManagedObject)
		// ManagedObjectService.getManagedObject(moid);
		logger.debug("Single file upload with userId! : " + userId);
		System.out.println("Single SYS file upload with userId! : " + userId);
		if (file.isEmpty()) {
			return new ResponseEntity<Object>("please select a file!", HttpStatus.OK);
		}

		try {

			fileService.saveUploadedFiles(Arrays.asList(file), userId, "avatar");

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>("Successfully uploaded - " + file.getOriginalFilename(), new HttpHeaders(),
				HttpStatus.OK);

	}

	@PostMapping(value = "/upload/avatar/{userType}/{userId}/{fileType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// If not @RestController, uncomment this
	// @ResponseBody
	public ResponseEntity<?> uploadavatarFile(@RequestParam("file") MultipartFile file,
			@PathVariable("userType") String userType, @PathVariable("userId") String userId,
			@PathVariable("fileType") String fileType) {
		Map<String, String> response = new HashMap<String, String>();
		// int moid =
		// userService.getUser(Integer.parseInt(userId)).getManagementObject();
		// ManagedObject mo = (ManagedObject)
		// ManagedObjectService.getManagedObject(moid);
		logger.debug("Single file upload with userId! : " + userId);
		System.out.println("Single SYS file upload with userId! : " + userId);
		if (file.isEmpty()) {
			return new ResponseEntity<Object>("please select a file!", HttpStatus.OK);
		}

		try {
			fileService.uploadImages(Arrays.asList(file), userType, userId, fileType);
			response.put("status","true");
			response.put("description","Successfully uploaded - " + file.getOriginalFilename());
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>(response, new HttpHeaders(),
				HttpStatus.OK);
	}

	// 3.1.2 Multiple file upload
	@PostMapping("multi")
	public ResponseEntity<?> uploadFileMulti(@RequestParam("extraField") String extraField,
			@RequestParam("files") MultipartFile[] uploadfiles) {

		logger.debug("Multiple file upload!");

		// Get file name
		String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity<Object>("please select a file!", HttpStatus.OK);
		}

		try {

			fileService.saveUploadedFiles(Arrays.asList(uploadfiles), null, "avatar");

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);

	}

	// 3.1.3 maps html form to a Model
	@PostMapping("multi/model")
	public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

		logger.debug("Multiple file upload! With UploadModel");

		try {

			fileService.saveUploadedFiles(Arrays.asList(model.getFiles()), null, "avatar");

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>("Successfully uploaded!", HttpStatus.OK);

	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
