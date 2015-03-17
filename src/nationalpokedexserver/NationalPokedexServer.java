package nationalpokedexserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;

/**
 *
 * @author Architect
 */
public class NationalPokedexServer {
    
    public static void main(String[] args) {
        
        HashMap pokemonData = new HashMap();
        HashMap pokemonInfo;
        ArrayList<HashMap> pokemonList = new ArrayList<>();
        GetPokemonInfo getInfo = new GetPokemonInfo();
        Integer portNumber = 4444;
        boolean listening = true;
        
        System.out.println("Server running.  Fetching pokemon data\n");
    
        for (int i = 1; i <= 718; i++) {
            
            try {
                
                URL pokemonURL = new URL("http://pokeapi.co/api/v1/pokemon/" + i + "/");
                JSONInputStream inFromURL = new JSONInputStream(pokemonURL.openStream());
                pokemonData = (HashMap)inFromURL.readObject();
                
            }
            
            catch (IOException | JSONException e) {
                
                e.printStackTrace();
                
            }
            
            pokemonInfo = new HashMap();
                
                pokemonInfo.put("Number", getInfo.getPokemonNumber(pokemonData));
                pokemonInfo.put("Name", getInfo.getPokemonName(pokemonData));
                pokemonInfo.put("Type", getInfo.getPokemonType(pokemonData));
                pokemonInfo.put("Height", getInfo.getPokemonHeight(pokemonData));
                pokemonInfo.put("Weight", getInfo.getPokemonWeight(pokemonData));
                pokemonInfo.put("Description", getInfo.getPokemonDescription(pokemonData));
                pokemonInfo.put("Sprite", getInfo.getPokemonSpriteURL(pokemonData));
                
                pokemonList.add(pokemonInfo);
                
                System.out.println("Fetched info for pokemon # " + getInfo.getPokemonNumber(pokemonData) + " out of 718");
            
        }
        
        System.out.println("\nDone fetching data.  Waiting for client connections");
        
        Executor clientConnect = Executors.newCachedThreadPool();
        SendPokemonData sendData;
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            
            while (listening) {
                
                sendData = new SendPokemonData(serverSocket.accept(), portNumber, pokemonList);
                
                clientConnect.execute(sendData);
                
            }
            
        }
        
        catch (IOException e) {
            
            e.printStackTrace();
            
        }
        
    }
    
    public void run() {
        
        
        
    }
    
}
