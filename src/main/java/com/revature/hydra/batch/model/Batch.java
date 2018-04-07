package com.revature.hydra.batch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer batchId;
    private Integer borderlineGradeThreshold;
    private Timestamp endDate;
    private Integer goodGradeThreshold;
    private String location;
    private String skillType;
    private Timestamp startDate;
    private String trainingName;
    private String trainingType;
    private Integer numberOfWeeks;
    private Integer coTrainerId;
    private Integer trainerId;
    private Integer addressId;
    private Integer gradedWeeks;


}
