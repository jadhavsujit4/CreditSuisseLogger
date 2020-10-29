package com.sujit.creditsuisse.service;

import java.util.List;
import java.util.Set;

import com.sujit.creditsuisse.model.Event;

/**
 * 
 * @author sujit
 *
 * @description Interface for Log and event management services. This is basically used if we need to provide multiple implementations
 */
public interface LogEventManagementService {

	public Set<Event> readFromFileAndprocessLogEvents(String fileLocation) throws Exception;

	public void saveEventInDB(Event event);

	public void saveAllEventsInDB(Set<Event> eventSet);

	public List<Event> getAllEventsFromDB();

}
