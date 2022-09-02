package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.ConsultationWorkflow;

public interface IConsultationWorkflowDAO {

	public void initiateConsultationWorkflow(ConsultationWorkflow consultaionWorkflow);

	public ConsultationWorkflow getConsultationWorkflowById(int consultaionWorkflowId);

	public ConsultationWorkflow getConsultationWorkflowById(int userId, int consultantId);

	public ConsultationWorkflow getConsultationWorkflowById(int userId, int consultantId, boolean status);

	public void updateConsultationWorkflow(int userId, int consultantId, boolean accepted);

	public void updateConsultationWorkflowById(ConsultationWorkflow consultaionWorkflow);

	public List<ConsultationWorkflow> getConsultationWorkflowByConsultantId(int consultantId);
}
