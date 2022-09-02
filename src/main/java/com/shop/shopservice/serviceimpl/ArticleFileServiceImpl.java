package com.shop.shopservice.serviceimpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.shop.shopservice.Idao.ArticleFileDao;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.ArticleFile;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.ShopImage;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.exception.MyFileNotFoundException;
import com.shop.shopservice.service.ArticleFileService;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IImageService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.IShopImageService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.utils.OsUtils;

@Repository
@Transactional
public class ArticleFileServiceImpl implements ArticleFileService {

	@Autowired
	private ArticleFileDao articleFileDao;

	@Autowired
	private IImageService imageService;

	@Autowired
	private IItemService itemService;

	@Autowired
	private IShopImageService shopImageService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserService userService;

	@Autowired
	private Environment env;

	@Autowired
	private ILookUp lookup;

	private Path fileStorageLocation;

	public List<ArticleFile> getAllArticleFiles() {
		return articleFileDao.listArticleFiles();
	}

	public ArticleFile findArticleFileById(int articleFileId) {
		return articleFileDao.getArticleFile(articleFileId);
	}

	public void addArticleFile(ArticleFile articleFile) {
		articleFileDao.addArticleFile(articleFile);
	}

	public void saveArticleFile(ArticleFile articleFile) {
		articleFileDao.updateArticleFile(articleFile);

	}

	public boolean deleteArticleFile(int articleFileId) {
		// TODO Auto-generated method stub
		return false;
	}

	// save file
	public void saveUploadedFiles(List<MultipartFile> files, String guid, String fileType) throws IOException {
		String UPLOADED_FOLDER = Strings.EMPTY;
		System.out.println("Single SYS file upload with userId!saveUploadedFiles  : "
				+ OsUtils.getOperatingSystemType().toString() + files.size());
		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles  : " + file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
				UPLOADED_FOLDER = fileType.equalsIgnoreCase("avatar") ? env.getProperty("userBucket.window.path")
						: env.getProperty("userBucket.window.path.timeline");
			} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
				UPLOADED_FOLDER = fileType.equalsIgnoreCase("avatar") ? env.getProperty("userBucket.linux.path")
						: env.getProperty("userBucket.linux.path.timeline");
			}

			else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.macos.path");
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER);

			String fileName = fileType.equalsIgnoreCase("avatar")
					? Strings.isNotBlank(guid) ? guid + "_avatar.png" : file.getOriginalFilename()
					: guid;
			System.out.println(
					"Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER + fileName);

			Path path = Paths.get(UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);

		}

	}

	public Resource loadFileAsResource(String fileName) {

		if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
			this.fileStorageLocation = Paths.get(env.getProperty("userBucket.window.path")).toAbsolutePath()
					.normalize();
		} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
			this.fileStorageLocation = Paths.get(env.getProperty("userBucket.linux.path")).toAbsolutePath().normalize();
		}

		else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
			this.fileStorageLocation = Paths.get(env.getProperty("userBucket.macos.path")).toAbsolutePath().normalize();
		}

		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

	@Override
	public void uploadProductImage(List<MultipartFile> files, String productId, String shopId) throws IOException {
		String UPLOADED_FOLDER = Strings.EMPTY;
		System.out.println("Single SYS file upload with userId!saveUploadedFiles  : "
				+ OsUtils.getOperatingSystemType().toString() + files.size());
		Image image = null;
		List<Image> imageList = null;
		String fileName = "";

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles  : " + file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.window.path.product");
			} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.linux.path.product");
			}

			else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.macos.path");
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER);

			System.out.println(
					"Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER + fileName);

			if (Strings.isNotBlank(productId) && Strings.isNotBlank(shopId)) {
				imageList = imageService.getImageByShopIdAndProductId(shopId, Integer.parseInt(productId));

//				if (null != imageList) {
				fileName = Strings.isNotBlank(shopId)
						? Strings.isNotBlank(productId)
								? productId + "_" + UUID.randomUUID().toString().substring(0, 10) + "_" + shopId
										+ "_product.png"
								: file.getOriginalFilename()
						: productId;

				image = new Image(shopId, Integer.parseInt(productId));

				image.setShopId(shopId);
				image.setActive(true);
				image.setDeleted(false);
				image.setCreatedOn(new Date());
				image.setProductId(Integer.parseInt(productId));
				image.setAvatarName(fileName);
				boolean imageCreate = imageService.createImage(image);
				if (imageCreate) {
					Item product = itemService.getById(Integer.parseInt(productId));
					List<Image> allImageList = imageService.getImageByProductId(Integer.parseInt(productId));
					product.setProductImage(allImageList.get(0).getAvatarName());
					itemService.updateItem(product);
				}

//				} else {
//					fileName = Strings.isNotBlank(shopId)
//							? Strings.isNotBlank(productId) ? productId + "_" + 1 + "_" + shopId + "_product.png"
//									: file.getOriginalFilename()
//							: productId;
//				}
			}

			Path path = Paths.get(UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);
		}

	}

	@Override
	public void updateProductImage(List<MultipartFile> files, String productId, String shopId, int imageId)
			throws IOException {
		String UPLOADED_FOLDER = Strings.EMPTY;
		System.out.println("Single SYS file upload with userId!saveUploadedFiles  : "
				+ OsUtils.getOperatingSystemType().toString() + files.size());
		Image image = null;
		List<Image> imageList = null;
		String fileName = "";

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles  : " + file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.window.path.product");
			} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.linux.path.product");
			}

			else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.macos.path");
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER);

			System.out.println(
					"Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER + fileName);

			if (Strings.isNotBlank(productId) && Strings.isNotBlank(shopId)) {
				imageList = imageService.getImageByShopIdAndProductId(shopId, Integer.parseInt(productId));

//				if (null != imageList) {
				fileName = Strings.isNotBlank(shopId)
						? Strings.isNotBlank(productId)
								? productId + "_" + UUID.randomUUID().toString().substring(0, 10) + "_" + shopId
										+ "_product.png"
								: file.getOriginalFilename()
						: productId;

				image = imageService.getImageById(imageId);
				if (Strings.isNotBlank(image.getAvatarName())) {
					Path path1 = Paths.get(UPLOADED_FOLDER + image.getAvatarName());
					Files.delete(path1);
				}
				image.setCreatedOn(new Date());
				image.setAvatarName(fileName);
				boolean imageCreate = imageService.updateImage(image);
				if (imageCreate) {
					Item product = itemService.getById(Integer.parseInt(productId));
					List<Image> allImageList = imageService.getImageByProductId(Integer.parseInt(productId));
					product.setProductImage(allImageList.get(0).getAvatarName());
					itemService.updateItem(product);
				}
//				} else {
//					fileName = Strings.isNotBlank(shopId)
//							? Strings.isNotBlank(productId) ? productId + "_" + 1 + "_" + shopId + "_product.png"
//									: file.getOriginalFilename()
//							: productId;
//				}
			}

			Path path = Paths.get(UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);
		}

	}

	@Override
	public void uploadShopImage(List<MultipartFile> files, String productId, String shopId) throws IOException {
		String UPLOADED_FOLDER = Strings.EMPTY;

		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REG_COMPLETED");
		int registrationStatus = lookUp.getLookUpId();
		System.out.println("Single SYS file upload with userId!saveUploadedFiles  : "
				+ OsUtils.getOperatingSystemType().toString() + files.size());
		ShopImage image = null;
		List<ShopImage> imageList = null;
		String fileName = "";

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles  : " + file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.window.path.shop");
			} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.linux.path.shop");
			}

			else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.macos.path");
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER);

			System.out.println(
					"Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER + fileName);

			if (Strings.isNotBlank(productId) && Strings.isNotBlank(shopId)) {
				imageList = shopImageService.getShopImageByShopIdAndProductId(shopId, Integer.parseInt(productId));

//				if (null != imageList) {
				fileName = Strings.isNotBlank(shopId) ? Strings.isNotBlank(productId)
						? productId + "_" + UUID.randomUUID().toString().substring(0, 10) + "_" + shopId + "_shop.png"
						: file.getOriginalFilename() : productId;

				image = new ShopImage(shopId, Integer.parseInt(productId));

				image.setShopId(shopId);
				image.setActive(true);
				image.setDeleted(false);
				image.setCreatedOn(new Date());
				image.setProductId(Integer.parseInt(productId));
				image.setAvatarName(fileName);
				boolean imageUpload = shopImageService.createShopImage(image);
				if (imageUpload) {
					Admin admin = adminService.getByShopId(shopId);
					List<Image> shopImageList = imageService.getImageByShopId(shopId);
					admin.setAvatar(shopImageList.get(0).getAvatarName());
					admin.setRegistrationStatus(registrationStatus);
					adminService.updateAdmin(admin);
				}

//				} else {
//					fileName = Strings.isNotBlank(shopId)
//							? Strings.isNotBlank(productId) ? productId + "_" + 1 + "_" + shopId + "_product.png"
//									: file.getOriginalFilename()
//							: productId;
//				}
			}

			Path path = Paths.get(UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);
		}

	}

	@Override
	public void updateShopImage(List<MultipartFile> files, String productId, String shopId, int imageId)
			throws IOException {
		String UPLOADED_FOLDER = Strings.EMPTY;

		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REG_COMPLETED");
		int registrationStatus = lookUp.getLookUpId();
		System.out.println("Single SYS file upload with userId!saveUploadedFiles  : "
				+ OsUtils.getOperatingSystemType().toString() + files.size());
		ShopImage image = null;
		List<ShopImage> imageList = null;
		String fileName = "";

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles  : " + file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.window.path.shop");
			} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.linux.path.shop");
			}

			else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.macos.path");
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER);

			System.out.println(
					"Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER + fileName);

			if (Strings.isNotBlank(productId) && Strings.isNotBlank(shopId)) {
				imageList = shopImageService.getShopImageByShopIdAndProductId(shopId, Integer.parseInt(productId));

//				if (null != imageList) {
				fileName = Strings.isNotBlank(shopId) ? Strings.isNotBlank(productId)
						? productId + "_" + UUID.randomUUID().toString().substring(0, 10) + "_" + shopId + "_shop.png"
						: file.getOriginalFilename() : productId;

				image = shopImageService.getShopImageById(imageId);
				if (Strings.isNotBlank(image.getAvatarName())) {
					Path path1 = Paths.get(UPLOADED_FOLDER + image.getAvatarName());
					Files.delete(path1);
				}

				image.setCreatedOn(new Date());
				image.setAvatarName(fileName);
				boolean imageUpload = shopImageService.updateShopImage(image);
				if (imageUpload) {
					Admin admin = adminService.getByShopId(shopId);
					List<Image> shopImageList = imageService.getImageByShopId(shopId);
					admin.setAvatar(shopImageList.get(0).getAvatarName());
					admin.setRegistrationStatus(registrationStatus);
					adminService.updateAdmin(admin);
				}

//				} else {
//					fileName = Strings.isNotBlank(shopId)
//							? Strings.isNotBlank(productId) ? productId + "_" + 1 + "_" + shopId + "_product.png"
//									: file.getOriginalFilename()
//							: productId;
//				}
			}

			Path path = Paths.get(UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);
		}

	}

	@Override
	public void uploadImages(List<MultipartFile> files, String userType, String userId, String fileType)
			throws IOException {
		String UPLOADED_FOLDER = Strings.EMPTY;
		System.out.println("Single SYS file upload with userId!saveUploadedFiles  : "
				+ OsUtils.getOperatingSystemType().toString() + files.size());
		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles  : " + file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			if (OsUtils.getOperatingSystemType().toString().equals("Windows")) {
				UPLOADED_FOLDER = fileType.equalsIgnoreCase("avatar") ? env.getProperty("userBucket.window.path")
						: fileType.equalsIgnoreCase("adhar") ? env.getProperty("userBucket.window.path.adhar")
								: env.getProperty("userBucket.window.path.pancard");
			} else if (OsUtils.getOperatingSystemType().toString().equals("Linux")) {
				UPLOADED_FOLDER = fileType.equalsIgnoreCase("avatar") ? env.getProperty("userBucket.linux.path")
						: fileType.equalsIgnoreCase("adhar") ? env.getProperty("userBucket.linux.path.adhar")
								: env.getProperty("userBucket.linux.path.pancard");
			}

			else if (OsUtils.getOperatingSystemType().toString().equals("MacOS")) {
				UPLOADED_FOLDER = env.getProperty("userBucket.macos.path");
			}
			System.out.println("Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER);

			String fileName = Strings.isNotBlank(userId)
					? userId + "_" + userType + "_" + UUID.randomUUID().toString().substring(0, 10) + fileType + ".png"
					: file.getOriginalFilename();
			System.out.println(
					"Single SYS file upload with userId!saveUploadedFiles to folder  : " + UPLOADED_FOLDER + fileName);
			if (Strings.isNotBlank(userId)) {
				User user = userService.getUser(Integer.parseInt(userId));
				if (Strings.isNotBlank(user.getAvatar())) {
					Path path1 = Paths.get(UPLOADED_FOLDER + user.getAvatar());
					Files.delete(path1);
				}
				user.setAvatar(fileName);
				userService.updateUser(user);
			}
			Path path = Paths.get(UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);
		}

	}
}