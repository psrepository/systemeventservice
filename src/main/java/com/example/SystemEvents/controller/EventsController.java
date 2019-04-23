package com.example.SystemEvents.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SystemEvents.model.Event;
import com.example.SystemEvents.service.EventsService;

/**
 * Rest Controller returning list of events.
 * Handles paging, filtering and sorting
 * 
 * @author psangani
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")
@RequestMapping("/events")
public class EventsController {

	Logger log = LoggerFactory.getLogger(EventsController.class);
			
	private static final String PARAM_PAGE_SIZE = "pageSize";
	private static final String PARAM_START_ROW = "startRow";
	private static final String PARAM_SEARCH_VALUE = "searchValue";
	private static final String PARAM_SORT_DIRECTION = "sortDirection";
	private static final String PARAM_SORT_COLUMN = "sortColumn";
	
	@Autowired
	EventsService eventsService;
	
	@GetMapping()
	public Page<Event> listEvents(@RequestParam Map<String, String> params) {
		String searchValue = params.get(PARAM_SEARCH_VALUE);
		Example<Event> example = buildExample(searchValue);
		Pageable pageOptions = getPageOptions(params);
		return eventsService.listEvents(example, pageOptions);
	}

	private Example<Event> buildExample(String searchValue) {
		log.info("Method Name - buildExample");
		if(searchValue == null)
			return null;
		
		ExampleMatcher matcher = ExampleMatcher.matchingAll().withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Event event = new Event();
		event.setType(searchValue);
		Example<Event> example = Example.of(event, matcher);
		 
		return example;
	}
	
	/**
	 * Reads request parameters and sets them in the PageRequest
	 * 
	 * @param params
	 * @return Pageable
	 */
	private Pageable getPageOptions(Map<String, String> params) {
		log.info("Method Name - getPageOptions");
		String pageSizeVal = params.get(PARAM_PAGE_SIZE) != null ?  params.get(PARAM_PAGE_SIZE) : "5" ;
		String firstRowVal = params.get(PARAM_START_ROW) != null ? params.get(PARAM_START_ROW) : "0";
		
		Integer pageSize = Integer.parseInt(pageSizeVal);
		Integer pageNum = Integer.parseInt(firstRowVal) / pageSize;
		
		Sort sort = null;
		if(params.get(PARAM_SORT_COLUMN) != null) {
			log.debug("Sort column is not null");
			if("ASC".equalsIgnoreCase(params.get(PARAM_SORT_DIRECTION))) {
				sort = Sort.by(Sort.Direction.ASC, params.get(PARAM_SORT_COLUMN));
			} else {
				sort = Sort.by(Sort.Direction.DESC, params.get(PARAM_SORT_COLUMN));
			}
			
			return PageRequest.of(pageNum, pageSize, sort);
		}
		
		return PageRequest.of(pageNum, pageSize);
	}
	
}
