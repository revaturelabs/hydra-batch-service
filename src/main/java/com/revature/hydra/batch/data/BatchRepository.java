package com.revature.hydra.batch.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.revature.beans.SimpleBatch;
@RepositoryRestResource(collectionResourceRel = "batch", path = "batch")
public interface BatchRepository extends JpaRepository<SimpleBatch, Integer>, BatchRepositoryCustom {
	/**
	 * 
	 * @param trainerId
	 * @return
	 */
	List <SimpleBatch> findAllByTrainerId(@Param("trainerId") Integer trainerId);
	/**
	 * 
	 * @param date
	 * @return
	 */
	List <SimpleBatch> findAllByStartDateAfter(@Param("date") Date date);
	/**
	 * 
	 * @param resourceId
	 * @return
	 */
	List <SimpleBatch> findOneByResourceId(@Param("resourceId") String resourceId);
	
}