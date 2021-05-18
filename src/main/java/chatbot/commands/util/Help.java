package chatbot.commands.util;

import chatbot.Bot;
import chatbot.commands.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help extends Commands{
	@Override
	public String name() {
		return "help";
	}

	@Override
	public String description() {
		return "helps with stuff idk";
	}

	@Override
	public String usage() {
		return "[command]";
	}

	@Override
	public int cooldown() {
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (args.length < 2)
			getHelp("help", event.getChannel());
		else
			getHelp(args[1], event.getChannel());
	}

	void getHelp(String string, MessageChannel channel) {
		if (Bot.commands.containsKey(string)) {
			Commands c = Bot.commands.get(string);
			EmbedBuilder eb = new EmbedBuilder();
			eb.setDescription(c.description());
			eb.setFooter(c.name() +" "+ c.usage());
			eb.setAuthor(c.name(), "https://dmoj.ca/problem/stnbd2", "https://static.dmoj.ca/media/martor/da93f548-1998-44d9-bcc9-fd82daad05a0.jpg");
			channel.sendMessage(eb.build()).queue();
			return;
		}channel.sendMessage("no command `" + string + "`").queue();
	}
}
