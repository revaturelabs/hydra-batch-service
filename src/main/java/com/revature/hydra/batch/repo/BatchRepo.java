package com.revature.hydra.batch.repo;

import com.revature.hydra.batch.model.Batch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchRepo extends CrudRepository<Batch, Integer> {
}