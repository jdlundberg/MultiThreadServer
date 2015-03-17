package nationalpokedexserver;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONOutputStream;

/**
 *
 * @author Architect
 */
public class SendPokemonData  implements Runnable {
    
    private Socket socket = null;
    private Integer portNumber = 0;
    ArrayList<HashMap> pokemonList = new ArrayList<>();
   
    public SendPokemonData(Socket socket, Integer portNumber, ArrayList<HashMap> pokemonList) {
        
        this.socket = socket;
        this.portNumber = portNumber;
        this.pokemonList = pokemonList;
        
    }
    
    @Override
    public void run() {
        
        System.out.println("New thread started.  Connected to " + socket.getInetAddress());
        
        try {
            
            JSONOutputStream sendData = new JSONOutputStream(socket.getOutputStream());
            
            sendData.writeObject(pokemonList);
            
            socket.close();
            
        }
        
        catch (IOException | JSONException e) {
            
            e.printStackTrace();
            
        }
        
    }
    
}
