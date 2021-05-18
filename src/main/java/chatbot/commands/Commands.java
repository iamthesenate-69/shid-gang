package chatbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Commands {
	public abstract String name();
	public abstract String description();
    public abstract String usage();
    public abstract int cooldown();
	public abstract void execute(MessageReceivedEvent event, String[] args);
}
