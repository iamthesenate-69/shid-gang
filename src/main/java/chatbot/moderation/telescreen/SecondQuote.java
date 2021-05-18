package chatbot.moderation.telescreen;

import chatbot.moderation.Moderations;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SecondQuote extends Moderations{

	@Override
	public String getString() {
		return " it was behind the picture ";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		String id = event.getAuthor().getId();
		GuildVoiceState vs = event.getMember().getVoiceState();
		
		if (!FirstQuote.update(vs, id, 0)) return;
		
		event.getChannel().sendMessage("It was behind the picture").tts(true).queue();
	}


}
