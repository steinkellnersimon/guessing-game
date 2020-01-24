package at.stnwtr.guessinggame.score;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private long gameId;

    private String playerName;
    private int number;
    private int attempts;
    private double time;
    private long score;

    protected Score() {
    }

    public Score(String playerName, int number, int attempts, double time) {
        this.playerName = playerName;
        this.number = number;
        this.attempts = attempts;
        this.time = time;
        this.score = (long) (time * 10 + 2 * attempts * 10);
    }

    public long getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNumber() {
        return number;
    }

    public int getAttempts() {
        return attempts;
    }

    public double getTime() {
        return time;
    }

    public long getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return gameId == score1.gameId &&
                number == score1.number &&
                attempts == score1.attempts &&
                time == score1.time &&
                score == score1.score &&
                Objects.equals(playerName, score1.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerName, number, attempts, time, score);
    }

    @Override
    public String toString() {
        return "Score{" +
                "gameId=" + gameId +
                ", playerName='" + playerName + '\'' +
                ", number=" + number +
                ", attempts=" + attempts +
                ", timeInMillis=" + time +
                ", score=" + score +
                '}';
    }
}
