package uz.pdp.lesson4vazifa1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private Double balance;

    @NotNull(message = "Card number must not be null")
    private String number;
    @NotNull(message = "Card expired Date must not be null")
    private Date expiredDate;
    private boolean active;
}
