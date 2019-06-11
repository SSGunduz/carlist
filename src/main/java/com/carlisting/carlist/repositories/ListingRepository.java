package com.carlisting.carlist.repositories;

import com.carlisting.carlist.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends MongoRepository<Listing, String> {

    Optional<List<Listing>> findByMake(String make);

    Optional<List<Listing>> findByModel(String model);

    Optional<List<Listing>> findByYear(String year);

    Optional<List<Listing>> findByColor(String color);

    boolean existsByDealerAndCode(String dealerId, String code);

    Optional<Listing> findByDealerAndCode(String dealerId, String code);
}
