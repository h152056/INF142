package WPS;
import java.io.*;
import java.net.*;


public class WPS {
    public static void main(String[] args)throws Exception{
        System.out.println("Starter server");
//------UDP-mottar fra klient.------------------------------------------------------------------------------------------
        DatagramSocket serverSocket = new DatagramSocket(80);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        int port = receivePacket.getPort();
        InetAddress IPAddress = receivePacket.getAddress();
//------------------------------------------------------------------------------------------------
        //Lager TCP Socket
        String url = new String(receivePacket.getData());

        Socket tcp = new Socket(url, 80);

        System.out.println(tcp.getInetAddress().getHostName());
        System.out.println();
        System.out.println(tcp.getInetAddress().getHostAddress());


        DataOutputStream out = new DataOutputStream(tcp.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));

        out.writeBytes("GET / HTTP/1.1\r\n");
        out.writeBytes("User-Agent: Firefox/3.6.10\r\n");
        out.writeBytes("Accept: text/html,application/xhtml+xml\r\n");
        out.writeBytes("Accept-Language: en-us,en;q=0.5\r\n");
        out.writeBytes("Accept-Encoding: gzip,deflate\r\n");
        out.writeBytes("Accept-Charset: ISO-8859-1,utf-8;q=0.7\r\n");
        out.writeBytes("Keep-Alive: 115\r\n");
        out.writeBytes("Connection: keep-alive\r\n");
        out.writeBytes("\r\n");

        StringBuilder respons = new StringBuilder();
        String s = in.readLine();
        while(s != null){
            respons.append(s + "\n");
            s = in.readLine();

        }
        System.out.println(respons);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        tcp.close();
       /* while (true){
            //Lager plass for motatt datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            //Mottar datagram
            serverSocket.receive(receivePacket);

            String line = new String(receivePacket.getData());

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
//----------------------------------------------------------------------------------------------
            //Modifiserer stringen som ble mottatt,
            //Endre til å hente headerinfo+path!
            String capitalizedLine = line.toUpperCase();
            System.out.println("Packet mottatt fra:" + IPAddress.getHostName());
            System.out.println("Data motatt: " + line);
            sendData = capitalizedLine.getBytes();
//-----------------------------------------------------------------------------------------------
            //Sender
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    */

    }
}
