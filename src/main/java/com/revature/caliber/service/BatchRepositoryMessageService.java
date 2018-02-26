package com.revature.caliber.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.caliber.model.Batch;
import com.revature.caliber.model.SimpleBatch;

@Service
public class BatchRepositoryMessageService {
	@Autowired
	BatchRepositoryDispatcher brd;
	
	@Autowired
	BatchCompositionDispatcher bcd;
	/**
	 * Listens to the batch queue for methods calls that retrieves SimpleBatch
	 * and dispatch proper method from repo and return them.
	 * 
	 * @param message
	 * @return
	 */
	@RabbitListener(queues = "revature.caliber.repos.batch")
	public SimpleBatch receive(String message) {
		System.out.println("message recieved 1");
		return brd.processSimpleBatchRequest(getRequest(message));
	}
	/**
	 * Listens to the batch list queue for methods calls that retrieves a List of SimpleBatch
	 * and disptach proper method.
	 *
	 * @param message
	 * @return
	 */
	@RabbitListener(queues = "revature.caliber.repos.batch.list")
	public List<SimpleBatch> receiveList(String message) {
		System.out.println("message recieved");
		return brd.processListSimpleBatchRequest(getRequest(message));
	}
	/**
	 * 
	 * Listens to the service.batch.list queue for methods calls that retrieves a list of Batches
	 * and disptach proper method.
	 * 
	 * @param message
	 * @return
	 */
	@RabbitListener(queues = "revature.caliber.service.batch.list")
	public List<Batch> receiveBatchList(String message) {
		System.out.println("message recieved 3");
		return bcd.processListBatchRequest(getRequest(message));
	}
	/**
	 * 
	 * Listens to the service.batch queue for methods calls that retrieves a Batch
	 * and disptach proper method.
	 * 
	 * @param message
	 * @return
	 */
	@RabbitListener(queues = "revature.caliber.service.batch")
	public Batch receiveBatch(String message) {
		System.out.println("message recieved 4");
		return bcd.processBatchRequest(getRequest(message));
	}
	/**
	 * process message and create json object for processBatchRequest
	 * 
	 * @param message
	 * @return
	 */
	public JsonObject getRequest(String message){
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		return element.getAsJsonObject();
	}
}
