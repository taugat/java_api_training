package fr.lernejo.navy_battle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class CellStatus
{
    private final eConsequence consequence;
    private final boolean shipLeft;

    public CellStatus(@JsonProperty(value = "consequence", required = true) eConsequence consequence, @JsonProperty(value = "shipLeft", required = true) boolean shipLeft) {
        this.consequence = consequence;
        this.shipLeft = shipLeft;
    }

    enum eConsequence {
        MISS("miss"),
        HIT("hit"),
        STUNK("stunk");
        private final String consequence;
        eConsequence(String consequence) {
            this.consequence = consequence;
        }
        @JsonValue
        public String toString(){
            return consequence;
        }
    }

    public eConsequence getConsequence() {
        return consequence;
    }

    public boolean isShipLeft() {
        return shipLeft;
    }

    @Override
    public String toString() {
        return "CellStatus{" +
            "consequence=" + consequence +
            ", shipLeft=" + shipLeft +
            '}';
    }
}
