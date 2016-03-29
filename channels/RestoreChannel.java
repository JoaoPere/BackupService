package channels;

import peers.*;

import java.io.IOException;

/**
 * Created by Joao Pereira on 22-03-2016.
 */
public class RestoreChannel extends Channel{
    public RestoreChannel(Peer peer, String addr, int port) throws IOException {
        super(peer, addr, port);
    }
}
