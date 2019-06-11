package controllers;

import com.carlisting.carlist.controllers.CarSearchController;
import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.services.CarSearchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.carlisting.carlist.constants.UrlConstants.URL_MAPPING_API_SEARCH;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = CarSearchController.class)
public class CarSearchControllerTest {

    @MockBean
    CarSearchServiceImpl carSearchService;

    @Autowired
    MockMvc mockMvc;


    ObjectMapper objectMapper = new ObjectMapper();

    private Listing listing = new Listing("1", "23", "1234", "2015", "red", "12331", "renault", "megane");
    private Listing listing1 = new Listing("1", "21", "2", "2019", "blue", "300000", "renault", "megane");
    private String make = listing.getMake();
    private String model = listing.getModel();
    private String year = listing.getYear();
    private String color = listing.getColor();

    private List<Listing> listings = Arrays.asList(listing, listing1);

    @Test
    public void makeAGetCallForSearchingListingByMake() throws Exception {

        String any = objectMapper.writeValueAsString(listings);

        given(carSearchService.findByMake(make)).willReturn(Optional.ofNullable(listings));

        this.mockMvc.perform(
                get(URL_MAPPING_API_SEARCH + "/makers/{make}", make))
                .andExpect(MockMvcResultMatchers.content().string(any))
                .andExpect(status().isOk());

        Mockito.verify(carSearchService, Mockito.times(1)).findByMake(make);
    }

    @Test
    public void makeGetCallForSearchingListingByModel() throws Exception {

        String any = objectMapper.writeValueAsString(listings);

        given(carSearchService.findByModel(model)).willReturn(Optional.ofNullable(listings));

        this.mockMvc.perform(
                get(URL_MAPPING_API_SEARCH + "/models/{model}", model))
                .andExpect(MockMvcResultMatchers.content().string(any))
                .andExpect(status().isOk());

        Mockito.verify(carSearchService, Mockito.times(1)).findByModel(model);
    }


    @Test
    public void makeGetCallForSearchingListingByYear() throws Exception {

        String any = objectMapper.writeValueAsString(listings);

        given(carSearchService.findByYear(year)).willReturn(Optional.ofNullable(listings));

        this.mockMvc.perform(
                get(URL_MAPPING_API_SEARCH + "/years/{year}", year))
                .andExpect(MockMvcResultMatchers.content().string(any))
                .andExpect(status().isOk());

        Mockito.verify(carSearchService, Mockito.times(1)).findByYear(year);
    }

    @Test
    public void makeGetCallForSearchingListingByColor() throws Exception {

        String any = objectMapper.writeValueAsString(listings);

        given(carSearchService.findByColor(color)).willReturn(Optional.ofNullable(listings));

        this.mockMvc.perform(
                get(URL_MAPPING_API_SEARCH + "/colors/{color}", color))
                .andExpect(MockMvcResultMatchers.content().string(any))
                .andExpect(status().isOk());

        Mockito.verify(carSearchService, Mockito.times(1)).findByColor(color);
    }

}
