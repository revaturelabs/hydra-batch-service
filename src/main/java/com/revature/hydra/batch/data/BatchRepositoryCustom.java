package com.revature.hydra.batch.data;

import java.util.List;

import com.revature.beans.SimpleBatch;

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
