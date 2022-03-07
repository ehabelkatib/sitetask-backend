package com.example.sitework.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sitework.model.OrderDetail;
import com.example.sitework.model.WorkOrder;
import com.example.sitework.repository.OrderDetailRepository;
import com.example.sitework.repository.WorkOrderRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "*")
@RestController
@Tag(name="Work Order Header")
@RequestMapping("/api/v1")
public class OrderDetailController {
   
    @Autowired
    private WorkOrderRepository orderheaderRepository;

    @Autowired
    private OrderDetailRepository orderdetailRepository;

   
    @Operation(summary = "get order detail", responses = {
            @ApiResponse(description = "get all order details ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = OrderDetail.class))),
            @ApiResponse(description = "no records for order details",responseCode = "409",content = @Content)})
    @GetMapping("/orderdetail")
    public ResponseEntity<?> getAlldetails() {
        try {
            List<OrderDetail> details =orderdetailRepository.findAll();
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessful");
        }
    }
    
    @Operation(summary = "get detail by id", responses = {
            @ApiResponse(description = "get list od details by work order id ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = OrderDetail.class))),
            @ApiResponse(description = "no records for order detail by this id ",responseCode = "409",content = @Content)})
    @GetMapping("/detailed/{workOrderId}")
    public ResponseEntity<?> getDetailByWorkOrderID(
    		@PathVariable Long workOrderId) {
        try {
            List<OrderDetail> detailsList = orderdetailRepository.findByWorkorder_Id(workOrderId);
    		System.out.println(detailsList);
            return ResponseEntity.ok(detailsList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessful");
        }

    }

    @Operation(summary = "get one detail order ", responses = {
            @ApiResponse(description = "get one order detail by order detail id ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = OrderDetail.class))),
            @ApiResponse(description = "no records for order detail by this id",responseCode = "409",content = @Content)})
    @GetMapping("/detailedorder/{detailedId}")
    public ResponseEntity<?> getDetailID(
    		@PathVariable Long detailedId) {
        try {
            OrderDetail detailsList = orderdetailRepository.findById(detailedId).orElse(null);
    		System.out.println(detailsList);
            return ResponseEntity.ok(detailsList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessful");
        }

    }

    
    @Operation(summary = "add order detail ", responses = {
            @ApiResponse(description = "create order detail under specific work order  ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = OrderDetail.class))),
            @ApiResponse(description = "unsuccessfull",responseCode = "409",content = @Content)})
    @PostMapping("/create/orderdetail/{WOHeaderId}")
  	public ResponseEntity<?> addOrderDetail(
  			@PathVariable Long WOHeaderId,
  			@RequestParam("items_to_work") String items_to_work,
  			@RequestParam("location") String location
  			)

  			throws IOException {
  			try {	
  				
				WorkOrder header = orderheaderRepository.findById(WOHeaderId).orElse(null);

  				LocalDateTime today = LocalDateTime.now();
  				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
  				String formatDateTime = today.format(formatter);
  					  								
  				OrderDetail details= new OrderDetail();
  				
  				details.setItem_to_work(items_to_work);
  				details.setLocation(location);
  				details.setProgress(0);		
  				header.getDetailedorder().add(details);  						
  				  			 			
  				WorkOrder saveheader = orderheaderRepository.save(header);
  				return ResponseEntity.ok(header);
  				
  			} catch (Exception e) {
  				e.printStackTrace();
  				return ResponseEntity.ok("Unsuccessful");
  			}
  	}
    
    @Operation(summary = "update order detail ", responses = {
            @ApiResponse(description = "update fields of a specific order detail ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = OrderDetail.class))),
            @ApiResponse(description = "you should enter all required field",responseCode = "409",content = @Content)})
    @PutMapping("/update/orderdetail/{detailOrderId}")
  	public ResponseEntity<?> editDetailOrder(
  			@PathVariable Long detailOrderId,
  			@RequestBody OrderDetail newdetailOrder )

  			throws IOException {
  	
  			try {	

				OrderDetail detail = orderdetailRepository.findById(detailOrderId).orElse(null);

  				LocalDateTime today = LocalDateTime.now();
  				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
  				String formatDateTime = today.format(formatter);
  				
  				if(newdetailOrder.getItem_to_work()==null) {
  					newdetailOrder.setItem_to_work(detail.getItem_to_work());
  				}
  				if(newdetailOrder.getLocation()==null) {
  					newdetailOrder.setLocation(detail.getLocation());
  				}
  				if(newdetailOrder.getProgress()==null) {
  					newdetailOrder.setProgress(detail.getProgress());
  				}
  					  								
  				detail.setItem_to_work(newdetailOrder.getItem_to_work());
  				detail.setLocation(newdetailOrder.getLocation());
  				detail.setProgress(newdetailOrder.getProgress());
  						  				  			 			
  				OrderDetail saveDetails = orderdetailRepository.save(detail);
  				return ResponseEntity.ok(detail);
  				  					
  			} catch (Exception e) {
  				e.printStackTrace();
  				return ResponseEntity.ok("Unsuccessful");
  			}
  	}

    @PutMapping("/edit/progressdetail/{detailOrderId}")
  	public ResponseEntity<?> editProgressDetail(
  			@PathVariable Long detailOrderId,
			@RequestParam("progress") Integer progress)

  			throws IOException {
  	
  			try {	

				OrderDetail detail = orderdetailRepository.findById(detailOrderId).orElse(null);

   				detail.setProgress(progress);
  						  				  			 			
  				OrderDetail saveDetails = orderdetailRepository.save(detail);
  				return ResponseEntity.ok(detail);
  				  				
  			} catch (Exception e) {
  				e.printStackTrace();
  				return ResponseEntity.ok("Unsuccessful");
  			}
  	}

    @Operation(summary = "delete order detail", responses = {
            @ApiResponse(description = "delete  specific order detail ", responseCode = "200",
                    content = @Content()),
            @ApiResponse(description = "unsuccessfull",responseCode = "409",content = @Content)})
    @DeleteMapping("delete/orderdetail/{detailOrderId}")
	public ResponseEntity<Map<String, Boolean>> deleteDetail(
			@PathVariable Long detailOrderId)
    {
		OrderDetail detail = orderdetailRepository.findById(detailOrderId)
				.orElseThrow(null);

		orderdetailRepository.delete(detail);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
    
   
}
