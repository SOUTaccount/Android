package first.example.beeradviser;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBrands (String Chose){
        List <String> brands = new ArrayList<>();
        if (Chose.equals("light")){
            brands.add("Coca-cola");
            brands.add("Goose");
            brands.add("Baltika");
            brands.add("Kozel");}
            else{
                brands.add("Milk stout");
                brands.add("Irish el");}
            return brands;

    }
}
