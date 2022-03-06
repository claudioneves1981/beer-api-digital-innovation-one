package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegistredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;

import java.util.List;

public interface BeerControllerDocs {

    BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegistredException;
    BeerDTO findByName(String name) throws BeerNotFoundException;
    List<BeerDTO> listBeers();
    void deleteById(Long id) throws BeerNotFoundException;
}
