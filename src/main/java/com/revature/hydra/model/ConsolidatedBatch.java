package com.revature.hydra.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "CALIBER_BATCH")
@Cacheable
public class ConsolidatedBatch implements Serializable {
	private static final long serialVersionUID = -7000300062384597615L;

	@Id
	@Column(name = "BATCH_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_SEQUENCE")
	@SequenceGenerator(name = "BATCH_ID_SEQUENCE", sequenceName = "BATCH_ID_SEQUENCE")
	private Integer batchId;

	@Column(name = "RESOURCE_ID")
	private String resourceId;

	@NotNull
	@Column(name = "TRAINING_NAME")
	private String trainingName;

	@NotNull
	@Column(name = "TRAINER_ID", nullable = false)
	private Integer trainerId;
	
	@Column(name = "CO_TRAINER_ID")
	private Integer coTrainerId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "SKILL_TYPE")
	private SkillType skillType;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TRAINING_TYPE")
	private TrainingType trainingType;

	@Column(name = "START_DATE", nullable = false)
	private Timestamp startDate;

	@Column(name = "END_DATE", nullable = false)
	private Timestamp endDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BATCH_LOCATION")
	private BatchLocation batchLocation;
	
	@Column(name = "CURRICULUM")
	private Integer curriculum;
	
	@Column(name = "FOCUS")
	private Integer focus;
	
	@ManyToOne
	@JoinColumn(name = "STATUS")
	private BatchStatusLookup batchStatus;

	@Column(name = "ADDRESS_ID")
	private Integer addressId;

	@Column(name = "GOOD_GRADE_THRESHOLD")
	private Short goodGradeThreshold;

	@Column(name = "BORDERLINE_GRADE_THRESHOLD")
	private Short borderlineGradeThreshold;

	@Column(name = "NUMBER_OF_WEEKS", nullable = false)
	private Integer weeks;

	@Column(name = "GRADED_WEEKS")
	private Integer gradedWeeks;
	
	@ElementCollection                                                          
	@CollectionTable(name = "BATCH_SKILL_JT", joinColumns = @JoinColumn(name = "BATCH_ID"))
	@Column(name = "SKILL_ID")                                                    
	private List<Integer> skills;
}

