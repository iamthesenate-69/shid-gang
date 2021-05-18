package chatbot.moderation.telescreen;

import chatbot.moderation.Moderations;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ThirdQuote extends Moderations{
	
	@Override
	public String getString() {
		return " now they can see us ";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		String id = event.getAuthor().getId();
		GuildVoiceState vs = event.getMember().getVoiceState();
		
		if (!FirstQuote.update(vs, id, 1)) return;
			
		event.getChannel().sendMessage("Now we can see you").tts(true).queue();
	}
}
