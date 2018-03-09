package com.revature.hydra.batch.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Address;
import com.revature.beans.Batch;
import com.revature.beans.Note;
import com.revature.beans.SimpleBatch;
import com.revature.beans.SimpleNote;
import com.revature.beans.SimpleTrainee;
import com.revature.beans.Trainee;
import com.revature.beans.Trainer;
import com.revature.beans.TrainingStatus;
import com.revature.hydra.batch.repository.BatchRepository;
@Service
public class BatchCompositionService {
	@Autowired
	private BatchRepository repo;
	@Autowired
	private BatchCompositionMessageService BCMS;
	/**
	 * 
	 * @param batch
	 */
	public void save(Batch batch) {
		repo.save(new SimpleBatch(batch));
	}
	/**
	 * 
	 * @param batch
	 */
	public void update(Batch batch) {
		repo.save(new SimpleBatch(batch));
	}
	/**
	 * 
	 * @param batch
	 */
	public void delete(Batch batch) {
		repo.delete(batch.getBatchId());
		BCMS.sendSimpleTraineeDeleteRequest(batch.getBatchId());
		
	}
	/**
	 * 
	 * @param batchId
	 * @return
	 */
	public Batch findOne(Integer batchId) {
		return composeBatch(repo.findOne(batchId), false);
		
	}
	/**
	 * 
	 * @param batchId
	 * @return
	 */
	public Batch findOneWithDroppedTrainees(Integer batchId) {
		return composeBatch(repo.findOne(batchId), true);
	}
	/**
	 * 
	 * @param batchId
	 * @return
	 */
	//needs refactoring;
	public Batch findOneWithTraineesAndGrades(Integer batchId) {
		return composeBatch(repo.findOne(batchId), false);
	}
	/**
	 * 
	 * @return
	 */
	public List<Batch> findAll() {
		List<SimpleBatch> batchList = repo.findAll();
		System.out.println("Repo call for findAll returned");
		System.out.println(batchList);
		composeBatch(batchList.remove(0), true);
		return composeListOfBatch(repo.findAll(), false, true, true);
		
	}
	/**
	 * 
	 * @param trainerId
	 * @return
	 */
	public List<Batch> findAllByTrainer(Integer trainerId) {
		return composeListOfBatch(repo.findAllByTrainerId(trainerId), false, false, false);
	}
	/**
	 * 
	 * @return
	 */
	public List<Batch> findAllCurrent() {
		return composeListOfBatch(repo.findAllCurrent(),false, true ,true);	
	}
	/**
	 * 
	 * @param trainerId
	 * @return
	 */
	public List<Batch> findAllCurrent(Integer trainerId) {
		return composeListOfBatch(repo.findAllCurrent(trainerId),false, true ,true);	
	}
	/**
	 * 
	 * @return
	 */
	public List<Batch> findAllCurrentWithNotes() {
		return composeListOfBatch(repo.findAllCurrent(),false, true, true);	
	}
	/**
	 * 
	 * @return
	 */
	public List<Batch> findAllCurrentWithTrainees() {
		return composeListOfBatch(repo.findAllCurrent(),false, true, true);
	}
	/**
	 * 
	 * @return
	 */
	public List<Batch> findAllCurrentWithNotesAndTrainees() {
		return composeListOfBatch(repo.findAllCurrent(),false, true, true);
	}
	/**
	 * 
	 * @param month
	 * @param day
	 * @param year
	 * @return
	 */
	public List<Batch> findAllAfterDate(Integer month, Integer day, Integer year) {
		Date date=Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
		return composeListOfBatch(repo.findAllByStartDateAfter(date), false, true, true);
	}
	/**
	 * 
	 * @param batchId
	 * @param includeDropped
	 * @return
	 */
	public Set<Trainee> getBatchTrainees(int batchId, boolean includeDropped){
		List<SimpleTrainee> simpleTList = BCMS.sendListSimpleTraineeRequest(batchId);
		return simpleTList.stream()
				.filter(s->includeDropped||s.getTrainingStatus()!=TrainingStatus.Dropped)
				.map(t->new Trainee(t))
				.collect(Collectors.toSet());
	}
	/**
	 * 
	 * @param batchId
	 * @return
	 */
	public Set<Note> getBatchNotes(int batchId){
		List<SimpleNote> simpleNList = BCMS.sendListSimpleNoteRequest(batchId);
		return simpleNList.stream()
				.map(n-> new Note(n))
				.collect(Collectors.toSet());
	}
	/**
	 * 
	 * @param src
	 * @return
	 */
	public Batch setAddressAndTrainer(SimpleBatch src){
		Address a = null;
		Trainer t = new Trainer(BCMS.sendSimpleTrainerRequest(src.getTrainerId()));
		Trainer c = null;
		if(src.getAddressId()!=null){
			a = BCMS.sendSimpleAddressRequest(src.getAddressId());
		}
		if(src.getCoTrainerId()!=null){
			c = new Trainer(BCMS.sendSimpleTrainerRequest(src.getCoTrainerId()));
		}
		Batch b= new Batch(src);
		b.setAddress(a);
		b.setTrainer(t);
		if(src.getCoTrainerId()!=null) {
			b.setCoTrainer(c);
		}
		return b;
	}
	/**
	 * 
	 * @param src
	 * @param includeDropped
	 * @param withTrainees
	 * @param withNotes
	 * @return
	 */
	public List<Batch> composeListOfBatch(List<SimpleBatch> src, 
			boolean includeDropped, boolean withTrainees, boolean withNotes){
		if(withTrainees&&withNotes){
			return src.stream()
					.map(b->composeBatch(b, includeDropped))
					.collect(Collectors.toList());
		}else{
			List<Batch> batches = src.stream()
					.map(b-> setAddressAndTrainer(b)).collect(Collectors.toList());
			if(withTrainees){
				return batches.stream()
						.map(b-> {
							b.setTrainees(getBatchTrainees(b.getBatchId(), includeDropped));
							return b;
						})
						.collect(Collectors.toList());
			}else if(withNotes) {
				return batches.stream().map(b->{
					b.setNotes(getBatchNotes(b.getBatchId()));
					return b;
				}).collect(Collectors.toList());
			}else{
				return batches;
			}
		}
	}
	/**
	 * 
	 * @param src
	 * @param includeDropped
	 * @return
	 */
	private Batch composeBatch(SimpleBatch src, boolean includeDropped) {
		System.out.println("composeBatch");
		Batch b = setAddressAndTrainer(src);
		System.out.println("Batch created: ");
		System.out.println(b);
		b.setTrainees(getBatchTrainees(src.getBatchId(), includeDropped));
		System.out.println("batch trainees are set");
		System.out.println(b);
		if(b.getTrainees() == null) {
			b.setTrainees(new HashSet<>());
			System.out.println("Trainees was null and is now set to an empty hashset");
			System.out.println(b);
		}
		
		b.setNotes(getBatchNotes(src.getBatchId()));
		System.out.println("Notes for batch has been set");
		System.out.println(b);
		
		if(b.getNotes() == null) {
			b.setNotes(new HashSet<>());
			System.out.println("No notes to add to batch object so instantiating it to an empty hashset");
			System.out.println(b);
		}
		
		System.out.println("ComposeBatch  method finished executing and returning the batch object");
		System.out.println(b);
		return b;	
	}
//	/**
//	 * Save a batch to the database.
//	 * 
//	 * @param batch
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void save(Batch batch) {
//		log.info("Saving Batch " + batch);
//		sessionFactory.getCurrentSession().save(batch);
//	}
//
//	/**
//	 * Looks for all batches without any restriction. Likely to only be useful for
//	 * calculating reports.
//	 * 
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAll() {
//		log.info("Fetching all batches");
//		return sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.add(Restrictions.or(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped),
//						Restrictions.isNull(T_TRAINING_STATUS)))
//				.addOrder(Order.desc(START_DATE))
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//	}
//
//	/**
//	 * Looks for all batches where the user was the trainer or co-trainer.
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllByTrainer(Integer trainerId) {
//		log.info("Fetching all batches for trainer: " + trainerId);
//		List<Batch> batches = sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.add(Restrictions.or(Restrictions.eq("trainer.trainerId", trainerId),
//						Restrictions.eq("coTrainer.trainerId", trainerId)))
//				.addOrder(Order.desc(START_DATE)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//		batches.parallelStream()
//				.forEach(b -> b.getTrainees().removeIf(t -> t.getTrainingStatus().equals(TrainingStatus.Dropped)));
//		return batches;
//	}
//
//	/**
//	 * Looks for all batches where the user was the trainer or co-trainer. Batches
//	 * returned are currently actively in training.
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrent(Integer trainerId) {
//		log.info("Fetching all current batches for trainer: " + trainerId);
//		Calendar endDateLimit = Calendar.getInstance();
//		endDateLimit.add(Calendar.MONTH, MONTHS_BACK);
//		List<Batch> batches = sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.add(Restrictions.or(Restrictions.eq("trainer.trainerId", trainerId),
//						Restrictions.eq("coTrainer.trainerId", trainerId)))
//				.add(Restrictions.le(START_DATE, Calendar.getInstance().getTime()))
//				.add(Restrictions.ge(END_DATE, endDateLimit.getTime())).addOrder(Order.desc(START_DATE))
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//		batches.parallelStream()
//				.forEach(b -> b.getTrainees().removeIf(t -> t.getTrainingStatus().equals(TrainingStatus.Dropped)));
//		return batches;
//	}
//
//	/**
//	 * Looks for all batches that are currently actively in training including
//	 * trainees, notes and grades
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrentWithNotesAndTrainees() {
//		log.info("Fetching all current batches with trainees, grades and notes");
//		Calendar endDateLimit = Calendar.getInstance();
//		endDateLimit.add(Calendar.MONTH, MONTHS_BACK);
//		return sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.createAlias("trainees.notes", "n", JoinType.LEFT_OUTER_JOIN)
//				.createAlias(T_GRADES, "g", JoinType.LEFT_OUTER_JOIN).add(Restrictions.gt(G_SCORE, 0.0))
//				.add(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped))
//				.add(Restrictions.le(START_DATE, Calendar.getInstance().getTime()))
//				.add(Restrictions.ge(END_DATE, endDateLimit.getTime())).add(Restrictions.eq("n.qcFeedback", true))
//				.addOrder(Order.desc(START_DATE)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrentWithNotes() {
//		log.info("Fetching all current batches with trainees, and notes");
//		Calendar endDateLimit = Calendar.getInstance();
//		endDateLimit.add(Calendar.MONTH, MONTHS_BACK);
//		return sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.createAlias("trainees.notes", "n", JoinType.LEFT_OUTER_JOIN)
//				.add(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped))
//				.add(Restrictions.le(START_DATE, Calendar.getInstance().getTime()))
//				.add(Restrictions.ge(END_DATE, endDateLimit.getTime())).add(Restrictions.eq("n.qcFeedback", true))
//				.addOrder(Order.desc(START_DATE)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrentWithTrainees() {
//		log.info("Fetching all current batches with trainees, grades");
//		Calendar endDateLimit = Calendar.getInstance();
//		endDateLimit.add(Calendar.MONTH, MONTHS_BACK);
//		return sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.createAlias(T_GRADES, "g", JoinType.LEFT_OUTER_JOIN).add(Restrictions.gt(G_SCORE, 0.0))
//				.add(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped))
//				.add(Restrictions.le(START_DATE, Calendar.getInstance().getTime()))
//				.add(Restrictions.ge(END_DATE, endDateLimit.getTime())).addOrder(Order.desc(START_DATE))
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//	}
//
//	/**
//	 * Looks for all batches that are currently actively in training. Useful for VP
//	 * and QC to get snapshots of currently operating batches.
//	 * 
//	 * @param auth
//	 * @returnF
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrent() {
//		log.info("Fetching all current batches with active trainees");
//		Calendar endDateLimit = Calendar.getInstance();
//		endDateLimit.add(Calendar.MONTH, MONTHS_BACK);
//		List<Batch> batches = sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.add(Restrictions.le(START_DATE, Calendar.getInstance().getTime()))
//				.add(Restrictions.ge(END_DATE, endDateLimit.getTime())).addOrder(Order.desc(START_DATE))
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//		batches.parallelStream()
//				.forEach(b -> b.getTrainees().removeIf(t -> t.getTrainingStatus().equals(TrainingStatus.Dropped)));
//		return batches;
//	}
//
//	/**
//	 * Find a batch by its given identifier
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public Batch findOne(Integer batchId) {
//		log.info("Fetching batch: " + batchId);
//		return (Batch) sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq(BATCH_ID, batchId))
//				.add(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped)).uniqueResult();
//	}
//
//	/**
//	 * Find a batch by its given identifier
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public Batch findOneWithDroppedTrainees(Integer batchId) {
//		log.info("Fetching batch: " + batchId);
//		return (Batch) sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq(BATCH_ID, batchId))
//				.uniqueResult();
//	}
//
//	/**
//	 * Find a batch by its given identifier, all trainees, and all their grades
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public Batch findOneWithTraineesAndGrades(Integer batchId) {
//		log.info("Fetching batch with trainees: " + batchId);
//		return (Batch) sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.createAlias("t.grades", "g", JoinType.LEFT_OUTER_JOIN).add(Restrictions.gt(G_SCORE, 0.0))
//				.add(Restrictions.eq(BATCH_ID, batchId)).add(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped))
//				.uniqueResult();
//	}
//
//	/**
//	 * Update details for a batch
//	 * 
//	 * @param batch
//	 */
//	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void update(Batch batch) {
//		log.info("Updating batch: " + batch);
//		batch.setStartDate(new Date(batch.getStartDate().getTime() + TimeUnit.DAYS.toMillis(1)));
//		batch.setEndDate(new Date(batch.getEndDate().getTime() + TimeUnit.DAYS.toMillis(1)));
//		sessionFactory.getCurrentSession().saveOrUpdate(batch);
//	}
//
//	/**
//	 * Delete a batch from the database
//	 * 
//	 * @param batch
//	 */
//	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void delete(Batch batch) {
//		log.info("Deleting batch: " + batch);
//		sessionFactory.getCurrentSession().delete(batch);
//	}
//
//	/**
//	 * Looks for all batches that whose starting date is after the given year,
//	 * month, and day. Month is 0-indexed Return all batches, trainees for that
//	 * batch, and the grades for each trainee
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllAfterDate(Integer month, Integer day, Integer year) {
//		Calendar startDate = Calendar.getInstance();
//		startDate.set(year, month, day);
//		log.info("Fetching all current batches since: " + startDate.getTime().toString());
//		return sessionFactory.getCurrentSession().createCriteria(Batch.class)
//				.createAlias(TRAINEES, "t", JoinType.LEFT_OUTER_JOIN)
//				.createAlias(T_GRADES, "g", JoinType.LEFT_OUTER_JOIN).add(Restrictions.gt(G_SCORE, 0.0))
//				.add(Restrictions.ne(T_TRAINING_STATUS, TrainingStatus.Dropped))
//				.add(Restrictions.ge(START_DATE, startDate.getTime())).addOrder(Order.desc(START_DATE))
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//	}
}
