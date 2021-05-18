package chatbot.commands.moderation;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.LinkedList;
import java.util.List;
import chatbot.commands.Commands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ban extends Commands {

	@Override
	public String name() {
		return "ban";
	}

	@Override
	public String description() {
		return "imprisons proles";
	}

	@Override
	public String usage() {
		return "<prole>";
	}

	@Override
	public int cooldown() {
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		Member admin = event.getMember();
		List<Member> victims = event.getMessage().getMentionedMembers();
		if (!admin.hasPermission(Permission.KICK_MEMBERS) || !canInteract(admin, victims)) return;

		List<Role> roles = new LinkedList<Role>();
		
		for (Member v : victims)
			event.getGuild().modifyMemberRoles(v, roles).queue();
	}

	boolean canInteract(Member admin, List<Member> victims) {
		for (Member v : victims) {
			if (!admin.canInteract(v))
				return false;
		}
		return true;
	}


}
