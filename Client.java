import java.net.Socket;
import java.io.*;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try{
            System.out.println("senf=ding request to server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection done");

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new PrintWriter(socket.getOutputStream()); 

            startReading();
            startWriting();


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void startReading(){
        // thread -- read karke det rahega ye 
        Runnable r1=()->{
            System.out.println("reader started ......");
            
            try{
            while(true){
                
                String msg=br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat ");
                    socket.close();
                    break;
                }
                System.out.println("Server: "+msg);
            
            }}catch(Exception e){
                // e.getStackTrace();
            System.out.println("Connection is closed !!");

            }

        };
        new Thread(r1).start();

    }

    public void startWriting(){
        // data user lega and send client tak karega
        Runnable r2=()->{
            System.out.println("writer satrted");
            try{
            while(!socket.isClosed()){
                

                    BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
                    String content= br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

                
            }
            System.out.println("Connection is closed !!");
        }catch(Exception e){
                // e.getStackTrace();
            System.out.println("Connection is closed !!");


            }

        };
        new Thread(r2).start();

    }
    public static void main(String[] args) {
        System.out.println("this is client bro!!!");
        new Client();
    }
    
}
