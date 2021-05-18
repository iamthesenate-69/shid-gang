package chatbot.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class OpenFile extends Commands{

	@Override
	public String name() {
		return "open";
	}

	@Override
	public String description() {
		return "opens a file on hardrive";
	}

	@Override
	public String usage() {
		return name() + "[path]";
	}

	@Override
	public int cooldown() {
		return 1000;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (!event.getAuthor().getId().equals("372123318167273502")) return;
		File file = new File(args[1]);
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
