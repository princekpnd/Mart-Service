package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;     
import com.shop.shopservice.Idao.ISlotDAO;
import com.shop.shopservice.entity.Slot;

@Repository
@Transactional
public class SlotDAOImpl implements ISlotDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public Slot getById(int id) {
		Slot slot = entityManager.createNamedQuery("Slot.findById", Slot.class).setParameter("id", id)
				.getSingleResult();
		return slot;
	}

	@Override
	public boolean slotExit(String billingNumber, int venderId) {
		Slot slot = entityManager.createNamedQuery("Slot.checkExit", Slot.class)
				.setParameter("billingNumber", billingNumber).setParameter("venderId", venderId).getResultList().stream().findFirst().orElse(null);
		return null != slot ? Boolean.TRUE : Boolean.FALSE;

	}

	@Override
	public void addSlot(Slot slot) {
		entityManager.persist(slot);

	}

	@Override
	public void updeteSlot(Slot slot) {
		entityManager.merge(slot);

	}

	@Override
	public Slot getItemListById(int itemListId) {
		Slot slot = entityManager.createNamedQuery("Slot.findByItemListId", Slot.class)
				.setParameter("itemListId", itemListId).getSingleResult();
		return slot;
	}

	@Override
	public List<Slot> getByShopId(String shopId) {
		List<Slot> slotList = entityManager.createNamedQuery("Slot.findBYShopId",Slot.class).setParameter("shopId", shopId).getResultList();
		return slotList;
	}

	@Override
	public boolean addVariantByVariantId(Slot slot, int itemListId) {
//	if(null != slot) {
//		String wishList = slot.getVariantList();
//		int variantCount = slot.getVariantCount();
//		if (!Strings.isBlank(wishList) && !wishList.contains(String.valueOf(itemListId))) {
//			wishList = wishList + "," + itemListId;
//			slot.setVariantCount(variantCount + 1);
//		} else if (!Strings.isBlank(wishList) && wishList.contains(String.valueOf(itemListId))) { 
//			wishList = wishList.startsWith(String.valueOf(itemListId)) && wishList.contains(",")
//					? wishList.replace(itemListId + ",", Strings.EMPTY)
//					: wishList.replace("," + itemListId, Strings.EMPTY);
//			if (wishList.startsWith(String.valueOf(itemListId)) && !wishList.contains(","))
//				wishList = wishList.replace(String.valueOf(itemListId), Strings.EMPTY);
//
//			slot.setVariantCount(variantCount - 1);
//		} else if (Strings.isBlank(wishList)) {
//			wishList = String.valueOf(itemListId);
//			slot.setVariantCount(variantCount == 0 ? 1 : variantCount + 1);
//		}
//		slot.setVariantList(wishList);
//		entityManager.persist(slot);
//		slot = null;
//	}
		if (null != slot) {
			String wishList = slot.getVariantList();
			String[] wishlistArray= {};

			boolean find = false;
			int index = 0;
			String wishLisListFinal = null;
			int wishCount = slot.getVariantCount();
			if (!Strings.isBlank(wishList)) {
				wishlistArray = wishList.split(",");
				for (int i = 0; i < wishlistArray.length; i++) {
					if (wishlistArray[i].equals(itemListId)) {
						index = i;
						find = Boolean.TRUE;
					}
				}
			}
				if (find) {
//					wishlistArray = (String[]) ArrayUtils.remove(wishlistArray, index);
//					slot.set
//					user.setWishCount(wishCount - 1);
//					wishLisListFinal = String.join(",", wishlistArray);
					return false;
				} else {
					wishlistArray = (String[]) ArrayUtils.add(wishlistArray, itemListId);
					slot.setVariantCount(wishCount + 1);
					wishLisListFinal = String.join(",", wishlistArray);
				} 
			

			slot.setVariantList(wishLisListFinal);
			entityManager.persist(slot);
			slot = null;
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Slot> getAllDetails(String nameOfSeller, String mobileNo) {
	List<Slot> slotList = entityManager.createNamedQuery("Slot.findBySellerName",Slot.class).setParameter("nameOfSeller", nameOfSeller).setParameter("mobileNo", mobileNo).getResultList();
		return slotList;
	}

	@Override
	public Slot getSlotNumberAndShopId(int slotNumber, String shopId) {
		Slot slot = entityManager.createNamedQuery("Slot.findBySlotNumber",Slot.class).setParameter("slotNumber", slotNumber).setParameter("shopId", shopId).getSingleResult();
		return slot;
	}

	@Override
	public Slot Addvariant(int id, int itemListId) {
		Slot slot = entityManager.createNamedQuery("Slot.addvariantId",Slot.class).setParameter("id", id).setParameter("itemListId", itemListId).getSingleResult();
		return slot;
	}

	@Override
	public void updeteVariant(Slot slot) {
		entityManager.merge(slot);
		
	}

	@Override
	public List<Slot> getAll() {
	List<Slot> slotList = entityManager.createNamedQuery("Slot.getAll",Slot.class).getResultList();
	return slotList;
	}

	@Override
	public List<Slot> getByVenderId(int venderId) {
		List<Slot> slotList = entityManager.createNamedQuery("Slot.getByVenderId",Slot.class).setParameter("venderId", venderId).getResultList();
		return slotList;
	}

	@Override
	public List<Slot> getIdByVariant(int venderId) {
		List<Slot> slotList = entityManager.createNamedQuery("Slot.getByvariantId",Slot.class).setParameter("venderId", venderId).getResultList();
		return slotList;
	}

	@Override
	public boolean checkExist(int slotNumber, String shopId, int venderId) {
		Slot slot = entityManager.createNamedQuery("Slot.checkSlotExit", Slot.class)
				.setParameter("slotNumber", slotNumber).setParameter("venderId", venderId).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);
		return null != slot ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Slot getDeactiveForExcelFile(int id) {
	Slot slot = entityManager.createNamedQuery("Slot.getDeActiveForExcel",Slot.class).setParameter("id", id).getSingleResult();
		return slot;
	}
		
	
}
