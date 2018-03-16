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
        InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

        //Lager inputtstrøm
        BufferedReader inFraBruker = new BufferedReader(new InputStreamReader(System.in));

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String line = inFraBruker.readLine();

        sendData = line.getBytes();
        //Lager datagram (data, lengde, IP adr, port
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);
        //Sender datagram til server
        klientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        //Les datagram fra server
        klientSocket.receive(receivePacket);

        String modifisertSetning = new String(receivePacket.getData());

        System.out.println("FROM SERVER:" + modifisertSetning);
        klientSocket.close();


    }
}
