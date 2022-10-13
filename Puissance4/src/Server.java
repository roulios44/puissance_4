import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Server {

    private ArrayList<SocketChannel> allClients = new ArrayList<SocketChannel>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int numberOfPlayer = 0;

    public static void main(String[] args){
        Server server = new Server();
        server.launch();
    }
    
    private void launch(){
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(4004));
            while(true){
                SocketChannel clientSocket = serverSocket.accept();
                System.out.println("client connected");
                players.add(new Player("Player" + numberOfPlayer+1, PlayerSymbole.values()[numberOfPlayer].toString()));
                allClients.add(clientSocket);
                System.out.println(players.get(numberOfPlayer).symbole);
                numberOfPlayer++;
                for (SocketChannel client : allClients){
                    Listen(client);
                }
                System.out.println(allClients.size());
            }
        }catch (IOException e){
            System.err.println(e.toString());
            System.err.println("Server stopped due to unexpected exception");
        }
    }

    private void Listen(SocketChannel socket) throws IOException{
        ByteBuffer bytes = ByteBuffer.allocate(1024);
        bytes.clear();
        try {
            int bytesRead = socket.read(bytes);
            if(bytesRead <= 0){
                socket.close();
                return;
            }
            String message = new String(bytes.array(),"UTF-16");
            System.out.println(message);
            }catch (IOException e){
            socket.close();
            return;
        } 
    }
}
