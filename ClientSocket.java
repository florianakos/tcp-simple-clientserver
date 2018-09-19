package TCP.ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientSocket {
    private Socket socket;
    //private Scanner scanner;
    private ClientSocket(int serverPort) throws Exception {
        this.socket = new Socket("localhost", serverPort);
        //this.scanner = new Scanner(System.in);
    }
    private void transmit(String command) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(command);
        out.flush();
        
        String data = null;
        BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream())); 
        System.out.println("\r\nMessage from server ");
        while ( (data = in.readLine()) != null ) {
            System.out.println(data);
        }
        return;
    }
    
    public static void main(String[] args) throws Exception {
        ClientSocket client = new ClientSocket(Integer.parseInt(args[0]));
        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        
        client.transmit("cd /home/flrnks/ && ls -a");                
    }
}