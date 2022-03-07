package com.example.sitework.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "workorder")
public class WorkOrder {
	
	@Id
	private long id = System.currentTimeMillis();
 
    private String creation_date;
    private String operation_description;
    private String start_date;
    private String end_date;
    private Integer tprogress; // total progress
    
    
    
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "workorder_id", referencedColumnName = "id")
	private List<OrderDetail> orderdetail = new ArrayList<>();

    
    
    public WorkOrder() {
    	
    }
    
    public WorkOrder(String creation_date, String operation_description, String start_date, String end_date, Integer progress_percentage) {

    	this.creation_date= creation_date;
    	this.operation_description= operation_description;
    	this.start_date= start_date;
    	this.end_date = end_date;
    	this.tprogress= progress_percentage;
    		
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getOperation_description() {
		return operation_description;
	}

	public void setOperation_description(String operation_description) {
		this.operation_description = operation_description;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public Integer getProgress_percentage() {
		return tprogress;
	}

	public void setProgress_percentage(Integer progress_percentage) {
		this.tprogress = progress_percentage;
	}

	public List<OrderDetail> getDetailedorder() {
		return orderdetail;
	}

	public void setDetailedorder(List<OrderDetail> detailedorder) {
		this.orderdetail = detailedorder;
	}
    
 

}
