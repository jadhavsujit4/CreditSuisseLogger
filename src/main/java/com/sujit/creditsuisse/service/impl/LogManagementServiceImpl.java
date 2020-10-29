package com.sujit.creditsuisse.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sujit.creditsuisse.model.Log;
import com.sujit.creditsuisse.service.LogManagementService;

@Service
public class LogManagementServiceImpl implements LogManagementService {

	@SuppressWarnings("deprecation")
	@Override
	public List<Log> readFromFile(String fileLocation) throws Exception {
		List<Log> logEventList = new ArrayList<Log>();

		try (Scanner myReader = new Scanner(new File(fileLocation))) {
			ObjectMapper objectMapper = new ObjectMapper();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				Log logEvent = objectMapper.readValue(data, Log.class);
				logEventList.add(logEvent);
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred: FileNotFoundException.");
			throw new FileNotFoundException();
		} catch (JsonParseException e) {
			System.out.println("An error occurred: JsonParseException.");
			throw e;
		} catch (JsonMappingException e) {
			System.out.println("An error occurred: JsonMappingException.");
			throw new JsonMappingException(fileLocation);
		}
		return logEventList;
	}
}
