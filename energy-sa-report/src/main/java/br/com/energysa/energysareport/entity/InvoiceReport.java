package br.com.energysa.energysareport.entity;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.util.Objects;

@DynamoDbBean
public class InvoiceReport {

    private Long invoiceId;
    private Long customerId;
    private String name;
    private BigDecimal total;
    private String latitude;
    private String longitude;

    public InvoiceReport() {
    }

    @DynamoDbAttribute("invoice_id")
    @DynamoDbPartitionKey
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public static InvoiceReport of(Long invoiceId,
                                   Long customerId,
                                   String name,
                                   BigDecimal total,
                                   String latitude,
                                   String longitude) {
        var c = new InvoiceReport();
        c.setInvoiceId(invoiceId);
        c.setCustomerId(customerId);
        c.setName(name);
        c.setTotal(total);
        c.setLatitude(latitude);
        c.setLongitude(longitude);
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceReport that = (InvoiceReport) o;
        return Objects.equals(invoiceId, that.invoiceId) && Objects.equals(customerId, that.customerId) && Objects.equals(name, that.name) && Objects.equals(total, that.total) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, name, total, latitude, longitude);
    }

    @Override
    public String toString() {
        return "CacheInvoiceReport{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
