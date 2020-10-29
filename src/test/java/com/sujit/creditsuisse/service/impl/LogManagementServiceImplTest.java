package com.sujit.creditsuisse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sujit.creditsuisse.model.Log;

@RunWith(MockitoJUnitRunner.class)
public class LogManagementServiceImplTest {

	@InjectMocks
	private LogManagementServiceImpl logManagementServiceImpl;

	@Test
	public void readFromFileTest() throws Exception {
		String filePath = ClassLoader.getSystemResource("testLogFile.txt").getPath();

		List<Log> logEventList = logManagementServiceImpl.readFromFile(filePath);
		assertNotNull(logEventList);
	}
	
	@Test
	public void readFromEmptyFileTest() throws Exception {
		String filePath = ClassLoader.getSystemResource("EmptyTestLogFile.txt").getPath();

		List<Log> logEventList = logManagementServiceImpl.readFromFile(filePath);
		assertEquals(logEventList.size(), 0);
//		assertEquals(6, logEventList.size());
	}
	
	@Test(expected = FileNotFoundException.class)
	public void fileNotFoundWhileReadingTest() throws Exception {
		logManagementServiceImpl.readFromFile(ClassLoader.getSystemResource("testLogFile.txt").getPath() + "invalid");
	}

	@Test(expected = JsonParseException.class)
	public void jsonParseExceptionWhileReadingTest() throws Exception {
		logManagementServiceImpl.readFromFile(ClassLoader.getSystemResource("InvalidJSONTestLogFile.txt").getPath());
	}
	
	@Test(expected = JsonMappingException.class)
	public void jsonMappingExceptionWhileReadingTest() throws Exception {
		logManagementServiceImpl.readFromFile(ClassLoader.getSystemResource("NonJSONTestLogFile.txt").getPath());
	}
}
