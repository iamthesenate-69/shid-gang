package chatbot;

import chatbot.moderation.Moderations;
import chatbot.reactions.Reaction;

import java.io.IOException;

import javax.annotation.Nonnull;

import chatbot.Log;
import chatbot.commands.Commands;
import chatbot.Bot.Limiter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageEvents extends ListenerAdapter{

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		Message msg = event.retrieveMessage().complete();
		if (msg.getAuthor().isBot() || !event.getReaction().getReactionEmote().isEmoji()) return;
		String c = event.getReaction().getReactionEmote().getEmoji();
		for (Reaction r : Bot.reactions.values()) 
			if (r.types().contains(c))
				r.execute(event, msg);
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Log.archiveMessage(event);
		if (event.getAuthor().isBot()) return;

		String msgRaw = event.getMessage().getContentRaw().toLowerCase();
		String msgClean = event.getMessage().getContentRaw().toLowerCase().replaceAll("[^a-z ]", " ");
		String[] split = msgRaw.split("( +)");
		
		updateCooldown(event.getAuthor());
		
		//commands
		if (containsPrefix(split[0]) && Bot.commands.containsKey(removePrefix(split[0])) && hasNoCooldown(event.getAuthor())) {
			try { 
				Commands c = Bot.commands.get(removePrefix(split[0]));
				c.execute(event, split);
				Bot.cooldown.add(new Limiter(event.getAuthor(), System.currentTimeMillis()+c.cooldown()));
			} catch (Exception e) {
				event.getChannel().sendMessage("Error: " + e.getMessage()).queue();
			}
		}
		//moderation
		for (Moderations m : Bot.moderations.values())	//alright stop; this is a complete clusterfuck; how did this get so out of control
			if ((" "+msgClean+" ").contains(m.getString()) || msgRaw.contains(m.getString())) 
				m.execute(event, msgRaw);

	}
	boolean containsPrefix(String string) {
		for (int i = 0; i < Bot.getPrefix().length; i++) {
			if (string.startsWith(Bot.getPrefix()[i])) {
				return true;
			}
		}
		return false;
	}

	String removePrefix(String string) {
		for (int i = 0; i < Bot.getPrefix().length; i++) {
			if (string.startsWith(Bot.getPrefix()[i])) {
				return string.substring(Bot.getPrefix()[i].length());
			}
		}
		return null;
	}

	public void onReady(ReadyEvent event) {
		try {
			new Log("699278323896811551", "802406010383892491", event); //telescreen in shid!gang
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void onMessageUpdate(MessageUpdateEvent event) {
		if (event.getAuthor().isBot()) return;
		//		Log.retrieveUpdatedMessage(event);
	}
	@Override
	public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
		//		Log.retrieveDeletedMessage(event);
	}

	//removes from Timer when t - current time is negative
	public void updateCooldown(User u) {
		for (int i = 0; i < Bot.cooldown.size(); i++) 
			if (Bot.cooldown.get(i).hasUser(u) && Bot.cooldown.get(i).t - System.currentTimeMillis() <= 0) 
				Bot.cooldown.remove(i);


	}

	//false if member is contained in Timer
	//true otherwise
	public boolean hasNoCooldown(User u) {
		for (int i = 0; i < Bot.cooldown.size(); i++) 
			if (Bot.cooldown.get(i).hasUser(u))
				return false;

		return true;
	}



}
