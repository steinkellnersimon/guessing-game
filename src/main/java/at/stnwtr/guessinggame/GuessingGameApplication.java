package at.stnwtr.guessinggame;

import at.stnwtr.guessinggame.score.Score;
import at.stnwtr.guessinggame.score.ScoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class GuessingGameApplication implements CommandLineRunner {

    public GuessingGameApplication(ScoreRepository scores) {
        this.scores = scores;
    }

    private final ScoreRepository scores;

    @Override
    public void run(String... args) throws IOException {
        showWelcomeBoard();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int number = new Random().nextInt(101);
        int attempts = 0;
        int guess = -1;

        long start = -1;

        do {
            System.out.print(++attempts + ". Attempt: ");

            try {
                guess = Integer.parseInt(reader.readLine());
                if (start == -1) {
                    start = System.currentTimeMillis();
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number!" + System.lineSeparator());
                attempts--;
                continue;
            }

            if (guess < 0 || guess > 100) {
                System.out.println("Your guess is out of the valid range!" + System.lineSeparator());
                attempts--;
                continue;
            }

            if (guess > number) {
                System.out.println("You guess was too high!" + System.lineSeparator());
            } else if (guess < number) {
                System.out.println("You guess was too low!" + System.lineSeparator());
            }
        } while (guess != number);

        double time = (System.currentTimeMillis() - start) / 1000.;
        System.out.println("Congratulations, you guessed right!" + System.lineSeparator());
        System.out.print("Enter your name: ");
        String name = reader.readLine();
        reader.close();

        scores.save(new Score(name, number, attempts, time));
        System.out.println("You were saved to the leaderboard, " + name + '!' + System.lineSeparator());

        showLeaderboard();
    }

    private void showLeaderboard() {
        System.out.println("+-------------+");
        System.out.println("| Leaderboard |");
        System.out.println("+------+------+-----------+----------+-------+-------+");
        System.out.println("| Rank | Name             | Attempts | Time  | Score |");
        System.out.println("+------+------------------+----------+-------+-------+");

        Score[] leaderboard = StreamSupport.stream(scores.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Score::getScore))
                .limit(10)
                .toArray(Score[]::new);

        for (int i = 0; i < leaderboard.length; i++) {
            Score current = leaderboard[i];
            String name = current.getPlayerName().length() >= 16 ? current.getPlayerName().substring(0, 16) : current.getPlayerName();

            System.out.printf("| %4d | %-16s | %8d | %5.1f | %5d |%s",
                    (i + 1), name, current.getAttempts(),
                    current.getTime(), current.getScore(), System.lineSeparator());
            System.out.println("+------+------------------+----------+-------+-------+");
        }
    }

    private void showWelcomeBoard() {
        System.out.println("+----------------------+");
        System.out.println("| Number Guessing Game |");
        System.out.println("+----------------------+--------------+");
        System.out.println("| I chose a number between 1 and 100  |");
        System.out.println("| Both range ends can be included     |");
        System.out.println("+-------------------------------------+------+");
        System.out.println("| 1 Guess = +2 Seconds (to prevent spamming) |");
        System.out.println("| The timer starts after the first guess!    |");
        System.out.println("+--------------------------------------------+" + System.lineSeparator());
    }

    public static void main(String[] args) {
        SpringApplication.run(GuessingGameApplication.class, args);
    }
}
