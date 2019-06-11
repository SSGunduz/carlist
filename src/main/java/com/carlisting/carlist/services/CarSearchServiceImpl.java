package com.carlisting.carlist.services;

import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarSearchServiceImpl implements CarSearchService {

    @Autowired
    ListingRepository listingRepository;

    @Override
    public Optional<List<Listing>> findByMake(String make) {
        return listingRepository.findByMake(make);
    }

    @Override
    public Optional<List<Listing>> findByModel(String model) {
        return listingRepository.findByModel(model);
    }

    @Override
    public Optional<List<Listing>> findByYear(String year) {
        return listingRepository.findByYear(year);
    }

    @Override
    public Optional<List<Listing>> findByColor(String color) {
        return listingRepository.findByColor(color);
    }

}
