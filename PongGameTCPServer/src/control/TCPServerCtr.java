package control;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import model.IPAddress;
import model.Match;
import model.Message;
import model.ObjectWrapper;
import model.Player;
import model.PlayerMatch;
import view.TCPServerMainFrm;
 
public class TCPServerCtr {
    private TCPServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private DatagramSocket myClient;  
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost",8887);  
    private IPAddress serverAddress = new IPAddress("localhost", 8888); 
    private ArrayList<Integer> onlineList=new ArrayList<>();
    private ArrayList<Integer> inGameList = new ArrayList<>();
    
    public TCPServerCtr(TCPServerMainFrm view){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();       
    }
     
    public TCPServerCtr(TCPServerMainFrm view, int serverPort){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();       
    }
    
    public void connectServer(){
        try {
            myClient = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());      
            view.showMessage("UDP client is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    public boolean disconnectServer(){
        try {
            myClient.close();
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    } 
    private void openServer(){
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            //System.out.println("server started!");
            view.showMessage("TCP server is running at the port " + myAddress.getPort() +"...");
            onlineList = new ArrayList<>();
        }catch(Exception e) {
            e.printStackTrace();;
        }
    }
     
    public void stopServer() {
        try {
            for(ServerProcessing sp:myProcess)
                sp.stop();
            
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public boolean sendDataToUDPServer(ObjectWrapper data){
        try {
            //prepare the buffer and write the data to send into the buffer
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.flush();            
             
            //create data package and send
            byte[] sendData = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverAddress.getHost()), serverAddress.getPort());
            myClient.send(sendPacket);
             
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error in sending data package");
            return false;
        }
        return true;
    }
    public ObjectWrapper receiveData(){
        ObjectWrapper result = null;
        try {   
            //prepare the buffer and fetch the received data into the buffer
            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
            myClient.receive(receivePacket);
             
            //read incoming data from the buffer 
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = (ObjectWrapper)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error in receiving data package");
        }
        return result;
    }

    public void publicOnlinePlayer() {
        for(ServerProcessing sp : myProcess) {
            sp.sendDataToClient(new ObjectWrapper(ObjectWrapper.SERVER_INFORM_ONLINE_PLAYER, onlineList));
        }
    }
    
    public void publicInGamePlayer() { 
        for(ServerProcessing sp : myProcess) {
            sp.sendDataToClient(new ObjectWrapper(ObjectWrapper.SERVER_INFORM_INGAME_PLAYER, inGameList));
        }
    }
     public void challengePlayer(ArrayList<Integer> challenge){
        for(ServerProcessing sp : myProcess) {
            if(sp.getSpId()==challenge.get(1))
            {   
                boolean check=false;
                for(int i=0;i<sp.getChallengeRequest().size();i++)
                    if(sp.getChallengeRequest().get(i)==challenge.get(0)){
                        check =true;
                        return;
                    }    
                if(check==false)
                    sp.getChallengeRequest().add(challenge.get(0));
                break;
            }
        }
    }
     public void responseChallenger(ArrayList<Integer> challenge){
      for(ServerProcessing sp : myProcess) {   
         if(challenge.get(0)==sp.getSpId()){
            sp.sendDataToClient(new ObjectWrapper(ObjectWrapper.RESPONSE_CHALLENGER, challenge));
            break;
         }
       }
     }
     public void sendDataToThisPlayer(int id,ObjectWrapper data){
        for(ServerProcessing sp : myProcess) {   
         if(id==sp.getSpId()){
            sp.sendDataToClient(data);
            break;
         }
       } 
     }
     public void sendDataToHostGame(ArrayList<Integer> hostGame){
         for(int i=0;i<myProcess.size();i++) {
             if(hostGame.get(0)==myProcess.get(i).getSpId()||hostGame.get(1)==myProcess.get(i).getSpId()){
//                 if(hostGame.get(2)==1&&hostGame.get(3)==1){
//                     sp.sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_HOST_GAME, "enterGame"));
//                 }
//                 else
                    myProcess.get(i).sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_HOST_GAME, hostGame));
             }
         }
     }
     public void messagePlayer(Message ms){
         for(ServerProcessing sp : myProcess) { 
             if(sp.getSpId()==ms.getIdReceive()){
                 sp.sendDataToClient(new ObjectWrapper(ObjectWrapper.SEND_MESSAGE_PLAYER, ms));
                 break;
             }
         }
     }
    /**
     * The class to listen the connections from client, avoiding the blocking of accept connection
     *
     */
    class ServerListening extends Thread{
         
        public ServerListening() {
            super();
        }
         
        public void run() {
            view.showMessage("server is listening... ");
            try {
                while(true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
    /**
     * The class to treat the requirement from client
     *
     */
    class ServerProcessing extends Thread{
        private Socket mySocket;
        private int spId;
        private ArrayList<Integer> challengeRequest=new ArrayList<>();
        //private ObjectInputStream ois;
        //private ObjectOutputStream oos;

        public int getSpId() {
            return spId;
        }

        public void setSpId(int spId) {
            this.spId = spId;
        }

        public ArrayList<Integer> getChallengeRequest() {
            return challengeRequest;
        }

        public void setChallengeRequest(ArrayList<Integer> challengeRequest) {
            this.challengeRequest = challengeRequest;
        }
         
        
        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
        }
         
        public void sendDataToClient(Object obj) {
            try {
                ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
         
        public void run() { 
            try {
                while(true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    Object o = ois.readObject();
                    if(o instanceof ObjectWrapper){
                        ObjectWrapper data = (ObjectWrapper)o;
 
                        switch(data.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.LOGIN_USER,data.getData()));
                            ObjectWrapper dataFromUDP= receiveData();
                            if(dataFromUDP.getPerformative()==ObjectWrapper.REPLY_LOGIN_USER){
                                int playerId = (int) dataFromUDP.getData();
                               if(playerId!=0){
                                    sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,playerId));
                                    onlineList.add(playerId); 
                                    spId=playerId;
                                    publicOnlinePlayer();    
                               }else
                                    sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,0));
                            }
                            break;
                        case ObjectWrapper.REGISTER_USER:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.REGISTER_USER,data.getData()));
                            ObjectWrapper dataFromUDP1= receiveData();
                            if(dataFromUDP1.getPerformative()==ObjectWrapper.REPLY_REGISTER_USER){
                                sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_USER, dataFromUDP1.getData()));  
                            }
                            break;
                        case ObjectWrapper.PLAYER_LIST:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.PLAYER_LIST,data.getData()));
                            ObjectWrapper dataFromUDP2= receiveData();
                            if(dataFromUDP2.getPerformative()==ObjectWrapper.REPLY_PLAYER_LIST){
                                sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_PLAYER_LIST, dataFromUDP2.getData()));  
                            }
                            break;
                        case ObjectWrapper.CLIENT_INFORM_OFFLINE_PLAYER:
                                int offlinePlayerId = (int) data.getData();
                                for(int i = 0; i< onlineList.size();i++){
                                    if(onlineList.get(i)==offlinePlayerId)
                                        onlineList.remove(i);
                                    }
                                publicOnlinePlayer();
                                break;
                        case ObjectWrapper.RANKING:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.RANKING,data.getData()));
                            ObjectWrapper dataFromUDP3= receiveData();
                            if(dataFromUDP3.getPerformative()==ObjectWrapper.REPLY_RANKING){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_RANKING, dataFromUDP3.getData()));
                            }
                        break;        
                        case ObjectWrapper.FRIEND_LIST:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.FRIEND_LIST,data.getData()));
                            ObjectWrapper dataFromUDP4= receiveData();
                            if(dataFromUDP4.getPerformative()==ObjectWrapper.REPLY_FRIEND_LIST){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_FRIEND_LIST, dataFromUDP4.getData()));
                            }
                            break;
                        case ObjectWrapper.REQUEST_FRIEND:
                                 sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.REQUEST_FRIEND,data.getData()));
                            ObjectWrapper dataFromUDP5= receiveData();
                            if(dataFromUDP5.getPerformative()==ObjectWrapper.REPLY_REQUEST_FRIEND){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_FRIEND, dataFromUDP5.getData()));
                            }
                            break;
                        case ObjectWrapper.FRIEND_REQUEST_LIST:
                               sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.FRIEND_REQUEST_LIST,data.getData()));
                            ObjectWrapper dataFromUDP6= receiveData();
                            if(dataFromUDP6.getPerformative()==ObjectWrapper.REPLY_FRIEND_REQUEST_LIST){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_FRIEND_REQUEST_LIST, dataFromUDP6.getData()));
                            }
                            break;
                        case ObjectWrapper.RESPONSE_FRIEND_REQUEST:
                                sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.RESPONSE_FRIEND_REQUEST,data.getData()));
                            ObjectWrapper dataFromUDP7= receiveData();
                            if(dataFromUDP7.getPerformative()==ObjectWrapper.REPLY_RESPONSE_FRIEND_REQUEST){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_RESPONSE_FRIEND_REQUEST, dataFromUDP7.getData()));
                            }
                            break;
                        case ObjectWrapper.UNFRIEND:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.UNFRIEND,data.getData()));
                            ObjectWrapper dataFromUDP8= receiveData();
                            if(dataFromUDP8.getPerformative()==ObjectWrapper.REPLY_UNFRIEND){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_UNFRIEND, dataFromUDP8.getData()));
                            }
                            break;  
                        case ObjectWrapper.EDIT_PLAYER:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.EDIT_PLAYER,data.getData()));
                            ObjectWrapper dataFromUDP9= receiveData();
                            if(dataFromUDP9.getPerformative()==ObjectWrapper.REPLY_EDIT_PLAYER){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_PLAYER, dataFromUDP9.getData()));
                            }
                            break;
                        case ObjectWrapper.CREATE_CLAN:
                       sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.CREATE_CLAN,data.getData()));
                            ObjectWrapper dataFromUDP10= receiveData();
                            if(dataFromUDP10.getPerformative()==ObjectWrapper.REPLY_CREATE_CLAN){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_CLAN, dataFromUDP10.getData()));
                            }
                            break;  
                    case ObjectWrapper.CLAN_LIST:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.CLAN_LIST,data.getData()));
                            ObjectWrapper dataFromUDP11= receiveData();
                            if(dataFromUDP11.getPerformative()==ObjectWrapper.REPLY_CLAN_LIST){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_CLAN_LIST, dataFromUDP11.getData()));
                            }
                            break;  
                    case ObjectWrapper.JOIN_CLAN:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.JOIN_CLAN,data.getData()));
                            ObjectWrapper dataFromUDP12= receiveData();
                            if(dataFromUDP12.getPerformative()==ObjectWrapper.REPLY_JOIN_CLAN){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_CLAN, dataFromUDP12.getData()));
                            }
                            break;  
                    case ObjectWrapper.EDIT_CLAN:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.EDIT_CLAN,data.getData()));
                            ObjectWrapper dataFromUDP13= receiveData();
                            if(dataFromUDP13.getPerformative()==ObjectWrapper.REPLY_EDIT_CLAN){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_CLAN, dataFromUDP13.getData()));
                            }
                            break;  
                    case ObjectWrapper.LEAVE_CLAN:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.LEAVE_CLAN,data.getData()));
                            ObjectWrapper dataFromUDP14= receiveData();
                            if(dataFromUDP14.getPerformative()==ObjectWrapper.REPLY_LEAVE_CLAN){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_CLAN, dataFromUDP14.getData()));
                            }
                            break;
                    case ObjectWrapper.KICK_MEMBER:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.KICK_MEMBER,data.getData()));
                            ObjectWrapper dataFromUDP15= receiveData();
                            if(dataFromUDP15.getPerformative()==ObjectWrapper.REPLY_KICK_MEMBER){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_KICK_MEMBER, dataFromUDP15.getData()));
                            }
                            break; 
                    case ObjectWrapper.JOIN_REQUEST_LIST:
                        sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.JOIN_REQUEST_LIST,data.getData()));
                            ObjectWrapper dataFromUDP16= receiveData();
                            if(dataFromUDP16.getPerformative()==ObjectWrapper.REPLY_JOIN_REQUEST_LIST){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_REQUEST_LIST, dataFromUDP16.getData()));
                            }
                            break; 
                    case ObjectWrapper.REQUEST_JOIN_CLAN:
                                 sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.REQUEST_JOIN_CLAN,data.getData()));
                            ObjectWrapper dataFromUDP17= receiveData();
                            if(dataFromUDP17.getPerformative()==ObjectWrapper.REPLY_REQUEST_JOIN_CLAN){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_JOIN_CLAN, dataFromUDP17.getData()));
                            }
                            break;
                    case ObjectWrapper.RESPONSE_REQUEST_JOIN_CLAN:
                                sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.RESPONSE_REQUEST_JOIN_CLAN,data.getData()));
                            ObjectWrapper dataFromUDP18= receiveData();
                            if(dataFromUDP18.getPerformative()==ObjectWrapper.REPLY_RESPONSE_REQUEST_JOIN_CLAN){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_RESPONSE_REQUEST_JOIN_CLAN, dataFromUDP18.getData()));
                            }
                            break; 
                    case ObjectWrapper.CHECK_PLAYER_NAME:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.CHECK_PLAYER_NAME,data.getData()));
                            ObjectWrapper dataFromUDP19= receiveData();
                            if(dataFromUDP19.getPerformative()==ObjectWrapper.REPLY_CHECK_PLAYER_NAME){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_PLAYER_NAME, dataFromUDP19.getData()));
                            }
                            break; 
                     case ObjectWrapper.CHANGE_PASSWORD:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.CHANGE_PASSWORD,data.getData()));
                            ObjectWrapper dataFromUDP20= receiveData();
                            if(dataFromUDP20.getPerformative()==ObjectWrapper.REPLY_CHANGE_PASSWORD){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_CHANGE_PASSWORD, dataFromUDP20.getData()));
                            }
                            break; 
                    case ObjectWrapper.GET_USER:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.GET_USER,data.getData()));
                            ObjectWrapper dataFromUDP21= receiveData();
                            if(dataFromUDP21.getPerformative()==ObjectWrapper.REPLY_USER){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_USER, dataFromUDP21.getData()));
                            }
                            break; 
                    case ObjectWrapper.GET_PLAYER:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.GET_PLAYER,data.getData()));
                            ObjectWrapper dataFromUDP22= receiveData();
                            if(dataFromUDP22.getPerformative()==ObjectWrapper.REPLY_PLAYER){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_PLAYER, dataFromUDP22.getData()));
                            }
                            break;
                    case ObjectWrapper.NEW_MATCH:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.NEW_MATCH,data.getData()));
                            ObjectWrapper dataFromUDP23= receiveData();
                            if(dataFromUDP23.getPerformative()==ObjectWrapper.REPLY_MATCH){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_MATCH, dataFromUDP23.getData()));
                            }
                            break;
                    case ObjectWrapper.RESULT_MATCH:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.RESULT_MATCH,data.getData()));
                            ObjectWrapper dataFromUDP24= receiveData();
                            if(dataFromUDP24.getPerformative()==ObjectWrapper.REPLY_RESULT_MATCH){
                               sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_RESULT_MATCH, dataFromUDP24.getData()));
                            }
                            break;        
                    case ObjectWrapper.NEW_PLAYER_MATCH:
                            sendDataToUDPServer(new ObjectWrapper(ObjectWrapper.NEW_PLAYER_MATCH,data.getData()));
                            ObjectWrapper dataFromUDP25= receiveData();
                            if(dataFromUDP25.getPerformative()==ObjectWrapper.REPLY_PLAYER_MATCH){
                                PlayerMatch pm = (PlayerMatch) dataFromUDP25.getData();
                               sendDataToThisPlayer(pm.getPlayer().getId(),new ObjectWrapper(ObjectWrapper.REPLY_PLAYER_MATCH, dataFromUDP25.getData()));
                            }
                            break;     
                        case ObjectWrapper.CHALLENGE:
                                 if(data.getData() instanceof ArrayList<?>){
                                     ArrayList<Integer> challenge = (ArrayList<Integer>) data.getData();
                                     challengePlayer(challenge);
                                 }
                                 break;
                        case ObjectWrapper.REQUEST_CHALLENGE_LIST:
                            if((int)data.getData()==spId){
                                if(this.getChallengeRequest().size()>0)
                                    sendDataToClient(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_CHALLENGE_LIST, challengeRequest));
                            }
                            break;
                        case ObjectWrapper.RESPONSE_CHALLENGE:
                        ArrayList<Integer> response  = (ArrayList<Integer>) data.getData();
                        if(response.get(1)==spId){
                            if(response.get(2)==1){
                                this.challengeRequest=new ArrayList<>();
                                responseChallenger(response);
                            }
                            else if(response.get(2)==0)
                            {
                                this.challengeRequest.clear();
                                responseChallenger(response);
                            }
                            
                        }
                            break;
                        case ObjectWrapper.HOST_GAME:
                            ArrayList<Integer> hostGame  = (ArrayList<Integer>) data.getData();
                               sendDataToHostGame(hostGame);
                            break;
                        case ObjectWrapper.RESPONSE_CHALLENGE_PLAYER:
                            ArrayList<Integer> responseCP  = (ArrayList<Integer>) data.getData();
                            sendDataToThisPlayer(responseCP.get(1), new ObjectWrapper(ObjectWrapper.REPLY_CHALLENGE_PLAYER, responseCP));
                            break;

                       case ObjectWrapper.CLIENT_INFORM_INGAME_PLAYER:
                                int inGameID  =  (int) data.getData();
                                inGameList.add(inGameID);
                                publicInGamePlayer();
                            break;
                        case ObjectWrapper.CLIENT_INFORM_OFFGAME:
                                int id2 = (int) data.getData();
                                if(inGameList.size()>0){
                                for(int i = 0; i<inGameList.size();i++){
                                    if(inGameList.get(i)==id2)
                                        inGameList.remove(i);
                                    }
                                }
                                if(inGameList.size()>0){
                                for(int i = 0; i<inGameList.size();i++){
                                    if(inGameList.get(i)==id2)
                                        inGameList.remove(i);
                                    }
                                }
                                publicInGamePlayer();   
                                break;
                                
                        case ObjectWrapper.MESSAGE_PLAYER:
                            Message ms = (Message) data.getData();
                            messagePlayer(ms);
                            break;
                    }
                }
                    //ois.reset();
                    //oos.reset();
                }
            }catch (EOFException | SocketException e) {             
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
               
                //publicClientNumber();
                try {
                    mySocket.close();
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}