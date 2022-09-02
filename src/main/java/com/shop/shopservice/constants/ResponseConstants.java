package com.shop.shopservice.constants;

public class ResponseConstants {
	public static final String SUCCESS_CODE = "5000";
	public static final String NULL_CODE = "5001";
	public static final String MOB_CODE = "5002";
	public static final String PWD_CODE = "5003";
	public static final String NOT_FOUND_CODE = "5004";
	public static final String ENTER_INF_CODE = "5005";
	public static final String NOT_MATCH_CODE = "5005";
//	for delete response
	public static final String DEL_CAT = "4001";
	public static final String DEL_SUB_CAT = "4002";
	public static final String DEL_BRAND = "4003";
	public static final String DEL_PRO = "4004";
	public static final String DEL_VARI = "4005";
	public static final String DEL_BEF_SUB_CAT = "4006";
	public static final String DEL_BEF_BRAND = "4007";
	public static final String DEL_BEF_PRO = "4008";
	public static final String DEL_BEF_VARI = "4009";
	public static final String DEL_NOT_CAT = "4010";
	public static final String DEL_NOT_SUB_CAT = "4011";
	public static final String DEL_NOT_BRAND = "4012";
	public static final String DEL_NOT_PRO = "4013";
	public static final String DEL_NOT_VAR = "4014";
	
	public static final String DELIVERY_UPDATE = "4015";
	public static final String DELIVERY_NOT_UPDATE = "4016";
	
	
	public static final String DELIVERY_MSG[] = {DELIVERY_UPDATE, "Data successfully loaded."};
	public static final String DELIVERY_NOT_MSG[] = {DELIVERY_NOT_UPDATE, "Data not successfully loaded."};
	public static final String SUCCESS_MSG[] = {SUCCESS_CODE, "Data successfully loaded."};
	public static final String MOB_MSG[] = {MOB_CODE, "your mobile number is not match."};
	public static final String NULL_MSG[] = {NULL_CODE, "Data not found."};
	public static final String NOT_FOUND[] = {NOT_FOUND_CODE, "Data not found."};
	public static final String PWD_MSG[] = {PWD_CODE, "your password is not match."};
	public static final String ENTER_INF_MSG[] = {ENTER_INF_CODE, "Please enter your mobile number and pwd."};
	public static final String NOT_MATCH_MSG[] = {NOT_MATCH_CODE, "your mobile number and pwd are incorrect."};
//	for delete response
	public static final String DEL_CAT_MSG[] = {DEL_CAT, "your category has been deleted successfully."};
	public static final String DEL_SUB_CAT_MSG[] = {DEL_SUB_CAT, "your sub category has been deleted successfully."};
	public static final String DEL_BRAND_MSG[] = {DEL_BRAND, "your brand has been deleted successfully."};
	public static final String DEL_PRO_MSG[] = {DEL_PRO, "your product has been deleted successfully."};
	public static final String DEL_VARI_MSG[] = {DEL_VARI, "your variant has been deleted successfully."};
	public static final String DEL_BEF_SUB_CAT_MSG[] = {DEL_BEF_SUB_CAT, "you delete before all subcategory selected category."};
	public static final String DEL_BEF_BRAND_MSG[] = {DEL_BEF_BRAND, "your delete before all brand selected subcategory."};
	public static final String DEL_BEF_PRO_MSG[] = {DEL_BEF_PRO, "your delete before all product selected brand."};
	public static final String DEL_BEF_VARI_MSG[] = {DEL_BEF_PRO, "your delete before all variant selected product."};
	public static final String DEL_NOT_CAT_MSG[] = {DEL_NOT_CAT, "your category is not deleted."};
	public static final String DEL_NOT_SUB_CAT_MSG[] = {DEL_NOT_SUB_CAT, "your sub category is not deleted."};
	public static final String DEL_NOT_BRAND_MSG[] = {DEL_NOT_BRAND, "your brand is not deleted."};
	public static final String DEL_NOT_PRO_MSG[] = {DEL_NOT_BRAND, "your product is not deleted."};
	public static final String DEL_NOT_VAR_MSG[] = {DEL_NOT_BRAND, "your variant is not deleted."};
}
