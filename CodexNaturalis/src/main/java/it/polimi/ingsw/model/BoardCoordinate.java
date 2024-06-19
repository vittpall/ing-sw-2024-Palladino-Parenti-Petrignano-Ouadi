package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BoardCoordinate(@JsonProperty("x") String x, @JsonProperty("y") String y,
                              @JsonProperty("score") int score) {
}
