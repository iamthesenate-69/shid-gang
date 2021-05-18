package chatbot.commands.util;

import chatbot.commands.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Rename extends Commands{

	@Override
	public String name() {
		return "rename";
	}

	@Override
	public String description() {
		return "renames bot";
	}

	@Override
	public String usage() {
		return "<name>";
	}

	@Override
	public int cooldown() {
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (!event.getAuthor().getId().equals("372123318167273502")) return;
		
		String nickname = combine(args);
		
		event.getGuild().modifyNickname(event.getGuild().getMember(event.getJDA().getSelfUser()), nickname).queue(
				success -> event.getChannel().sendMessage("Renamed to "+nickname).queue(),
				failure -> event.getChannel().sendMessage("An error occurred "+nickname).queue());
	}

	String combine(String[] args) {
		String s = "";
		for (int i = 1; i < args.length; i++) {
			s = s + args[i] + " ";
		}
		return s.trim();
	}

}
