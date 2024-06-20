package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BoardCoordinate(@JsonProperty("x") String x, @JsonProperty("y") String y,
                              @JsonProperty("score") int score) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardCoordinate that = (BoardCoordinate) obj;
        return score == that.score &&
                x.equals(that.x) &&
                y.equals(that.y);
    }
}
