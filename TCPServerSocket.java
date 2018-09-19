package TCP.ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerSocket {
    private ServerSocket server;
    
    public TCPServerSocket(int port) throws Exception {
        this.server = new ServerSocket(port);
    }
    
    private void listen() throws Exception {
        String data = null;
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);
        
        BufferedReader in = new BufferedReader( new InputStreamReader(client.getInputStream()));        
        data = in.readLine();
        System.out.println("\r\nMessage from " + clientAddress + ": " + data);
        
        String result = executeCommand(data);
        System.out.println(result);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        if (result != null) {
        	out.println(result);
        	out.flush();
            out.close();
        }
        out.println("ERROR in execution");
        out.flush();
        out.close();
    }
    
    public String executeCommand(String command) {
		if (command.equals("") | command.equals("Type here your command...")) {
			return null;
		}
		
		String[] cmdTemplate = {
				"/bin/sh",
				"-c",
				command
				};
		

		StringBuilder sb = new StringBuilder();
		String newText;
		try {
			Process process = Runtime.getRuntime().exec(cmdTemplate);
			// Process process = Runtime.getRuntime().exec("cmd /c" + command); FOR WINDOWS
	        BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
	         
	        while ((newText = reader.readLine()) != null){
	            sb.append(newText + "\n");
	        } 
	        return sb.toString();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
    public static void main(String[] args) throws Exception {
        TCPServerSocket app = new TCPServerSocket(Integer.parseInt(args[0]));
        System.out.println("\r\nRunning Server: " + 
                "Host=" + app.getSocketAddress().getHostAddress() + 
                " Port=" + app.getPort());
        
        while (true) {
        	app.listen();
        }
    }
}