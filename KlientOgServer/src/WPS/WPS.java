package WPS;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class WPS {
    private Socket tcp;
    public static void main(String[] args)throws Exception{
        System.out.println("Starter server");

        int serverPort = 8000;
        DatagramSocket server = new DatagramSocket(serverPort);
        System.out.println("IP: " + InetAddress.getLocalHost().toString());
        System.out.println("Port: " + serverPort);
    try {
        while (true) {
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            //Lager plass for motatt datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            //Mottar datagram
            server.receive(receivePacket);

            String motatt = new String(receivePacket.getData());
            System.out.println("Received: " + motatt);

            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("Packet motatt fra: " + IPAddress.getHostAddress());

            int port = receivePacket.getPort();

            Socket tcp = new Socket(motatt, 80);

            DataOutputStream tilServer = new DataOutputStream(tcp.getOutputStream());
            BufferedReader fraServer = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
            tilServer.writeBytes("GET / HTTP/1.1\r\n");
            //tilServer.writeBytes("HEAD / HTTP/1.1\r\n");
            tilServer.writeBytes("Host: " + motatt + "\r\n");

            String answer = "";
            answer = fraServer.readLine();
            System.out.println(answer);

            sendData = answer.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            server.send(sendPacket);

            tcp.close();
            fraServer.close();
        }
    }catch (BindException b){
        b.printStackTrace();
        System.out.println("The port is already in use.");
    }

    }
}
