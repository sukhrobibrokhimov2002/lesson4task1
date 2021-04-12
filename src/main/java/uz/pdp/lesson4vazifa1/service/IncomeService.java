package uz.pdp.lesson4vazifa1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson4vazifa1.entity.Card;
import uz.pdp.lesson4vazifa1.entity.Income;
import uz.pdp.lesson4vazifa1.repository.CardRepository;
import uz.pdp.lesson4vazifa1.repository.IncomeRepository;
import uz.pdp.lesson4vazifa1.security.JwtProvider;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class IncomeService {


    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CardRepository cardRepository;

    //User uchun
    public List<Income> getIncome(HttpServletRequest httpServletRequest) {
        String tokenBearer = httpServletRequest.getHeader("Authorization");
        String token = tokenBearer.substring(7);
        String usernameFromToken = jwtProvider.getUsernameFromToken(token);

        Card byUserName = cardRepository.findingByUsername(usernameFromToken);
        if (byUserName.getUsername() == null) return null;
        List<Income> allByToCardId = incomeRepository.findAllByReceivingCardId(byUserName);
        return allByToCardId;

    }

    //Admin uchun
    public List<Income> getAll() {
        List<Income> all = incomeRepository.findAll();
        return all;

    }
}
