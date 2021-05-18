package chatbot;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Based {

	public static void main(String[] args) throws IOException, LoginException {
		BasicConfigurator.configure();

		API mao = new API("token here", 
				Activity.streaming("cringe based", "https://www.youtube.com/watch?v=xF2uHwOg2kk"), OnlineStatus.DO_NOT_DISTURB);
		
		API[] bot = {mao};
		new Bot(bot, "~ \\ ilikedtheguywhokilledhitler");
	
	}



}
