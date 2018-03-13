package com.revature.hydra.batch.controller;

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

import com.revature.beans.Address;
import com.revature.beans.Batch;
import com.revature.beans.BatchLocation;
import com.revature.beans.Trainer;
import com.revature.hydra.batch.exceptions.ResponseErrorDTO;
import com.revature.hydra.batch.security.models.SalesforceUser;
import com.revature.hydra.batch.service.BatchCompositionService;
import com.revature.hydra.batch.service.BatchService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	
	
	
	
	
	
	
	
	
	/*
	 * ASSIGNFORCE CONTROLLER
	 */
	
	
	// CREATE
	// creating new batch object from information passed from batch data
//	@PreAuthorize("hasPermission('', 'manager')")
	@ApiOperation(value = "Create a branch", response = BatchCompositionService.class)
	@ApiResponses({
			@ApiResponse(code=200, message ="Successfully Created a Batch"),
			@ApiResponse(code=400, message ="Bad Request, BatchDTO"),
			@ApiResponse(code=500, message ="Cannot retrieve batch")
	})
	@RequestMapping(value="/api/v2/batch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public Object createBatchAssignForce(@RequestBody Batch batch) {
		batchService.save(batch);
		if (batch == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Batch failed to save."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Batch>(batch, HttpStatus.OK);
		}
	}

//	@PreAuthorize("hasPermission('', 'manager')")
	@ApiOperation(value = "Retrieve a batch", response = BatchCompositionService.class)
	@ApiResponses({
			@ApiResponse(code=200, message ="Successfully retrieved a Batch"),
			@ApiResponse(code=400, message ="Bad Request, BatchDTO"),
			@ApiResponse(code=500, message ="Cannot create batch")
	})
	// RETRIEVE
	// retrieve batch with given ID
	@RequestMapping(value = "/api/v2/batch/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object retrieveBatch(@PathVariable("id") Integer batchId) {
		Batch batch = batchService.findBatch(batchId);
		if (batch == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("No batch found of ID " + batchId + "."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Batch>(batch, HttpStatus.OK);
		}
	}

	// DELETE
	// delete batch with given ID
//	@PreAuthorize("hasPermission('', 'manager')")
	@ApiOperation(value = "Delete a batch", response = BatchCompositionService.class)
	@ApiResponses({
			@ApiResponse(code=200, message ="Successfully Deleted a Batch"),
			@ApiResponse(code=400, message ="Bad Request, ID"),
			@ApiResponse(code=500, message ="Cannot delete batch")
	})
	@RequestMapping(value = "/api/v2/batch/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public Object deleteBatch(@PathVariable("id") Integer batchId) {
		Batch batch = batchService.findBatch(batchId);
		
		log.info("deleting batch: " + batch);
		batchService.delete(batch);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}

	
	// GET ALL
	// retrieve all batches
//	@PreAuthorize("hasPermission('', 'Trainers')")


//	@PreAuthorize("hasPermission('', 'basic')")

	@ApiOperation(value = "Retrieve all batches", response = BatchCompositionService.class)
	@ApiResponses({
			@ApiResponse(code=200, message ="Successfully retrieved all batches"),
			@ApiResponse(code=400, message ="Bad Request"),
			@ApiResponse(code=500, message ="Cannot retrieve all batches")
	})

	@RequestMapping(value = "/api/v2/batch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object retrieveAllBatches() {
		List<Batch> batch_list = batchService.findAllBatches();

		if (batch_list == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Fetching all batches failed."),
					HttpStatus.NOT_FOUND);
		} else if (batch_list.isEmpty()) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("No batches available."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Batch>>(batch_list, HttpStatus.OK);
		}
	}

	// UPDATE
//	@PreAuthorize("hasPermission('', 'manager')")
	@ApiOperation(value = "Update a batch", response = BatchCompositionService.class)
	@ApiResponses({
			@ApiResponse(code=200, message ="Successfully updated a batch"),
			@ApiResponse(code=400, message ="Bad Request, BATCHDTO"),
			@ApiResponse(code=500, message ="Cannot update batch")
	})
	@RequestMapping(value = "/api/v2/batch", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public Object updateBatchAssignForce(@RequestBody Batch batch) {
		Batch b = batchService.findBatch(batch.getBatchId());

		if (b == null) {
			return new ResponseEntity<ResponseErrorDTO>(
					new ResponseErrorDTO("No batch with id '" + batch.getBatchId() + "' could be found to update"),
					HttpStatus.NOT_FOUND);
		}

		b.setName(batch.getName());
		b.setSkills(batch.getSkills());
		b.setStartDate(batch.getStartDate());
		b.setEndDate(batch.getEndDate());
		b.setTrainer(batch.getTrainer());
		b.setCoTrainer(batch.getCoTrainer());
		
		if (batch.getCurriculum() == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Curriculum cannot be null"),
					HttpStatus.BAD_REQUEST);
		}
		
		b.setCurriculum(batch.getCurriculum());
		b.setFocus(batch.getFocus());

		BatchLocation bl = batch.getBatchLocation();
		b.setBatchLocation(bl);

		log.info("changing to: " + batch);
		
		try {
			batchService.save(b);
		} catch (Exception ex) {
			log.warn(ex);
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO(ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Batch>(b, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/api/v2/batch/hydramotto", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getHydraMotto() {
		log.info("Fetching common training locations");
		return new ResponseEntity<>("Hail Hydra!", HttpStatus.OK);
	}
	
}