package model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AddressHierarchy {
    private Long objectId;

    private Long parentObjId;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isActive;
}
