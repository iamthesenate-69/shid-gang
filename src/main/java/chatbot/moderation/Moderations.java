package chatbot.moderation;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Moderations {
	public abstract String getString();
	public abstract void execute(MessageReceivedEvent event, String args);


}
