package uz.pdp.lesson4vazifa1.controller;//package uz.pdp.lesson4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson4vazifa1.entity.Card;
import uz.pdp.lesson4vazifa1.payload.ResultDto;
import uz.pdp.lesson4vazifa1.service.CardService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Card card) {
        ResultDto resultDto = cardService.addCard(card);
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
    private ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody Card card) {
        ResultDto edit = cardService.edit(card, id);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }
}
