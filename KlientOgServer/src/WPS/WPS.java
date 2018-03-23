package WPS;
import java.io.*;
import java.net.*;

public class WPS {
   // private Socket tcp;
    public static void main(String[] args)throws Exception{
        System.out.println("Start server:");

        int serverPort = 8000;

        DatagramSocket server = new DatagramSocket(serverPort);

        System.out.println("IP: " + InetAddress.getLocalHost().toString());
        System.out.println("Port: " + serverPort);

        Socket tcp = new Socket("www.vg.no", 80);
        while (true) {

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            //Lager plass for motatt datagram
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            //Mottar datagram: Får error her som sier "Socket closed".
            server.receive(receivePacket);

            //Melding motatt, oversetter til IP adresse
            String motatt = new String(receivePacket.getData());

            //Henter avsenderens IP-adresse og portnr. fra receivePacket.
            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("Packet motatt fra: " + IPAddress.getHostName());
            int port = receivePacket.getPort();

            System.out.println("Received: " + motatt);

            DataOutputStream tilWebServer = new DataOutputStream(tcp.getOutputStream());
            BufferedReader fraWebServer = new BufferedReader(new InputStreamReader(tcp.getInputStream()));

            tilWebServer.writeBytes("HEAD /index.html HTTP/1.1\r\n");
            tilWebServer.writeBytes("Host: " + motatt.trim() + "\r\n");
            tilWebServer.writeBytes("\r\n");
            tilWebServer.flush();

            //Sender svar til klient
            String svar = "";
            //svar = fraWebServer.readLine();
            //System.out.println(svar);
            StringBuilder strbld = new StringBuilder();
            String r = fraWebServer.readLine();
            while(r!=null){
                strbld.append(r + "\n");
                r = fraWebServer.readLine();
            }
            svar = strbld.toString();

            sendData = svar.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            server.send(sendPacket);

            tcp.close();
            server.close();

        }


    }
}
