import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;



public class myServer implements Runnable{
		
	ServerSocket serverSock;
	public  LinkedHashMap<String, Integer> buffer=new LinkedHashMap<String, Integer>();
	public myServer(ServerSocket socket){
		serverSock=socket;
		
		
	}
public void run(){
	boolean isConnected=true;
	while(isConnected){
		try {
			new Thread(new innerClientConnection(serverSock.accept())).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	try {
		serverSock.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public class innerClientConnection implements Runnable{

	public Socket socket=null;
	public boolean isConnected=true;
	String clientSays;
	String serverSays;
	BufferedReader read;
	PrintWriter write;
	boolean visited=false;
	public innerClientConnection(Socket socket){
		this.socket=socket;
		//clientCount++;
		
	}
 public void run(){
	 System.out.println("Client connected to the server :"+socket.getInetAddress().getHostAddress());
	 
	 try {
	
		  read =new BufferedReader(new InputStreamReader(socket.getInputStream()));
		  write= new PrintWriter(socket.getOutputStream(),true);
		  //write.println("Welcome home, you are successfully connected");
		 while(isConnected){
			 
			 clientSays=read.readLine();
			 if(clientSays!=null){
			 System.out.println("The client says:"+clientSays);
			 myNode.clock++;
			 serverSays=m_receive(clientSays);
			 if(serverSays!=null && !serverSays.equals("")){
				 write.println(serverSays);
			 }
			 }

				System.out.println("Messages that have been received in this process:");
				System.out.println(buffer.keySet());
				System.out.println(buffer.values());
			 
					 }
		 
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {
			write.close();
			read.close();
			socket.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
	}
 }
 
 public String m_receive(String clientString){
	 String [] message =clientString.split(":");
	 System.out.println("Visited"+visited);
	 if(!visited){
		 visited=true;
		 int senderClock=Integer.parseInt(message[1]);
		if(senderClock<myNode.clock) 
		 return message[0]+":"+senderClock;
		else
			return message[0]+":"+myNode.clock;
	 }
	 else{
		 System.out.println("Final message with timestamp: "+message[0]+":"+message[1]);
		 buffer.put(message[0], Integer.parseInt(message[1]));
		 return "";
	 }
	 
	 
	 
 }
 
	

}
}
