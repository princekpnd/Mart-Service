package com.shop.shopservice.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.Measurement;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.entity.Vender;

public class LocalData {
	public List<ItemList> variantList = new ArrayList<ItemList>();

	public List<Item> item = new ArrayList<Item>();
	public List<Item> itemWithVariant = new ArrayList<Item>();
	public List<Brand> brandList = new ArrayList<Brand>();
	public List<Category> categoryList = new ArrayList<Category>();
	public List<SubCategory> subcategoryList  = new ArrayList<SubCategory>();
	public List<Measurement> measurementList = new ArrayList<Measurement>();
	public List<Slot> slotList = new ArrayList<Slot>();
	public List<Vender> venderList = new ArrayList<Vender>();
	
	public static HashMap<String, String> map = new HashMap<String, String>();
	
	

	
	
	
	
	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public List<Vender> getVenderList() {
		return venderList;
	}

	public List<Vender> setVenderList(List<Vender> venderList) {
		return this.venderList = venderList;
	}

	public List<Slot> getSlotList() {
		return slotList;
	}

	public List<Slot> setSlotList(List<Slot> slotList) {
		return this.slotList = slotList;
	}

	public List<Measurement> getMeasurementList() {
		return measurementList;
	}

	public List<Measurement> setMeasurementList(List<Measurement> measurementList) {
		return this.measurementList = measurementList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public List<Brand> setBrandList(List<Brand> brandList) {
		return this.brandList = brandList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public List<Category> setCategoryList(List<Category> categoryList) {
		return this.categoryList = categoryList;
	}

	public List<SubCategory> getSubcategoryList() {
		return subcategoryList;
	}

	public List<SubCategory> setSubcategoryList(List<SubCategory> subcategoryList) {
		return this.subcategoryList = subcategoryList;
	}

	public List<Item> getItemWithVariant() {
		return itemWithVariant;
	}

	public void setItemWithVariant(List<Item> itemWithVariant) {
		this.itemWithVariant = itemWithVariant;
	}

	public List<ItemList> setVariantData(List<ItemList> data) {
		variantList = data;
		return variantList;
	}

	public List<ItemList> getVariantData() {
		return variantList;
	}

	public List<Item> getItem() {
		return item;
	}

	public List<Item> setItem(List<Item> item) {
		this.item = item;
		return item;
	}
	
	
}
