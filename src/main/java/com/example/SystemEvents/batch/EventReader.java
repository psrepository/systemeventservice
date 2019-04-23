package com.example.SystemEvents.batch;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.stereotype.Component;

import com.example.SystemEvents.model.Event;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

/**
 * Custom event reader
 * Reads the file from the resources folder and
 * then reads the data from the file 
 * 
 * @author psangani
 *
 */
@Component
public class EventReader extends AbstractItemStreamItemReader<Event> {

	Logger log = LoggerFactory.getLogger(EventReader.class);
	
	private static final String EVENT_ELEMENT = "events";
	
	private JsonParser jsonParser;
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		log.info("Method Name - write");
		JsonFactory jsonFactory = new MappingJsonFactory();
		try {
			File jsonFile = new File(getClass().getClassLoader().getResource("events.json").getFile());
			jsonParser = jsonFactory.createParser(jsonFile);
			
			if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
				moveParserToTargetElement();
			}
			
		} catch (IOException e) {
			log.error("Error creating JsonParser", e.getMessage());
			throw new ItemStreamException(e);
		}
		
	}
	
	private void moveParserToTargetElement() throws IOException {
		log.info("Method Name - moveParserToTargetElement");
		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jsonParser.getCurrentName();

			if (EVENT_ELEMENT.equals(fieldName)) {
				jsonParser.nextToken();
				break;
			}
		}
		
	}

	@Override
	public Event read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		log.info("Method Name - read");
		if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			Class<Event> clazz = (Class<Event>) Class.forName("com.example.SystemEvents.model.Event");
			return jsonParser.readValueAs(clazz);
		} else {
			return null;
		}
	}
	
	@Override
	public void close() throws ItemStreamException {
		log.info("Method Name - close");
		try {
			jsonParser.close();
		} catch (IOException e) {
			log.error("Error closing jsonparser", e.getMessage());
			throw new ItemStreamException(e);
		}
	}

}
