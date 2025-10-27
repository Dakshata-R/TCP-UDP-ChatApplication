import java.io.*; import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("localhost");
        new Thread(() -> {
            byte[] buf = new byte[1024];
            DatagramPacket p = new DatagramPacket(buf,buf.length);
            try { while(true){ s.receive(p); System.out.println("UDP Msg: "+new String(p.getData(),0,p.getLength())); } } catch(Exception e){}
        }).start();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line; while((line=in.readLine())!=null){
            byte[] buf = line.getBytes();
            s.send(new DatagramPacket(buf,buf.length,addr,5001));
        }
    }
}
