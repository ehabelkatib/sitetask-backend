package com.example.sitework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sitework.model.OrderDetail;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>{
	
	List<OrderDetail> findByWorkorder_Id(long id);
	
}
