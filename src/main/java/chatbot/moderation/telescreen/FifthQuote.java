package chatbot.moderation.telescreen;

import chatbot.moderation.Moderations;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FifthQuote extends Moderations{

	String id;

	@Override
	public String getString() {
		return " i suppose we may as well say goodbye ";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		id = event.getAuthor().getId();
		GuildVoiceState vs = event.getMember().getVoiceState();

		if (!FirstQuote.update(vs, id, 3)) return;

		event.getChannel().sendMessage("You may as well say goodbye").tts(true).queue();

		VoiceChannel endVC = event.getGuild().getVoiceChannelsByName(FirstQuote.ending, true).get(0);
		VoiceChannel startVC = event.getGuild().getVoiceChannelsByName(FirstQuote.starting, true).get(0);

		move(event, endVC, startVC);
	}

	void move(MessageReceivedEvent event, VoiceChannel endVC, VoiceChannel startVC) {
		event.getGuild().moveVoiceMember(event.getMember(), endVC).queue();
		//move bots into vc
		for (Member m : startVC.getMembers()) 
			if (m.getUser().isBot()) 
				event.getGuild().moveVoiceMember(m, endVC).queue();
	}


}
