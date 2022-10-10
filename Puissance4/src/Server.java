import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.io.IOException;
import java.net.InetSocketAddress;
public class Server{
    public static void main(String[] args){
        try{
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(8000));
            while(true){
                SocketChannel clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                Listen(clientSocket);
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error into server open :" + e.toString());
        }
        
    }
    private static void Listen(SocketChannel clientSocket)throws IOException{
        ByteBuffer bytes = ByteBuffer.allocate(1024);
        int BytesRead = clientSocket.read(bytes);
        if (BytesRead <= 0){
            clientSocket.close();
            return;
        }
        String message = new String(bytes.array(), "UTF-8");
        bytes.clear();
        System.out.println(message);
    }
}