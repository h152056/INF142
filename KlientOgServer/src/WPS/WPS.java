package WPS;
import java.io.*;
import java.net.*;

public class WPS {
    private Socket tcp;
    public static void main(String[] args)throws Exception{
        System.out.println("Start server:");

        int serverPort = 8000;

        DatagramSocket server = new DatagramSocket(serverPort);

        System.out.println("IP: " + InetAddress.getLocalHost().toString());
        System.out.println("Port: " + serverPort);

        while (true) {
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            //Lager plass for motatt datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            //Mottar datagram
            server.receive(receivePacket);

            //Melding motatt, oversetter til IP adresse
            // String motatt = new String(receivePacket.getData());
            String motatt = "www.vg.no";
            System.out.println("Received: " + motatt);

            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("Packet motatt fra: " + IPAddress.getHostAddress());

            int port = receivePacket.getPort();
            Socket tcp = new Socket(motatt, 80);

//            System.out.println(tcp.getInetAddress().getHostName());
//            System.out.println(tcp.getInetAddress().getHostAddress());

            DataOutputStream tilWebServer = new DataOutputStream(tcp.getOutputStream());
            BufferedReader fraWebServer = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
            tilWebServer.writeBytes("HEAD / HTTP/1.1\r\n");
            tilWebServer.writeBytes("Host: " + motatt + "\r\n");
            tilWebServer.writeBytes("\r\n");
            

            Socket tcpSocket = new Socket(motatt, 80);
            DataOutputStream ut = new DataOutputStream(tcpSocket.getOutputStream());
            BufferedReader inn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            ut.writeBytes("HEAD / HttP/1.1\r\n");
            ut.writeBytes("Host: " + motatt.split(motatt, motatt.length()) + "\r\n");
            ut.writeBytes("\r\n");


            //Sender svar til klient
            String answer = "";
            answer = fraWebServer.readLine();
            System.out.println(answer);

            sendData = answer.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            server.send(sendPacket);


            tcp.close();
            //fraServer.close();
        }

    }
}
