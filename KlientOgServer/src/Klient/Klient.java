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

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    public Klient (String addresse, int port){
        try{
            socket  = new Socket(addresse, port);
            System.out.println("Tilkoblet");

            input = new DataInputStream(System.in);

            out = new DataOutputStream(socket.getOutputStream());

         //Error dersom portnummer i bruk allerede.
        }catch (BindException e){
            System.err.print(e);
            //Retry? close?

        }catch (IOException i){
            System.err.println(i);
        }

        String line = "";
        while(!line.equals("over")){
            try{
                line = input.readUTF();
                out.writeUTF(line);

            }catch(IOException i){
                System.err.println(i);
            }
        }
        try{
            input.close();
            out.close();
            socket.close();

        }catch(IOException i){
            System.err.println(i);
        }
    }

    public static void main(String[] args) throws Exception{
        Klient klient = new Klient("158.37.233.27", 5000);

    }
}
