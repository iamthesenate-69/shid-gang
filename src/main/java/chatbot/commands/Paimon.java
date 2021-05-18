package chatbot.commands;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import chatbot.Bot;
import chatbot.Bot.Limiter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Paimon extends Commands {

	@Override
	public String name() {
		return "paimon";
	}

	@Override
	public String description() {
		return "what do you mean";
	}

	@Override
	public String usage() {
		return " <text>";
	}

	@Override
	public int cooldown() {
		return 10000;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		try {
			BufferedImage img = ImageIO.read(new File(".\\checkmate.jpg"));
			Graphics2D g = img.createGraphics();
			g.setFont(new Font( "SansSerif", Font.BOLD, 20));
			
			args = toString(args).split("\n");
			
			String s = "";
			int sLength = 0;
			int maxLength = 15;
			for (int i = 1; i < args.length; i++) {
				s += " " + args[i];
				sLength += args[i].length();
				System.out.println(s + " " + sLength);
				if (sLength + args[Math.min(i+1, args.length-1)].length() > maxLength) {
					s += "\n";
					sLength = 0;
					maxLength = 30;
				}
			}
			s = s.charAt(s.length()-1) == '\n'?s.substring(0,s.length()-1)+"!?":s+"!?";
			
			String[] lines = s.split("\n");
			g.drawString(lines[0], 564, 651);
			for (int i = 1; i < lines.length; i++) {
				g.drawString(lines[i].trim(), 356, 651 + i*20);
			}
			
			File outputfile = new File("temp.jpg");
			ImageIO.write(img, "jpg", outputfile);
			event.getChannel().sendFile(outputfile, "paimon.png").queue();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	String toString(String[] args) {
		String s = args[0];
		for (int i = 1; i < args.length; i++) {
			s += "\n" + args[i];
		}
		return s;
	}

}
