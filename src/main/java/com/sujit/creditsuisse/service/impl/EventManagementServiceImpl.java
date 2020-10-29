package com.sujit.creditsuisse.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.model.Log;
import com.sujit.creditsuisse.repository.EventRepository;
import com.sujit.creditsuisse.service.EventManagementService;
import com.sujit.creditsuisse.utils.Constants;

@Service
public class EventManagementServiceImpl implements EventManagementService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public Set<Event> processLogEvents(List<Log> logEvents) {

		if (logEvents == null)
			throw new NullPointerException();

		Map<String, Log> eventsMap = new HashMap<String, Log>();
		Set<Event> eventSet = new HashSet<Event>();

		for (Log logEvent : logEvents) {
			if (!eventsMap.containsKey(logEvent.getId())) {
				eventsMap.put(logEvent.getId(), logEvent);
			} else {
				if (logEvent.getState().equals(Constants.FINISHED)) {
					Log startedLogEvent = eventsMap.get(logEvent.getId());
					long duration = logEvent.getTimestamp() - startedLogEvent.getTimestamp();
					Event event = new Event(logEvent, duration);
					eventSet.add(event);
				} else {
					Log finishedLogEvent = eventsMap.get(logEvent.getId());
					long duration = finishedLogEvent.getTimestamp() - logEvent.getTimestamp();
					Event event = new Event(logEvent, duration);
					eventSet.add(event);
				}
			}
		}

		return eventSet;
	}

	@Override
	public void saveEventInDB(Event event) {
		eventRepository.save(event);
	}

	@Override
	public void saveAllEventsInDB(Set<Event> eventSets) {
		eventRepository.saveAll(eventSets);
	}

	@Override
	public List<Event> getAllEventsFromDB() {
		return eventRepository.findAll();
	}
}
