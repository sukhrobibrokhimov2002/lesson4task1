package uz.pdp.lesson4vazifa1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson4vazifa1.entity.Outcome;
import uz.pdp.lesson4vazifa1.payload.OutcomeDto;
import uz.pdp.lesson4vazifa1.payload.ResultDto;
import uz.pdp.lesson4vazifa1.service.OutcomeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/outcome")
public class OutcomeController {
    @Autowired
    OutcomeService outcomeService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMoney(@RequestBody OutcomeDto outcomeDto, HttpServletRequest httpServletRequest) {
        ResultDto resultDto = outcomeService.sendMoney(outcomeDto, httpServletRequest);
        if (resultDto.isSuccess()) {
            return ResponseEntity.ok(resultDto);
        }
        return ResponseEntity.status(409).body(resultDto);
    }

    //User uchun
    @GetMapping("/getOutcomes")
    public ResponseEntity<?> getOutcomes(HttpServletRequest httpServletRequest) {
        List<Outcome> outcomes = outcomeService.getOutcomes(httpServletRequest);
        if (outcomes != null) return ResponseEntity.ok(outcomes);
        return ResponseEntity.status(409).body(outcomes);
    }

    //Admin uchun
    @GetMapping("/getAllOutcomes")
    public ResponseEntity<?> getAll() {
        List<Outcome> allOutcome = outcomeService.getAllOutcome();
        if (allOutcome != null) return ResponseEntity.ok(allOutcome);
        return ResponseEntity.status(409).body(null);

    }
}
