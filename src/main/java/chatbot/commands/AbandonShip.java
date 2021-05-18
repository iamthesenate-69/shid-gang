package chatbot.commands;

import java.io.File;
import java.net.URL;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import chatbot.Bot;
import chatbot.commands.lavaplayer.GuildMusicManager;
import chatbot.commands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class AbandonShip extends Commands {
	
	String path = "C:\\timetoabandonship.mp3";

	@Override
	public String name() {
		return "abandonship";
	}

	@Override
	public String description() {
		return "time to abandon ship *click*";
	}

	@Override
	public String usage() {
		return Bot.prefix+name();
	}

	@Override
	public int cooldown() {
		return 10000;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		Stop(event);
		Play(event);
		event.getChannel().sendFile(new File(path), "ship.mp3").queue();
	}

	public static class DisconnectUsers extends TimerTask {
		VoiceChannel vc;
		public DisconnectUsers(VoiceChannel _vc) {
			vc = _vc;
		}
		
		@Override
		public void run() throws NullPointerException{
			vc.createCopy().setPosition(vc.getPosition()).queue();
			vc.delete().queue();
			cancel();
		}
		
	}

	long GetDuration(MessageReceivedEvent event) {
		GuildMusicManager Newmanager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
		AudioTrack track = Newmanager.scheduler.player.getPlayingTrack();
		return track.getDuration();
	}

	void Play(MessageReceivedEvent event) {
		PlayerManager.getInstance().PlayThenDisconnect(event.getTextChannel(), path, event.getMember().getVoiceState().getChannel());
		event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
		
	}

	void Stop(MessageReceivedEvent event) {
		GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
		manager.scheduler.stop();
	}
	

}
