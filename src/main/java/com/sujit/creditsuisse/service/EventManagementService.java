package com.sujit.creditsuisse.service;

import java.util.List;
import java.util.Set;

import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.model.Log;

public interface EventManagementService {

	public Set<Event> processLogEvents(List<Log> logEvents);

	public void saveEventInDB(Event event);

	public void saveAllEventsInDB(Set<Event> eventSet);

	public List<Event> getAllEventsFromDB();
	
}
