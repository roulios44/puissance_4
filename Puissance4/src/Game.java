import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game{
    protected Integer numberOfPlayers;
    protected Grid grid;
    protected ArrayList<Player> allPlayers = new ArrayList<Player>();
    public void menu()throws Exception{
        System.out.println("Hello Welcome to our Power 4 game !");
        Integer choice = 0;
        choice = Integer.parseInt(AskInfo("1) Start Game \n5) Leave"));
        switch(choice){
            case 1:
                classicGame();
                break;
            case 5:
                System.out.print("Bye, see you soon !");
                break;
            default:
                menu();
        }
    }
    private void classicGame(){
        numberOfPlayers = 2;
        grid = new Grid(6,8);
        generatePlayers();
    }
    private void generatePlayers(){
        try{
            for (int i=0;i<numberOfPlayers;i++){
                allPlayers.add(new Player(AskInfo("Player " + (i+1) + " name"), AskInfo("Player "+(i+1)+" Symbol"))) ;
            }
        } catch (Error e){
            System.out.println("Error creating players "+ e);
        }
    }
    public static String AskInfo(String inputSentence){
        try{
            java.io.InputStreamReader byteInfo = new InputStreamReader(System.in);
            java.io.BufferedReader StringInfo = new BufferedReader(byteInfo);
            System.out.println(inputSentence);
            try{
                return StringInfo.readLine();
            } catch (IOException e){
                return ("AskInfo:   Error IOExpection " + e);
            }
        }
        catch(Error e){
            return("Error into AskInfo func: " + e);
        }
    }
}
