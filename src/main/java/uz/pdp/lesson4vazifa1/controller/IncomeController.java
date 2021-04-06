package uz.pdp.lesson4vazifa1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lesson4vazifa1.entity.Income;
import uz.pdp.lesson4vazifa1.service.IncomeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @GetMapping("/getIncomes")
    public ResponseEntity<?> getIncome(HttpServletRequest httpServletRequest) {
        List<Income> income = incomeService.getIncome(httpServletRequest);
        if (income != null) return ResponseEntity.ok(income);
        return ResponseEntity.status(409).body(null);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Income> all = incomeService.getAll();
        if (all != null) return ResponseEntity.ok(all);
        return ResponseEntity.status(409).body(null);

    }
}
