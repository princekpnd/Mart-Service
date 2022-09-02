package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IMeasurementDAO;
import com.shop.shopservice.entity.Measurement;

@Repository
@Transactional
public class MeasurementDAOImpl implements IMeasurementDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Measurement> getAll() {
		List<Measurement> measurementList = entityManager.createNamedQuery("Measurement.findAll", Measurement.class)
				.getResultList();
		return measurementList;
	}

	@Override
	public List<Measurement> getMeasurementByShopId(String shopId) {
		List<Measurement> measurementList = entityManager
				.createNamedQuery("Measurement.findByShopId", Measurement.class).setParameter("shopId", shopId)
				.getResultList();
		return measurementList;
	}

	@Override
	public Measurement getById(int id) {
		return this.entityManager.find(Measurement.class, id);
	}

	@Override
	public void updateMeasurement(Measurement measurement) {
		entityManager.merge(measurement);

	}

	@Override
	public void addMeasurement(Measurement measurement) {
		entityManager.persist(measurement);

	}

	@Override
	public boolean measurementExists(String title, String shopId) {
		Measurement measurement = entityManager.createNamedQuery("Measurement.existMeasurement", Measurement.class)
				.setParameter("title", title).setParameter("shopId", shopId).getResultList().stream().findFirst()
				.orElse(null);
		;
		return null != measurement ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Measurement getByTitleAndShopId(String shopId, String title) {
		Measurement measurement = entityManager.createNamedQuery("Measurement.findByTitle",Measurement.class).setParameter("shopId", shopId).setParameter("title", title).getSingleResult();
		return measurement;
	}

	@Override
	public Measurement getDeActiveForExcel(int id) {
		Measurement measurement = entityManager.createNamedQuery("Measurement.deActiveForExcel",Measurement.class).setParameter("id", id).getSingleResult();
		return measurement;
	}

//@Override
//public boolean measurementExists(String name, String shopId) {
//	Measurement measurement = entityManager.createNamedQuery("Measurement.existMeasurement",Measurement.class).setParameter("name", name).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);;
//	return null != measurement ?Boolean.TRUE:Boolean.FALSE;
//}

}
