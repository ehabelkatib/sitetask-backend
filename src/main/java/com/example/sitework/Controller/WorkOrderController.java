package com.example.sitework.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.multipart.MultipartFile;

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
public class WorkOrderController {
   
    @Autowired
    private WorkOrderRepository orderheaderRepository;

    @Autowired
    private OrderDetailRepository orderdetailRepository;

   



    @GetMapping("/workorder")
    @Operation(summary = "Get orderHeader", responses = {
            @ApiResponse(description = "Get all work order header  ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = WorkOrder.class))),
            @ApiResponse(description = "no records for work order",responseCode = "409",content = @Content)})
    public ResponseEntity<?> getAllOrderHeader() {
        try {
            List<WorkOrder> headerList = orderheaderRepository.findAll();
            return ResponseEntity.ok(headerList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessful");
        }
    }

    @Operation(summary = "create work order", responses = {
            @ApiResponse(description = "create new work order  ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = WorkOrder.class))),
            @ApiResponse(description = "unsuccessful creating work order",responseCode = "409",content = @Content)})
    @PostMapping("/create/workorder")
    public ResponseEntity<?> createWorkOrder(
    		@RequestParam("operation_description") String operation_description,
    		@RequestParam("start_date") String start_date,
    		@RequestParam("end_date") String end_date

    		)

    		throws IOException {

    		try {	

    			LocalDateTime today = LocalDateTime.now();
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
    			String formatDateTime = today.format(formatter);
    			
    			WorkOrder header = new WorkOrder();
    			
    			header.setOperation_description(operation_description);
    			header.setCreation_date(formatDateTime);
    			header.setEnd_date(end_date);
    			header.setStart_date(start_date);
    			header.setProgress_percentage(0);
    					
    			System.out.println("Before : " );								
    
    			
    			WorkOrder saveheader = orderheaderRepository.save(header);
    		
    			return ResponseEntity.ok(header);
    			
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    			return ResponseEntity.ok("Unsuccessful");
    		}
    }

    @Operation(summary = "get one work order", responses = {
            @ApiResponse(description = "get work order by work order id  ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = WorkOrder.class))),
            @ApiResponse(description = "unsuccessful creating work order",responseCode = "409",content = @Content)})
    @GetMapping("/workorder/{workOrderId}")
    public ResponseEntity<?> getWorkOrderByID(
    		@PathVariable Long workOrderId) {
        try {
            WorkOrder header = orderheaderRepository.findById(workOrderId).orElse(null);
            return ResponseEntity.ok(header);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessful");
        }

    }
 
    @Operation(summary = "update workorder", responses = {
            @ApiResponse(description = "update work order field ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = WorkOrder.class))),
            @ApiResponse(description = "no records for work order",responseCode = "409",content = @Content)})
    @PutMapping("/update/workorder/{workOrderId}")
  	public ResponseEntity<?> editWorkOrder(
  			@PathVariable Long workOrderId,
  			@RequestBody WorkOrder newWorkOrder )

  			throws IOException {
  	
  			try {	

  				WorkOrder header = orderheaderRepository.findById(workOrderId).orElse(null);

  				
  				if(newWorkOrder.getOperation_description()==null) {
  					newWorkOrder.setOperation_description(header.getOperation_description());
  				}
  				if(newWorkOrder.getStart_date()==null) {
  					newWorkOrder.setStart_date(header.getStart_date());
  				}
  				if(newWorkOrder.getEnd_date()==null) {
  					newWorkOrder.setEnd_date(header.getEnd_date());
  				}
  					  								
  				header.setOperation_description(newWorkOrder.getOperation_description());
  				header.setStart_date(newWorkOrder.getStart_date());
  				header.setEnd_date(newWorkOrder.getEnd_date());
  						  				  			 			
  				WorkOrder saveheader = orderheaderRepository.save(header);
  				return ResponseEntity.ok(header);
  				  					
  			} catch (Exception e) {
  				e.printStackTrace();
  				return ResponseEntity.ok("Unsuccessful");
  			}
  	}
    

    @Operation(summary = "update work order progress ", responses = {
            @ApiResponse(description = "update total work order progress percentage  ", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = WorkOrder.class))),
            @ApiResponse(description = "no records for work order",responseCode = "409",content = @Content)})
    @PutMapping("/update/progress/{orderId}")
  	public ResponseEntity<?> editProgress(
  			@PathVariable Long orderId
			)

  			throws IOException {
  	
  			try {	

  				List<OrderDetail> detailsList = orderdetailRepository.findByWorkorder_Id(orderId);
  				
  				WorkOrder header = orderheaderRepository.findById(orderId).orElse(null);
  				
  				int detailNbr= detailsList.size();
  				
  				int factor = 100/detailNbr;

  				if (detailNbr>0) {
  					int totalP=0;
  					header.setProgress_percentage(0);
  					for (OrderDetail detail : detailsList)  		
  					{
  						
  					int exp =(detail.getProgress()*factor)/100;
  		    		System.out.println("hellooo" + exp);
  		    		totalP=+ exp+header.getProgress_percentage();
  					header.setProgress_percentage(totalP);
  	  				WorkOrder saveHeader = orderheaderRepository.save(header);

  					}
  	  			return ResponseEntity.ok(header);
  				}else return null;
  				
  				  				
  			} catch (Exception e) {
  				e.printStackTrace();
  				return ResponseEntity.ok("Unsuccessful");
  			}
  	}

    @Operation(summary = "delete work order", responses = {
            @ApiResponse(description = "delete work order record by specific work order id ", responseCode = "200",
                    content = @Content()),
            @ApiResponse(description = "no records for work order",responseCode = "409",content = @Content)})
    @DeleteMapping("delete/workorder/{workOrderId}")
   	public ResponseEntity<Map<String, Boolean>> deleteWorkOrderById(
   			@PathVariable Long workOrderId)
       {
   		WorkOrder header = orderheaderRepository.findById(workOrderId)
   				.orElseThrow(null);

   		orderheaderRepository.delete(header);
   		Map<String, Boolean> response = new HashMap<>();
   		response.put("deleted", Boolean.TRUE);
   		return ResponseEntity.ok(response);
   	}

}