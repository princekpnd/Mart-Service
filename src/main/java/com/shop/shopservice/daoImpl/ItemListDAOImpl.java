package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IItemListDAO;
import com.shop.shopservice.entity.ItemList;
@Repository
@Transactional
public class ItemListDAOImpl implements IItemListDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public ItemList getById(int id) {
	ItemList itemList = entityManager.createNamedQuery("ItemList.getById",ItemList.class).setParameter("id", id).getResultList().stream().findFirst().orElse(null);
	return itemList;	
	}

	@Override
	public List<ItemList> getAllItemListByShopId(String shopId) {
		List<ItemList> itemLists = entityManager.createNamedQuery("ItemList.findByShopId", ItemList.class).setParameter("shopId", shopId).getResultList();
		return itemLists;
	}

	public boolean itemListExits(String slotList,String shopId,float unitQuantity, float unitSellingPrice,int productId, String barcode, float costPrice) {
		ItemList itemList  = entityManager.createNamedQuery("ItemList.itemListExits",ItemList.class).setParameter("slotList", slotList).setParameter("shopId", shopId).setParameter("unitQuantity", unitQuantity).setParameter("unitSellingPrice", unitSellingPrice).setParameter("productId", productId).setParameter("barcode", barcode).setParameter("costPrice", costPrice).getResultList().stream().findFirst().orElse(null);
		return null != itemList ? Boolean.TRUE : Boolean.FALSE;
		
	}

	@Override
	public void addItemList(ItemList itemList) {
		entityManager.persist(itemList);
		
	}

	@Override
	public void updateItemList(ItemList itemList) {
		entityManager.merge(itemList);		
	}

	@Override
	public List<ItemList> getByProductId(int productId) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.findByProductId",ItemList.class).setParameter("productId", productId).getResultList();
		return itemList;
	}

	@Override
	public List<ItemList> getAll() {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.findAll",ItemList.class).getResultList();
		return itemList;
	}

	@Override
	public boolean deleteItemList(int id) {
		Query query = entityManager.createQuery("delete ItemList where id = " + id);			
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

	@Override
	public void addVariantBySlotNumber(ItemList itemList, String slotNumber) {
		// TODO Auto-generated method stub		
	}

	@Override
	public List<ItemList> getByBarcode(String barcode) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getByBarcode",ItemList.class).setParameter("barcode", barcode).getResultList();
		return itemList;	
	}

	@Override
	public List<ItemList> getByShopId(String shopId) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllShopId",ItemList.class).setParameter("shopId", shopId).getResultList();
		return itemList;	
	}

	@Override
	public List<ItemList> getAllByShopId(String shopId) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllByShopId",ItemList.class).setParameter("shopId", shopId).getResultList();
		return itemList;	
	}

	@Override
	public ItemList getAllDetailsById(int id) {
	ItemList itemList = entityManager.createNamedQuery("ItemList.getAllDetailsById",ItemList.class).setParameter("id", id).getSingleResult();
		return itemList;
	}

	@Override
	public boolean checkExist(int productId, String shopId, float unitQuantity, String slotList) {
		ItemList itemList  = entityManager.createNamedQuery("ItemList.itemListCheckExits",ItemList.class).setParameter("productId", productId).setParameter("shopId", shopId).setParameter("unitQuantity", unitQuantity).setParameter("slotList", slotList).getResultList().stream().findFirst().orElse(null);
		return null != itemList ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public ItemList getForExcel(int productId, String shopId, float unitQuantity, String slotList) {
		ItemList itemList  =  entityManager.createNamedQuery("ItemList.itemListCheckExits",ItemList.class).setParameter("productId", productId).setParameter("shopId", shopId).setParameter("unitQuantity", unitQuantity).setParameter("slotList", slotList).getSingleResult();
		return itemList;
	}

	@Override
	public List<ItemList> getAllOnlineVariant(boolean isOnline) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllOnline",ItemList.class).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public List<ItemList> getAllHotSellVariant(boolean hotSell) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllHotSell",ItemList.class).setParameter("hotSell", hotSell).getResultList();
		return itemList;
	}

	@Override
	public List<ItemList> getAllBaggageVariant(boolean baggage) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllBaggage",ItemList.class).setParameter("baggage", baggage).getResultList();
		return itemList;
	}

	@Override
	public List<ItemList> getAllMilaanOfferVariant(boolean milaanOffer) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllMilaanOffer",ItemList.class).setParameter("milaanOffer", milaanOffer).getResultList();
		return itemList;
	}

	@Override
	public List<ItemList> getAllAditionalDiscountVariant(boolean aditionalDiscount) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllAditionalDiscount",ItemList.class).setParameter("aditionalDiscount", aditionalDiscount).getResultList();
		return itemList;
	}

	@Override
	public ItemList getDeActiveForExcel(int id) {
		ItemList itemList = entityManager.createNamedQuery("ItemList.getDeActiveForExcel",ItemList.class).setParameter("id", id).getSingleResult();
		return itemList;
	}

	@Override
	public List<ItemList> getProductByProductId(int productId) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getItemListByProductId",ItemList.class).setParameter("productId", productId).getResultList();
		return itemList;
	}

	@Override
	public List<ItemList> getAllOnlineVariantByShopId(String shopId, boolean isOnline) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllVariantByOnlineByShopId",ItemList.class).setParameter("shopId", shopId).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public ItemList getVariantByProductId(int productId) {
		ItemList item = entityManager.createNamedQuery("ItemList.getVariantByProductId",ItemList.class).setParameter("productId", productId).getSingleResult();
		return item;
	}

	@Override
	public List<ItemList> getAllOnlineProductByProductId(int productId, boolean isOnline) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.getAllVariantByOnlineByProductId",ItemList.class).setParameter("productId", productId).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public ItemList getDeactiveById(int id) {
		ItemList itemList = entityManager.createNamedQuery("ItemList.getDeActiveById", ItemList.class).setParameter("id", id).getSingleResult();
		return itemList;
	}

	@Override
	public List<ItemList> getByProductIdForDelete(int productId) {
		List<ItemList> itemList = entityManager.createNamedQuery("ItemList.forDelete",ItemList.class).setParameter("productId", productId).getResultList();
		return itemList;
	}

}
