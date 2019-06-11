package com.carlisting.carlist.services;

import com.carlisting.carlist.models.Listing;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarSearchService {
    Optional<List<Listing>> findByMake(String make);

    Optional<List<Listing>> findByModel(String model);

    Optional<List<Listing>> findByYear(String year);

    Optional<List<Listing>> findByColor(String color);
}
