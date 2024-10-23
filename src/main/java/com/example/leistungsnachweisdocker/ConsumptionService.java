package com.example.leistungsnachweisdocker;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ConsumptionService class provides the logic to process and calculate customer consumption data based on event timestamps
 */
@Service
public class ConsumptionService {
    // The RestTemplate instance for making HTTP requests to external services.
    private final RestTemplate restTemplate = new RestTemplate();

    // The URL for fetching event data from an external API
    private static final String GET_DATA_URL = "http://localhost:8080/v1/dataset";

    // The URL for sending the consumption results to an external API
    private static final String POST_RESULT_URL = "http://localhost:8080/v1/result";

    /***
     * Fetches event data, calculates total consumption per customer and sends the results.
     */
    public void processConsumption() {
        EventResponse response = restTemplate.getForObject(GET_DATA_URL, EventResponse.class);
        if (response == null || response.getEvents() == null) {
            throw new RuntimeException("Fehler beim Abrufen der Daten");
        }

        Map<String, Long> customerConsumption = calculateTotalConsumption(response.getEvents());

        sendResults(customerConsumption);
    }

    /**
     * Calculates the total consumption time per customer based on "start" and "stop" events
     * @param events A list of events with timestamps and customer details
     * @return A map where the key is the customer ID and the value is the total consumption time
     */
    private Map<String, Long> calculateTotalConsumption(List<Event> events) {
        Map<String, Long> consumptionMap = new HashMap<>();
        Map<String, Long> startTimes = new HashMap<>();

        for (Event event : events) {
            String key = event.getCustomerId() + ":" + event.getWorkloadId();

            if ("start".equals(event.getEventType())) {
                startTimes.put(key, event.getTimestamp());
            } else if ("stop".equals(event.getEventType())) {
                Long startTime = startTimes.get(key);
                if (startTime != null) {
                    long duration = event.getTimestamp() - startTime;
                    consumptionMap.put(event.getCustomerId(), consumptionMap.getOrDefault(event.getCustomerId(), 0L) + duration);
                    startTimes.remove(key);
                }
            }
        }
        return consumptionMap;
    }

    /**
     * Sends the total consumption results for each customer to an external API.
     * @param customerConsumption A map where the key is the customer ID and the value is the total consumption time
     */

    private void sendResults(Map<String, Long> customerConsumption) {
        customerConsumption.forEach((customerId, consumption) -> {
            Result result = new Result(customerId, consumption);
            restTemplate.postForObject(POST_RESULT_URL, result, String.class);
        });
    }
}
