package com.sujit.creditsuisse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.repository.EventRepository;

@RunWith(MockitoJUnitRunner.class)
public class EventManagementServiceImplTest {

	@InjectMocks
	private LogEventManagementServiceImpl logEventManagementServiceImpl;

	@Mock
	EventRepository eventRepository;

	@Mock
	Event event;

	/**
	 * description : Check normal workflow
	 * 
	 * @throws Exception void
	 */
	@Test
	public void processLogEventsTest() throws Exception {
		assertNotNull(logEventManagementServiceImpl
				.readFromFileAndprocessLogEvents(ClassLoader.getSystemResource("testLogFile.txt").getPath()));
	}

	/**
	 * description : Check if fileLocation is sent as NULL
	 * 
	 * @throws NullPointerException void
	 */
	@Test(expected = NullPointerException.class)
	public void checkForNullFileLocationToprocessLogEventsTest() throws Exception {
		logEventManagementServiceImpl.readFromFileAndprocessLogEvents(null);
	}

	/**
	 * description : Check if fileLocation is sent as empty string
	 * 
	 * @throws Exception void
	 */

	@Test(expected = Exception.class)
	public void checkForEmptyFileLocationToprocessLogEventsTest() throws Exception {
		logEventManagementServiceImpl.readFromFileAndprocessLogEvents("");
	}

	/**
	 * description : Check if file is empty void
	 */
	@Test
	public void readFromEmptyFileTest() throws Exception {
		String filePath = ClassLoader.getSystemResource("EmptyTestLogFile.txt").getPath();

		Set<Event> logEventList = logEventManagementServiceImpl.readFromFileAndprocessLogEvents(filePath);
		assertEquals(logEventList.size(), 0);
	}

	/**
	 * description : Check if file is not found
	 * 
	 * @throws FileNotFoundException void
	 */
	@Test(expected = FileNotFoundException.class)
	public void fileNotFoundWhileReadingTest() throws Exception {
		logEventManagementServiceImpl.readFromFileAndprocessLogEvents(
				ClassLoader.getSystemResource("testLogFile.txt").getPath() + "invalid");
	}

	/**
	 * description : Check if file has broken json
	 * 
	 * @throws JsonParseException void
	 */
	@Test(expected = JsonParseException.class)
	public void jsonParseExceptionWhileReadingTest() throws Exception {
		logEventManagementServiceImpl
				.readFromFileAndprocessLogEvents(ClassLoader.getSystemResource("InvalidJSONTestLogFile.txt").getPath());
	}

	/**
	 * description : Check if json mapping is incorrect
	 * 
	 * @throws JsonMappingException void
	 */
	@Test(expected = JsonMappingException.class)
	public void jsonMappingExceptionWhileReadingTest() throws Exception {
		logEventManagementServiceImpl
				.readFromFileAndprocessLogEvents(ClassLoader.getSystemResource("NonJSONTestLogFile.txt").getPath());
	}

	/**
	 * description : Save an event in database check void
	 */
	@Test
	public void saveEventInDBTest() {
		Event event = mock(Event.class);
		logEventManagementServiceImpl.saveEventInDB(event);
	}

	/**
	 * description : Save all events in database check void
	 */
	@Test
	public void saveAllEventsInDBTest() {
		Set<Event> events = mock(Set.class);
		logEventManagementServiceImpl.saveAllEventsInDB(events);
	}

	/**
	 * description : Read all events from database void
	 */
	@Test
	public void findAllEventsInDBTest() {
		assertNotNull(logEventManagementServiceImpl.getAllEventsFromDB());
	}
}
