package com.sujit.creditsuisse.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.model.Log;
import com.sujit.creditsuisse.repository.EventRepository;

@RunWith(MockitoJUnitRunner.class)
public class EventManagementServiceImplTest {

	@InjectMocks
	private EventManagementServiceImpl eventManagementServiceImpl;

	@Mock
	EventRepository eventRepository;
	
	@Mock
	Event event;
	

	@Test
	public void processLogEventsTest() {
		List<Log> logEventList = createLogEventListForTests();

		assertNotNull(eventManagementServiceImpl.processLogEvents(logEventList));
	}
	
	@Test(expected = NullPointerException.class)
	public void checkForNullWhileProcessLogEventsTest() {
		List<Log> logEventList = mock(List.class);
		
		eventManagementServiceImpl.processLogEvents(logEventList);
	}

	@Test
	public void saveEventInDBTest() {
		Event event = mock(Event.class);
		eventManagementServiceImpl.saveEventInDB(event);
	}

	@Test
	public void saveAllEventsInDBTest() {
		Set<Event> events = mock(Set.class);
		eventManagementServiceImpl.saveAllEventsInDB(events);
	}

	@Test
	public void findAllEventsInDBTest() {
		assertNotNull(eventManagementServiceImpl.getAllEventsFromDB());
	}

	private static List<Log> createLogEventListForTests() {
		List<Log> logEventList = new ArrayList<Log>();
		Log logEvent = new Log();
		logEvent.setId("scsmbstgrb");
		logEvent.setState("FINISHED");
		logEvent.setTimestamp(1491377495213L);
		logEventList.add(logEvent);

		logEvent = new Log();
		logEvent.setId("scsmbstgrb");
		logEvent.setState("STARTED");
		logEvent.setTimestamp(1491377495216L);
		logEventList.add(logEvent);

		logEvent = new Log();
		logEvent.setId("scsmbstgra");
		logEvent.setState("STARTED");
		logEvent.setType("APPLICATION_LOG");
		logEvent.setHost("12345");
		logEvent.setTimestamp(1491377495212L);
		logEventList.add(logEvent);

		logEvent = new Log();
		logEvent.setId("scsmbstgra");
		logEvent.setState("FINISHED");
		logEvent.setType("APPLICATION_LOG");
		logEvent.setHost("12345");
		logEvent.setTimestamp(1491377495217L);
		logEventList.add(logEvent);

		return logEventList;
	}
}
