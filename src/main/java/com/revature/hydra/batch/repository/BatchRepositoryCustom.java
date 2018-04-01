package com.revature.hydra.batch.repository;

import java.util.List;

import com.revature.hydra.batch.model.SimpleBatch;

public interface BatchRepositoryCustom {
	/**
	 * 
	 * @return
	 */
	public List<SimpleBatch>findAllCurrent();
	/**
	 * 
	 * @param trainerId
	 * @return
	 */
	public List<SimpleBatch>findAllCurrent(int trainerId);
}
