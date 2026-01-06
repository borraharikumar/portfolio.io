package com.hari_world.portfolio.service;

import java.util.List;

import com.hari_world.portfolio.entity.ContactMessage;
import com.hari_world.portfolio.entity.ReplyMessage;

public interface IContactService {

	public ContactMessage saveContactMessage(ContactMessage contact);
	public ContactMessage getContactMessage(Integer id);
	public Integer getCountOfUnreadMessages();
	public List<ContactMessage> getAllUnreadMessages();
	public List<ContactMessage> getAllMessages();
	public List<ContactMessage> getLatestUnreadMessages();
	public void markMessageAsRead(Integer id);
	public ReplyMessage saveReplyMessage(ReplyMessage replyMessage);

}