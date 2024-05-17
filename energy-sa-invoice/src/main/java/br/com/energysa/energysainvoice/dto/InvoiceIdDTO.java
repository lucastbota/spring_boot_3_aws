package br.com.energysa.energysainvoice.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@JsonRootName("invoice")
@Relation(collectionRelation = "invoices")
public class InvoiceIdDTO extends RepresentationModel<InvoiceIdDTO> {
    private String id;

    public InvoiceIdDTO() {
    }

    public InvoiceIdDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                '}';
    }
}
