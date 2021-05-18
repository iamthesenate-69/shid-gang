package chatbot.commands.util;

import chatbot.commands.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class IBMarks extends Commands{

	@Override
	public String name() {
		return "convert";
	}

	@Override
	public String description() {
		return "converts ib mark to standard mark";
	}

	@Override
	public String usage() {
		return "<1-7>";
	}

	@Override
	public int cooldown() {
		return 0;
	}
	
	int high[] = {49, 60, 71, 83, 92, 96, 100};
	int low[] = {0, 50, 61, 72, 84, 93, 97};
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		int n = Integer.parseInt(args[1]);
		event.getChannel().sendMessage(low[n-1] + "-" + high[n-1]).queue();;
	}
	
}
