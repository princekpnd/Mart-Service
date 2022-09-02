package com.shop.shopservice.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.LookUpType;

@Repository
public class PropertyBundle {

//	public static Map <String, Map <String, Map <String, String>>> systemMap = new HashMap<>();
	public static Map<String, List<LookUp>> systemMap = new HashMap<String, List<LookUp>>();
	
	public static Map<String, List<LookUp>> adminSystemMap = new HashMap<String, List<LookUp>>();
	@PersistenceContext
	EntityManager entityManager;

	@EventListener(ApplicationReadyEvent.class)
	public void initilizeProperty() {
		List<LookUpType> lookupTypeList = entityManager.createNamedQuery("LookUpType.findAll", LookUpType.class)
				.getResultList();
		System.out.println(lookupTypeList.size());
		for (LookUpType lut : lookupTypeList) {
			List<LookUp> lookupList = entityManager.createNamedQuery("LookUp.findAllByLookUpType", LookUp.class)
					.setParameter("lookUpTypeId", lut.getLookUpTypeId()).getResultList();
			PropertyBundle.systemMap.put(lut.getLookUpTypeName(), lookupList);
		}
		for (LookUpType lut : lookupTypeList) {
			List<LookUp> lookupList = entityManager.createNamedQuery("LookUp.findAllByLookUpTypeForAdmin", LookUp.class)
					.setParameter("lookUpTypeId", lut.getLookUpTypeId()).getResultList();
			PropertyBundle.adminSystemMap.put(lut.getLookUpTypeName(), lookupList);
		}
	}

}
