package uz.pdp.lesson4vazifa1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDto {
    private boolean success;
    private String message;
}
