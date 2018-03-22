package Klient;
import java.net.*;
import java.io.*;

public class Klient {
    /*
    Kommunisere med WPS ved hjelp av UDP/datagramsocketer

    Sende headerinformasjon med http, angitt ved en tekststreng, til WPS.

    Skal beskrive et filnavn/stinavn som skal hentes på WPS, dersom klient ikke
    oppgir stinavn skal WPS bruke "/" som default stinavn.

    Klient mottar svar fra WPS, evt. feilmelding.
    */

    //private Socket socket = null;
    public static void main(String[] args)throws Exception {
        //Lager socket
        DatagramSocket klientSocket = new DatagramSocket();

        //Oversetter servernavn til IP adresse ved bruk av DNS
        InetAddress IPAddress = InetAddress.getByName("");

        //Port til server
        int serverPort = 8000;

        //Lager inputtstrøm
        BufferedReader brukerInput = new BufferedReader(new InputStreamReader(System.in));
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

       // String stinavn = new URL("").getPath(); henter stinavn

        String line = brukerInput.readLine();
        sendData = line.getBytes();

        //Lager datagram og sender til server
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 80);
        klientSocket.send(sendPacket);

        //Oppretter datagram som kan motta fra server, og mottar datagram
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        klientSocket.receive(receivePacket);

        //Oppretter string for motatt svar.
        String svar = new String(receivePacket.getData());

        //Printer melding fra server og lukker socket.
        System.out.println("FROM SERVER:" + svar);
        klientSocket.close();


    }
}
