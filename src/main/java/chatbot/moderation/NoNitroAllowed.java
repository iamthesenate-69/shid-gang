package chatbot.moderation;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NoNitroAllowed extends Moderations{

	@Override
	public String getString() {
		return "funny";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		if (!(event.isFromGuild() && event.getGuild().getId().equals("699278323896811551"))) return;
		
		for (Emote e : event.getMessage().getEmotes()) {
			System.out.println(e.toString());
			
			if (e.isAnimated() || !e.getGuild().equals(event.getGuild()))  {
				event.getMessage().delete().queue();
				return;
			}
		}
		
	}
	

}
