package se.iths.jokeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.jokeservice.model.Joke;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/jokes")
public class JokeController {

    private final List<String> jokes = List.of(
            "Why don't scientists trust atoms? Because they make up everything!",
            "Parallel lines have so much in common... it's a shame they'll never meet.",
            "Why did the scarecrow win an award? Because he was outstanding in his field!"
    );

    private final Random random = new Random();

    @GetMapping("/random")
    public ResponseEntity<Joke> getRandomJoke() {
        int randomIndex = random.nextInt(jokes.size());
        return ResponseEntity.ok(new Joke(jokes.get(randomIndex)));
    }
}