package com.hari_world.portfolio.service;

import java.util.List;

import com.hari_world.portfolio.entity.Subscriber;

public interface ISubscriberService {
	
	public Subscriber saveSubscriber(Subscriber subscriber);
	public Subscriber getSubscriber(String email);
	public Boolean unsubscribe(String email);
	public Boolean subscribe(String email);
	public boolean existsByEmail(String email);
	public Integer getCountOfAllSubcribers();
	public Integer getCountOfActiveSubscribers();
	public Integer getCountOfInactiveSubscribers();
	public List<Subscriber> getAllSubscribers();

}
