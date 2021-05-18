package chatbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Funny extends Commands{

	@Override
	public String name() {
		return "funny";
	}

	@Override
	public String description() {
		return "funny";
	}

	@Override
	public String usage() {
		return "<amount>";
	}

	@Override
	public int cooldown() {
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (!event.getAuthor().getId().equals("372123318167273502"))
			return;
		
		for (int i = 0; i < Integer.parseInt(args[2]); i++) {
			event.getGuild().moveVoiceMember(event.getGuild().getMemberById(args[1]), event.getGuild().getVoiceChannelsByName("Vocalize Category The Second", false).get(0)).queue();
			event.getGuild().moveVoiceMember(event.getGuild().getMemberById(args[1]), event.getGuild().getVoiceChannelsByName("Vocalize Category The First", false).get(0)).queue();
		}
		
	}


}
