package com.example.leistungsnachweisdocker;

/**
 * The Result class represents the consumption data for a specific customer. It holds information about the customer's ID and their total consumption.
 */
public class Result {
   private String customerId;
   private long consumption;

   // Getter and setter
    public long getConsumption() {
        return consumption;
    }

    public void setConsumption(long consumption) {
        this.consumption = consumption;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Result(String customerId, long consumption) {
        this.customerId = customerId;
        this.consumption = consumption;
    }
}
