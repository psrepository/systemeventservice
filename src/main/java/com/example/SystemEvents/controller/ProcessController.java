package com.example.SystemEvents.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller to kick start the 
 * spring batch job
 * 
 * @author psangani
 *
 */
@RestController
@RequestMapping("/process")
public class ProcessController {

	Logger log = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	/**
	 * Rest service to process the events in the events.json file
	 * 
	 * @return BatchStatus
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobRestartException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws JobParametersInvalidException
	 */
	@GetMapping
	public BatchStatus process() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.info("Method Name - process");
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("process-time", new Date());
		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());

		return jobExecution.getStatus();
	}
}
