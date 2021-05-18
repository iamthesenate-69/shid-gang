package chatbot.commands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import chatbot.commands.AbandonShip.DisconnectUsers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class PlayerManager {
	public static PlayerManager INSTANCE;
	public final AudioPlayerManager playerManager;
	public final Map<Long, GuildMusicManager> musicManagers;

	private PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
		long guildId = guild.getIdLong();
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

	public void loadAndPlay(TextChannel channel, String trackUrl) {
		GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

		playerManager.loadItemOrdered(musicManager, trackUrl, new Handler(channel, musicManager, trackUrl));

	}
	
	public void PlayThenDisconnect(TextChannel channel, String trackUrl, VoiceChannel vc) {
		GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
		playerManager.loadItemOrdered(musicManager, trackUrl, new DisconnectHandler(channel, musicManager, trackUrl, vc));

	}

	public static synchronized PlayerManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}
		return INSTANCE;
	}
	class Handler implements AudioLoadResultHandler {
		TextChannel channel;
		GuildMusicManager musicManager;
		String trackUrl;
		
		Handler(TextChannel _channel, GuildMusicManager _musicManager, String _trackUrl) {
			channel = _channel;
			musicManager = _musicManager;
			trackUrl = _trackUrl;
		}

		@Override
		public void trackLoaded(AudioTrack track) {
			channel.sendMessage("Adding to queue `" + track.getInfo().title + "`").queue();

			musicManager.scheduler.queue(track);
		}

		@Override
		public void playlistLoaded(AudioPlaylist playlist) {
			
			if (playlist.isSearchResult()) {
				AudioTrack first = playlist.getTracks().get(0);
				musicManager.scheduler.queue(first);
				channel.sendMessage("Adding to queue `" + first.getInfo().title + "`").queue();
				return;
			}
			for (AudioTrack track : playlist.getTracks()) {
				musicManager.scheduler.queue(track);
			}
			
			channel.sendMessage("Adding to queue " + playlist.getTracks().size() + " track(s) from playlist `" + playlist.getName() + "`").queue();
		}

		@Override
		public void noMatches() {
			channel.sendMessage("Nothing found by `" + trackUrl + "`").queue();
		}

		@Override
		public void loadFailed(FriendlyException exception) {
			channel.sendMessage("Could not play: `" + exception.getMessage() + "`").queue();
		}
	}
	
	class DisconnectHandler implements AudioLoadResultHandler {
		TextChannel channel;
		GuildMusicManager musicManager;
		String trackUrl;
		VoiceChannel vc;
		
		DisconnectHandler(TextChannel _channel, GuildMusicManager _musicManager, String _trackUrl, VoiceChannel _vc) {
			channel = _channel;
			musicManager = _musicManager;
			trackUrl = _trackUrl;
			vc = _vc;
		}

		@Override
		public void trackLoaded(AudioTrack track) {
			musicManager.scheduler.queue(track);
			new Timer().schedule(new DisconnectUsers(vc), track.getDuration(), 1);
		}

		@Override
		public void playlistLoaded(AudioPlaylist playlist) {
		}
		@Override
		public void noMatches() {
			channel.sendMessage("No matches").queue();
		}
		@Override
		public void loadFailed(FriendlyException exception) {
			channel.sendMessage("An error occurred: `" + exception.getMessage() + "`").queue();
		}
	}
}