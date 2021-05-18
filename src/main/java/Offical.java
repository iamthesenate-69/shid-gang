import java.awt.Color;
import java.awt.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Offical extends ListenerAdapter
{
	static LinkedList<User> user = new LinkedList<User>();
	static LinkedList<User> dead = new LinkedList<User>();	//bankrupt
	static int jail[];	// if > 0, Jail
	static int pos[];	// 0 <= pos < 40
	static int cur[];	// if < 0, lose

	static int[][] pprty;	//1-6 owned 7-12 owned + mortgaged

	static boolean stop; //for stopping skipping turn when given option to buy

	static int userinput; //user's Input
	static int index;	//house index
	static int rollagain;	//counts the number of times the user rolls doubles
	public static User u;

	static boolean canStart = true;
	static boolean auction = false;
	static int auctiontime = 15;
	static int turn;
	static User highestbidder;
	static int highestbid = 0;

	// u1					0 = not owned
	// u2					1 = owned
	// ...					2 = 1 house
	// 						i = i-1 houses
	//						6 = hotel
	static String grid[] = 
		{"s","11","C1","12","t","r1","21","?1","22","23",
				"01","31","u","32","33","r2","41","C2","42","43",
				"02","51","?2","52","53","r3","61","62","U","63",
				"j","71","72","C3","73","r4","?3","81","T","82"};

	//dihydrogen monopoly
	static String g =  "| 02 | 51 | ?2 | 52 | 53 | r3 | 61 | 62 | U  | 63 | j  |";
	static String g1 = "| 43 |                                -----       | 71 |";
	static String g2 = "| 42 |         ?????               / ly /         | 72 |";	
	static String g3 = "| C2 |       ?{[]?             / opo /            | C3 |";
	static String g4 = "| 41 |    ?????             / Mon /               | 73 |";
	static String g5 = "| r2 |                   / en /                   | r4 |";
	static String g6 = "| 33 |                / rog /            ?????    | ?3 |";
	static String g7 = "| 32 |             / hyd /            ?[?]?       | 81 |";
	static String g8 = "| u  |          / Di /              ?????         | T  |";
	static String g9 = "| 31 |        -----                               | 82 |";
	static String g0 = "| 01 | 23 | 22 | ?1 | 21 | r1 | t  | 12 | C1 | 11 | s  |";

	// s = start
	// c = community chest
	// ? = chance
	// t = income tax
	// T = luxury tax
	// r1,r2,r3,r4 = railroad
	// u = Electricity
	// U = Power
	// j = go to jail
	// 0 = empty
	// ## = squares


	public static void main(String[] args) throws LoginException
	{
		args = new String[1];
		args[0] = "NzMwMTA3MTc5MzUyMTk1MTM0.XwSrTA.GHMeqd5rbqWONZW9nHEDCr81xrc";
		if (args.length < 1) {
			System.out.println("You have to provide a token as first argument!");
			System.exit(1);
		}

		BasicConfigurator.configure();

		JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
		.addEventListeners(new Offical())
		.setActivity(Activity.playing("Eroge"))

		.build();


	}

	boolean disassemble;

	public void onMessageReceived(MessageReceivedEvent event) {


		//runs when message is sent
		net.dv8tion.jda.api.entities.Message msg = event.getMessage();

		if (msg.getContentRaw().startsWith("dm")) {

			java.util.List<Member> mentioned;

			mentioned = msg.getMentionedMembers();

			int b = msg.getContentRaw().indexOf('"')+1;
			int e = 0;
			for (int i = msg.getContentRaw().length()-1; i >= 0 ; i--) {
				if (msg.getContentRaw().charAt(i) == '"') {
					e = i;
					break;
				}
			}

			String message = msg.getContentRaw().substring(b, e);

			for (Member member: mentioned) 
				if (member != event.getGuild().getSelfMember() || member != event.getGuild().getMemberById("372123318167273502"))
					sendMessage(member.getUser(), message);


		}


		if (msg.getContentRaw().contentEquals("sea bee tea") && msg.getAuthor().getId().equals("372123318167273502")) {

			event.getChannel().sendMessage("Cock and ball torture from Wikipedia, the free encyclopedia at en.wikipedia.org. Cock and ball torture, CBT, is a sexual activity involving torture of the male genitals. This may involve directly painful activities such as wax play, genital spanking, squeezing, ball busting, genital flogging, urethera play, tickle torture, erotic electro stimulation, or even kicking. The recipient of such activities may receive direct physical pleasure via masochism through knowledge that the play is pleasing to a sadistic dominant. Image: electro stimulation applied on a penis. Contents Section 1: In pornography Section 2: Ball stretcher Section 3: Parachute Section 4: Humbler Section 5: Testicle Cuff Section 1: In pornography. In addition to it's occational role in BDSM pornography, temakeria, literally ball kicking, is a separate genre in Japan. One notable actress in temakeria is Erika Negai, who typically uses her martial art skills to knee or kick men in the testicles. Section 2: Ball stretcher. A ball stretcher is a sex toy that is fastened around a man in order to elongate the scrotum, and provide a feeling of weight, pulling the testicles away from the body. While leather stretchers are most common, other models are made of steel rings that are fastened with screws causing additional, mildly uncomfortable weight to the wearer. The length of the stretchers may very from 1-4 inches, and the steel models can weigh up to 5 pounds. Section 3: Parachute.").queue();

		}

		if (msg.getContentRaw().contentEquals("avengers assemble") && msg.getAuthor().getId().equals("372123318167273502")) {
			int period = 5000;
			disassemble = false;
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask()
			{
				public void run()
				{
					if (disassemble) timer.cancel();
					java.util.List<TextChannel> c = event.getGuild().getTextChannels();
					for (TextChannel channel: c)
						channel.sendTyping().queue();
				}
			}, 0, period);

		}

		if (msg.getContentRaw().contentEquals("avengers disassemble") && msg.getAuthor().getId().equals("372123318167273502")) {
			disassemble = true;
		}

		if (msg.getContentRaw().startsWith("create role ") && msg.getAuthor().getId().equals("372123318167273502")) {

			String S = msg.getContentRaw().substring(12)+" ";
			event.getChannel().sendMessage("creating role " + S).queue();
			int prev = 0;
			int counter = 0;
			for (int i = 0; i < S.length(); i++) {
				if (S.charAt(i) == ' ') {
					Color c;
					if (counter % 7 == 0) {
						c = Color.RED;
					}else if (counter % 7 == 1) {
						c = Color.ORANGE;
					}else if (counter % 7 == 2) {
						c = Color.YELLOW;
					}else if (counter % 7 == 3) {
						c = Color.GREEN;
					}else if (counter % 7 == 4) {
						c = Color.BLUE;
					}else if (counter % 7 == 5) {
						c = Color.decode("#4B0082");
					}else 
						c = Color.decode("#EE82EE");
					event.getGuild().createRole().setName(S.substring(prev, i)).setColor(c).complete();

					prev = i;
					counter++;
				}


			}
		}

		if (msg.getContentRaw().startsWith("assign role ") && msg.getAuthor().getId().equals("372123318167273502")) {
			java.util.List<Member> m = msg.getMentionedMembers();
			for (int j = 0; j < m.size(); j++) {


				String S = msg.getContentRaw().substring(12)+" ";
				event.getChannel().sendMessage("adding roles...").queue();
				int prev = 0;
				for (int i = 0; i < S.length(); i++) {
					if (S.charAt(i) == ' ') {
						System.out.println(S.substring(prev, i));
						java.util.List<Role> r = event.getGuild().getRolesByName(S.substring(prev, i).strip(), true);
						System.out.println(r);

						for (int t = 0; t < r.size(); t++) {
							event.getGuild().addRoleToMember(m.get(j), r.get(t)).queue();
						}
						prev = i;
					}


				}
				event.getChannel().sendMessage("finished!").queue();
			}
		}
		if (msg.getContentRaw().startsWith("remove role ") && msg.getAuthor().getId().equals("372123318167273502")) {
			java.util.List<Member> m = msg.getMentionedMembers();
			for (int j = 0; j < m.size(); j++) {


				String S = msg.getContentRaw().substring(12)+" ";
				event.getChannel().sendMessage("removing roles...").queue();
				int prev = 0;

				for (int i = 0; i < S.length(); i++) {

					if (S.charAt(i) == ' ') {
						System.out.println(S.substring(prev, i));
						java.util.List<Role> r = event.getGuild().getRolesByName(S.substring(prev, i).strip(), true);
						System.out.println(r);

						for (int t = 0; t < r.size(); t++) {
							event.getGuild().removeRoleFromMember(m.get(j), r.get(t)).queue();
						}
						prev = i;
					}


				}
				event.getChannel().sendMessage("finished!").queue();
			}
		}






		if (msg.getContentRaw().replace(" ", "").startsWith("tsuginiomaewa\"") && msg.getContentRaw().replace(" ", "").endsWith("\"toiu")) {
			MessageChannel channel = event.getChannel();
			int bindex = msg.getContentRaw().indexOf("\"")+1;
			int eindex = 0;
			for (int i = msg.getContentRaw().length()-1; i >= 0; i--) {
				if (msg.getContentRaw().charAt(i) == '"') {
					eindex = i;
					break;
				}
			}
			channel.sendMessage(msg.getContentRaw().substring(bindex, eindex)).queue();;
		}

		if (msg.getContentRaw().startsWith(("~ping"))) {
			MessageChannel channel = event.getChannel();
			long time = System.currentTimeMillis();
			channel.sendMessage("Pong!").queue(response -> {
				response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
			});
		}

		if (msg.getContentRaw().toLowerCase().contains("donut") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			Random rand = new Random(); 
			int n = rand.nextInt(4);
			if (n == 0) channel.sendMessage("https://tenor.com/view/jjba-josuke-donut-part4-jojos-bizarre-adventure-gif-16941574").queue();
			else if (n == 1) channel.sendMessage("https://tenor.com/view/jojo-luiz-abacchio-king-crimson-jjba-gif-16383531").queue();
			else if (n == 2) channel.sendMessage("https://tenor.com/view/kakyoin-noriaki-noriaki-kakyoin-death-kakyoin-death-gif-17648047").queue();
			else channel.sendMessage("https://tenor.com/view/doppio-diavolo-jojo-bizarre-adventure-jojo-part5-king-crimson-gif-14027434").queue();
		}

		if (msg.getContentRaw().toLowerCase().contains("arrivederci") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			Random rand = new Random(); 
			int n = rand.nextInt(2);
			if (n == 0) channel.sendMessage("https://tenor.com/view/jojo-arrivederci-bruno-bucciarati-bruno-part5-gif-13710195").queue();
			else channel.sendMessage("https://tenor.com/view/jojo-part5-giorno-bruno-narancia-gif-13874017").queue();
		}

		if (msg.getContentRaw().toLowerCase().contains("objection") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://tenor.com/view/ace-attorney-ace-attorney-phoenix-wright-phoenix-gif-5454234").queue();
		}

		if (msg.getContentRaw().replace(" ", "").startsWith("koregamadeinheavenda") && msg.getAuthor().isBot() == false && msg.getAuthor().getId().equals("372123318167273502")) {

			for (Webhook h: msg.getGuild().retrieveWebhooks().complete()) {
				h.delete().queue();
			}

			//			for (Member m : event.getGuild().getMembers()) {
			//				System.out.println(m);
			//				event.getChannel().sendMessage(event.getGuild().getMembers()+"").queue();
			////				if (m.getUser() == event.getJDA().getSelfUser()) {
			////					event.getChannel().sendMessage("gay gay man 420").queue();
			////					continue;
			////				}
			//			    msg.getGuild().ban(m,0);
			//				event.getChannel().sendMessage("SHINE " + m.getEffectiveName()).queue();
			//			}
			//			for (GuildChannel c : event.getGuild().getChannels()) {
			//			    msg.getGuild().getStoreChannelById(c.getId());
			//				event.getChannel().sendMessage("SHINE " + c.getName()).queue();
			//			}

		}

		if ((msg.getContentRaw().toLowerCase().contains("002") || msg.getContentRaw().toLowerCase().contains("zero two")) && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://tenor.com/view/zero-two-002-cute-anime-wink-gif-11994868").queue();
		}

		if (msg.getContentRaw().toLowerCase().contains("hayato") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://tenor.com/view/koichi-kira-yoshikage-anime-jojo-gif-10238237").queue();
		}

		if (msg.getContentRaw().toLowerCase().contains("go! launch the attack helicopters against") && msg.getAuthor().isBot() == false) {
			sendMessage(msg.getAuthor(), "https://tenor.com/view/thunder-cross-split-attack-you-fell-for-it-fool-power-gif-16746128");
			event.getChannel().sendMessage("https://tenor.com/view/thunder-cross-split-attack-you-fell-for-it-fool-power-gif-16746128").queue();
			event.getGuild().kick(msg.getMember()).queue();
		}

		if (msg.getContentRaw().toLowerCase().contains("megumin") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			Random rand = new Random(); 
			int n = rand.nextInt(6);

			if (n == 0) channel.sendMessage("https://tenor.com/view/konosuba-megumin-explosion-gif-7479916").queue();
			else if (n == 1) channel.sendMessage("https://tenor.com/view/fgo-megumin-gif-13248050").queue();
			else if (n == 2) channel.sendMessage("https://tenor.com/view/konosuba-megumin-gif-6228268").queue();
			else if (n == 3) channel.sendMessage("https://tenor.com/view/megumin-thumbs-up-okay-approve-like-gif-11492110").queue();
			else if (n == 4) channel.sendMessage("https://tenor.com/view/anime-shocked-konosuba-megumin-gif-14210797").queue();
			else channel.sendMessage("https://tenor.com/view/megumin-gif-9358976").queue();
		}

		if (msg.getContentRaw().toLowerCase().contains("pog") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://tenor.com/view/pogchamp-pog-pogey-poggers-twitch-gif-14340727").queue();
		}

		else if (msg.getContentRaw().toLowerCase().contains("dmoj") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://dmoj.ca/problems/").queue();
		}

		else if (msg.getContentRaw().toLowerCase().contains("wcipeg") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://wcipeg.com/problems").queue();
		}

		else if (msg.getContentRaw().toLowerCase().contains("pat") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://tenor.com/view/miko-iino-kaguya-sama-love-is-war-anime-tapping-table-gif-17362217").queue();
		}

		else if (msg.getContentRaw().toLowerCase().contains("usaco") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("http://www.usaco.org/index.php?page=contests").queue();
		}

		else if (msg.getContentRaw().toLowerCase().contains("wynncraft") && msg.getAuthor().isBot() == false) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("https://map.wynncraft.com/").queue();
		}

		else if (msg.getContentRaw().toLowerCase().contains("@everyone") && msg.getAuthor().isBot() == false) {
			sendMessage(msg.getAuthor(), "no shut ur gee man");
			event.getChannel().sendMessage("https://tenor.com/view/chika-chika-fujiwara-kaguya-ishigami-newspaper-gif-13606647").queue();
			event.getGuild().kick(msg.getMember()).queue();

		}

		else if (msg.getContentRaw().toLowerCase().contains("@here") && msg.getAuthor().isBot() == false) {
			Invite i = event.getTextChannel().createInvite().setMaxUses(1).complete();
			sendMessage(msg.getAuthor(), "bruh ur gee man "+ i.getUrl());
			event.getChannel().sendMessage("https://tenor.com/view/jojo-kick-jjba-gif-13894493").queue();
			event.getGuild().kick(msg.getMember()).queue();

		}

		else if (msg.getContentRaw().contentEquals("yamete kudasai") && msg.getAuthor().getId().equals("372123318167273502")) {
			event.getChannel().sendMessage("onii-chan").queue();
			System.exit(1);
		}

		else if (msg.getContentRaw().startsWith("~kick ")) {
			java.util.List<Member> S = msg.getMentionedMembers();
			for (int i = 0; i < S.size(); i++) event.getGuild().kick(S.get(i)).queue();
			event.getChannel().sendMessage("BANNED " + S).queue();
		}

		else if (msg.getContentRaw().startsWith("~ban ")) {
			java.util.List<Member> S = msg.getMentionedMembers();
			for (int i = 0; i < S.size(); i++) event.getGuild().ban(S.get(i), 0, "banned by " + msg.getAuthor().getName()).queue();
			event.getChannel().sendMessage("SHINE KAKYOIN " + S).queue();
		}

		else if ((" "+msg.getContentRaw()+" ").toLowerCase().contains(" ez ")) {
			MessageChannel channel = event.getChannel();
			event.getMessage().delete().queue();
			int r = (int) ((Math.random() * 32) + 1);
			censor(channel, r);

		}else if (msg.getContentRaw().startsWith(("~game")) && msg.getContentRaw().contains(("help"))) {
			MessageChannel channel = event.getChannel();
			msg.addReaction("🗿").queue();

			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setColor(Color.red);
			embedBuilder.setTitle("**Dihydrogen Monopoly:**");
			embedBuilder.addField("Commands:","\n`~join` - joins the game\n" +
					"`~leave` - leaves the game\n" +
					"`~start` - starts the game - only one game of Monopoly can be played per server channel\n" +
					"`~game roll` - rolls two RNG dice - used for ending your turn\n" +
					"`~game house <property code>` - builds a house on specified property\n" +
					"`~game mortgage <property code>` - mortgages specified property\n" +
					"`~game lift <mortgaged property code>` - lifts mortgage on specified property\n" +
					"`~game info` - shows Monopoly board with every player's Position, Currency, Owned Properties, Houses, and **Jail** Status\n" +
					"`~game bid <value>` - bids an amount when an auction occurs\n"+
					"`~game y` - accepts the action\n"+
					"`~game n` - declines the action\n",false);
			embedBuilder.setFooter("For additional help, contact iamthesenate_69#2727",event.getAuthor().getAvatarUrl());
			sendDM(msg.getAuthor(), embedBuilder.build());
			embedBuilder.clear();
		}








		//dihydrogen monopoly
		//----------------------------------------------------------------------------------------------------------------------------------------------------------------

		else if (msg.getContentRaw().startsWith("~join") && user.contains(msg.getAuthor()) == false && canStart) {
			MessageChannel channel = event.getChannel();
			user.add(msg.getAuthor());
			channel.sendMessage(msg.getAuthor().getName() + " joined the lobby.").queue();
		}
		else if (msg.getContentRaw().startsWith("~join") && user.contains(msg.getAuthor()) && canStart) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("You already joined the lobby.").queue();
		}
		else if (msg.getContentRaw().startsWith("~leave") && user.contains(msg.getAuthor()) && canStart) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage(msg.getAuthor().getName() + " left the lobby.").queue();
			user.remove(msg.getAuthor());
		}
		else if (msg.getContentRaw().startsWith("~leave") && user.contains(msg.getAuthor()) == false && canStart) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("You are not in a lobby.").queue();
		}
		else if (msg.getContentRaw().startsWith("~start") && user.contains(msg.getAuthor()) == true && canStart) {
			user.add(event.getJDA().getSelfUser());
			MessageChannel channel = event.getChannel();
			canStart = false;
			turn = 0;
			u = user.get(turn);
			pos = new int[user.size()];
			cur = new int[user.size()];
			pprty = new int[user.size()][40];
			jail = new int[user.size()];
			Arrays.fill(cur, 1500);
			channel.sendMessage(msg.getAuthor().getName() +" started the game.\n-Dihydrogen-Monopoly-v0.1-\nIt's " + u.getName() + "'s turn!").queue();
		}
		else if (msg.getContentRaw().startsWith("~game") && user.contains(msg.getAuthor()) == true && canStart == false) {
			monopoly(msg, event);


		}
	}

	//functions
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void monopoly(net.dv8tion.jda.api.entities.Message msg, MessageReceivedEvent event) {
		if (u.getId().equals(event.getJDA().getSelfUser().getId())) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("uwu").queue();
		}



		if (msg.getContentRaw().contains("roll") && msg.getAuthor().getId().equals(u.getId()) && userinput == 0) {

			MessageChannel channel = event.getChannel();
			int n1 = (int) ((Math.random()*6) + 1);
			int n2 = (int) ((Math.random()*6) + 1);
			int n = n1 + n2;


			if (msg.getContentRaw().contains("verylegitroll")) {
				n1 = 6;
				n2 = 6;
				n = 12;
			}


			if (n1 == n2 && n1 != 0) {
				rollagain++;
			}else if (n != n2) rollagain = 0;


			if (jail[turn] > 0 && n1 != n2 && n1 != 0) {
				channel.sendMessage("You are in **Jail** for `" + jail[turn] + "` more turn(s).").queue();
				jail[turn]--;
				next(channel);
			}
			else {

				if (jail[turn]-1 == 0 || (n1 == n2 && jail[turn] > 0)) {
					jail[turn] = 0;
					channel.sendMessage("You are no longer in **Jail**").queue();
				}

				pos[turn] = pos[turn] + n;

				if (pos[turn] >= 40) {
					pos[turn] -= 40;
					cur[turn] += 200;
					channel.sendMessage("You went passed Collect ¥200. You now have ¥" + cur[turn]+".").queue();
				}


				String S = msg.getAuthor().getName() + " rolled a " + n + ". You are on Square " + (pos[turn]+1) + " on `" + grid[pos[turn]] + "`\n";

				if (grid[pos[turn]].charAt(0) == 'C') {
					channel.sendMessage(S+"You landed on Community Chest.").queue();
					cc(channel);
					if (stop == false) next(channel);
					else userinput = 1;

				}else if (grid[pos[turn]].charAt(0) == '?') {
					channel.sendMessage(S+"You landed on Chance.").queue();
					chance(channel, n);
					if (stop == false) next(channel);
					else userinput = 1;
				}else if (grid[pos[turn]].charAt(0) == 't') {
					cur[turn] -= 200;
					channel.sendMessage(S+"You landed on Income Tax. You paid ¥200. You now have ¥" + cur[turn]+".").queue();
					next(channel);
				}else if (grid[pos[turn]].charAt(0) == 'T') {
					cur[turn] -= 100;
					channel.sendMessage(S+"You landed on Luxury Tax. You paid ¥100. You now have ¥" + cur[turn]+".").queue();
					next(channel);
				}else if (grid[pos[turn]].charAt(0) == 'j') {
					channel.sendMessage(S+"You landed on **GO TO Jail**. You are now in **Jail**.").queue();
					pos[turn] = 10;
					if (jail[turn] < 0) {
						jail[turn]++;
						channel.sendMessage(S+"You've used your Jail Free card. You are no longer in **Jail**.").queue();
					}
					else jail[turn] = 3;
					next(channel);
				}else if (grid[pos[turn]].charAt(0) == '0') {
					channel.sendMessage(S+"You landed on a Blank Square.").queue();
					next(channel);
				}else if (grid[pos[turn]].charAt(0) == 's') {
					cur[turn] += 200;
					channel.sendMessage(S+"You went passed Collect ¥200. You now have ¥" + cur[turn]+".").queue();
					next(channel);
				}else {
					if (grid[pos[turn]].charAt(0) == 'u') {
						channel.sendMessage(S+"You landed on Electric Company (`u`).").queue();
					}else if (grid[pos[turn]].charAt(0) == 'U') {
						channel.sendMessage(S+"You landed on Water Works (`U`).").queue();
					}else if (grid[pos[turn]].charAt(0) == 'r') {
						channel.sendMessage(S+"You landed on Rail Road `" + grid[pos[turn]]+"`.").queue();
					}else {
						channel.sendMessage(S+"You landed on Property `" + grid[pos[turn]]+"`.").queue();
					}

					int i = getOwner(pos[turn]);
					if (i == -1) {
						int cost = getCost(pos[turn]);
						channel.sendMessage("Do you wish to buy for ¥"+cost+"?").queue();
						userinput = 1; //allows user input
					}else if (pprty[i][pos[turn]] > 6) {
						channel.sendMessage("Property is mortgaged").queue();
						next(channel);
					}else if (i != turn){;
					int price = getOwnedPrice(i, pos[turn], n);
					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Property is owned, you pay " + user.get(i).getName() + " ¥" + price + "\n"+
							"You now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
					next(channel);

					}else {
						channel.sendMessage("You own this Property").queue();
						next(channel);
					}

				}

			}

		}
		else if (msg.getContentRaw().contains("end")) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage(msg.getAuthor().getName() + " ended the game").queue();
			canStart = true;
			user.clear();
		}
		else if (msg.getContentRaw().contains("info")) {
			MessageChannel channel = event.getChannel();
			EmbedBuilder eb = new EmbedBuilder();

			eb.setColor(Color.red);
			eb.setTitle("dihydrogen monopoly");
			eb.setDescription("```"+g+"\n"+g1+"\n"+g2+"\n"+g3+"\n"+g4+"\n"+g5+"\n"+g6+"\n"+g7+"\n"+g8+"\n"+g9+"\n"+g0+"```");
			channel.sendMessage(eb.build()).queue();
			for (int i = 0; i < user.size(); i++) {
				String S = "\nProperties:\n";
				String d = "";
				if (dead.contains(user.get(i))) d = "`[bankrupted]`";
				for (int j = 0; j < 40; j++) {
					if (pprty[i][j] != 0 && pprty[i][j] < 7) {
						S += "   Property `" +grid[j] + "` with " + (pprty[i][j]-1) + " house(s)\n";
					}else if (pprty[i][j] > 6) {
						S += "   Property `" +grid[j] + "` mortgaged with " + (pprty[i][j]-8) + " house(s)\n ";
					}
				}

				channel.sendMessage(d+user.get(i)+"\n   Square "+(pos[i]+1)+ " on `" + grid[pos[i]]+"` ¥" + cur[i] +S + "**Jail** Status: " + jail[i]).queue();
			}
		}


		else if (msg.getContentRaw().contains("mortgage") && user.contains(msg.getAuthor()) && (dead.size() == 0 || !dead.contains(msg.getAuthor())) && auction) {
			MessageChannel channel = event.getChannel();
			String s = msg.getContentRaw().substring(msg.getContentRaw().indexOf("mortgage")+9);
			int Lindex = 0;
			int Lturn = user.indexOf(msg.getAuthor());
			for (int i = 0; i < grid.length; i++) {
				if (grid[i].equals(s)) {
					Lindex = i;
					break;
				}
			}if (Lindex == -1 || getOwner(Lindex) != Lturn || pprty[Lturn][Lindex] > 6) channel.sendMessage("Enter a valid owned property").queue();
			else {
				int cost = getCost(Lindex)/2;
				cur[Lturn] += cost;

				pprty[Lturn][Lindex] += 7;
				channel.sendMessage("You've mortgaged this property for ¥" +cost +". You now have ¥" + cur[Lturn]).queue();
			}

		}

		else if (msg.getContentRaw().contains("mortgage") && msg.getAuthor().getId().equals(u.getId())) {
			MessageChannel channel = event.getChannel();
			String s = msg.getContentRaw().substring(msg.getContentRaw().indexOf("mortgage")+9);


			for (int i = 0; i < grid.length; i++) {
				if (grid[i].equals(s)) {
					index = i;
					break;
				}
			}if (index == -1 || getOwner(index) != turn || pprty[turn][index] > 6) channel.sendMessage("Enter a valid owned property").queue();
			else {
				int cost = getCost(index)/2;
				channel.sendMessage("Do you wish to mortgage this property and gain ¥"+cost).queue();
				userinput = cost;


			}
		}



		else if (msg.getContentRaw().contains("lift") && msg.getAuthor().getId().equals(u.getId())) {
			MessageChannel channel = event.getChannel();
			String s = msg.getContentRaw().substring(msg.getContentRaw().indexOf("lift")+5);


			for (int i = 0; i < grid.length; i++) {
				if (grid[i].equals(s)) {
					index = i;
					break;
				}
			}if (index == -1 || getOwner(index) != turn || pprty[turn][index] < 7) channel.sendMessage("Enter a valid owned property").queue();
			else {
				int cost = (int) (getCost(index)/2 * 1.10);
				channel.sendMessage("Do you wish to lift this property for ¥"+cost).queue();
				userinput = -cost;
			}
		}

		else if (msg.getContentRaw().contains("cheatcode") && msg.getAuthor().getId().equals(u.getId())) {
			pprty[turn][1] = 1;

			pprty[turn][6] = 1;
			pprty[turn][8] = 1;
			pprty[turn][9] = 1;
		}

		else if (msg.getContentRaw().contains("house") && msg.getAuthor().getId().equals(u.getId())) {
			MessageChannel channel = event.getChannel();
			String s = msg.getContentRaw().substring(msg.getContentRaw().indexOf("house")+6);


			for (int i = 0; i < grid.length; i++) {
				if (grid[i].equals(s)) {
					index = i;
					break;
				}
			}
			//i = property index
			//s = property code

			if (index == -1 || getOwner(index) != turn || s.charAt(0) < '1' || s.charAt(0) > '8' || pprty[turn][index] > 5) channel.sendMessage("Enter a valid property inside an owned monopoly").queue();
			else {
				if (s.charAt(0) == '1' && getOwner(1) == turn && getOwner(2) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥50?").queue();
					userinput = 2;
				}else if (s.charAt(0) == '2' && getOwner(6) == turn && getOwner(8) == turn && getOwner(9) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥50?").queue();
					userinput = 2;
				}else if (s.charAt(0) == '3' && getOwner(11) == turn && getOwner(13) == turn && getOwner(14) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥100?").queue();
					userinput = 3;
				}else if (s.charAt(0) == '4' && getOwner(16) == turn && getOwner(18) == turn && getOwner(19) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥100?").queue();
					userinput = 3;
				}else if (s.charAt(0) == '5' && getOwner(21) == turn && getOwner(23) == turn && getOwner(24) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥150?").queue();
					userinput = 4;
				}else if (s.charAt(0) == '6' && getOwner(26) == turn && getOwner(27) == turn && getOwner(29) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥150?").queue();
					userinput = 4;
				}else if (s.charAt(0) == '7' && getOwner(31) == turn && getOwner(32) == turn && getOwner(34) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥200?").queue();
					userinput = 5;
				}else if (s.charAt(0) == '8' && getOwner(37) == turn && getOwner(39) == turn) {
					channel.sendMessage("Do you wish to buy a house for ¥200?").queue();
					userinput = 5;
				}else channel.sendMessage("Enter a valid property inside an owned monopoly").queue();

			}
		}
		else if (msg.getContentRaw().contains("y") && msg.getAuthor().getId().equals(u.getId()) && userinput > 1 && userinput < 6) {
			MessageChannel channel = event.getChannel();
			int cost = 0;
			if (userinput == 2) cost = 50;
			else if (userinput == 3) cost = 100;
			else if (userinput == 4) cost = 150;
			else cost = 200;
			cur[turn] -= cost;
			pprty[turn][index]++;
			channel.sendMessage("You've bought a House. You now have ¥" + cur[turn]).queue();
			userinput = 0;
		}

		else if (msg.getContentRaw().contains("y") && msg.getAuthor().getId().equals(u.getId()) && userinput == 1) {
			MessageChannel channel = event.getChannel();
			int cost = getCost(pos[turn]);
			cur[turn] -= cost;
			channel.sendMessage("You've bought `" + grid[pos[turn]] + "`. You now have ¥" + cur[turn]).queue();
			pprty[turn][pos[turn]] = 1;

			next(channel);
		}

		else if (msg.getContentRaw().contains("y") && msg.getAuthor().getId().equals(u.getId()) && userinput > 6) {
			MessageChannel channel = event.getChannel();
			int cost = userinput;
			cur[turn] += cost;
			channel.sendMessage("You've mortgaged`" + grid[index] + "`. You now have ¥" + cur[turn]).queue();
			pprty[turn][index] += 7;
			userinput = 0;
		}

		else if (msg.getContentRaw().contains("y") && msg.getAuthor().getId().equals(u.getId()) && userinput < 0) {
			MessageChannel channel = event.getChannel();
			int cost = userinput;
			cur[turn] += cost;
			channel.sendMessage("You've lifted`" + grid[index] + "`. You now have ¥" + cur[turn]).queue();
			pprty[turn][index] -= 7;
			userinput = 0;
		}

		else if (msg.getContentRaw().contains("n") && msg.getAuthor().getId().equals(u.getId()) && userinput == 1) {
			MessageChannel channel = event.getChannel();
			userinput = 0;
			ah(channel);
			stop=false;
		}

		else if (msg.getContentRaw().contains("n") && msg.getAuthor().getId().equals(u.getId()) && (userinput != 0 || userinput != 1) ) {
			userinput = 0;
		}

		else if (msg.getContentRaw().contains("show")) {
			for (int i = 0; i < user.size(); i++) {
				for (int j = 0; j < 40; j++) {
					System.out.print(pprty[i][j]+" ");
				}System.out.println();
			}

			//			static int jail[];	// if > 0, Jail
			//			static int pos[];	// 0 <= pos < 40
			//			static int cur[];	// if < 0, lose
			//			
			//			static int[][] pprty;	//1-6 owned 7-12 owned + mortgaged
			//			
			//			static boolean stop; //for stopping skipping turn when given option to buy
			//			
			//			static int userinput; //user's Input
			//			static int index;	//house index
			//			static int rollagain;	//counts the number of times the user rolls doubles
			//			public static User u;
			//			
			//			static boolean canStart = true;
			//			static int turn;
			for (int i = 0; i < user.size(); i++) {
				System.out.println("j " +jail[i]);
				System.out.println("p " +pos[i]);
				System.out.println("c " +cur[i]);
			}System.out.println("u " + userinput);
			System.out.println("i " + index);
			System.out.println("d " + rollagain);
			System.out.println(stop);

		}else if (msg.getContentRaw().contains("die") && msg.getAuthor().getId().equals(u.getId())) {
			cur[turn] = -1;
		}else if (msg.getContentRaw().contains("incest") && msg.getAuthor().getId().equals(u.getId())) {
			jail[turn] = 3;
			pos[turn] = 10;
		}


		else if (msg.getContentRaw().contains("bid") && user.contains(msg.getAuthor()) && (dead.size() == 0 || !dead.contains(msg.getAuthor())) && auction) {
			int bid = Integer.parseInt(msg.getContentRaw().substring(msg.getContentRaw().indexOf("bid")+4));
			if (bid > highestbid) {
				highestbid = bid;
				highestbidder = msg.getAuthor();
				auctiontime = 10;
			}
		}


	}
	public void ah(MessageChannel channel) {
		highestbidder = user.get(turn);
		channel.sendMessage("Auctioning Property `" + grid[pos[turn]] + "`.").queue();
		auction = true;
		auctiontime = 20;
		int delay = 5000;
		int period = 4000;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				auctiontime--;
				channel.sendMessage("Time Left: "+auctiontime+"\nHighest bid: ¥"+highestbid+" from "+highestbidder.getName()).queue();
				if (auctiontime <= 1) {
					channel.sendMessage("Sold Property `" + grid[pos[turn]] + "` for ¥" + highestbid + " from " + highestbidder.getName()).queue();
					int index = user.indexOf(highestbidder);
					cur[index] -= highestbid;
					pprty[index][pos[turn]] = 1;
					next(channel);
					auction = false;
					highestbid = 0;
					timer.cancel();
				}
			}
		}, delay, period);


	}


	public void sendDM(User user, MessageEmbed messageEmbed) {
		user.openPrivateChannel()
		.flatMap(channel -> channel.sendMessage(messageEmbed))
		.queue();
	}

	public int getCost(int i) {	//returns the cost of buying property
		switch(i) {
		case 1:
			return 60;
		case 3:
			return 60;
		case 5:
			return 200;
		case 6:
			return 100;
		case 8:
			return 100;
		case 9:
			return 120;
		case 11:
			return 40;
		case 12:
			return 150;
		case 13:
			return 140;
		case 14:
			return 160;
		case 15:
			return 200;
		case 16:
			return 180;
		case 18:
			return 180;
		case 19:
			return 200;
		case 21:
			return 220;
		case 23:
			return 220;
		case 24:
			return 240;
		case 25:
			return 200;
		case 26:
			return 260;
		case 27:
			return 260;
		case 28:
			return 150;
		case 29:
			return 280;
		case 31:
			return 300;
		case 32:
			return 300;
		case 34:
			return 320;
		case 35:
			return 200;
		case 37:
			return 350;
		case 39:
			return 400;
		}
		return -1;
	}


	public void next(MessageChannel channel) {

		userinput = 0;
		index = -1;
		stop = false;

		if (cur[turn] < 0 && !dead.contains(u)) {
			channel.sendMessage(u.getName() + " is now bankrupt").queue();
			dead.add(u);
		}

		if (rollagain > 0 && jail[turn] <= 0) {
			if (rollagain != 3) channel.sendMessage("Doubles! Roll again.").queue();
			else {
				rollagain = 0;
				pos[turn] = 10;
				if (jail[turn] < 0) {
					channel.sendMessage("You were caught speeding, but you had a Jail Free card").queue();
					jail[turn]++;
				}else {
					jail[turn] = 3;
					channel.sendMessage("You were caught speeding. Go to **Jail**").queue();
				}
				next(channel);
			}
			return;
		}




		do {
			if (turn+1 >= user.size()) {
				turn = 0;
			}
			else turn++;
			u = user.get(turn);
		} while (dead.contains(u));




		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		channel.sendMessage("------------------------------------\nTurn #" + (turn+1) + "\nIt's " + u.getName() + "'s turn!").queue();


		if (user.size() == dead.size()+1) {
			channel.sendMessage(u.getName() + " wins!").queue();
			canStart = true;
			user.clear();
		}


	}





	public void cc(MessageChannel channel) {
		int n1 = (int) (Math.random()*17);
		switch(n1) {
		case 0:
			cur[turn] += 200;
			channel.sendMessage("Advance to Go. (Collect ¥200). You now have ¥" + cur[turn]+".").queue();
			pos[turn] = 0;
			break;
		case 1:
			cur[turn] += 200;
			channel.sendMessage("Bank error in your favor. Collect ¥200. You now have ¥" + cur[turn]+".").queue();
			break;
		case 2:
			cur[turn] -= 50;
			channel.sendMessage("Your Crunchyroll Subscription expired. Pay ¥50. You now have ¥" + cur[turn]+".").queue();
			break;
		case 3:
			cur[turn] += 50;
			channel.sendMessage("From sale of anime figures you get ¥50. You now have ¥" + cur[turn]+".").queue();
			break;
		case 4:
			channel.sendMessage("Get out of Jail Free.").queue();
			if (jail[turn] > 0) {
				channel.sendMessage("You've used your Jail Free card. You are no longer in **Jail**.").queue();
			}else jail[turn]--;
			break;
		case 5:
			channel.sendMessage("You watched Eromanga-sensei. Go to **Jail**. Go directly to **Jail**. Do not pass Go, Do not collect ¥200").queue();
			pos[turn] = 10;
			if (jail[turn] < 0) {
				jail[turn]++;
				channel.sendMessage("You've used your Jail Free card. You are no longer in **Jail**.").queue();
			}
			else jail[turn] = 3;

			break;
		case 6:
			for (int i = 0; i < user.size(); i++) {
				cur[i] -= 50;
			}
			cur[turn] += 50*(user.size()) - 50*dead.size();;
			channel.sendMessage("New Kono Suba Movie. Collect ¥50 from every player for movie seats. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 7:
			cur[turn] += 100;
			channel.sendMessage("OnlyFans Account Money. Received ¥100. You now have ¥" + cur[turn]+".").queue();
			break;
		case 8:
			cur[turn] += 20;
			channel.sendMessage("Income tax refund. Collect ¥20. You now have ¥" + cur[turn]+".").queue();
			break;
		case 9:
			for (int i = 0; i < user.size(); i++) {
				cur[i] -= 10;
			}
			cur[turn] += 10*(user.size()) - 10*dead.size();;
			channel.sendMessage("It is your Waifu's Anniversary. Collect ¥10 from every player. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 10:
			cur[turn] += 100;
			channel.sendMessage("Waifu insurance matures. Collect ¥100. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 11:
			cur[turn] -= 50;
			channel.sendMessage("Bought Megumin Body Pillow. Pay ¥50. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 12:
			cur[turn] -= 50;
			channel.sendMessage("Catgirl Insurance Fees. Pay ¥50. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 13:
			cur[turn] += 25;
			channel.sendMessage("Receive ¥25 consultancy fee. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 14:
			int h = 0;
			int H = 0;
			for (int i = 0; i < 40; i++) {
				if (pprty[turn][i] == 6 || pprty[turn][i] == 12) H++;
				else if (pprty[turn][i] != 0 && pprty[turn][i] != 1 && pprty[turn][i] != 7) {
					if (pprty[turn][i] - 7 > 0) h += pprty[turn][i] - 7;
					else h += pprty[turn][i];
				}
			}
			cur[turn] -= (h*40 + H*115);

			channel.sendMessage("You decorated your properties with anime posters: For each house pay ¥40, For each hotel ¥115. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 15:
			cur[turn] += 10;
			channel.sendMessage("You have won second prize in having good anime taste. Collect ¥10. You now have ¥" + cur[turn] + ".").queue();
			break;
		default:
			cur[turn] += 100;
			channel.sendMessage("You inherit ¥100. You now have ¥" + cur[turn] + ".").queue();
			break;
		}

	}


	public void chance(MessageChannel channel, int n) {

		int n1 = (int) (Math.random()*17);
		switch(n1) {
		case 0:
			cur[turn] += 200;
			channel.sendMessage("Advance to GO (`s`). (Collect ¥200). You now have ¥" + cur[turn]+".").queue();
			pos[turn] = 0;
			break;
		case 1:
			pos[turn] = 39;
			if (pos[turn] < 39) {
				cur[turn] += 200;
				channel.sendMessage("Advance to Boardwalk (`82`) You passed Go, collect ¥200. You now have ¥" + cur[turn]+".").queue();
			}else if (getOwner(pos[turn]) == -1) {	//not owned
				channel.sendMessage("Advance token to Boardwalk (`82`).").queue();
			}else if (pprty[getOwner(39)][39] > 6) {	//owned but mortgaged
				channel.sendMessage("Advance token to Boardwalk (`82`). Property is mortgaged").queue();
			}else if (getOwner(pos[turn]) == turn) {	//owned but owned by you
				channel.sendMessage("Advance token to Boardwalk (`82`). You owned this property").queue();
			}else {									//pay
				int i = getOwner(pos[turn]);
				int price = getOwnedPrice(i, pos[turn], n);
				cur[turn] -= price;
				cur[i] += price;
				channel.sendMessage("Advance token to Boardwalk (`82`). Property is owned, you pay " + user.get(i).getName() + " ¥" + 10*n + 
						".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
			}
			break;
		case 2:
			pos[turn] = 11;
			if (pos[turn] < 11) {
				cur[turn] += 200;
				channel.sendMessage("Advance to St. Charles Place (`31`) You passed Go, collect ¥200. You now have ¥" + cur[turn]+".").queue();
			}else if (getOwner(pos[turn]) == -1) {	//not owned
				channel.sendMessage("Advance token to St. Charles Place (`31`).").queue();
			}else if (pprty[getOwner(11)][11] > 6) {	//owned but mortgaged
				channel.sendMessage("Advance token to St. Charles Place (`31`). Property is mortgaged").queue();
			}else if (getOwner(pos[turn]) == turn) {	//owned but owned by you
				channel.sendMessage("Advance token to St. Charles Place (`31`). You owned this property").queue();
			}else {									//pay
				int i = getOwner(pos[turn]);
				int price = getOwnedPrice(i, pos[turn], n);
				cur[turn] -= price;
				cur[i] += price;
				channel.sendMessage("Advance token to St. Charles Place (`31`). Property is owned, you pay " + user.get(i).getName() + " ¥" + 10*n + 
						".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
			}
			break;
		case 3:
			if (pos[turn] > 12 && pos[turn] <= 28) {
				pos[turn] = 28;
				int i = getOwner(28);
				if (i == -1) {
					channel.sendMessage("Advance token to Water Works (`U`). Property is unowned, you may buy it from the Bank for ¥" + getCost(28)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][28] > 6) {
					channel.sendMessage("Advance token to  Water Works (`U`). Utility is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to Water Works (`U`). You've Landed on your own Property").queue();
				}else {
					cur[turn] -= 10*n;
					cur[i] += 10*n;
					channel.sendMessage("Advance token to Water Works (`U`). Property is owned, you pay " + user.get(i).getName() + " ¥" + 10*n + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else {
				pos[turn] = 12;
				int i = getOwner(12);
				if (i == -1) {
					channel.sendMessage("Advance token to Electric Company (`u`). Property is unowned, you may buy it from the Bank ¥" + getCost(12)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][12] > 6) {
					channel.sendMessage("Advance token to Electric Company (`u`). Utility is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to Electric Company (`u`). You've Landed on your own Property").queue();
				}else {
					cur[turn] -= 10*n;
					cur[i] += 10*n;
					channel.sendMessage("Advance token to Electric Company (`u`). Property is owned, you pay " + user.get(i).getName() + " ¥" + 10*n + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}

			break;
		case 4:
			if (pos[turn] <= 5) {
				pos[turn] = 5;
				int i = getOwner(5);
				if (i == -1) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). Railway is unowned, you may buy it from the Bank ¥" + getCost(5)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][5] > 6) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). Railway is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). You own this Railway").queue();
				}else {
					int price = getOwnedPrice(i, 5, n)*2; //user, property, dice

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to Reading Railroad (`r1`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else if (pos[turn] <= 15) {
				pos[turn] = 15;
				int i = getOwner(15);
				if (i == -1) {
					channel.sendMessage("Advance token to Pennsylvania Railroad (`r2`). Railway is unowned, you may buy it from the Bank ¥" + getCost(15)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][15] > 6) {
					channel.sendMessage("Advance token to Pennsylvania Railroad (`r2`). Railway is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). You owned this Railway").queue();
				}else {
					int price = getOwnedPrice(i, 15, n)*2;

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to Pennsylvania Railroad (`r2`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else if (pos[turn] <= 25) {
				pos[turn] = 25;
				int i = getOwner(25);
				if (i == -1) {
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). Railway is unowned, you may buy it from the Bank ¥" + getCost(25)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][25] > 6) {
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). Railway is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). You owned this Railway").queue();
				}else {
					int price = getOwnedPrice(i, 25, n)*2;

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else if (pos[turn] <= 35) {
				pos[turn] = 35;
				int i = getOwner(35);
				if (i == -1) {
					channel.sendMessage("Advance token to Short Line (`r4`). Railway is unowned, you may buy it from the Bank ¥" + getCost(35)).queue();
					userinput = 1;
					stop = true;
				}else if (i == turn) {
					channel.sendMessage("Advance token to Short Line (`r4`). You owned this Railway").queue();
				}else if (pprty[i][35] > 6) {
					channel.sendMessage("Advance token to Short Line (`r4`). Railway is mortgaged").queue();
				}else {
					int price = getOwnedPrice(i, 35, n)*2;

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to Short Line (`r4`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}
			break;
		case 5:
			cur[turn] += 50;
			channel.sendMessage("Bank pays you dividend of ¥50. You now have ¥" + cur[turn]+".").queue();
			break;
		case 6:
			channel.sendMessage("Get out of Jail Free.").queue();
			if (jail[turn] > 0) {
				channel.sendMessage("You've used your **Jail** Free card. You are no longer in **Jail**.").queue();
			}else jail[turn]--;

			break;
		case 7:
			if (pos[turn] - 3 < 0) pos[turn] = pos[turn] - 3 + 39;
			else pos[turn] -= 3;
			channel.sendMessage("You saw your Anime in Dubbed Version. Go Back Three Spaces.").queue();
			break;
		case 8:
			channel.sendMessage("You watched Boku no Pico. Go to **Jail**. Go directly to **Jail**. Do not pass Go, Do not collect ¥200").queue();
			pos[turn] = 10;
			if (jail[turn] < 0) {
				jail[turn]++;
				channel.sendMessage("You've used your Jail Free card. You are no longer in **Jail**.").queue();
			}
			else jail[turn] = 3;
			break;
		case 9:
			int h = 0;
			int H = 0;
			for (int i = 0; i < 40; i++) {
				if (pprty[turn][i] == 6 || pprty[turn][i] == 12) H++;
				else if (pprty[turn][i] != 0 && pprty[turn][i] != 1 && pprty[turn][i] != 7) {
					if (pprty[turn][i] - 7 > 0) h += pprty[turn][i] - 7;
					else h += pprty[turn][i];
				}
			}
			cur[turn] -= (h*40 + H*115);
			channel.sendMessage("You decorated your properties with anime posters: For each house pay ¥25, For each hotel ¥100. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 10:
			cur[turn] -= 15;
			channel.sendMessage("Pay poor tax of ¥15. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 11:
			pos[turn] = 5;
			if (getOwner(5) == -1) {
				channel.sendMessage("Advance token to Reading Railroad (`r1`). Railway is unowned, you may buy it from the Bank ¥" + getCost(5)).queue();
				userinput = 1;
				stop = true;
			}else if (pprty[getOwner(5)][5] > 6) {
				channel.sendMessage("Advance token to Reading Railroad (`r1`). Railway is mortgaged").queue();
			}else if (getOwner(5) == turn) {
				channel.sendMessage("Advance token to Reading Railroad (`r1`). You own this Railway").queue();
			}else {
				int i = getOwner(5);
				int price = getOwnedPrice(i, 5, n); //user, property, dice

				cur[turn] -= price;
				cur[i] += price;
				channel.sendMessage("Advance token to Reading Railroad (`r1`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
						".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
			}
			break;
		case 12:
			pos[turn] = 39;
			if (pos[turn] < 39) {
				cur[turn] += 200;
				channel.sendMessage("Advance to Boardwalk (`82`) You passed Go, collect ¥200. You now have ¥" + cur[turn]+".").queue();
			}else if (getOwner(pos[turn]) == -1) {	//not owned
				channel.sendMessage("Advance token to Boardwalk (`82`).").queue();
			}else if (pprty[getOwner(39)][39] > 6) {	//owned but mortgaged
				channel.sendMessage("Advance token to Boardwalk (`82`). Property is mortgaged").queue();
			}else if (getOwner(pos[turn]) == turn) {	//owned but owned by you
				channel.sendMessage("Advance token to Boardwalk (`82`). You owned this property").queue();
			}else {									//pay
				int i = getOwner(pos[turn]);
				int price = getOwnedPrice(i, pos[turn], n);
				cur[turn] -= price;
				cur[i] += price;
				channel.sendMessage("Advance token to Boardwalk (`82`). Property is owned, you pay " + user.get(i).getName() + " ¥" + 10*n + 
						".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
			}
			break;
		case 13:
			for (int i = 0; i < user.size(); i++) {
				cur[i] += 50;
			}
			cur[turn] -= 50*user.size() + 50*dead.size();
			channel.sendMessage("You have been elected for having bad taste. Pay each player ¥50. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 14:
			cur[turn] += 150;
			channel.sendMessage("Your Apartment loan matures. Receive ¥150. You now have ¥" + cur[turn] + ".").queue();
			break;
		case 15:
			cur[turn] += 100;
			channel.sendMessage("You have won a crossword competition. Collect ¥100. You now have ¥" + cur[turn] + ".").queue();
			break;
		default:
			if (pos[turn] <= 5) {
				pos[turn] = 5;
				int i = getOwner(5);
				if (i == -1) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). Railway is unowned, you may buy it from the Bank ¥" + getCost(5)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][5] > 6) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). Railway is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). You own this Railway").queue();
				}else {
					int price = getOwnedPrice(i, 5, n)*2; //user, property, dice

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to Reading Railroad (`r1`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else if (pos[turn] <= 15) {
				pos[turn] = 15;
				int i = getOwner(15);
				if (i == -1) {
					channel.sendMessage("Advance token to Pennsylvania Railroad (`r2`). Railway is unowned, you may buy it from the Bank ¥" + getCost(15)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][15] > 6) {
					channel.sendMessage("Advance token to Pennsylvania Railroad (`r2`). Railway is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to Reading Railroad (`r1`). You owned this Railway").queue();
				}else {
					int price = getOwnedPrice(i, 15, n)*2;

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to Pennsylvania Railroad (`r2`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else if (pos[turn] <= 25) {
				pos[turn] = 25;
				int i = getOwner(25);
				if (i == -1) {
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). Railway is unowned, you may buy it from the Bank ¥" + getCost(25)).queue();
					userinput = 1;
					stop = true;
				}else if (pprty[i][25] > 6) {
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). Railway is mortgaged").queue();
				}else if (i == turn) {
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). You owned this Railway").queue();
				}else {
					int price = getOwnedPrice(i, 25, n)*2;

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to B. & O. Railroad (`r3`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}else if (pos[turn] <= 35) {
				pos[turn] = 35;
				int i = getOwner(35);
				if (i == -1) {
					channel.sendMessage("Advance token to Short Line (`r4`). Railway is unowned, you may buy it from the Bank ¥" + getCost(35)).queue();
					userinput = 1;
					stop = true;
				}else if (i == turn) {
					channel.sendMessage("Advance token to Short Line (`r4`). You owned this Railway").queue();
				}else if (pprty[i][25] > 6) {
					channel.sendMessage("Advance token to Short Line (`r4`). Railway is mortgaged").queue();
				}else {
					int price = getOwnedPrice(i, 35, n)*2;

					cur[turn] -= price;
					cur[i] += price;
					channel.sendMessage("Advance token to Short Line (`r4`). Property is owned, you pay " + user.get(i).getName() + " ¥" + price + 
							".\nYou now have ¥" + cur[turn] + ". " + user.get(i).getName() + " now has ¥" + cur[i] + ".").queue();
				}
			}
			break;
		}

	}

	//owner, property, dice
	//pprty
	public int getOwnedPrice(int o, int p, int d) {
		//railway
		if (p == 5 || p == 15 || p == 25 || p == 35) {
			int cost = 0;
			if (pprty[o][5] == 1) cost++;
			if (pprty[o][15] == 1) cost++;
			if (pprty[o][25] == 1) cost++;
			if (pprty[o][35] == 1) cost++;
			return (int) (25 * Math.pow(2, cost-1));
		}

		//utility
		else if (p == 12 || p == 28) {
			if (pprty[o][12] + pprty[o][28] == 2) return d*10;
			return d*4;
		}
		//1 3	6 8 9	11 13 14	16 18 19	21 23 24	26 27 29	31 32 34	37 39
		//brown 1 3
		if (p == 1) {
			int h = pprty[o][1];
			switch (h) {
			case 1:
				if (pprty[o][3] != 0) return 4;
				return 2;
			case 2: return 10;
			case 3: return 30;
			case 4: return 90;
			case 5: return 160;
			case 6: return 250;
			}
		}else if (p == 3) {
			int h = pprty[o][3];
			switch (h) {
			case 1:
				if (pprty[o][1] != 0) return 8;
				return 4;
			case 2: return 20;
			case 3: return 60;
			case 4: return 180;
			case 5: return 320;
			case 6: return 450;
			}
		}
		//light blue 6 8 9
		else if (p == 6) {
			int h = pprty[o][6];
			switch (h) {
			case 1:
				if (pprty[o][8] != 0 && pprty[o][9] != 0) return 12;
				return 6;
			case 2: return 30;
			case 3: return 90;
			case 4: return 270;
			case 5: return 400;
			case 6: return 550;
			}
		}else if (p == 8) {
			int h = pprty[o][8];
			switch (h) {
			case 1:
				if (pprty[o][6] != 0 && pprty[o][9] != 0) return 12;
				return 6;
			case 2: return 30;
			case 3: return 90;
			case 4: return 270;
			case 5: return 400;
			case 6: return 550;
			}
		}else if (p == 9) {
			int h = pprty[o][9];
			switch (h) {
			case 1:
				if (pprty[o][6] != 0 && pprty[o][8] != 0) return 16;
				return 8;
			case 2: return 40;
			case 3: return 100;
			case 4: return 300;
			case 5: return 450;
			case 6: return 600;
			}
		}
		//pink 11 13 14
		else if (p == 11) {
			int h = pprty[o][11];
			switch (h) {
			case 1:
				if (pprty[o][13] != 0 && pprty[o][14] != 0) return 20;
				return 10;
			case 2: return 50;
			case 3: return 150;
			case 4: return 450;
			case 5: return 625;
			case 6: return 750;
			}
		}else if (p == 13) {
			int h = pprty[o][13];
			switch (h) {
			case 1:
				if (pprty[o][11] != 0 && pprty[o][14] != 0) return 20;
				return 10;
			case 2: return 50;
			case 3: return 150;
			case 4: return 450;
			case 5: return 620;
			case 6: return 750;
			}
		}else if (p == 14) {
			int h = pprty[o][14];
			switch (h) {
			case 1:
				if (pprty[o][11] != 0 && pprty[o][13] != 0) return 24;
				return 12;
			case 2: return 60;
			case 3: return 180;
			case 4: return 500;
			case 5: return 700;
			case 6: return 900;
			}
		}
		//orange 16 18 19
		else if (p == 16) {
			int h = pprty[o][16];
			switch (h) {
			case 1:
				if (pprty[o][18] != 0 && pprty[o][19] != 0) return 28;
				return 14;
			case 2: return 70;
			case 3: return 200;
			case 4: return 550;
			case 5: return 750;
			case 6: return 950;
			}
		}else if (p == 18) {
			int h = pprty[o][18];
			switch (h) {
			case 1:
				if (pprty[o][16] != 0 && pprty[o][19] != 0) return 28;
				return 14;
			case 2: return 70;
			case 3: return 200;
			case 4: return 550;
			case 5: return 750;
			case 6: return 950;
			}
		}else if (p == 19) {
			int h = pprty[o][19];
			switch (h) {
			case 1:
				if (pprty[o][16] != 0 && pprty[o][18] != 0) return 32;
				return 16;
			case 2: return 80;
			case 3: return 220;
			case 4: return 600;
			case 5: return 800;
			case 6: return 1000;
			}
		}
		//red 21 23 24
		else if (p == 21) {
			int h = pprty[o][21];
			switch (h) {
			case 1:
				if (pprty[o][23] != 0 && pprty[o][24] != 0) return 36;
				return 18;
			case 2: return 90;
			case 3: return 250;
			case 4: return 700;
			case 5: return 875;
			case 6: return 1050;
			}
		}else if (p == 23) {
			int h = pprty[o][23];
			switch (h) {
			case 1:
				if (pprty[o][21] != 0 && pprty[o][24] != 0) return 36;
				return 18;
			case 2: return 90;
			case 3: return 250;
			case 4: return 700;
			case 5: return 875;
			case 6: return 1050;
			}
		}else if (p == 24) {
			int h = pprty[o][24];
			switch (h) {
			case 1:
				if (pprty[o][21] != 0 && pprty[o][23] != 0) return 40;
				return 20;
			case 2: return 100;
			case 3: return 300;
			case 4: return 750;
			case 5: return 925;
			case 6: return 1100;
			}
		}
		//yellow 26 27 29
		else if (p == 26) {
			int h = pprty[o][26];
			switch (h) {
			case 1:
				if (pprty[o][27] != 0 && pprty[o][29] != 0) return 44;
				return 22;
			case 2: return 110;
			case 3: return 330;
			case 4: return 800;
			case 5: return 975;
			case 6: return 1150;
			}
		}else if (p == 27) {
			int h = pprty[o][27];
			switch (h) {
			case 1:
				if (pprty[o][29] != 0 && pprty[o][26] != 0) return 44;
				return 22;
			case 2: return 110;
			case 3: return 330;
			case 4: return 800;
			case 5: return 975;
			case 6: return 1150;
			}
		}else if (p == 29) {
			int h = pprty[o][29];
			switch (h) {
			case 1:
				if (pprty[o][26] != 0 && pprty[o][27] != 0) return 48;
				return 24;
			case 2: return 120;
			case 3: return 360;
			case 4: return 850;
			case 5: return 1025;
			case 6: return 1200;
			}
		}
		//green 31 32 34
		else if (p == 31) {
			int h = pprty[o][31];
			switch (h) {
			case 1:
				if (pprty[o][32] != 0 && pprty[o][34] != 0) return 52;
				return 26;
			case 2: return 130;
			case 3: return 390;
			case 4: return 900;
			case 5: return 110;
			case 6: return 1275;
			}
		}else if (p == 32) {
			int h = pprty[o][32];
			switch (h) {
			case 1:
				if (pprty[o][31] != 0 && pprty[o][34] != 0) return 52;
				return 26;
			case 2: return 130;
			case 3: return 390;
			case 4: return 900;
			case 5: return 110;
			case 6: return 1275;
			}
		}else if (p == 34) {
			int h = pprty[o][34];
			switch (h) {
			case 1:
				if (pprty[o][31] != 0 && pprty[o][32] != 0) return 56;
				return 28;
			case 2: return 150;
			case 3: return 450;
			case 4: return 1000;
			case 5: return 1200;
			case 6: return 1400;
			}
		}
		//37 39
		else if (p == 37) {
			int h = pprty[o][37];
			switch (h) {
			case 1:
				if (pprty[o][39] != 0) return 70;
				return 35;
			case 2: return 175;
			case 3: return 500;
			case 4: return 110;
			case 5: return 1300;
			case 6: return 1500;
			}
		}else {
			int h = pprty[o][39];
			switch (h) {
			case 1:
				if (pprty[o][37] != 0) return 100;
				return 50;
			case 2: return 200;
			case 3: return 600;
			case 4: return 1400;
			case 5: return 1700;
			case 6: return 2000;
			}
		}
		return -1;
	}


	public int getOwner(int p) {	//returns -1 when property is not owned, otherwise returns owner index
		for (int i = 0; i < user.size(); i++) {
			if	(pprty[i][p] != 0) {	//user, property
				return i;
			}
		}
		return -1;
	}

	public void censor(MessageChannel channel, int r) {
		switch (r) {
		case 1:
			channel.sendMessage("Hello everyone! I'm an innocent player who loves everything Hybagel.").queue();
			break;
		case 2:
			channel.sendMessage("Your personality shines like the sun.").queue();
			break;
		case 3:
			channel.sendMessage("I like Minecraft PvP but you are truly better than me!").queue();
			break;
		case 4:
			channel.sendMessage("I enjoy long walks on the beach and playing Hybagel.").queue();
			break;
		case 5:
			channel.sendMessage("In my free time I like to watch cat videos on YouTube.").queue();
			break;
		case 6:
			channel.sendMessage("What happens if I add chocolate milk to macaroni and cheese?").queue();
			break;
		case 7:
			channel.sendMessage("Can you paint with all the colours of the wind?").queue();
			break;
		case 8:
			channel.sendMessage("I had something to say, then I forgot it.").queue();
			break;
		case 9:
			channel.sendMessage("You're a great person! Do you want to play some Hybagel games with me?").queue();
			break;
		case 10:
			channel.sendMessage("I have really enjoyed playing with you! <3").queue();
			break;
		case 11:
			channel.sendMessage("Behold, the great and powerful, my magnificent and almighty nemesis!").queue();
			break;
		case 12:
			channel.sendMessage("Why can't the Ender Dragon read a book? Because he always starts at the End.").queue();
			break;
		case 13:
			channel.sendMessage("Blue is greener than purple for sure.").queue();
			break;
		case 14:
			channel.sendMessage("Pineapple doesn't go on pizza!").queue();
			break;
		case 15:
			channel.sendMessage("I like pineapple on my pizza.").queue();
			break;
		case 16:
			channel.sendMessage("Let's be friends instead of fighting okay?").queue();
			break;
		case 17:
			channel.sendMessage("I heard you like Minecraft, so I built a computer so you can Minecarft, while Minecrafting in your Minecraft.").queue();
			break;
		case 18:
			channel.sendMessage("Please go easy on me, this is my first game!").queue();
			break;
		case 19:
			channel.sendMessage("Maybe we can have a rematch?").queue();
			break;
		case 20:
			channel.sendMessage("You are very good at this game friend.").queue();
			break;
		case 21:
			channel.sendMessage("Hey Helper, how play game?").queue();
			break;
		case 22:
			channel.sendMessage("ILY<3").queue();
			break;
		case 23:
			channel.sendMessage("Wait, this isn't what I typed!").queue();
			break;
		case 24:
			channel.sendMessage("I sometimes try to say bad things and then this happens. :( ").queue();
			break;
		case 25:
			channel.sendMessage("If the world in Minecraft is infinite... how can the sun revolve around it?").queue();
			break;
		case 26:
			channel.sendMessage("Doin a bamboozle fren.").queue();
			break;
		case 27:
			channel.sendMessage("Pls give me doggo memes!").queue();
			break;
		case 28:
			channel.sendMessage("When I saw the guy with a potion I knew there was trouble brewing.").queue();
			break;
		case 29:
			channel.sendMessage("I need help, teach me how to play!").queue();
			break;
		case 30:
			channel.sendMessage("Anyone else really like Rick Astley?").queue();
			break;
		case 31:
			channel.sendMessage("Your clicks per second are godly. :O ").queue();
			break;
		case 32:
			channel.sendMessage("Sometimes I sing soppy, love songs in the car.").queue();
			break;
		}

	}
	public void sendMessage(User user, String content) {
		user.openPrivateChannel()
		.flatMap(channel -> channel.sendMessage(content))
		.queue();
	}

}