package at.stnwtr.guessinggame.score;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Long> {

    List<Score> findTop10ByOrderByScore();
}
