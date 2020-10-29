package com.sujit.creditsuisse.service;

import java.util.List;

import com.sujit.creditsuisse.model.Log;

public interface LogManagementService {

	public List<Log> readFromFile(String fileLocation) throws Exception;
	
}
