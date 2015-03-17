package nationalpokedexserver;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;

/**
 *
 * @author Architect
 */
public class GetPokemonInfo {
    
    public String getPokemonNumber(HashMap pokemonData) {
        
        String pokemonNumber = String.format("%03d", pokemonData.get("national_id"));
        
        return pokemonNumber;
        
    }
    
    public String getPokemonName(HashMap pokemonData) {
        
        String pokemonName = pokemonData.get("name").toString();
        
        return pokemonName;
        
    }
    
    public ArrayList<String> getPokemonType(HashMap pokemonData) {
        
        ArrayList<HashMap> typeList = new ArrayList<>();
        ArrayList<String> pokemonType = new ArrayList<>();
        typeList = (ArrayList)pokemonData.get("types");
        Integer numTypes = typeList.size();
        HashMap subTypes = new HashMap();
        
        for (int i = 0; i < numTypes; i++) {
            
            subTypes = (HashMap)typeList.get(i);
            pokemonType.add(subTypes.get("name").toString().toUpperCase());
            
        }
        
        return pokemonType;
        
    }
    
    public String getPokemonHeight(HashMap pokemonData) {
        
        Double height = Double.parseDouble(pokemonData.get("height").toString());
        
        String formattedHeight = new DecimalFormat("##.#").format((height * .10)) + " m";
        
        return formattedHeight;
        
    }
    
    public String getPokemonWeight(HashMap pokemonData) {
        
        Double weight = Double.parseDouble(pokemonData.get("weight").toString());
        
        String formattedWeight = new DecimalFormat("##.#").format((weight * .10)) + " kg";
        
        return formattedWeight;
        
    }
    
    public String getPokemonDescription(HashMap pokemonData) {
        
        ArrayList<HashMap> descriptionList = new ArrayList<>();
        HashMap descriptions = new HashMap();
        String descriptionURI, description;
        URL descriptionURL;
        JSONInputStream inFromURL;
        HashMap descriptionData = new HashMap();
        
        descriptionList = (ArrayList)pokemonData.get("descriptions");
        descriptions = (HashMap)descriptionList.get(0);
        descriptionURI = descriptions.get("resource_uri").toString();
        
        try {
        
            descriptionURL = new URL("http://pokeapi.co" + descriptionURI);
            inFromURL = new JSONInputStream(descriptionURL.openStream());
            descriptionData = (HashMap)inFromURL.readObject();
            
        }
        
        catch (IOException | JSONException e) {
            
            e.printStackTrace();
            
        }
        
        description = descriptionData.get("description").toString();
        
        return description;
            
    }
    
    public String getPokemonSpriteURL(HashMap pokemonData) {
        
        ArrayList<HashMap> spriteURIList = new ArrayList<>();
        HashMap sprites = new HashMap();
        String spriteURI, spritePath;
        URL spriteURL;
        JSONInputStream inFromURL;
        HashMap spriteData = new HashMap();
        
        spriteURIList = (ArrayList)pokemonData.get("sprites");
        sprites = (HashMap)spriteURIList.get(0);
        spriteURI = sprites.get("resource_uri").toString();
        
        try {
            
            spriteURL = new URL("http://pokeapi.co" + spriteURI);
            inFromURL = new JSONInputStream(spriteURL.openStream());
            spriteData = (HashMap)inFromURL.readObject();
            
        }
        
        catch (IOException | JSONException e) {
            
            e.printStackTrace();
            
        }
        
        spritePath = "http://pokeapi.co" + spriteData.get("image").toString();
        
        return spritePath;
        
    }
    
}
