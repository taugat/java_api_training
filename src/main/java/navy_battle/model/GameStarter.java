package navy_battle.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStarter {

    String id;
    String url;
    String message;

    public GameStarter(@JsonProperty(value = "id", required = true) String id,
                       @JsonProperty(value = "url", required = true) String url,
                       @JsonProperty(value = "message", required = true) String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
