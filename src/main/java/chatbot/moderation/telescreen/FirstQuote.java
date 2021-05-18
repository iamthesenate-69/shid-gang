package chatbot.moderation.telescreen;

import java.util.ArrayList;
import chatbot.moderation.Moderations;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FirstQuote extends Moderations{

	@SuppressWarnings("unchecked")
	public static ArrayList<String> quoteProgress[] = new ArrayList[4];

	static String starting = "mr. charrington's roof space";
	static String ending = "telescreen rear of canvas";


	@Override
	public String getString() {
		return " we are the dead ";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		String id = event.getAuthor().getId();
		GuildVoiceState vs = event.getMember().getVoiceState();

		if (!update(vs, id, -1)) return;

		event.getChannel().sendMessage("You are the dead").tts(true).queue();
	}


	public FirstQuote() {
		quoteProgress[0] = new ArrayList<String>();
		quoteProgress[1] = new ArrayList<String>();
		quoteProgress[2] = new ArrayList<String>();
		quoteProgress[3] = new ArrayList<String>();
	}

	static boolean update(GuildVoiceState vs, String id, int i) {
		FirstQuote.removeExcept(i, id);

		if (i == -1) {
			if (FirstQuote.inCorrectVC(vs)) {
				FirstQuote.quoteProgress[i+1].add(id);
				return true;
			}return false;
		}
		
		if (!FirstQuote.quoteProgress[i].contains(id) || !FirstQuote.inCorrectVC(vs))
			return false;

		FirstQuote.remove(i, id);

		if (i != 3)
			FirstQuote.quoteProgress[i+1].add(id);

		return true;
	}

	static void remove(int index, String id) {
		if (quoteProgress[index].contains(id)) {
			quoteProgress[index].remove(quoteProgress[index].indexOf(id));
		}
	}

	static void removeExcept(int index, String id) {
		for (int i = 0; i < 4; i++) {
			if (i == index) continue;
			remove(i, id);
		}
	}

	static boolean inCorrectVC(GuildVoiceState vs) {
		if ((vs.inVoiceChannel() && vs.getChannel().getName().equalsIgnoreCase(FirstQuote.starting)))
			return true;

		return false;
	}

}
