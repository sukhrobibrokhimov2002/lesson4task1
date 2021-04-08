package uz.pdp.lesson4vazifa1.controller;//package uz.pdp.lesson4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson4vazifa1.entity.Card;
import uz.pdp.lesson4vazifa1.payload.CardDto;
import uz.pdp.lesson4vazifa1.payload.ResultDto;
import uz.pdp.lesson4vazifa1.service.CardService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody CardDto card,HttpServletRequest httpServletRequest) {
        ResultDto resultDto = cardService.addCard(card,httpServletRequest);
        if (resultDto.isSuccess()) return ResponseEntity.ok(resultDto);
        return ResponseEntity.status(409).body(resultDto);
    }

    //User uchun
    @GetMapping("/getUserCard")
    public ResponseEntity<?> getUserCard(HttpServletRequest httpServletRequest) {
        Card cardByUser = cardService.getCardByUser(httpServletRequest);
        if (cardByUser != null) return ResponseEntity.ok(cardByUser);
        return ResponseEntity.status(409).body(cardByUser);
    }

    //Admin uchun
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Card> allCards = cardService.getAllCards();
        if (allCards != null) return ResponseEntity.ok(allCards);
        return ResponseEntity.status(409).body(allCards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        ResultDto resultDto = cardService.deleteCard(id);
        if (resultDto.isSuccess()) return ResponseEntity.ok(resultDto);
        return ResponseEntity.status(409).body(resultDto);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> edit(@PathVariable Integer id, @Valid@RequestBody CardDto card,HttpServletRequest httpServletRequest) {
        ResultDto edit = cardService.edit(card, id,httpServletRequest);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
