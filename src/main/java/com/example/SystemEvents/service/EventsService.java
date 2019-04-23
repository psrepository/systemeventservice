package com.example.SystemEvents.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.SystemEvents.model.Event;

public interface EventsService {

	/**
	 * Returns Events for the match criteria and page options
	 * 
	 * @param example
	 * @param pageOptions
	 * @return Page<Event>
	 */
	Page<Event> listEvents(Example<Event> example, Pageable pageOptions);
}
