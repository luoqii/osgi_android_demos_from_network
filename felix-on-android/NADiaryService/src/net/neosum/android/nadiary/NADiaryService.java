package net.neosum.android.nadiary;

import java.util.List;

public interface NADiaryService 
{
	public String insertNewMessage(String msg);
	public List<String> getMessageList();	
}
