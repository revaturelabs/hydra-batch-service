package com.revature.caliber.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caliber.model.Address;
import com.revature.caliber.model.Batch;

/**
 * Provides logic concerning trainer and trainee data. Application logic has no
 * business being in a DAO nor in a Controller. This is the ideal place for
 * calculations
 *
 * @author Patrick Walsh
 *
 */
@Service
public class BatchService {

	private static final Logger log = Logger.getLogger(BatchService.class);
	
	@Autowired
	private BatchCompositionService batchDAO;
	
	@Autowired
	private BatchCompositionMessageService batchMessage;
	
	/*
	 *******************************************************
	 * BATCH SERVICES
	 *
	 *******************************************************
	 */

	/**
	 * Returns a list of commonly used locations. Allows user to select from
	 * locations, but also add new locations manually. Suggested UI component is the
	 * HTML5 <datalist>
	 *
	 * @return
	 */
	public List<Address> findCommonLocations() {
		return (List<Address>) batchMessage.sendSimpleAddressListRequest();
	}

	/**
	 * ADD ANOTHER WEEK TO BATCH
	 *
	 * @param batchId
	 */
	public void addWeek(Integer batchId) {
		log.debug("Adding week to batch: " + batchId);
		Batch batch = batchDAO.findOne(batchId);
		if (batch == null) 
			throw new IllegalArgumentException("Invalid Batch");
		int weeks = batch.getWeeks();
		batch.setWeeks(++weeks);
		batchDAO.update(batch);
	}

	/**
	 * SAVE BATCH
	 *
	 * @param batch
	 */
	public void save(Batch batch) {
		log.debug("Saving batch: " + batch);
		batchDAO.save(batch);
	}

	/**
	 * FIND ALL BATCHES
	 *
	 * @return
	 */
	public List<Batch> findAllBatches() {
		log.debug("Find all batches");
		return batchDAO.findAll();
	}

	/**
	 * FIND ALL CURRENT BATCHES
	 *
	 * @return
	 */
	public List<Batch> findAllCurrentBatches() {
		log.debug("Find all current batches");
		return batchDAO.findAllCurrent();
	}

	/**
	 * FIND ALL BATCHES BY TRAINER
	 *
	 * @param trainerId
	 * @return
	 */
	public List<Batch> findAllBatches(int trainerId) {
		log.debug("Find all batches for trainer: " + trainerId);
		return batchDAO.findAllByTrainer(trainerId);
	}

	/**
	 * FIND ALL CURRENT BATCHES BY TRAINER
	 *
	 * @param trainerId
	 * @return
	 */
	public List<Batch> findAllCurrentBatches(int trainerId) {
		log.debug("Find all current batches for trainer: " + trainerId);
		return batchDAO.findAllCurrent(trainerId);
	}

	/**
	 * FIND BATCH BY ID
	 *
	 * @param batchId
	 * @return
	 */
	public Batch findBatch(Integer batchId) {
		log.debug("Finding batch with id: " + batchId);
		return batchDAO.findOne(batchId);
	}

	/**
	 * UPDATE BATCH
	 *
	 * @param batch
	 */
	public void update(Batch batch) {
		log.debug("Update batch " + batch);
		batchDAO.update(batch);
	}

	/**
	 * DELETE BATCH
	 *
	 * @param batch
	 */
	public void delete(Batch batch) {
		Batch fullBatch = batchDAO.findOneWithDroppedTrainees(batch.getBatchId());
		log.debug("Delete batch " + fullBatch);
		batchDAO.delete(fullBatch);
	}

}
