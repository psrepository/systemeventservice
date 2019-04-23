package com.example.SystemEvents.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.SystemEvents.dao.EventsRepository;
import com.example.SystemEvents.model.Event;
import com.example.SystemEvents.service.EventsService;

@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventsRepository eventsRepository;
	
	public Page<Event> listEvents(Example<Event> example, Pageable pageOptions) {
		Page<Event> returns = null;
		if(example == null) {
			returns = eventsRepository.findAll(pageOptions);
		}
		else {
			returns = eventsRepository.findAll(example, pageOptions);
		}
		return returns;
	}
}

