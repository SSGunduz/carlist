package com.carlisting.carlist.services;

import com.carlisting.carlist.models.Listing;
import com.carlisting.carlist.repositories.ListingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListingCreateService {

    @Autowired
    private ListingRepository listingRepository;


    private static final Logger logger = LoggerFactory.getLogger(ListingCreateService.class);

    public Listing createListFromCSV(Listing listing, String dealerId, MultipartFile file) throws IOException {

        String uplodedFolder = System.getProperty("user.dir") + "/src/main/resources/static/";
        Path path = Paths.get(uplodedFolder + file.getOriginalFilename());

        uploadFile(file, path);

        String fileName = path.toString();

        csvProcessor(listing, dealerId, fileName);

        return listing;
    }

    public List<Listing> createFromList(List<Listing> listings, String dealerId) {

        List<Listing> duplicatedEntries = new ArrayList<>();

        listings
                .stream()
                .filter(listing -> !(listingRepository.existsByDealerAndCode(dealerId, listing.getCode())))
                .forEach(listing -> {
                    listing.setDealer(dealerId);
                    listingRepository.save(listing);
                });

        return duplicatedEntries;
    }


    public java.util.Optional<Listing> updateListing(Listing listing, String id) {

        java.util.Optional<Listing> listingFoundById = listingRepository.findById(id);

        listingFoundById.ifPresent(listingById -> {
            String code = listing.getCode();
            String dealer = listing.getDealer();
            java.util.Optional<Listing> listingByDealerAndCode = listingRepository.findByDealerAndCode(dealer, code);

            listingByDealerAndCode.ifPresent(more -> {
                if (id.equals(more.getId())) {
                    listingById.setkW(listing.getkW());
                    listingById.setDealer(listing.getDealer());
                    listingById.setColor(listing.getColor());
                    listingById.setCode(listing.getCode());
                    listingById.setkW(listing.getkW());
                    listingById.setModel(listing.getModel());
                    listingById.setPrice(listing.getPrice());
                    listingById.setYear(listing.getYear());
                    listingById.setMake(listing.getMake());
                    listingRepository.save(listingById);
                }
            });
        });

        return listingFoundById;
    }

    private void csvProcessor(Listing listing, String dealerId, String csvName) throws IOException {

        ICsvListReader csvReader = null;

        final CellProcessor[] allProcessors = new CellProcessor[]
                {new UniqueHashCode(), new NotNull(), new NotNull(), new NotNull(), new Optional(), new NotNull()};

        final CellProcessor[] listProcesor = new CellProcessor[]
                {allProcessors[0], allProcessors[1], allProcessors[2], allProcessors[3], allProcessors[5]};


        try {
            csvReader = new CsvListReader(new FileReader(csvName), CsvPreference.STANDARD_PREFERENCE);

            //skip the first line
            csvReader.getHeader(true);

            while ((csvReader.read()) != null) {

                final CellProcessor[] processors;

                if (csvReader.length() == allProcessors.length) {
                    processors = allProcessors;
                } else {
                    processors = listProcesor;
                }


                final List<Object> customerList = csvReader.executeProcessors(processors);

                String code = customerList.get(0).toString();

                customerList.stream()
                        .filter(it -> !(listingRepository.existsByDealerAndCode(dealerId, code)))
                        .forEach(it -> {
                            listingSetterFromCSV(listing, dealerId, customerList, code);
                            listingRepository.save(listing);
                        });
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("missing IO Exception: ", e);

        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }
    }

    private void listingSetterFromCSV(Listing listing, String dealerId, List<Object> customerList, String code) {
        String makeModel;
        String kW;
        String year;
        String color;
        String price;
        String make;
        String model;
        String[] split;

        makeModel = customerList.get(1).toString();
        kW = customerList.get(2).toString();
        year = customerList.get(3).toString();

        if (customerList.size() == 5) {
            price = customerList.get(4).toString();
            color = null;
        } else {
            price = customerList.get(5).toString();
            color = (customerList.get(4) != null) ? customerList.get(4).toString() : null;
        }

        split = makeModel.split("/");

        make = split[0];
        model = split[1];

        listing.setDealer(dealerId);
        listing.setCode(code);
        listing.setMake(make);
        listing.setModel(model);
        listing.setkW(kW);
        listing.setYear(year);
        listing.setPrice(price);
        listing.setColor(color);

    }


    private void uploadFile(MultipartFile file, Path path) {
        try {
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
            logger.info("File uploaded");

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("missing IO Exception: ", e);

        }
    }

}
