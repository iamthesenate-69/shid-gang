package chatbot;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import javax.security.auth.login.LoginException;

import chatbot.commands.AbandonShip;
import chatbot.commands.Bilingual;
import chatbot.commands.Commands;
import chatbot.commands.Funny;
import chatbot.commands.OpenFile;
import chatbot.commands.Paimon;
import chatbot.commands.Quiz;
import chatbot.commands.moderation.Ban;
import chatbot.commands.moderation.Vaporize;
import chatbot.commands.util.CheckVC;
import chatbot.commands.util.Help;
import chatbot.commands.util.IBMarks;
import chatbot.commands.util.Rename;
import chatbot.moderation.Moderations;
import chatbot.moderation.NoNitroAllowed;
import chatbot.moderation.Ok;
import chatbot.moderation.Okay;
import chatbot.moderation.PingRemover;
import chatbot.moderation.TyrantSlurs;
import chatbot.moderation.VerbTest;
import chatbot.moderation.telescreen.FifthQuote;
import chatbot.moderation.telescreen.FirstQuote;
import chatbot.moderation.telescreen.ForthQuote;
import chatbot.moderation.telescreen.SecondQuote;
import chatbot.moderation.telescreen.ThirdQuote;
import chatbot.reactions.Mudae;
import chatbot.reactions.Reaction;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
	
	public static String[] prefix;
	public static HashMap<String, Commands> commands = new HashMap<String, Commands>();
	public static HashMap<String, Moderations> moderations = new HashMap<String, Moderations>();
	public static HashMap<String, Reaction> reactions = new HashMap<String, Reaction>();
	public static ArrayList<Limiter> cooldown = new ArrayList<Limiter>();
	
	Commands[] commandlist = {new Rename(), new AbandonShip(), new IBMarks(), new CheckVC(), new Help(), new Ban(), new Vaporize(), new Quiz(), new Bilingual(), new Funny(), new Paimon(), new OpenFile()};
	Moderations[] moderationlist = {new TyrantSlurs(), new PingRemover(), new FirstQuote(), new SecondQuote(), new ThirdQuote(), new ForthQuote(), new FifthQuote(), new VerbTest(), new NoNitroAllowed(), new Okay(), new Ok()};
	Reaction[] reactionlist = {new Mudae()};
	
	Bot(API[] bot, String _prefix) throws LoginException {
		
		prefix = _prefix.split(" ");
		for (API api : bot) build(api);	
		for (Commands c : commandlist) addC(c);
		for (Moderations m : moderationlist) addM(m);
		for (Reaction r : reactionlist) addR(r);
	}
	
	void addC(Commands c) {
		commands.put(c.name(), c);
		commands.put(c.name(), c);
	}
	void addM(Moderations m) {
		moderations.put(m.getString(), m);
	}
	void addR(Reaction r) {
		reactions.put(r.types(), r);
	}
	
	void build(API api) throws LoginException {
		JDABuilder.create(api.getToken(), EnumSet.allOf(GatewayIntent.class))
		.addEventListeners(new MessageEvents())
		.addEventListeners(new VoiceEvents())
		.setStatus(api.status)
		.setActivity(api.activity)
		.build();
	}
	
	static String[] getPrefix() {
		return prefix;
	}
	
	public static class Limiter {
		User user;
		long t;
		public Limiter (User _user, long _t) {
			user = _user;
			t = _t;
		}

		public boolean hasUser(User m) {
			if (m.equals(user)) 
				return true;

			return false;
		}
		

	}


}