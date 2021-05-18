package chatbot.reactions;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class Mudae extends Reaction {

	List<Message> lastReact = new ArrayList<Message>();
	
	@Override
	public String types() {
		return "♥️\t💖💞 ❤️ 💕 💝 💓 💗"; //java what the fuck why cant i remove the \t
	}

	@Override
	public void execute(MessageReactionAddEvent event, Message msg) {
		update();
		if (lastReact.contains(msg) || (System.currentTimeMillis()/1000 - msg.getTimeCreated().toEpochSecond()) > 30)
			return;

		event.getChannel().sendMessage("💖 **"+event.getUser().getName()+"** and **"+getcharname(msg)+"** are now married! 💖").queue();
		lastReact.add(msg);
	}
	
	void update() {	//update msg timers
		List<Message> remove = new ArrayList<Message>(); //prevent ConcurrentModificationException 
		for (Message msg : lastReact)
			if ((System.currentTimeMillis()/1000 - msg.getTimeCreated().toEpochSecond()) > 30) 
				remove.add(msg);
		
		lastReact.removeAll(remove);
	}
	
	String getcharname(Message msg) {
		if (!msg.getContentRaw().equals("")) 
			return  msg.getContentRaw();
		else if (msg.getAttachments().size() != 0) 
			return getAttachment(msg.getAttachments().get(0));
			return "[no title]";
	}

	String getAttachment(Attachment att) {
		String filename = att.getFileName();
		String exe = att.getFileExtension();
		return filename.substring(0, filename.length()-exe.length()-1);	//remove .filetype
	}


}
