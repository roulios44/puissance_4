import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args){
        try {
            SocketChannel clientSocket = SocketChannel.open();
            clientSocket.connect(new InetSocketAddress(("localhost"), 8000));
            String message = "Hello World from ur fucking fat ass";
            ByteBuffer bytes = ByteBuffer.wrap(message.getBytes("UTF-8"));
            while(bytes.hasRemaining()){
                clientSocket.write(bytes);
            }
            clientSocket.close();
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}