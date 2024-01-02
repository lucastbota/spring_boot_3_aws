package br.com.energy.lambda.customer.house.data;

public record EnergyData(double watts, int consumptionTime) {
    public double calculateKwh(){
        final int thousand = 1000;
        return watts * consumptionTime / thousand;
    }
}
