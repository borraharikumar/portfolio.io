package com.hari_world.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hari_world.portfolio.entity.ContactMessage;
import com.hari_world.portfolio.entity.ReplyMessage;
import com.hari_world.portfolio.exception.ContactRequestNotFoundException;
import com.hari_world.portfolio.reposirory.ContactMessageRepository;
import com.hari_world.portfolio.reposirory.ReplyMessageRepository;

import jakarta.transaction.Transactional;

@Service
public class ContactServiceImpl implements IContactService {

	@Autowired
	private ContactMessageRepository contactRepository;
	@Autowired
	private ReplyMessageRepository replyRepository;

	@Override
	public ContactMessage saveContactMessage(ContactMessage contact) {
		return contactRepository.save(contact);
	}

	@Override
	public ContactMessage getContactMessage(Integer id) {
		return contactRepository.findById(id).orElseThrow(
				() -> new ContactRequestNotFoundException("Contact Request Not Found with id '" + id + "'"));
	}

	@Override
	public Integer getCountOfUnreadMessages() {
		return contactRepository.countByReadStatusFalse();
	}

	@Override
	public List<ContactMessage> getAllUnreadMessages() {
		return contactRepository.findByReadStatusFalse();
	}

	@Override
	public List<ContactMessage> getAllMessages() {
		return contactRepository.findAll();
	}

	@Override
	public List<ContactMessage> getLatestUnreadMessages() {
		return contactRepository.findByReadStatusFalse(PageRequest.of(0, 5));
	}

	@Transactional
	@Override
	public void markMessageAsRead(Integer id) {
		contactRepository.markMessageAsRead(id);
	}

	@Override
	public ReplyMessage saveReplyMessage(ReplyMessage replyMessage) {
		replyMessage = replyRepository.save(replyMessage);
		ContactMessage contactMessage = replyMessage.getContactMessage();
		contactMessage.setReadStatus(true);
		contactRepository.save(contactMessage);
		return replyMessage;
	}

}