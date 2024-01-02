package br.com.energysa.energysareport.dto;

import java.math.BigDecimal;

public record CustomerDataDTO(Long id, String name, BigDecimal total, String latitude, String longitude) {
}