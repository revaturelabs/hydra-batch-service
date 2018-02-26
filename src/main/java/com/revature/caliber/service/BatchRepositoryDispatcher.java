package com.revature.caliber.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.revature.caliber.model.SimpleBatch;
import com.revature.caliber.repository.BatchRepository;
@Service
public class BatchRepositoryDispatcher {
	@Autowired
	BatchRepository repo;
	/**
	 * Process incoming request and run actual repository methods base on methodname
	 * 
	 * @param request
	 * @return
	 */
	public SimpleBatch processSimpleBatchRequest(JsonObject request) {
		Gson gson=new GsonBuilder().setDateFormat("mm-dd-yyy").create();
		String methodName = request.get("methodName").getAsString();
		switch(methodName) {
			case "findOne":
				return repo.findOne(request.get("batchId").getAsInt());
			case "findOneWithDroppedTrainees":
				return repo.findOne(request.get("batchId").getAsInt());
			case "findOneWithTraineesAndGrades" :
				return repo.findOne(request.get("batchId").getAsInt());
			case "save":
				System.out.println(request.get("batch"));
				repo.save(gson.fromJson(request.get("batch").getAsString(), SimpleBatch.class));
				return null;
			default:
				return null;
		}
	}
	/**
	 * 
	 * Process incoming request and run list repository methods base on method name
	 * 
	 * @param request
	 * @return
	 */
	public List<SimpleBatch> processListSimpleBatchRequest(JsonObject request) {
		String methodName = request.get("methodName").getAsString();
		if(methodName.contains("Current")){
			if(request.get("trainerId")==null) {
				return repo.findAllCurrent();
			}else{
				return repo.findAllCurrent(request.get("trainerId").getAsInt());
			}
		}else if(methodName.equals("findAllByTrainerId")){
			return repo.findAllByTrainerId(request.get("trainerId").getAsInt());
		}else{
			return repo.findAll();
		}
	}
}
