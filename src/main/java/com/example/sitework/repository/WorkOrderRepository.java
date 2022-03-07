package com.example.sitework.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.sitework.model.WorkOrder;


@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder,Long>{
	
	
	
}