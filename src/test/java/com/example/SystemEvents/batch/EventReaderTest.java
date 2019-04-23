package com.example.SystemEvents.batch;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.SystemEvents.model.Event;

public class EventReaderTest {

	private EventReader reader;
	
	@Before
    public void setUp() {
        reader = new EventReader();
    }
    
	@Test
	public void eventCount_success_test() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		reader.open(new ExecutionContext());
		int count = 0;
		while(true) {
			Event event = reader.read();
			if(event == null)
				break;
			
			count++;
		}
		assertEquals(9, count);
	}

	@After
    public void tearDown() {
        reader.close();
    }

}
