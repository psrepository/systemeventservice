package com.example.SystemEvents.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SystemEvents.model.Event;

@Repository
public interface EventsRepository extends MongoRepository<Event, String>{

}
