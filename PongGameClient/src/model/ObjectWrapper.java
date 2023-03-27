package model;

import java.io.Serializable;

public class ObjectWrapper  implements Serializable{
	private static final long serialVersionUID = 20210811011L;
	public static final int LOGIN_USER = 1;
	public static final int REPLY_LOGIN_USER = 2;
        public static final int REGISTER_USER = 3;
	public static final int REPLY_REGISTER_USER = 4;
        
        public static final int SERVER_INFORM_PLAYER_LIST = 5;
        public static final int SERVER_INFORM_ONLINE_PLAYER = 6;
        public static final int SERVER_INFORM_OFFLINE_PLAYER = 7;
        public static final int CLIENT_INFORM_OFFLINE_PLAYER = 8;
        public static final int CLIENT_INFORM_INGAME_PLAYER = 9;
        public static final int CLIENT_INFORM_OFFGAME = 10;    
        public static final int SERVER_INFORM_INGAME_PLAYER = 11;
        
	public static final int FRIEND_LIST = 12;
        public static final int REPLY_FRIEND_LIST = 13;
        
        public static final int REQUEST_FRIEND = 14;
        public static final int REPLY_REQUEST_FRIEND = 15;
        public static final int FRIEND_REQUEST_LIST =16;
        public static final int REPLY_FRIEND_REQUEST_LIST =17;
	public static final int RESPONSE_FRIEND_REQUEST=18;
        public static final int REPLY_RESPONSE_FRIEND_REQUEST=19;
        public static final int UNFRIEND = 20;
        public static final int REPLY_UNFRIEND = 21;
        
	public static final int EDIT_PLAYER=22;
	public static final int REPLY_EDIT_PLAYER=23;
        
        public static final int CREATE_CLAN=24;
	public static final int REPLY_CREATE_CLAN=25;
	public static final int CLAN_LIST=26;
	public static final int REPLY_CLAN_LIST=27;
        public static final int JOIN_CLAN = 28;
        public static final int REPLY_JOIN_CLAN = 29;
        public static final int EDIT_CLAN = 30;
        public static final int REPLY_EDIT_CLAN = 31;
        public static final int LEAVE_CLAN = 32;
        public static final int REPLY_LEAVE_CLAN = 33;
        public static final int KICK_MEMBER = 34;
        public static final int REPLY_KICK_MEMBER = 35;
        public static final int REQUEST_JOIN_CLAN = 36;
        public static final int REPLY_REQUEST_JOIN_CLAN = 37;
        public static final int RESPONSE_REQUEST_JOIN_CLAN = 38;
        public static final int REPLY_RESPONSE_REQUEST_JOIN_CLAN = 39;
        public static final int JOIN_REQUEST_LIST = 40;
        public static final int REPLY_JOIN_REQUEST_LIST = 41;
        
        public static final int RANKING=42;
        public static final int REPLY_RANKING=43;
        
        public static final int PLAYER_LIST=44;
        public static final int REPLY_PLAYER_LIST=45;
        
        public static final int MESSAGE_PLAYER=46;
        public static final int SEND_MESSAGE_PLAYER=46;
        
	public static final int CHALLENGE = 54;
        public static final int REQUEST_CHALLENGE_LIST = 55;
        public static final int REPLY_REQUEST_CHALLENGE_LIST = 56;
        public static final int RESPONSE_CHALLENGE = 57;
        public static final int RESPONSE_CHALLENGER = 58;
        public static final int RESPONSE_CHALLENGE_PLAYER = 59;
        public static final int REPLY_CHALLENGE_PLAYER = 60;
        public static final int HOST_GAME = 61;
        public static final int REPLY_HOST_GAME = 62;
        
        public static final int CHECK_PLAYER_NAME=63;
        public static final int REPLY_CHECK_PLAYER_NAME=64;
        
        public static final int GET_USER=65;
        public static final int REPLY_USER=66;
        public static final int CHANGE_PASSWORD=67;
        public static final int REPLY_CHANGE_PASSWORD=68;
        public static final int GET_PLAYER=69;
        public static final int REPLY_PLAYER=70;
        public static final int ENTER_GAME = 0;
        
        public static final int NEW_MATCH=71;
        public static final int REPLY_MATCH=72;
        public static final int NEW_PLAYER_MATCH=73;
        public static final int REPLY_PLAYER_MATCH=74;
        
        
        public static final int RESULT_MATCH=75;
        public static final int REPLY_RESULT_MATCH=76;
        
        
        
        
        
	private int performative;
	private Object data;
	public ObjectWrapper() {
		super();
	}
	public ObjectWrapper(int performative, Object data) {
		super();
		this.performative = performative;
		this.data = data;
	}
	public int getPerformative() {
		return performative;
	}
	public void setPerformative(int performative) {
		this.performative = performative;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}	
}