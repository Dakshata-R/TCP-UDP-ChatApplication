import java.io.*; import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",5000);
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))){
                String m; while((m=in.readLine())!=null) System.out.println("TCP Msg: "+m);
            }catch(Exception e){}
        }).start();
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        String line; while((line=c.readLine())!=null) out.println(line);
    }
}
