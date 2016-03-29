package message;

/**
 * Created by Joao Pereira on 25-03-2016.
 */
public class Body {
    byte[] body;

    public Body(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
