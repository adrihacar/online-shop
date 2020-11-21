package entities;

import java.util.List;

public interface ChatDAO {
		
	public void insert(ChatBean newChat) throws Exception;
	public void update(ChatBean chat) throws Exception;
	public List<ChatBean> getChatsByUser(int user);
	public ChatBean getChatById(long chatId);	
}
