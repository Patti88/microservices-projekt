package se.iths.quoteservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.quoteservice.model.Quote;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private List<Quote> quotes = List.of(
            new Quote("The only way to do great work is to love what you do.", "Steve Jobs"),
            new Quote("Strive not to be a success, but rather to be of value.", "Albert Einstein"),
            new Quote("The mind is everything. What you think you become.", "Buddha")
    );

    private Random random = new Random();

    @GetMapping("/random")
    public ResponseEntity<Quote> getRandomQuote() {
        int randomIndex = random.nextInt(quotes.size());
        return ResponseEntity.ok(quotes.get(randomIndex));
    }
}