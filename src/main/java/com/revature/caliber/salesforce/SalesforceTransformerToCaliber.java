package com.revature.caliber.salesforce;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.revature.caliber.model.Batch;
import com.revature.caliber.model.SkillType;
import com.revature.caliber.model.Trainee;
import com.revature.caliber.model.Trainer;
import com.revature.caliber.model.TrainingStatus;
import com.revature.caliber.salesforce.beans.BatchTrainer;
import com.revature.caliber.salesforce.beans.SalesforceBatch;
import com.revature.caliber.salesforce.beans.SalesforceTrainee;

@Component
public class SalesforceTransformerToCaliber {

	private static final Logger logger = Logger.getLogger(SalesforceTransformerToCaliber.class);

	public Batch transformBatch(SalesforceBatch salesforceBatch) {
		Batch batch = new Batch();
		if (salesforceBatch == null) {
			return batch;
		}
		batch.setResourceId(salesforceBatch.getId());
		batch.setTrainingName(salesforceBatch.getName());
		batch.setStartDate(salesforceBatch.getBatchStartDate());
		batch.setTrainer(transformTrainer(salesforceBatch.getTrainer()));
		batch.setCoTrainer(transformTrainer(salesforceBatch.getCotrainer()));
		batch.setEndDate(salesforceBatch.getBatchEndDate());
		batch.setResourceId(salesforceBatch.getId());
		batch.setSkillType(transformSkillType(salesforceBatch));
		batch.setLocation(salesforceBatch.getLocation());

		return batch;
	}

	// TO DO - Tranform batchtrainers into trainers
	public Trainer transformTrainer(BatchTrainer batchTrainer) {
		Trainer trainer = new Trainer();
		if (batchTrainer == null) {
			return trainer;
		}
		trainer.setName(batchTrainer.getName());
		trainer.setEmail(batchTrainer.getEmail());
		return trainer;
	}

	public SkillType transformSkillType(SalesforceBatch salesforceBatch) {
		String stringSkillType = salesforceBatch.getSkillType();
		if (stringSkillType == null) {
			return SkillType.OTHER;
		}
		return transformSkillTypeHelper(stringSkillType);

	}

	private SkillType transformSkillTypeHelper(String skillType) {
		switch (skillType) {
		case "J2EE":
			return SkillType.J2EE;
		case ".NET":
			return SkillType.NET;
		case "SDET":
			return SkillType.SDET;
		case "BPM":
			return SkillType.BPM;
		default:
			return SkillType.OTHER;
		}
	}

	public Trainee transformTrainee(SalesforceTrainee salesforceTrainee) {

		Trainee trainee = new Trainee();

		if (salesforceTrainee == null) {
			return trainee;
		}

		trainee.setName(salesforceTrainee.getName());
		trainee.setEmail(salesforceTrainee.getEmail());
		trainee.setTrainingStatus(transformStatus(salesforceTrainee));
		trainee.setPhoneNumber(salesforceTrainee.getPhone());
		trainee.setPhoneNumber(salesforceTrainee.getMobilePhone());
		trainee.setResourceId(salesforceTrainee.getId());
		trainee.setCollege(salesforceTrainee.getCollege().getName());
		trainee.setProjectCompletion(salesforceTrainee.getProjectCompletion());
		trainee.setRecruiterName(salesforceTrainee.getRecruiter().getName());
		trainee.setTechScreenerName(salesforceTrainee.getScreener());

		// need to add: trainee setTechScreenFeedback
		String degree = "None";
		if (salesforceTrainee.getAssociates() != null){
			degree = "Associates";
			trainee.setMajor(salesforceTrainee.getAssociates());
		}
		if (salesforceTrainee.getBachelors() != null){
			degree = "Bachelors";
			trainee.setMajor(salesforceTrainee.getBachelors());
		}
		if (salesforceTrainee.getMasters() != null){
			degree = "Masters";
			trainee.setMajor(salesforceTrainee.getMasters());
		}
		trainee.setDegree(degree);
		return trainee;
	}

	public TrainingStatus transformStatus(SalesforceTrainee salesforceTrainee) {

		String stringTrainingStatus = salesforceTrainee.getTrainingStatus();

		if (stringTrainingStatus == null) {
			stringTrainingStatus = "";
		}
		switch (stringTrainingStatus) {
		case "Declined Offer":
			stringTrainingStatus = "Dropped";
			return transformStatusHelper(stringTrainingStatus);
		case "Did Not Show":
			stringTrainingStatus = "Dropped";
			return transformStatusHelper(stringTrainingStatus);
		default:
			return transformStatusHelper(stringTrainingStatus);
		}
	}

	private TrainingStatus transformStatusHelper(String stringTrainingStatus) {
		try {
			return TrainingStatus.valueOf(stringTrainingStatus);
		} catch (IllegalArgumentException exp) {
			logger.debug("Exp caught in SalesforceTransformer.transformStatusHelper");
			logger.debug(exp);
			return TrainingStatus.Training;
		}
	}
}
