package com.shop.shopservice.entity;

public class MyResponse {
	
		 private String status;
		 private String description;
		 private Object  data;


		 // Getter Methods 

		 public String getStatus() {
		  return status;
		 }

		 public String getDescription() {
		  return description;
		 }

		 // Setter Methods 

		 public void setStatus(String status) {
		  this.status = status;
		 }

		 public void setDescription(String description) {
		  this.description = description;
		 }

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
		

}
