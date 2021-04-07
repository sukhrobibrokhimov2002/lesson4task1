package uz.pdp.lesson4vazifa1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson4vazifa1.entity.Card;
import uz.pdp.lesson4vazifa1.entity.Income;
import uz.pdp.lesson4vazifa1.entity.Outcome;
import uz.pdp.lesson4vazifa1.payload.OutcomeDto;
import uz.pdp.lesson4vazifa1.payload.ResultDto;
import uz.pdp.lesson4vazifa1.repository.CardRepository;
import uz.pdp.lesson4vazifa1.repository.IncomeRepository;
import uz.pdp.lesson4vazifa1.repository.OutcomeRepository;
import uz.pdp.lesson4vazifa1.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;

    public ResultDto sendMoney(OutcomeDto outcomeDto, HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String usernameFromToken = jwtProvider.getUsernameFromToken(token);
        //token bilan kirgan user kartasini topib oldik
        Card senderCard = cardRepository.findingByUsername(usernameFromToken);
        if (senderCard == null) return new ResultDto(false, "Karta mavjud emas");
        //yuborayptgan karta mavjud yoki mavjud emasligini tekshirish
        Optional<Card> optionalCardCard = cardRepository.findById(outcomeDto.getToCardId());
        if (!optionalCardCard.isPresent()) return new ResultDto(false, "Card not found");
        Card receiveCard = optionalCardCard.get();
        //kartada username bormi yoki yo'qligini tekshirish
        String userName = receiveCard.getUsername();
        if (userName == null) return new ResultDto(false, "Karta username mavjud emas");


//Outcomega ma'lumotlarni saqlash
        Outcome outcome = new Outcome();
        outcome.setCommissionAmount(2.0);
        Double commissionAmount = outcome.getCommissionAmount();
        double sendingMoney = (outcomeDto.getAmount() / 100 * commissionAmount) + outcomeDto.getAmount();
        //karta balansi yetaidimi yuqmi tekshirish
        if (sendingMoney <= senderCard.getBalance()) {
            outcome.setAmount(outcomeDto.getAmount());
            outcome.setDate(new Date(System.currentTimeMillis()));
            outcome.setFromCardId(senderCard);
            outcome.setToCardId(receiveCard);
            //cardni balansini kamaytirish
            senderCard.setBalance(senderCard.getBalance() - sendingMoney);
            receiveCard.setBalance(receiveCard.getBalance() + sendingMoney);
            Outcome savedOutcome = outcomeRepository.save(outcome);
            cardRepository.save(senderCard);
            cardRepository.save(receiveCard);

            //Income ga ma'lumotlarni saqlash
            Income income = new Income();
            income.setAmount(savedOutcome.getAmount());
            income.setDate(new Date(System.currentTimeMillis()));
            income.setReceivingCardId(receiveCard);
            income.setSendingCardId(senderCard);
            incomeRepository.save(income);

            return new ResultDto(true, "Success");

        }
        return new ResultDto(false, "Kartada yetarlicha mablag' mavjud emas");


    }


    //User uchun
    public List<Outcome> getOutcomes(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String usernameFromToken = jwtProvider.getUsernameFromToken(token);
        Card cardByUsername = cardRepository.findingByUsername(usernameFromToken);
        if (cardByUsername == null) return null;
        List<Outcome> byFromCardId = outcomeRepository.findAllByFromCardId(cardByUsername.getId());
        if (byFromCardId != null) return byFromCardId;
        return null;
    }

    //Admin uchun
    public List<Outcome> getAllOutcome() {
        List<Outcome> all = outcomeRepository.findAll();
        return all;

    }

}
