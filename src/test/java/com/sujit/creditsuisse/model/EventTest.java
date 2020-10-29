package com.sujit.creditsuisse.model;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventTest {

	@Mock
	Event event;

	@Test
	public void createLogTest() {
		Log log = mock(Log.class);
		assertNotNull(new Event(log, 6L));
	}

}
