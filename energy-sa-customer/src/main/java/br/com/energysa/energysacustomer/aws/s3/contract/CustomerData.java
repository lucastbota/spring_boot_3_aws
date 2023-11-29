package br.com.energysa.energysacustomer.aws.s3.contract;

public record CustomerData(Long id, String name, AddressData addressData) {
}
