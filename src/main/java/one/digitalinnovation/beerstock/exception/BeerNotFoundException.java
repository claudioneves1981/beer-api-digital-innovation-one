package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception {

    public BeerNotFoundException(Long id){
        super(String.format("Beer with id %s not found in the system.", id));
    }

   public BeerNotFoundException(String name){
       super(String.format("Beer with name %s not found in the system.", name));
   }
}
