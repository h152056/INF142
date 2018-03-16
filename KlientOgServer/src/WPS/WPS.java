package WPS;
import java.io.*;
import java.net.*;


public class WPS {
    public static void main(String[] args)throws Exception{
        //Lager datagramsocket på port 5000
        DatagramSocket serverSocket = new DatagramSocket(5000);

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while (true){
            //Lager plass for motatt datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            //Motta datagram
            serverSocket.receive(receivePacket);

            String line = new String(receivePacket.getData());

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedLine = line.toUpperCase();
            System.out.println("Packet mottatt fra:" + IPAddress.getHostName());
            System.out.println("Data motatt:" + line);
            sendData = capitalizedLine.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}
