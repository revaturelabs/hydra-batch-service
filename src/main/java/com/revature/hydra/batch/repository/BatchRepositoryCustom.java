package com.revature.hydra.repository;

import java.util.List;

import com.revature.hydra.model.SimpleBatch;

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
