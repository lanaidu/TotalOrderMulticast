import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;




public class myNode {
	public ServerSocket serverSock;
	
	
	public myNode(){
		try {
			serverSock=new ServerSocket(4000);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		new Thread(new myServer(serverSock)).start();
	}
	static int clock;
	static boolean triggered=false; 
	public static void main(String[] args) {

		new myNode();
		String [] trial= ManagementFactory.getRuntimeMXBean().getName().split("@");
		System.out.println("whoAmI?"+trial[1]);
		String whoAmI=trial[1];
		String infoFromFile;
		boolean found=false;
		
		int i=0;
		InetAddress host[]= new InetAddress[10];
		BufferedReader console;
		BufferedReader br;
		BufferedReader read[]= new BufferedReader[10];
		PrintWriter write[]= new PrintWriter[10];
		console = new BufferedReader(new InputStreamReader(System.in));
		//Properties prop = new Properties();
		Socket[] socket=new Socket[10];
		String fromServer;
		
		try {
			br = new BufferedReader(new FileReader("/home/004/l/lj/ljn130030/Lanaidu/testing.txt"));
				//	"C:\\Users\\Lakshmi\\workspace\\project\\Demo\\src\\testing.txt")); /home/004/l/lj/ljn130030/Lanaidu/testing.txt
			System.out.println("Information from the files: Destination processes and message");
		while ((infoFromFile = br.readLine()) != null) {
			System.out.println(infoFromFile);
			String [] fromFile=infoFromFile.split(":");
			System.out.println(fromFile[0]);
			if(fromFile[0]!=null){
			if(fromFile[0].contains(whoAmI)){
				found=true;
				break;
			}}
			
		}
		if(found==true){
			String [] fromFile=infoFromFile.split(":");
			String message=fromFile[1];
			System.out.println(message);
			
			int sendCount=0;			
			boolean done=false;
			ArrayList<Integer> clocks=new ArrayList<Integer>();
			while(true){
				if(console.readLine()!=null){
					break;
				}
				
			}
			while(!done){
				i=0;
				//m_send
			for(int j=2;j<fromFile.length;j++){
				if(fromFile[j]!=null||fromFile[j]!=""){
					host[i]=InetAddress.getByName(fromFile[j]);
					socket[i]=new Socket(host[i].getHostName(),4000);
					write[i]=new PrintWriter(socket[i].getOutputStream(), true);
					read[i]=new BufferedReader(new InputStreamReader(socket[i].getInputStream()));
					clock++;
					write[i].println(message+":"+clock);
					sendCount++;
					i++;
					
				}
				done=true;
			}
			while(sendCount>0)
			{
				i=0;
				
				for(int j=2;j<fromFile.length;j++){
					
					fromServer=read[i].readLine();
					//System.out.println("Inside for"+i);
					i++;
					if(fromServer!=null){
						clock++;
						System.out.println("from server"+fromServer);
						String [] timestamp=fromServer.split(":");
						//System.out.println("Message received from :"+socket[i].getInetAddress()+timestamp[1]+timestamp[0]);
						clocks.add(new Integer(timestamp[1]));
						sendCount--;
						
					}
					
					
				}
			}
			
			Collections.sort(clocks);
			for(int k=0;k<clocks.size();k++)
				System.out.println(clocks.get(k));
			System.out.println("the highest timestamp is:"+clocks.get(clocks.size()-1)+"Sending the same to servers!!");
			i=0;
			for(int j=2;j<fromFile.length;j++){
				
				if(fromFile[i]!=null||fromFile[i]!=""){
					write[i].println(message+":"+clocks.get(clocks.size()-1));
					i++;
				}
			}
			
			}
		
		

	
		
		}else{
			System.out.println("Info regarding this node is missing in the file!!!!");
		}
				} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}finally{
			//try{
				
				/*for(i=0;i<4;i++){
					read[i].close();
					write[i].close();
					socket[i].close();
				}
				//socket0.close();
				//socket1.close();
				//socket2.close();
				//socket3.close();
				
			} catch (Exception e) {
				System.out.println("Io exception!");
				e.printStackTrace();
			}*/
		}


	}
}
