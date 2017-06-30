package org.knoesis.health.dataHolders;

/**
 * Data holder object for Foobot data.
 *
 * Created by Quintin on 8/26/2016.
 */
public class FoobotDataHolder {
    String timestamp;
    double particulateMatter;
    double temperature;
    double humidity;
    double carbonDioxide;
    double volatileOrganicCompounds;
    double pollution;

    public FoobotDataHolder(String timestamp, double particulateMatter, double temperature, double humidity,
                     double carbonDioxide, double volatileOrganicCompounds, double pollution){
        this.timestamp = timestamp;
        this.particulateMatter = particulateMatter;
        this.temperature = temperature;
        this.humidity = humidity;
        this.carbonDioxide = carbonDioxide;
        this.volatileOrganicCompounds = volatileOrganicCompounds;
        this.pollution = pollution;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getParticulateMatter() {
        return particulateMatter;
    }

    public void setParticulateMatter(double particulateMatter) {
        this.particulateMatter = particulateMatter;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCarbonDioxide() {
        return carbonDioxide;
    }

    public void setCarbonDioxide(double carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public double getVolatileOrganicCompounds() {
        return volatileOrganicCompounds;
    }

    public void setVolatileOrganicCompounds(double volatileOrganicCompounds) {
        this.volatileOrganicCompounds = volatileOrganicCompounds;
    }

    public double getPollution() {
        return pollution;
    }

    public void setPollution(double pollution) {
        this.pollution = pollution;
    }
}
