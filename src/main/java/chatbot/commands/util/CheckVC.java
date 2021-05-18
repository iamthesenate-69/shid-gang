package chatbot.commands.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import chatbot.commands.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CheckVC extends Commands{

	@Override
	public String name() {
		return "checktelescreen";
	}

	@Override
	public String description() {
		return "outputs the number of members in telescreen";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public int cooldown() {
		return 0;
	}

	String vcName = "telescreen rear of canvas";
	int vcOrder = 0; 
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		int n = countMembers(event.getGuild().getVoiceChannelsByName(vcName, true).get(vcOrder));
		event.getChannel().sendMessage(n + " member(s) in " + vcName).queue();
	}
	
	int countMembers(VoiceChannel vc) {
		int counter = 0;
		for (Member m : vc.getMembers())
			if (!m.getUser().isBot())
				counter++;
		return counter;
	}

}
