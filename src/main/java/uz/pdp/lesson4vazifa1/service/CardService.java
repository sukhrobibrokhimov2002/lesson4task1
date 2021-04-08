package uz.pdp.lesson4vazifa1.service;//package uz.pdp.lesson4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.lesson4vazifa1.entity.Card;
import uz.pdp.lesson4vazifa1.payload.CardDto;
import uz.pdp.lesson4vazifa1.payload.ResultDto;
import uz.pdp.lesson4vazifa1.repository.CardRepository;
import uz.pdp.lesson4vazifa1.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;

    public ResultDto addCard(CardDto card, HttpServletRequest httpServletRequest) {
        String tokenBearer = httpServletRequest.getHeader("Authorization");
        String token = tokenBearer.substring(7);
        String usernameFromToken = jwtProvider.getUsernameFromToken(token);

        boolean existsByNumber = cardRepository.existsByNumber(card.getNumber());
        if (existsByNumber) return new ResultDto(false, "Bunday karta mavjud");
        Card card1 = new Card();
        card1.setUsername(usernameFromToken);
        card1.setBalance(card.getBalance());
        card1.setActive(true);
        card1.setExpiredDate(card.getExpiredDate());
        card1.setNumber(card.getNumber());
        cardRepository.save(card1);
        return new ResultDto(true, "Successfully added");
    }

    //Admin uchun
    public List<Card> getAllCards() {
        List<Card> all = cardRepository.findAll();
        return all;
    }

    //User uchun

    public Card getCardByUser(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String usernameFromToken = jwtProvider.getUsernameFromToken(token);
        Card byUserName = cardRepository.findingByUsername(usernameFromToken);
        return byUserName;
    }

    public ResultDto deleteCard(Integer id) {
        try {
            cardRepository.deleteById(id);
            return new ResultDto(true, "Successfully deleted");
        } catch (Exception e) {
            return new ResultDto(false, "Error in deleting");
        }
    }

    public ResultDto edit(CardDto card, Integer id,HttpServletRequest httpServletRequest) {
        String tokenBearer = httpServletRequest.getHeader("Authorization");
        String token = tokenBearer.substring(7);
        String usernameFromToken = jwtProvider.getUsernameFromToken(token);

        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) return new ResultDto(false, "Card not found");
        boolean existsByNumberAndIdNot = cardRepository.existsByNumberAndIdNot(card.getNumber(), id);
        if (existsByNumberAndIdNot) return new ResultDto(false, "Karta mavjud");

        Card editedCard = optionalCard.get();
        editedCard.setNumber(card.getNumber());
        editedCard.setActive(true);
        editedCard.setUsername(usernameFromToken);
        editedCard.setBalance(card.getBalance());
        editedCard.setExpiredDate(card.getExpiredDate());
        cardRepository.save(editedCard);
        return new ResultDto(true, "Successfully edited");
    }
}
