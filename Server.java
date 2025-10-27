import java.io.*; import java.net.*; import java.util.*;

public class Server {
    static List<Socket> tcpClients = Collections.synchronizedList(new ArrayList<>());
    public static void main(String[] args) throws Exception {
        new Thread(() -> { // TCP
            try (ServerSocket ss = new ServerSocket(5000)) {
                while (true) {
                    Socket c = ss.accept(); tcpClients.add(c);
                    new Thread(() -> {
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                            String m; while ((m = in.readLine()) != null) {
                                synchronized(tcpClients){for(Socket s:tcpClients) if(s!=c) new PrintWriter(s.getOutputStream(),true).println(m);}
                                System.out.println("TCP: "+m);
                            }
                        } catch(Exception e){} finally{tcpClients.remove(c);}
                    }).start();
                }
            } catch(Exception e){e.printStackTrace();}
        }).start();

        DatagramSocket udp = new DatagramSocket(5001);
        byte[] buf = new byte[1024]; System.out.println("Server running TCP:5000 UDP:5001");
        while(true){
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            udp.receive(p);
            String msg = new String(p.getData(),0,p.getLength()); System.out.println("UDP: "+msg);
            udp.send(new DatagramPacket(msg.getBytes(),msg.length(),p.getAddress(),p.getPort()));
        }
    }
}
