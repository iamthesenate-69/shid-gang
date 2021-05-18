package chatbot.moderation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import chatbot.commands.Quiz;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class VerbTest extends Moderations{


	@Override
	public String getString() {
		return "";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {

		User u = event.getAuthor();
		
		if (Quiz.pending.containsKey(u) && !event.getMessage().getContentRaw().contains("hlfrench")) {
			if (args.equals(Quiz.pending.get(u))) {
				event.getChannel().sendMessage("tu as raison").queue();
				Quiz.pending.remove(u);
				Quiz.frenchScores.put(u.getId(), Quiz.frenchScores.get(u.getId())+1);
			}
			else {
				event.getChannel().sendMessage("non, c'est `" + Quiz.pending.get(event.getAuthor())+"`").queue();
				Quiz.pending.remove(event.getAuthor());
				Quiz.frenchScores.put(u.getId(), Quiz.frenchScores.get(u.getId())-1);
			}
			backup();
		}

	}

	String modify(String string, int i) {
		return (Integer.parseInt(string)+i)+"";
	}

	void backup() {
		Properties properties = new Properties();
		for (String k : Quiz.frenchScores.keySet())
			properties.put(k, Quiz.frenchScores.get(k)+"");
		try {
			properties.store(new FileOutputStream(Quiz.pathToProperties), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
