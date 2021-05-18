package chatbot;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class API {
	String token;
	Activity activity;
	OnlineStatus status;
	API (String _token, Activity _activity, OnlineStatus _status) {
		token = _token;
		activity = _activity;
		status = _status;
	}

	String getToken() {
		return token;
	}

	Activity getActivity() {
		return activity;
	}

	OnlineStatus getOnlineStatus() {
		return status;
	}
}