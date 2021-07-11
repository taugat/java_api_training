package fr.lernejo.navy_battle.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GameStarter {

    private final UUID id;
    private final String url;
    private final String message;

    public GameStarter(@JsonProperty(value = "id", required = true) String id,
                       @JsonProperty(value = "url", required = true) String url,
                       @JsonProperty(value = "message", required = true) String message) {
        this.id = UUID.fromString(id);
        this.url = url;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GameStarter{" +
            "id='" + id + '\'' +
            ", url='" + url + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
