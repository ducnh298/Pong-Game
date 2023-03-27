package control;
 
import dao.ClanDAO;
import dao.ClanRequestDAO;
import dao.FriendDAO;
import dao.FriendRequestDAO;
import dao.MatchDAO;
import dao.PlayerDAO;
import dao.PlayerMatchDAO;
import dao.ResultDAO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
 
import dao.UserDAO;
import java.util.ArrayList;
import model.Clan;
import model.IPAddress;
import model.Match;
import model.ObjectWrapper;
import model.User;
import model.Player;
import model.Result;
import model.Friend;
import model.FriendRequest;
import model.PlayerMatch;
import view.UDPServerMainFrm;
 
 
public class UDPServerCtr {
    private UDPServerMainFrm view;
    private DatagramSocket myServer;    
    private IPAddress myAddress = new IPAddress("localhost", 8888); 
    private UDPListening myListening;
     
    public UDPServerCtr(UDPServerMainFrm view){
        this.view = view;       
    }
     
    public UDPServerCtr(UDPServerMainFrm view, int port){
        this.view = view;
        myAddress.setPort(port);
    }
     
     
    public boolean open(){
        try {
            myServer = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            myListening = new UDPListening();
            myListening.start();
            view.showMessage("UDP server is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
            return false;
        }
        return true;
    }
     
    public boolean close(){
        try {
            myListening.stop();
            myServer.close();
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    }
     
    class UDPListening extends Thread{
        public UDPListening() {
             
        }
         
        public void run() {
            while(true) {               
                try {   
                    //prepare the buffer and fetch the received data into the buffer
                    byte[] receiveData = new byte[2048];
                    DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
                    myServer.receive(receivePacket);
                     
                    //read incoming data from the buffer 
                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ObjectWrapper receivedData = (ObjectWrapper)ois.readObject();
                     
                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();
                    switch(receivedData.getPerformative()) {
                    case ObjectWrapper.LOGIN_USER:              // login
                        User user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_LOGIN_USER);
                        UserDAO ud= new UserDAO();
                        int id= ud.CheckLogin(user);
                        if(id!=0)
                            resultData.setData(id);
                        else
                            resultData.setData(0);
                        break;
                    case ObjectWrapper.REGISTER_USER:
                        resultData.setPerformative(ObjectWrapper.REPLY_REGISTER_USER);
                            User us1 = (User)receivedData.getData();
                            UserDAO usd = new UserDAO();
                            PlayerDAO pld = new PlayerDAO();
                            int id1 = usd.Register(us1);
                           if(id1!=0){
                                if(pld.RegisterPlayer(id1)){
                                    resultData.setData("ok");    
                                }
                                else
                                    resultData.setData("false");
                           }
                            break;
                    case ObjectWrapper.PLAYER_LIST:
                        PlayerDAO pd = new PlayerDAO();
                        ArrayList<Player> list = pd.getPlayerList();
                        resultData.setPerformative(ObjectWrapper.REPLY_PLAYER_LIST);
                        if(list.size()>0){
                            resultData.setData(list);
                        }
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.RANKING:
                        String key=(String) receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_RANKING);
                        PlayerDAO pld1=new PlayerDAO();
                        ArrayList<Player> resultList = pld1.getRank(key);
                        if(resultList!=null){
                            resultData.setData(resultList);
                        }else
                            resultData.setData("false");
                        
                        break;
                        
                    case ObjectWrapper.FRIEND_LIST:
                        int id2= (int) receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_FRIEND_LIST);
                        FriendDAO fd=new FriendDAO();
                        Friend resultList2= fd.getFriendList(id2);
                         if(resultList2!=null){
                            resultData.setData(resultList2);
                        }else
                            resultData.setData("false");
                        break;
                        
                    case ObjectWrapper.FRIEND_REQUEST_LIST:
                        int id3 = (int) receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_FRIEND_REQUEST_LIST);
                        FriendRequestDAO frd=new FriendRequestDAO();
                        FriendRequest resultList1 = frd.getFriendRequest(id3);
                        if(resultList1!=null){
                            resultData.setData(resultList1);
                        }else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.RESPONSE_FRIEND_REQUEST:
                                ArrayList<Integer> response = (ArrayList<Integer>) receivedData.getData();
                                resultData.setPerformative(ObjectWrapper.REPLY_RESPONSE_FRIEND_REQUEST);
                                
                                FriendRequestDAO frd1 = new FriendRequestDAO();
                                if(response.get(2)==1){
                                    FriendDAO fd1= new FriendDAO();
                                    if(fd1.addFriend(response.get(0),response.get(1))){
                                        resultData.setData("ok");
                                        frd1.deleteFriendRequest(response);
                                    }
                                    else
                                        resultData.setData("false");
                                }else if(response.get(2)==0){
                                    if(frd1.deleteFriendRequest(response))
                                        resultData.setData("ok");
                                    else
                                        resultData.setData("false");
                                }   
                            break;     
                    case ObjectWrapper.REQUEST_FRIEND:
                        resultData.setPerformative(ObjectWrapper.REPLY_REQUEST_FRIEND);
                                 if(receivedData.getData() instanceof ArrayList<?>){
                                     ArrayList<Player> request = (ArrayList<Player>) receivedData.getData();
                                     FriendRequestDAO frd2=new FriendRequestDAO();
                                     int result = frd2.addFriendRequest(request);
                                 if(result!=0){
                                        resultData.setData(result); 
                                     }
                                     else
                                        resultData.setData("false"); 
                                 }
                                 break;
                    case ObjectWrapper.UNFRIEND:
                        resultData.setPerformative(ObjectWrapper.REPLY_UNFRIEND);
                                 if(receivedData.getData() instanceof ArrayList<?>){
                                     ArrayList<Integer> unfriend = (ArrayList<Integer>) receivedData.getData();
                                     FriendDAO fd2=new FriendDAO();
                                     if(fd2.unFriend(unfriend.get(0), unfriend.get(1))){
                                         resultData.setData("ok"); 
                                     }else 
                                         resultData.setData("false"); 
                                 }
                                 break;
                    case ObjectWrapper.EDIT_PLAYER:                        
                        resultData.setPerformative(ObjectWrapper.REPLY_EDIT_PLAYER);
                                Player newPlayer = (Player) receivedData.getData();
                                PlayerDAO pld2 = new PlayerDAO();
                                if(pld2.editPlayer(newPlayer)){
                                    resultData.setData("ok");
                                }
                                else
                                    resultData.setData("false");  
                            break;    
                    case ObjectWrapper.CREATE_CLAN:
                        resultData.setPerformative(ObjectWrapper.REPLY_CREATE_CLAN);
                        Clan clan = (Clan) receivedData.getData();
                        ClanDAO cd = new ClanDAO();
                        Clan clanID= cd.createClan(clan);
                        if(clanID!=null){
                            resultData.setData(clanID); 
                        }
                        else
                            resultData.setData("false");  
                       break;
                    case ObjectWrapper.CLAN_LIST:
                        resultData.setPerformative(ObjectWrapper.REPLY_CLAN_LIST);
                        String key1 = (String) receivedData.getData();
                        ClanDAO cd1 = new ClanDAO();
                        ArrayList<Clan> clanList = cd1.getClanList(key1);
                        resultData.setData(clanList);
                        break;
                    case ObjectWrapper.JOIN_CLAN:
                        resultData.setPerformative(ObjectWrapper.REPLY_JOIN_CLAN);
                        Player pl= (Player) receivedData.getData();
                        PlayerDAO pld3 = new PlayerDAO();
                        if(pld3.setInClan(pl))
                            resultData.setData("ok");
                        else                        
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.EDIT_CLAN:
                        resultData.setPerformative(ObjectWrapper.REPLY_EDIT_CLAN);
                        Clan newClan =(Clan) receivedData.getData();
                        ClanDAO cd2 =new ClanDAO();
                        if(cd2.editClan(newClan))
                            resultData.setData("ok");
                        else                        
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.CHECK_PLAYER_NAME:
                        resultData.setPerformative(ObjectWrapper.REPLY_CHECK_PLAYER_NAME);
                        String newName = (String) receivedData.getData();
                        if(new PlayerDAO().checkName(newName))
                            resultData.setData("false");
                        else resultData.setData("ok");
                        break;
                    case ObjectWrapper.LEAVE_CLAN:
                        resultData.setPerformative(ObjectWrapper.REPLY_LEAVE_CLAN);
                        Player outPl = (Player) receivedData.getData();
                        PlayerDAO pld4 = new PlayerDAO();
                        if(pld4.setInClan(outPl))
                            resultData.setData("ok");
                        else                        
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.KICK_MEMBER:
                        resultData.setPerformative(ObjectWrapper.REPLY_KICK_MEMBER);
                        Player kickedPl = (Player) receivedData.getData();
                        PlayerDAO pld5 = new PlayerDAO();
                        if(pld5.setInClan(kickedPl))
                            resultData.setData("ok");
                        else                        
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.REQUEST_JOIN_CLAN:
                        resultData.setPerformative(ObjectWrapper.REPLY_REQUEST_JOIN_CLAN);
                            Player joinPlayer = (Player) receivedData.getData();
                               ClanRequestDAO crd=new ClanRequestDAO();
                                int result = crd.addJoinRequest(joinPlayer);
                                if(result!=0){
                                        resultData.setData(result); 
                                     }
                                else
                                        resultData.setData("false"); 
                                 
                                 break;
                    case ObjectWrapper.RESPONSE_REQUEST_JOIN_CLAN:
                                ArrayList<Integer> response1 = (ArrayList<Integer>) receivedData.getData();
                                resultData.setPerformative(ObjectWrapper.REPLY_RESPONSE_REQUEST_JOIN_CLAN);
                                ClanRequestDAO crd1=new ClanRequestDAO();
                                if(response1.get(2)==1){
                                    Player plc = new Player();
                                    plc.setId(response1.get(0));
                                    Clan clan1 = new Clan();
                                    clan1.setId(response1.get(1));
                                    plc.setInClan(clan1);
                                    PlayerDAO pdc=new PlayerDAO();
                                    if(pdc.setInClan(plc)){
                                        resultData.setData("ok");
                                        crd1.deleteJoinRequest(response1);
                                    }
                                    else
                                        resultData.setData("false");
                                }else if(response1.get(2)==0){
                                    crd1.deleteJoinRequest(response1);
                                }   
                            break; 
                    case ObjectWrapper.JOIN_REQUEST_LIST:
                        resultData.setPerformative(ObjectWrapper.REPLY_JOIN_REQUEST_LIST); 
                        int idClan = (int) receivedData.getData();
                        ClanRequestDAO crd2 = new ClanRequestDAO();
                        ArrayList<Integer> joinRequestList = crd2.getJoinRequest(idClan);
                        if(joinRequestList.size()>=0)
                            resultData.setData(joinRequestList);
                        else
                            resultData.setData("false");
                        break;   
                    case ObjectWrapper.GET_USER:
                        resultData.setPerformative(ObjectWrapper.REPLY_USER);
                        int idUs = (int) receivedData.getData();
                        UserDAO usd1 = new UserDAO();
                        User userG = usd1.getUser(idUs);
                        if(userG!=null)
                            resultData.setData(userG);
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.CHANGE_PASSWORD:
                        resultData.setPerformative(ObjectWrapper.REPLY_CHANGE_PASSWORD);
                        User userCP = (User) receivedData.getData();
                        UserDAO usd2 = new UserDAO();
                        if(usd2.ChangePassword(userCP))
                            resultData.setData("ok");
                        else
                            resultData.setData("false");
                        break; 
                    case ObjectWrapper.GET_PLAYER:
                        resultData.setPerformative(ObjectWrapper.REPLY_PLAYER);
                        int idpl = (int) receivedData.getData();
                        PlayerDAO pld6 = new PlayerDAO();
                        Player pl1 = pld6.getPlayer(idpl);
                        if(pl1!=null)
                            resultData.setData(pl1);
                        else
                            resultData.setData("false");
                        break;  
                        
                    case ObjectWrapper.NEW_MATCH:
                        resultData.setPerformative(ObjectWrapper.REPLY_MATCH);
                        Match match = (Match) receivedData.getData();
                        MatchDAO md = new MatchDAO();
                        Match newMatch = md.createMatch(match);
                        if(newMatch!=null)
                            resultData.setData(newMatch);
                        else
                            resultData.setData("false");
                        break; 
                    case ObjectWrapper.NEW_PLAYER_MATCH:
                        resultData.setPerformative(ObjectWrapper.REPLY_PLAYER_MATCH);
                        PlayerMatch pm = (PlayerMatch) receivedData.getData();
                        PlayerMatchDAO pmd = new PlayerMatchDAO();
                        PlayerMatch pmr = pmd.addPlayerMatch(pm);
                        if(pmr!=null)
                            resultData.setData(pmr);
                        else
                            resultData.setData("false");
                        break;     
                    
                     case ObjectWrapper.RESULT_MATCH:
                        resultData.setPerformative(ObjectWrapper.REPLY_RESULT_MATCH);
                        Result resultMatch = (Result) receivedData.getData();
                         ResultDAO rd = new ResultDAO();
                         
                        if(rd.addResult(resultMatch))
                        {
                            Player resultPlayer = new Player();
                            resultPlayer.setId(resultMatch.getPlayer().getId());
                            if(resultMatch.getIsWin()==1){
                                resultPlayer.setMatchWon(1);
                            }
                            else
                                resultPlayer.setMatchPlayed(0);
                            PlayerDAO pldr = new PlayerDAO();
                            if(pldr.updateResultPlayer(resultPlayer))
                                resultData.setData("ok");
                        }
                        else
                            resultData.setData("false");
                        break;  
                    
                    }
 
                     
                    //prepare the buffer and write the data to send into the buffer
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(resultData);
                    oos.flush();            
                     
                    //create data package and send
                    byte[] sendData = baos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    myServer.send(sendPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessage("Error when processing an incoming package");
                }    
            }
        }
    }
}