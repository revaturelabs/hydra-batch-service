package com.revature.hydra.batch.repo;

import com.revature.hydra.batch.model.Batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchRepo extends JpaRepository<Batch, Integer> {
}