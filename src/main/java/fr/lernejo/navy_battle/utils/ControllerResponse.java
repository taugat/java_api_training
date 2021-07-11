package fr.lernejo.navy_battle.utils;

public class ControllerResponse {

    public interface iOnResponseSentListener{
        void onResponseSend();
    }

    public final int rCode;
    public final String response;
    public final iOnResponseSentListener onResponseSendListener;

    public ControllerResponse(int rCode, String response) {
        this.rCode = rCode;
        this.response = response;
        onResponseSendListener = null;
    }

    public ControllerResponse(int rCode, String response, iOnResponseSentListener onResponseSendListener) {
        this.rCode = rCode;
        this.response = response;
        this.onResponseSendListener = onResponseSendListener;
    }
}
