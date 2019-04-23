package com.example.SystemEvents.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.SystemEvents.dao.EventsRepository;
import com.example.SystemEvents.model.Event;

@Component
public class EventWriter implements ItemWriter<Event> {

	Logger log = LoggerFactory.getLogger(EventWriter.class);
	@Autowired
	EventsRepository eventsRepository;
	
	@Override
	public void write(List<? extends Event> events) throws Exception {
		log.info("Method Name - write");
		log.debug("Events to be saved size - "+events.size());
		eventsRepository.saveAll(events);
	}

}
