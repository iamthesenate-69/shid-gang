package chatbot.commands;


import java.util.ArrayList;
import java.util.Collections;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Bilingual extends Commands {

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "bilingual";
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "WOW CANADA";
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public int cooldown() {
		// TODO Auto-generated method stub
		return 0;
	}
	ArrayList<Sort> sorter;
	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		initialize();
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(getLeaderboard(event.getJDA()));
		eb.setThumbnail(getTopAvatar(event.getJDA()));
		eb.setAuthor("Most Bilingual", 
				"https://www.thecanadianencyclopedia.ca/en/article/bilingualism", 
				"https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Canadian_Duality_Flag.svg/1200px-Canadian_Duality_Flag.svg.png");
		event.getChannel().sendMessage(eb.build()).queue();
	}

	void initialize() {
		sorter = new ArrayList<Sort>();
		for (String k : Quiz.frenchScores.keySet()) {
			System.out.println(k + " " + Quiz.frenchScores.get(k));
			sorter.add(new Sort(k, Quiz.frenchScores.get(k)));
		}
		
		Collections.sort(sorter);
	}

	String getTopAvatar(JDA jda) {
		return jda.getUserById(sorter.get(1).name).getAvatarUrl();
	}

	String getLeaderboard(JDA jda) {
		String s = "";
		for (int i = 0; i < sorter.size(); i++) {
			
			if (jda.getUserById(sorter.get(i).name).getName() == null) {
				s += (i+1)+". **"+"null"+"** - "+clarify(sorter.get(i).score)+"\n";
				continue;
			}
			
			String u = jda.getUserById(sorter.get(i).name).getName();
			s += (i+1)+". **"+u+"** - "+clarify(sorter.get(i).score)+"\n";
		}

		return s;
	}

	String clarify(int score) {
		if (score > 0) return score+"";
		return "("+score+")";
	}

	class Sort implements Comparable<Sort> {
		String name;
		int score;
		public Sort(String a, int b) {
			name = a;
			score = b;
		}
		@Override
		public int compareTo(Sort o) {
			return o.score-this.score;
		}

	}



}
