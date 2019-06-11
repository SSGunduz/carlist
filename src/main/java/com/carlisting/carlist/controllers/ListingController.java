package com.carlisting.carlist.controllers;

import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.services.ListingCreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.carlisting.carlist.constants.UrlConstants.URL_MAPPING_API_UPLOAD;

@RestController
@RequestMapping(URL_MAPPING_API_UPLOAD)
public class ListingController {

    @Autowired
    private ListingCreateService listingCreateService = new ListingCreateService();

    private static final Logger logger = LoggerFactory.getLogger(ListingController.class);


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/csvs/{dealerId}")
    public Listing createListing(@PathVariable String dealerId,
                                 @RequestParam("file") MultipartFile file) throws IOException {

        logger.info("creates the listing with CSV");

        Listing listing = new Listing();

        if (file.isEmpty()) {
            return listing;
        }

        return listingCreateService.createListFromCSV(listing, dealerId, file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/listings/{id}")
    public Optional<Listing> updateListing(@PathVariable String id,
                                           @RequestBody Listing listing) {

        logger.info("update  listing");

        return listingCreateService.updateListing(listing, id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/listings/{dealerId}")
    public List<Listing> addVehiclesByListings(@PathVariable String dealerId,
                                               @RequestBody List<Listing> listing) {

        logger.info("creating a new listing");

        return listingCreateService.createFromList(listing, dealerId);
    }


}
