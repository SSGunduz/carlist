package controllers;

import com.carlisting.carlist.controllers.ListingController;
import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.services.ListingCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.carlisting.carlist.constants.UrlConstants.URL_MAPPING_API_UPLOAD;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ListingController.class)
public class ListingControllerTest {

    @MockBean
    ListingCreateService listingCreateService;

    @Autowired
    MockMvc mockMvc;

    private Listing firstListing = new Listing("1", "23", "1234", "2015", "red", "12331", "renault", "megane");
    private Listing secondListing = new Listing("1", "21", "2", "2019", "blue", "300000", "renault", "megane");
    private Listing updatedListing = new Listing("1", "21", "2", "2020", "blue", "300000", "renault", "megane");

    private String dealer = firstListing.getDealer();


    private List<Listing> listings = Arrays.asList(firstListing, secondListing);

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void createListingFromJsonList() throws Exception {

        String allListingsAsString = objectMapper.writeValueAsString(listings);

        when(listingCreateService.createFromList(listings, dealer)).thenReturn((listings));

        this.mockMvc.perform(
                post(URL_MAPPING_API_UPLOAD + "/listings/{dealerId}", dealer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(allListingsAsString))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateListingFromJson() throws Exception {

        String firstListingAsString = objectMapper.writeValueAsString(firstListing);
        String id = firstListing.getId();

        when(listingCreateService.updateListing(firstListing, id)).thenReturn((Optional.ofNullable(updatedListing)));


        mockMvc.perform(
                put(URL_MAPPING_API_UPLOAD + "/listings/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstListingAsString))
                .andExpect(status().isCreated());


    }


}
