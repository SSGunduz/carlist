package com.carlisting.carlist.controllers;

import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.services.CarSearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.carlisting.carlist.constants.UrlConstants.URL_MAPPING_API_SEARCH;

@Controller
@RequestMapping(URL_MAPPING_API_SEARCH)
public class CarSearchController {

    @Autowired
    private CarSearchServiceImpl carSearchService;

    private static final Logger logger = LoggerFactory.getLogger(CarSearchController.class);

    @GetMapping("/makers/{make}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<List<Listing>> searchByMake(@PathVariable String make) {

        logger.info("Search listing by make ");

        return carSearchService.findByMake(make);
    }

    @GetMapping("/models/{model}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<List<Listing>> searchByModel(@PathVariable String model) {

        logger.info("Search listing by model ");

        return carSearchService.findByModel(model);
    }

    @GetMapping("/years/{year}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<List<Listing>> searchByYear(@PathVariable String year) {

        logger.info("Search listing by year ");

        return carSearchService.findByYear(year);
    }

    @GetMapping("/colors/{color}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<List<Listing>> searchByColor(@PathVariable String color) {

        logger.info("Search listing by color ");

        return carSearchService.findByColor(color);
    }
}
