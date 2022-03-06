package one.digitalinnovation.beerstock.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyRegistredException extends Exception {

    public BeerAlreadyRegistredException(String beerName){
        super(String.format("Beer with name %s already registred in the system.", beerName));
    }
}
