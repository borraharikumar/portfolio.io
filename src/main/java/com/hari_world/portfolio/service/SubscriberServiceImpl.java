package com.hari_world.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hari_world.portfolio.entity.Subscriber;
import com.hari_world.portfolio.exception.SubscriberNotFoundException;
import com.hari_world.portfolio.reposirory.SubscriberRepository;
import com.hari_world.portfolio.util.EmailUtil;

@Component
public class SubscriberServiceImpl implements ISubscriberService {

	@Autowired
	private SubscriberRepository subscriberRepository;
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public Subscriber saveSubscriber(Subscriber subscriber) {
		subscriber = subscriberRepository.save(subscriber);
		emailUtil.sendSubscriptionConfirmedMessage(subscriber.getEmail(), subscriber.getName());
		return subscriber;
	}

	@Override
	public Boolean unsubscribe(String email) {
		Subscriber subscriber = getSubscriber(email);
		subscriber.setActive(false);
		return !subscriberRepository.save(subscriber).getActive();
	}
	
	@Override
	public boolean existsByEmail(String email) {
		return subscriberRepository.getCountByEmail(email)>0;
	}

	@Override
	public Subscriber getSubscriber(String email) {
		return subscriberRepository.findByEmail(email)
				.orElseThrow(() -> new SubscriberNotFoundException("Subscriber not found with email '" + email + "'"));
	}

	@Override
	public Boolean subscribe(String email) {
		Subscriber subscriber = getSubscriber(email);
		subscriber.setActive(true);
		return subscriberRepository.save(subscriber).getActive();
	}

	@Override
	public Integer getCountOfAllSubcribers() {
		return (int) subscriberRepository.count();
	}

	@Override
	public Integer getCountOfActiveSubscribers() {
		return subscriberRepository.findCountOfActiveSubscribers();
	}

	@Override
	public Integer getCountOfInactiveSubscribers() {
		return subscriberRepository.findCountOfInactiveSubscribers();
	}

	@Override
	public List<Subscriber> getAllSubscribers() {
		return subscriberRepository.findAll();
	}

}
