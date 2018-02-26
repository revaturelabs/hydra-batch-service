package com.revature.caliber.repository;

import java.util.List;

import com.revature.caliber.model.SimpleBatch;

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
