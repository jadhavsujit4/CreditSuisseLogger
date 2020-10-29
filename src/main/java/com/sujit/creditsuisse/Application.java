package com.sujit.creditsuisse;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sujit.creditsuisse.model.Event;
import com.sujit.creditsuisse.service.LogEventManagementService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@SpringBootApplication(scanBasePackages = "com.sujit.creditsuisse")
public class Application implements CommandLineRunner {

	static Logger logger = LogManager.getLogger(Application.class);

	@Autowired
	private final LogEventManagementService logEventManagementService;

	public Application(LogEventManagementService logEventManagementService) {
		this.logEventManagementService = logEventManagementService;
	}

	public static void main(String[] args) {
		try {
			SpringApplication app = new SpringApplication(Application.class);

			// If no argument is passed, do not run the application
			if (args != null && args.length > 0) {
				app.run(args);
			} else {
				throw new Exception("Please specify Logfile path");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			// Reading from file to get logs and convert those logs to events
			logger.info("Reading from file and processing to create events");
			Set<Event> eventSet = logEventManagementService.readFromFileAndprocessLogEvents(args[0]);

			// Saving the created events in database
			logger.info("Saving events to HSQLDB");
			logEventManagementService.saveAllEventsInDB(eventSet);

			// Reading the saved events from database
			logger.info("Getting saved events from HSQLDB");
			logEventManagementService.getAllEventsFromDB().stream().forEach(x -> logger.debug(x));

		} catch (Exception e) {
			logger.error("Exception occured in run: " + e);
		}

	}
}