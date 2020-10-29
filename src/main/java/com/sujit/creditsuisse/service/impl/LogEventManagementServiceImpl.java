package com.sujit.creditsuisse.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.model.Log;
import com.sujit.creditsuisse.repository.EventRepository;
import com.sujit.creditsuisse.service.LogEventManagementService;

/**
 * @author sujit
 *
 * @description Class that manages all services for logs and events
 */
@Service
public class LogEventManagementServiceImpl implements LogEventManagementService {

	@Autowired
	private EventRepository eventRepository;

	Logger logger = LogManager.getLogger(LogEventManagementServiceImpl.class);

	/**
	 * @author sujit
	 *
	 * @description Function that reads from the specified file location and maps
	 *              the read JSON to Log object. Add the log to a map and create
	 *              Event object when similar id Log is found in map.
	 *
	 * @param String
	 *
	 * @return Set<Event>
	 */
	@Override
	public Set<Event> readFromFileAndprocessLogEvents(String fileLocation) throws Exception {

		// Check for null fileLocation or empty string in fileLocation
		if (fileLocation == null)
			throw new NullPointerException();
		else if (fileLocation.equals("") || fileLocation.length() == 0) {
			throw new Exception("Please provide file location");
		}

		// Map to store log id with the Log object for that Log
		Map<String, Log> eventsMap = new ConcurrentHashMap<String, Log>();

		// Set to store event objects that have been created from Log objects
		Set<Event> eventSet = new HashSet<Event>();

		// Try with resource block
		try (Scanner myReader = new Scanner(new File(fileLocation))) {

			// Object mapper to map json properties with the class variables
			ObjectMapper logObjectMapper = new ObjectMapper();

			while (myReader.hasNextLine()) {
				String jsonData = myReader.nextLine();
				// Map JSON values with Log class to create Log object
				Log currentLog = logObjectMapper.readValue(jsonData, Log.class);

				// Check if the Log id is found in Map, if found, go to else block, else add the
				// object to map
				if (!eventsMap.containsKey(currentLog.getId())) {
					eventsMap.put(currentLog.getId(), currentLog);
				} else {
					Log previousLog = eventsMap.get(currentLog.getId());

					// Calculate the absolute value of duration
					long duration = Math.abs(currentLog.getTimestamp() - previousLog.getTimestamp());

					// Create object of event using Log object and duration
					Event event = new Event(currentLog, duration);
					eventSet.add(event);

					// Remove the
					eventsMap.remove(currentLog.getId());
				}
			} // Exception Handling
		} catch (FileNotFoundException e) {
			throw e;
		} catch (JsonParseException e) {
			throw e;
		} catch (JsonMappingException e) {
			throw e;
		}

		return eventSet;
	}

	/**
	 * @author sujit
	 *
	 * @description Function that saves a single event to HSQLDB
	 *
	 * @param Event
	 */
	@Override
	public void saveEventInDB(Event event) {
		eventRepository.save(event);
	}

	/**
	 * @author sujit
	 *
	 * @description Function that saves a set of events to HSQLDB
	 *
	 * @param Set<Event>
	 */
	@Override
	public void saveAllEventsInDB(Set<Event> eventSets) {
		eventRepository.saveAll(eventSets);
	}

	/**
	 * @author sujit
	 *
	 * @description Function that reads all events from database to cross verify.
	 *
	 * @return Set<Event>
	 */
	@Override
	public List<Event> getAllEventsFromDB() {
		return eventRepository.findAll();
	}
}
