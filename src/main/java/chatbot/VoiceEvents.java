package chatbot;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceEvents  extends ListenerAdapter{
	ArrayList<String> unaffected = new ArrayList<String>();
	@Override
	public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
		if (event.getMember().getVoiceState().isGuildMuted() && inList(event.getMember().getId()))
			event.getGuild().mute(event.getMember(), false).queue();
	}
    boolean inList(String id) {
		if (unaffected.contains(id))
			return true;
		return false;
	}
    @Override
	public void onGuildVoiceGuildDeafen(@Nonnull GuildVoiceGuildDeafenEvent event) {
    	if (event.getMember().getVoiceState().isGuildDeafened() && inList(event.getMember().getId()))
			event.getGuild().deafen(event.getMember(), false).queue();
    }
    @Override
	public void onReady(ReadyEvent event) {
		unaffected.add("372123318167273502");
		unaffected.add("730107179352195134");
		unaffected.add("809852735247745125");
		
	}


}
