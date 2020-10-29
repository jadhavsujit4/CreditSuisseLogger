package com.sujit.creditsuisse;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.model.Log;
import com.sujit.creditsuisse.service.EventManagementService;
import com.sujit.creditsuisse.service.LogManagementService;

@SpringBootApplication(scanBasePackages = "com.sujit.creditsuisse")
public class Application implements CommandLineRunner {

	@Autowired
	private final LogManagementService logManagementService;

	@Autowired
	private final EventManagementService eventManagementService;

	public Application(LogManagementService logManagementService, EventManagementService eventManagementService) {
		this.logManagementService = logManagementService;
		this.eventManagementService = eventManagementService;
	}

	public static void main(String[] args) {

		try {
			SpringApplication app = new SpringApplication(Application.class);
			if (args != null && args.length > 0) {
				app.run(args);
			}
			else {
				System.out.println("Please specify Logfile path");
			}

		} catch (Exception e) {
			System.out.println("Exception occured in main: " + e);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			List<Log> logEvents = logManagementService.readFromFile(args[0]);

			if (logEvents.size() > 0) {
				Set<Event> eventSet = eventManagementService.processLogEvents(logEvents);
				/*
				 * Event events = null; for (Event event : eventSet) { events = event; }
				 * eventManagementService.saveEventInDB(events);
				 */
				eventManagementService.saveAllEventsInDB(eventSet);

				eventManagementService.getAllEventsFromDB().stream().forEach(System.out::println);
			} else {
				System.out.println("No Logs to process");
			}

		} catch (Exception e) {
			System.out.println("Exception occured : " + e);
		}

	}
}