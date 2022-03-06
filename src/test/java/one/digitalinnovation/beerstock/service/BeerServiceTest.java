package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegistredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private static final long INVALID_USER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;
    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
       void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegistredException {
       BeerDTO expectedBeerDTO =  BeerDTOBuilder.builder().build().toBeerDTO();
       Beer expectedSavedBeer = beerMapper.toModel(expectedBeerDTO);

       when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
       when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

       BeerDTO createdBeerDTO = beerService.createBeer(expectedBeerDTO);

       assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
       assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
       assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));

    }

    @Test
    void whenAlreadyRegistredBeerInformedThenAnExceptionShouldBeThrown()  {
        BeerDTO expectedBeerDTO =  BeerDTOBuilder.builder().build().toBeerDTO();
        Beer duplicatedBeer = beerMapper.toModel(expectedBeerDTO);
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));
        assertThrows(BeerAlreadyRegistredException.class, () -> beerService.createBeer(expectedBeerDTO));
    }

    @Test
    void whenValidBeerNameIsGivenReturnABeer() throws BeerNotFoundException {
        BeerDTO expectedFoundBeerDTO =  BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);
        when(beerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));
        BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());
        assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
    }

    @Test
    void whenNotRegistredBeerNameIsGivenThrowAnException() {
        BeerDTO expectedFoundBeerDTO =  BeerDTOBuilder.builder().build().toBeerDTO();
        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());
        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName()));
    }
}