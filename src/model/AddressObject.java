package model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AddressObject {
    private Long objectId;

    private String name;

    private String typename;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isActual;

    private boolean isActive;
}
