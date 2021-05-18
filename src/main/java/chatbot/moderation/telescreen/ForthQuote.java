package chatbot.moderation.telescreen;

import chatbot.moderation.Moderations;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ForthQuote extends Moderations{
	
	@Override
	public String getString() {
		return " the house is surrounded ";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		String id = event.getAuthor().getId();
		GuildVoiceState vs = event.getMember().getVoiceState();
		
		if (!FirstQuote.update(vs, id, 2)) return;
			
		event.getChannel().sendMessage("The house is surrounded").tts(true).queue();
	}


}
