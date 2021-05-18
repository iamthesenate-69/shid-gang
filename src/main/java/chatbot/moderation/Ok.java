package chatbot.moderation;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ok extends Moderations{

	@Override
	public String getString() {
		return " ok ";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		event.getMessage().addReaction(event.getGuild().getEmoteById("812729347961389056")).queue();
	}

}
