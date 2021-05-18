package chatbot.reactions;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public abstract class Reaction {
	public abstract void execute(MessageReactionAddEvent event, Message msg);
	public abstract String types(); //format: "😳 🔥 💯"


}
