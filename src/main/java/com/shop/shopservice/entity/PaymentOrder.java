package com.shop.shopservice.entity;

import java.util.ArrayList;

public class PaymentOrder {
	 public int amount;
	    public int amount_paid;
	    public ArrayList<Object> notes;
	    public int created_at;
	    public int amount_due;
	    public String currency;
	    public String receipt;
	    public String id;
	    public String entity;
	    public Object offer_id;
	    public String status;
	    public int attempts;
	    
	    public PaymentOrder () {
	    	super();
	    }
		/**
		 * @return the amount
		 */
		public int getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(int amount) {
			this.amount = amount;
		}
		/**
		 * @return the amount_paid
		 */
		public int getAmount_paid() {
			return amount_paid;
		}
		/**
		 * @param amount_paid the amount_paid to set
		 */
		public void setAmount_paid(int amount_paid) {
			this.amount_paid = amount_paid;
		}
		/**
		 * @return the notes
		 */
		public ArrayList<Object> getNotes() {
			return notes;
		}
		/**
		 * @param notes the notes to set
		 */
		public void setNotes(ArrayList<Object> notes) {
			this.notes = notes;
		}
		/**
		 * @return the created_at
		 */
		public int getCreated_at() {
			return created_at;
		}
		/**
		 * @param created_at the created_at to set
		 */
		public void setCreated_at(int created_at) {
			this.created_at = created_at;
		}
		/**
		 * @return the amount_due
		 */
		public int getAmount_due() {
			return amount_due;
		}
		/**
		 * @param amount_due the amount_due to set
		 */
		public void setAmount_due(int amount_due) {
			this.amount_due = amount_due;
		}
		/**
		 * @return the currency
		 */
		public String getCurrency() {
			return currency;
		}
		/**
		 * @param currency the currency to set
		 */
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		/**
		 * @return the receipt
		 */
		public String getReceipt() {
			return receipt;
		}
		/**
		 * @param receipt the receipt to set
		 */
		public void setReceipt(String receipt) {
			this.receipt = receipt;
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the entity
		 */
		public String getEntity() {
			return entity;
		}
		/**
		 * @param entity the entity to set
		 */
		public void setEntity(String entity) {
			this.entity = entity;
		}
		/**
		 * @return the offer_id
		 */
		public Object getOffer_id() {
			return offer_id;
		}
		/**
		 * @param offer_id the offer_id to set
		 */
		public void setOffer_id(Object offer_id) {
			this.offer_id = offer_id;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		/**
		 * @return the attempts
		 */
		public int getAttempts() {
			return attempts;
		}
		/**
		 * @param attempts the attempts to set
		 */
		public void setAttempts(int attempts) {
			this.attempts = attempts;
		}
	    
	    
}
