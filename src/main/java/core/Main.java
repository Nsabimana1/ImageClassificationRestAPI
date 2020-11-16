package core;

import networking.Communication;
import networking.Server;
import networking.ServerListener;
import networking.Utilities;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String val = "good";
        String encripted = Base64.getEncoder().encodeToString(val.getBytes());
        System.out.println(encripted);
        System.out.println("the decoded message is");
        System.out.println(new String (Base64.getDecoder().decode(encripted)));


        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);

    }
    public Main(){
    }

    public void sendMove(final String hostIpAddress, final int portNumber, final String messege){
//        Log.e("StringParsing", "Sending " + messege);
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("about to open");
                    Socket target = new Socket(hostIpAddress, portNumber);
                    System.out.println("about to communicate");
                    Communication.sendOver(target, messege);
                    System.out.println("about to receive");
                    displayReceivedMessage(Communication.receive(target));
                    System.out.println("should be received");
                    target.close();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void displayReceivedMessage(String message){
        System.out.println(message);
    }

    public void setupServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().addListener(new ServerListener() {
                        @Override
                        public void notifyMessage(String msg) {
                            displayReceivedMessage(msg);
                        }
                    });
                } catch (IOException e) {
                    System.out.println("Could not start server");
                }
            }
        }).start();
    }


//    public static void main(String[] args) throws IOException {
//        String connectedIpAddress = Utilities.getLocalIpAddress();
//        String homeIpAddress = Utilities.getLocalIpAddress();
//
//        Main m = new Main();
//        m.setupServer();
//
//        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//        System.out.println("Hello");
//        System.out.println("the local ip address is " + homeIpAddress);
//        boolean isComplete = true;
//
//        System.out.println("Enter username");
//        String userName = myObj.nextLine();  // Read user input
//        m.sendMove(connectedIpAddress, Server.APP_PORT, userName );
//
//
//    }
}
