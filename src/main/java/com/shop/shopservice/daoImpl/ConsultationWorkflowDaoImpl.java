package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IConsultationWorkflowDAO;
import com.shop.shopservice.entity.ConsultationWorkflow;

@Repository
@Transactional
public class ConsultationWorkflowDaoImpl implements IConsultationWorkflowDAO {

	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public void initiateConsultationWorkflow(ConsultationWorkflow consultaionWorkflow) {
		entityManager.persist(consultaionWorkflow);

	}

	@Override
	public ConsultationWorkflow getConsultationWorkflowById(int consultaionWorkflowId) {
		return entityManager.find(ConsultationWorkflow.class, consultaionWorkflowId);
	}

	@Override
	public ConsultationWorkflow getConsultationWorkflowById(int userId, int consultantId) {
		ConsultationWorkflow cw = null;
		try {
			cw = entityManager
					.createNamedQuery("ConsultationWorkflow.findByUserIdConsultantId", ConsultationWorkflow.class)
					.setParameter("userId", userId).setParameter("consultantId", consultantId).getSingleResult();
		} catch (NoResultException nse) {
		}
		return cw;
	}

	@Override
	public List<ConsultationWorkflow> getConsultationWorkflowByConsultantId(int consultantId) {
		return entityManager.createNamedQuery("ConsultationWorkflow.findByConsultantId", ConsultationWorkflow.class).setParameter("consultantId", consultantId).getResultList();
	}

	@Override
	public ConsultationWorkflow getConsultationWorkflowById(int userId, int consultantId, boolean status) {
		ConsultationWorkflow cw = null;
		try {
			cw = entityManager
					.createNamedQuery("ConsultationWorkflow.findByUserIdConsultantIdwithstatus",
							ConsultationWorkflow.class)
					.setParameter("userId", userId).setParameter("consultantId", consultantId)
					.setParameter("status", status).getSingleResult();
		} catch (NoResultException ne) {
		}
		return cw;
	}

	@Override
	public void updateConsultationWorkflow(int userId, int consultantId, boolean accepted) {
		ConsultationWorkflow consultationWorkflow = getConsultationWorkflowById(userId, consultantId);
		consultationWorkflow.setAccepted(accepted);
		entityManager.merge(consultationWorkflow);
	}

	@Override
	public void updateConsultationWorkflowById(ConsultationWorkflow consultaionWorkflow) {
		entityManager.merge(consultaionWorkflow);

	}

}
