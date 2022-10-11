import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args){
        try {
            SocketChannel Socket = SocketChannel.open();
            Socket.connect(new InetSocketAddress("localhost", 8000));
            
            ByteBuffer bytes = ByteBuffer.wrap(message.getBytes("UTF-8"));
            while(bytes.hasRemaining()){
                Socket.write(bytes);
            }
            Socket.close();
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}

