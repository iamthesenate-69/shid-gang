package chatbot.commands.moderation;

import net.dv8tion.jda.api.entities.Member;
import java.util.List;
import chatbot.commands.Commands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Vaporize extends Commands {

	@Override
	public String name() {
		return "vaporize";
	}

	@Override
	public String description() {
		return "deletes proles";
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
		if (!admin.hasPermission(Permission.BAN_MEMBERS) || !canInteract(admin, victims)) return;

		for (Member v : victims) {
			v.ban(7, "thought crime").queue();
		}
	}

	boolean canInteract(Member admin, List<Member> victims) {
		for (Member v : victims) {
			if (!admin.canInteract(v))
				return false;
		}
		return true;
	}


}
