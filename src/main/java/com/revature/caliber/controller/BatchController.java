package com.revature.caliber.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.model.Address;
import com.revature.caliber.model.Batch;
import com.revature.caliber.model.Trainer;
import com.revature.caliber.security.models.SalesforceUser;
import com.revature.caliber.service.BatchService;

/**
 * Services requests for Trainer, Trainee, and Batch information
 *
 * @author Patrick Walsh
 *
 */
@RestController
//@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class BatchController {

	private static final Logger log = Logger.getLogger(BatchController.class);
	private BatchService batchService;

	@Autowired
	public void setTrainingService(BatchService batchService) {
		this.batchService = batchService;
	}

	/*
	 *******************************************************
	 * BATCH SERVICES
	 *
	 *******************************************************
	 */

	/**
	 * Find all batches for the currently logged in trainer
	 *
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/trainer/batch/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'TRAINER', 'STAGING', 'PANEL')")
	public ResponseEntity<List<Batch>> findAllBatchesByTrainer(Authentication auth) {
		Trainer userPrincipal = getPrincipal(auth);
		log.info("Getting all batches for trainer: " + userPrincipal);
		List<Batch> batches = batchService.findAllBatches(userPrincipal.getTrainerId());
		return new ResponseEntity<>(batches, HttpStatus.OK);
	}

	/**
	 * Create batch
	 *
	 * @param batch
	 *            the batch
	 * @return the response entity
	 */
	@RequestMapping(value = "/all/batch/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'PANEL')")
	public ResponseEntity<Batch> createBatch(@Valid @RequestBody Batch batch) {
		log.info("Saving batch: " + batch);
		batchService.save(batch);
		return new ResponseEntity<>(batch, HttpStatus.CREATED);
	}

	/**
	 * Update batch
	 *
	 * @param batch the batch
	 * @return the response entity
	 */
	@RequestMapping(value = "/all/batch/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'PANEL')")
	public ResponseEntity<Void> updateBatch(@Valid @RequestBody Batch batch) {
		log.info("Updating batch: " + batch);
		batchService.update(batch);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Delete batch
	 *
	 * @param id the id of the batch to delete
	 * @return the response entity
	 */
	@RequestMapping(value = "/all/batch/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'PANEL')")
	public ResponseEntity<Void> deleteBatch(@PathVariable int id) {
		Batch batch = new Batch();
		batch.setBatchId(id);
		log.info("Deleting batch: " + id);
		batchService.delete(batch);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Gets all current batches
	 *
	 * @return the all batches
	 */
	@RequestMapping(value = {"/vp/batch/all/current" }, method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING', 'PANEL')")
	public ResponseEntity<List<Batch>> getAllCurrentBatches() {
		log.info("Fetching all current batches");
		List<Batch> batches = batchService.findAllCurrentBatches();
		return new ResponseEntity<>(batches, HttpStatus.OK);
	}

	/**
	 * Gets all batches
	 *
	 * @return the all batches
	 */
	@RequestMapping(value = { "/qc/batch/all", "/vp/batch/all" },
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'QC', 'STAGING', 'PANEL')")
	public ResponseEntity<List<Batch>> getAllBatches() {
		log.info("Fetching all batches");
		List<Batch> batches = batchService.findAllBatches();
		System.out.println(batches);
		return new ResponseEntity<>(batches, HttpStatus.OK);

	}

	/**
	 * Adds a new week to the batch. Increments counter of total_weeks in database
	 *
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/trainer/week/new/{batchId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'QC','TRAINER', 'PANEL')")
	public ResponseEntity<Void> createWeek(@PathVariable int batchId) {
		log.info("Adding week to batch: " + batchId);
		batchService.addWeek(batchId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/all/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//@PreAuthorize("hasAnyRole('VP', 'STAGING', 'QC', 'TRAINER', 'PANEL')")
	public ResponseEntity<List<Address>> findCommonLocations() {
		log.info("Fetching common training locations");
		return new ResponseEntity<>(batchService.findCommonLocations(), HttpStatus.OK);
	}

	/**
	 * Convenience method for accessing the Trainer information from the User
	 * Principal.
	 *
	 * @param auth
	 * @return
	 */
	private Trainer getPrincipal(Authentication auth) {
		return ((SalesforceUser) auth.getPrincipal()).getCaliberUser();
	}
	
//	private Trainer getPrincipal() {
//		return null;
//		
//	}
}