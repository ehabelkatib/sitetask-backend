package com.example.sitework.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "orderdetail")
public class OrderDetail {
	
	@Id
	private long id = System.currentTimeMillis();
 
    private String item_to_work;
    private String location;
    private Integer fprogress;   //foreman progress 
    
    
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="workorder_id", referencedColumnName = "id")
    private WorkOrder workorder;

    
    public OrderDetail() {
    	
    }
    
    public OrderDetail(String item_to_work, String location, Integer progress) {

    	super();
    	this.item_to_work= item_to_work;
    	this.location= location;
    	this.fprogress= progress;
    	
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItem_to_work() {
		return item_to_work;
	}

	public void setItem_to_work(String item_to_work) {
		this.item_to_work = item_to_work;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getProgress() {
		return fprogress;
	}

	public void setProgress(Integer progress) {
		this.fprogress = progress;
	}

    
 

}
