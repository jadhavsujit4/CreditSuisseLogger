package com.sujit.creditsuisse.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogTest {

	@Mock
	Log log;
	
	@Test
	public void createLogTest(){
		when(log.getId()).thenReturn("scsmbstgra");
		assertEquals(log.getId(), "scsmbstgra");
		
		when(log.getHost()).thenReturn("12345");
		assertEquals(log.getHost(), "12345");
		
		when(log.getState()).thenReturn("STARTED");
		assertEquals(log.getState(), "STARTED");
		
		when(log.getTimestamp()).thenReturn(1491377495212L);
		assertNotEquals(log.getTimestamp(), 1491377495213L);
		
		
		
	}
	
}
