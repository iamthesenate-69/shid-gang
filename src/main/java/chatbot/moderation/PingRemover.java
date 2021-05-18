package chatbot.moderation;

import java.util.LinkedList;
import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingRemover extends Moderations{

	@Override
	public String getString() {
		return "@";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		if (!(event.isFromGuild() && event.getGuild().getId().equals("699278323896811551"))) return;
		
		if (event.getMessage().mentionsEveryone()) {
			Member v = event.getMember();
			event.getChannel().sendMessage("BANED FROM SHID!GANG").queue();
			List<Role> roles = new LinkedList<Role>();
			event.getGuild().modifyMemberRoles(v, roles).queue();
		}
		
		for (Role r : event.getMessage().getMentionedRoles())
			if (r.getName().equals("everyone"))
				event.getGuild().addRoleToMember(event.getMember(), r).queue();
		
		
		if (event.getMessage().mentionsEveryone() || event.getMessage().getMentionedMembers().size() == 0)
			return;
		
		event.getMessage().delete().queue();
	}


}
