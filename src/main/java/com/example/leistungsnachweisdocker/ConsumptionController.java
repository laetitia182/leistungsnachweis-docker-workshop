package com.example.leistungsnachweisdocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The ConsumptionController class is a REST controller that handles HTTP requests for processing the consumption data.
 */
@RestController
public class ConsumptionController {

    /**
     * The service that contains the logic for processing the data
     */
    @Autowired
    private ConsumptionService consumptionService;

    /***
     * Processes the data and returns a confirmation message
     * @return A message confirming that the consumption data has been processed and sent.
     */

    @GetMapping("/process")
    public String processConsumption() {
        consumptionService.processConsumption();
        return "Consumption data processed and sent.";
    }

}