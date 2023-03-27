package control;
 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import model.Player;
 
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.login.LoginFrm;
import view.ClientMainFrm;
import view.FriendFrm;
import view.LobbyGame.GameMenuFrm;
import view.LobbyGame.HostGameFrm;
import view.InfoPlayerFrm;
import view.LobbyGame.PlayGameFrm;
import view.ProfileFrm;
import view.RankingFrm;
import view.clan.ClanFrm;
import view.clan.CreateClanFrm;
import view.clan.ClanManagement;
import view.clan.InfoClanFrm;
import view.login.ChangePasswordFrm;
import view.login.RegisterFrm;
 
public class ClientCtr {
    private Socket mySocket;
    private ClientMainFrm view;
    private ClientListening myListening;                            // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;                  // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost",8887);  // default server host and port 
    public ClientCtr(ClientMainFrm view){
        super();
        this.view = view;
        myFunction = new ArrayList<ObjectWrapper>();  
    }
     
    public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }
 
 
 
    public boolean openConnection(){        
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());  
            myListening = new ClientListening();
            myListening.start();   
        } catch (Exception e) {
            //e.printStackTrace();
            view.showMessage("Error when connecting to the server!");
            return false;
        }
        return true;
    }
     
    public boolean sendData(Object obj){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);           
             
        } catch (Exception e) {
            //e.printStackTrace();
            view.showMessage("Error when sending data to the server!");
            return false;
        }
        return true;
    }
     
     
    public boolean closeConnection(){
         try {
             if(myListening != null)
                 myListening.stop();
             if(mySocket !=null) {
                 mySocket.close();
                 view.showMessage("Disconnected from the server!");
             }
            myFunction.clear();             
         } catch (Exception e) {
             //e.printStackTrace();
             view.showMessage("Error when disconnecting from the server!");
             return false;
         }
         return true;
    }
  
     
    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }
 
    class ClientListening extends Thread{
         
        public ClientListening() {
            super();
        }
         
        public void run() {
            try {
                while(true) {
                ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                Object obj = ois.readObject();
                if(obj instanceof ObjectWrapper) {
                    ObjectWrapper data = (ObjectWrapper)obj;

                    if(data.getPerformative() == ObjectWrapper.SERVER_INFORM_ONLINE_PLAYER){
                            view.updateOnlinePlayer(data);
                    }if(data.getPerformative() == ObjectWrapper.SERVER_INFORM_OFFLINE_PLAYER){
                            view.updateOnlinePlayer(data);
                    }
                    if(data.getPerformative() == ObjectWrapper.SERVER_INFORM_INGAME_PLAYER){
                            view.updateInGamePlayer(data);
                    }
                    if(data.getPerformative() == ObjectWrapper.SEND_MESSAGE_PLAYER){
                            view.showMessageFromPlayer(data);
                    }
                    else {
                        for(int i=0;i<myFunction.size();i++)
                            if(myFunction.get(i).getPerformative() == data.getPerformative()) {
                                switch(data.getPerformative()) {
                                case ObjectWrapper.REPLY_LOGIN_USER:
                                    if(myFunction.get(i).getData() instanceof LoginFrm){
                                        LoginFrm loginView = (LoginFrm)myFunction.get(i).getData();
                                        loginView.receivedDataProcessing(data); 
                                    }
                                    break;
                                case ObjectWrapper.REPLY_REGISTER_USER:
                                    if(myFunction.get(i).getData() instanceof RegisterFrm){
                                        RegisterFrm rf = (RegisterFrm)myFunction.get(i).getData();
                                        rf.receivedData(data);
                                    }
                                    break;
                                 case ObjectWrapper.REPLY_PLAYER_LIST:
                                    if(myFunction.get(i).getData() instanceof ClientMainFrm){
                                        ClientMainFrm cmf = (ClientMainFrm)myFunction.get(i).getData();
                                        cmf.receivedData(data);
                                    }
                                    break;    
                                case ObjectWrapper.REPLY_FRIEND_LIST:
                                    if(myFunction.get(i).getData() instanceof ClientMainFrm){
                                        ClientMainFrm cmf = (ClientMainFrm)myFunction.get(i).getData();
                                        cmf.receivedData(data);
                                    }
                                    break;
                                case ObjectWrapper.REPLY_FRIEND_REQUEST_LIST:
                                     if(myFunction.get(i).getData() instanceof FriendFrm)
                                     {
                                        FriendFrm ff = (FriendFrm)myFunction.get(i).getData();
                                        ff.receivedDataFF(data);
                                     }
                                     break;
                               case ObjectWrapper.REPLY_REQUEST_FRIEND:
                                    if(myFunction.get(i).getData() instanceof InfoPlayerFrm)
                                    {
                                        InfoPlayerFrm ipf = (InfoPlayerFrm) myFunction.get(i).getData();
                                        ipf.receivedData(data);
                                    }
                                    break;
                               case ObjectWrapper.REPLY_UNFRIEND:
                                    if(myFunction.get(i).getData() instanceof InfoPlayerFrm)
                                    {
                                        InfoPlayerFrm ipf = (InfoPlayerFrm) myFunction.get(i).getData();
                                        ipf.receivedData(data);
                                    }
                                    break;
                                case ObjectWrapper.REPLY_EDIT_PLAYER:
                                         ProfileFrm pf = (ProfileFrm) myFunction.get(i).getData();
                                         pf.receivedData(data);
                                     break;
                                case ObjectWrapper.REPLY_CHECK_PLAYER_NAME:
                                         ProfileFrm pf1 = (ProfileFrm) myFunction.get(i).getData();
                                         pf1.receivedData(data);
                                         break;
                                case ObjectWrapper.REPLY_RANKING:
                                     if(myFunction.get(i).getData() instanceof RankingFrm)
                                     {
                                        RankingFrm rf = (RankingFrm) myFunction.get(i).getData();
                                        rf.receivedData(data);
                                     }
                                     break;
                                case ObjectWrapper.REPLY_CLAN_LIST:
                                     if(myFunction.get(i).getData() instanceof ClanFrm)
                                     {
                                        ClanFrm cf = (ClanFrm) myFunction.get(i).getData();
                                        cf.receivedData(data);
                                     }
                                     break;
                                case ObjectWrapper.REPLY_LEAVE_CLAN:
                                     if(myFunction.get(i).getData() instanceof ClanFrm)
                                     {
                                        ClanFrm cf = (ClanFrm) myFunction.get(i).getData();
                                        cf.receivedData(data);
                                     }
                                case ObjectWrapper.REPLY_KICK_MEMBER:     
                                     if(myFunction.get(i).getData() instanceof ClanManagement)
                                     {
                                        ClanManagement ecf = (ClanManagement) myFunction.get(i).getData();
                                        ecf.receivedData(data);
                                     }
                                     break;
                                case ObjectWrapper.REPLY_CREATE_CLAN:
                                     if(myFunction.get(i).getData() instanceof CreateClanFrm)
                                     {
                                         CreateClanFrm ccf = (CreateClanFrm) myFunction.get(i).getData();
                                        ccf.receivedData(data);
                                     }
                                     break;  
                                case ObjectWrapper.REPLY_JOIN_CLAN:
                                     if(myFunction.get(i).getData() instanceof InfoClanFrm)
                                     {
                                         InfoClanFrm icf = (InfoClanFrm) myFunction.get(i).getData();
                                        icf.receivedData(data);
                                     }
                                     break;  
                                case ObjectWrapper.REPLY_EDIT_CLAN:
                                     if(myFunction.get(i).getData() instanceof ClanManagement)
                                     {
                                         ClanManagement ecf = (ClanManagement) myFunction.get(i).getData();
                                         ecf.receivedData(data);
                                     }
                                     break; 
                                case ObjectWrapper.REPLY_REQUEST_JOIN_CLAN:
                                     if(myFunction.get(i).getData() instanceof InfoClanFrm)
                                     {
                                         InfoClanFrm icf = (InfoClanFrm) myFunction.get(i).getData();
                                         icf.receivedData(data);
                                     }
                                     break;    
                                case ObjectWrapper.REPLY_JOIN_REQUEST_LIST:
                                     if(myFunction.get(i).getData() instanceof ClanManagement)
                                     {
                                         ClanManagement ecf = (ClanManagement) myFunction.get(i).getData();
                                         ecf.receivedData(data);
                                     }
                                     break;  
                                case ObjectWrapper.REPLY_RESPONSE_REQUEST_JOIN_CLAN:
                                     if(myFunction.get(i).getData() instanceof ClanManagement)
                                     {
                                         ClanManagement ecf = (ClanManagement) myFunction.get(i).getData();
                                         ecf.receivedData(data);
                                     }
                                     break;
                                case ObjectWrapper.REPLY_REQUEST_CHALLENGE_LIST:
                                    if(myFunction.get(i).getData() instanceof GameMenuFrm)
                                     {
                                         GameMenuFrm gmf = (GameMenuFrm) myFunction.get(i).getData();
                                         gmf.receivedData(data);
                                     }
                                    break;
                                case ObjectWrapper.RESPONSE_CHALLENGER:
                                    if(myFunction.get(i).getData() instanceof ClientMainFrm){
                                        ClientMainFrm cmf = (ClientMainFrm)myFunction.get(i).getData();
                                        cmf.receivedChallengeData(data);
                                    }
                                    break;
                                case ObjectWrapper.REPLY_CHALLENGE_PLAYER:
                                    if(myFunction.get(i).getData() instanceof GameMenuFrm)
                                     {
                                         GameMenuFrm gmf = (GameMenuFrm) myFunction.get(i).getData();
                                         gmf.receivedData(data);
                                     }
                                    break;
                                case ObjectWrapper.REPLY_HOST_GAME:
                                    if(myFunction.get(i).getData() instanceof HostGameFrm)
                                     {
                                         HostGameFrm hgf = (HostGameFrm) myFunction.get(i).getData();
                                         hgf.receivedData(data);
                                     }
                                    break;    
                                case ObjectWrapper.REPLY_USER:
                                    if(myFunction.get(i).getData() instanceof ChangePasswordFrm)
                                     {
                                         ChangePasswordFrm cpf = (ChangePasswordFrm) myFunction.get(i).getData();
                                         cpf.receivedData(data);
                                     }
                                    break;
                                 case ObjectWrapper.REPLY_PLAYER:
                                    if(myFunction.get(i).getData() instanceof ProfileFrm)
                                     {
                                         ProfileFrm pf2 = (ProfileFrm) myFunction.get(i).getData();
                                         pf2.receivedData(data);
                                     }
                                    break;   
                                 case ObjectWrapper.REPLY_CHANGE_PASSWORD:
                                    if(myFunction.get(i).getData() instanceof ChangePasswordFrm)
                                     {
                                         ChangePasswordFrm cpf1 = (ChangePasswordFrm) myFunction.get(i).getData();
                                         cpf1.receivedData(data);
                                     }
                                    break; 
                                case ObjectWrapper.REPLY_MATCH:
                                    if(myFunction.get(i).getData() instanceof PlayGameFrm)
                                     {
                                         PlayGameFrm dgf = (PlayGameFrm) myFunction.get(i).getData();
                                         dgf.receivedData(data);
                                     }
                                    break; 
                                case ObjectWrapper.REPLY_RESULT_MATCH:
                                    if(myFunction.get(i).getData() instanceof PlayGameFrm)
                                     {
                                         PlayGameFrm dgf = (PlayGameFrm) myFunction.get(i).getData();
                                         dgf.receivedData(data);
                                     }
                                    break; 
                            }
                        //view.showMessage("Received an object: " + data.getPerformative());
                    }
                }
                }
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error when receiving data from the server!");
            }
        }
    }
}