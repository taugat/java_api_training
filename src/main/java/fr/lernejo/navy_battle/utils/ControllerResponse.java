package fr.lernejo.navy_battle.utils;

public class ControllerResponse {

    public final int rCode;
    public final String response;

    public ControllerResponse(int rCode, String response) {
        this.rCode = rCode;
        this.response = response;
    }
}
