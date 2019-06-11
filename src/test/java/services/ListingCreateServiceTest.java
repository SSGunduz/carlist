package services;


import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.repositories.ListingRepository;
import com.carlisting.carlist.services.ListingCreateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ListingCreateServiceTest {

    @InjectMocks
    private ListingCreateService listingCreateService;

    @Mock
    private ListingRepository listingRepository;


    private Listing listing = new Listing(null, "23", "1234", "2015", "red", "12331", "renault", "megane");
    private Listing listing1 = new Listing(null, "21", "2", "2019", "blue", "300000", "Volkswagen", "megane");
    private String dealerId = "1";
    private List<Listing> newList = new ArrayList<>();


    @Test
    public void listingSuccessfullyRecorded() {

        newList.add(listing);
        newList.add(listing1);

        when(listingRepository.existsByDealerAndCode(dealerId, listing.getCode())).thenReturn(false);
        when(listingRepository.save(listing)).thenReturn(listing);
        when(listingRepository.existsByDealerAndCode(dealerId, listing.getCode())).thenReturn(false);

        when(listingRepository.existsByDealerAndCode(dealerId, listing1.getCode())).thenReturn(false);
        when(listingRepository.save(listing1)).thenReturn(listing1);
        listingCreateService.createFromList(newList, dealerId);

        verify(listingRepository, times(1)).existsByDealerAndCode(dealerId, listing.getCode());
        verify(listingRepository, times(1)).save(listing);
        verify(listingRepository, times(1)).existsByDealerAndCode(dealerId, listing1.getCode());
        verify(listingRepository, times(1)).save(listing1);

    }

    @Test
    public void onlyNonExistingListingsWillBeSaved() {

        newList.add(listing);
        newList.add(listing1);

        when(listingRepository.existsByDealerAndCode(dealerId, listing.getCode())).thenReturn(false);
        when(listingRepository.save(listing)).thenReturn(listing);
        when(listingRepository.existsByDealerAndCode(dealerId, listing.getCode())).thenReturn(false);

        when(listingRepository.existsByDealerAndCode(dealerId, listing1.getCode())).thenReturn(true);

        listingCreateService.createFromList(newList, dealerId);

        verify(listingRepository, times(1)).existsByDealerAndCode(dealerId, listing.getCode());
        verify(listingRepository, times(1)).save(listing);
        verify(listingRepository, times(1)).existsByDealerAndCode(dealerId, listing1.getCode());
        verify(listingRepository, times(0)).save(listing1);

    }

    @Test
    public void onlyNonExistingListingsWillBeSavsed() throws IOException {

        when(listingRepository.save(listing)).thenReturn(listing);

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "1,mercedes/a 180,123,2014,black,15950\n1,mercedes/a 180,123,2014,black,15950".getBytes());

        listingCreateService.createListFromCSV(listing, dealerId, firstFile);

        verify(listingRepository, times(6)).save(listing);

    }

    @Test
    public void updateListingAfterAddingNewListing() {

        listing.setDealer(dealerId);

        String id = listing.getId();
        listing.setId("1");
        when(listingRepository.findByDealerAndCode(dealerId, "23")).thenReturn(Optional.ofNullable(listing));
        when(listingRepository.findById("1")).thenReturn(Optional.ofNullable(listing));

        listingCreateService.updateListing(listing, dealerId);

        verify(listingRepository, times(1)).findByDealerAndCode(dealerId, "23");
        verify(listingRepository, times(1)).findById("1");

    }

}
