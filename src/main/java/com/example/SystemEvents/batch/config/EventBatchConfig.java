package com.example.SystemEvents.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.SystemEvents.model.Event;

@Configuration
@EnableBatchProcessing
public class EventBatchConfig {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory
						, ItemReader<Event> itemReader, ItemWriter<Event> itemWriter) {
		Step step = stepBuilderFactory.get("event-file-load")
									.<Event, Event>chunk(100)
									.reader(itemReader)
									.writer(itemWriter)
									.build();
		
		return jobBuilderFactory.get("event-load")
						.incrementer(new RunIdIncrementer())
						.start(step)
						.build();
	}

}
