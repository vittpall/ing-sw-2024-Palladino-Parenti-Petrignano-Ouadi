package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a coordinate on the board.
 * It has two string variables for x and y coordinates and an integer variable for the score.
 */
public record BoardCoordinate(@JsonProperty("x") String x, @JsonProperty("y") String y,
                              @JsonProperty("score") int score) {

    /**
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
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
