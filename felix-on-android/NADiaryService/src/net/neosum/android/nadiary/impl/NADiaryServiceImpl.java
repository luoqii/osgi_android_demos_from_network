package net.neosum.android.nadiary.impl;

import java.util.ArrayList;
import java.util.List;

import net.neosum.android.nadiary.NADiaryService;

public class NADiaryServiceImpl implements NADiaryService{

	List<String> messages;
	
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public NADiaryServiceImpl()
	{
		messages = new ArrayList<String>();
	}
	@Override
	public String insertNewMessage(String msg) {
		if(msg!=null)
		{
			messages.add(msg);
			return "Message Inserted";
		}
		return "Message Insert fail";
	}

	@Override
	public List<String> getMessageList() {
		
		return messages;
	}


}
