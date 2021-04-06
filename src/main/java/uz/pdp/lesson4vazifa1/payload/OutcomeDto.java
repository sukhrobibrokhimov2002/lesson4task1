package uz.pdp.lesson4vazifa1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutcomeDto {

    private Integer toCardId;
    private double amount;
}
