package services;

import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.repositories.ListingRepository;
import com.carlisting.carlist.services.CarSearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarSearchServiceTest {

    @InjectMocks
    private CarSearchServiceImpl carSearchService;

    @Mock
    private ListingRepository listingRepository;

    private Listing listing = new Listing(null, "23", "1234", "2015", "red", "12331", "renault", "megane");
    private Listing listing1 = new Listing(null, "21", "2", "2019", "blue", "300000", "Volkswagen", "megane");
    private List<Listing> listings = new ArrayList<>();
    private String make = listing.getMake();
    private String model = listing.getModel();
    private String year = listing.getYear();
    private String color = listing.getYear();


    @Test
    public void willReturnListingWhichSearchedByMake() {
        listings.add(listing);
        listings.add(listing1);

        when(listingRepository.findByMake(make)).thenReturn(Optional.ofNullable(listings));

        carSearchService.findByMake(make);

        verify(listingRepository, times(1)).findByMake(make);

    }

    @Test
    public void willReturnListingWhichSearchedByModel() {
        listings.add(listing);
        listings.add(listing1);

        when(listingRepository.findByModel(model)).thenReturn(Optional.ofNullable(listings));

        carSearchService.findByModel(model);

        verify(listingRepository, times(1)).findByModel(model);

    }

    @Test
    public void willReturnListingWhichSearchedByYear() {
        listings.add(listing);
        listings.add(listing1);

        when(listingRepository.findByYear(year)).thenReturn(Optional.ofNullable(listings));

        carSearchService.findByYear(year);

        verify(listingRepository, times(1)).findByYear(year);

    }

    @Test
    public void willReturnListingWhichSearchedByColor() {
        listings.add(listing);
        listings.add(listing1);

        when(listingRepository.findByColor(color)).thenReturn(Optional.ofNullable(listings));

        carSearchService.findByColor(color);

        verify(listingRepository, times(1)).findByColor(color);

    }


}
