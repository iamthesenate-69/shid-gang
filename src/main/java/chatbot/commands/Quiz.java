package chatbot.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Quiz extends Commands {

	@Override
	public String name() {
		return "hlfrench";
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "hl french is funy";
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public int cooldown() {
		// TODO Auto-generated method stub
		return 500;
	}

	public static HashMap<User, String> pending = new HashMap<User, String>();
	public static HashMap<String, Integer> frenchScores = new HashMap<String, Integer>();


	ArrayList<Verb> verbs = new ArrayList<Verb>();
	String[] pronouns = {"je", "tu", "il/elle", "nous", "vous", "ils/elles"};

	String pathToCsv = ".\\presenttence.csv";
	public static String pathToProperties = ".\\frenchscores.properties";

	ObjectOutputStream writer;

	public Quiz() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(pathToProperties));

			Set<String> keys = properties.stringPropertyNames();
			for (String key : keys) {
				frenchScores.put(key, Integer.parseInt(properties.getProperty(key)));
			}

			BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
			String row;
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				verbs.add(new Verb(data[0], removeFirst(data)));
			}
			csvReader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	String[] removeFirst(String[] data) {
		return Arrays.copyOfRange(data, 1, data.length); 	//removes first element
	}

	String[] conv(String je, String tu, String il, String nous, String vous, String ils) {
		String[] s = {je, tu, il, nous, vous, ils};
		return s;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		Random r = new Random();

		int n = r.nextInt(pronouns.length);
		Verb v = RandomVerb(r);
		User u = event.getAuthor();

		event.getChannel().sendMessage(pronouns[n] + " (" + v.name + ")").queue();
		pending.put(u, v.pronoun[n]);

		if (!frenchScores.containsKey(u.getId())) {
			frenchScores.put(u.getId(), 0);
		}
	}

	Verb RandomVerb(Random r) {
		return verbs.get(r.nextInt(verbs.size()));
	}

	class Verb {
		String name;
		String[] pronoun;
		Verb(String _name, String[] _pronoun) {
			name = _name;
			pronoun = _pronoun;
		}
	}
}


