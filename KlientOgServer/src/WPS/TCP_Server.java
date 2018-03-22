package WPS;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class TCP_Server {
    public static void main(String[] args)throws Exception{

        Socket tcpSocket = new Socket(url, 80);
        System.out.println(tcpSocket.getInetAddress().getHostAddress());
        System.out.println(tcpSocket.getInetAddress().getHostName());

        DataOutputStream ut = new DataOutputStream(tcpSocket.getOutputStream());
        BufferedReader inn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

        ut.writeBytes("HEAD / HTTP/1.1\r\n");
        ut.writeBytes("Host: " + url + "\r\n");
        ut.writeBytes("User-Agent: Firefox/3.6.10\r\n");
        ut.writeBytes("Accept: text/html,application/xhtml+xml\r\n");
        ut.writeBytes("Accept-Language: en-us,en;q=0.5\r\n");
        ut.writeBytes("Accept-Encoding: gzip,deflate\r\n");
        ut.writeBytes("Accept-Charset: ISO-8859-1,utf-8;q=0.7\r\n");
        ut.writeBytes("Keep-Alive: 115\r\n");
        ut.writeBytes("Connection: keep-alive\r\n");
        ut.writeBytes("\r\n");

        StringBuilder svar = new StringBuilder();
        String t = inn.readLine();
        while(t != null){
            svar.append(t + "\n");
            t = inn.readLine();
        }
        inn.close();
        System.out.println(svar);
        ut.close();
        tcpSocket.close();
    }
}
