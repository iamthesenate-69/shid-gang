package chatbot.moderation;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TyrantSlurs extends Moderations{

	@Override
	public String getString() {
		return " dyno ";
	}

	String criminalName = "eastasian war criminal";
	int criminalOrder = 0;

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		if (!(event.isFromGuild() && event.getGuild().getId().equals("699278323896811551"))) return;

		Guild g = event.getMember().getGuild();

		Member bot = g.getMember(event.getJDA().getSelfUser());
		Member member = event.getMember();

		Role criminal = g.getRolesByName(criminalName, true).get(criminalOrder);

		event.getChannel().sendMessage("THOUGHT CRIME").queue();

		if (!canInteract(bot, member, criminal))
			return;

		g.addRoleToMember(member, criminal);

	}

	boolean canInteract(Member bot, Member member, Role criminal) {
		if (!bot.canInteract(member)) 
			return false;

		for (Role r : member.getRoles()) {
			if (!bot.canInteract(r))
				return false;
		}

		if (!bot.canInteract(criminal))
			return false;

		return true;

	}
}
