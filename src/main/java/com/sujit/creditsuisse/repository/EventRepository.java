package com.sujit.creditsuisse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sujit.creditsuisse.model.Event;

/**
 * 
 * @author sujit
 *
 * @description Repository for database CRUD operations
 */
@Repository
public interface EventRepository extends JpaRepository<Event, String>{

}
