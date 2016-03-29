package channels;

import peers.*;

import java.io.IOException;

/**
 * Created by Joao Pereira on 22-03-2016.
 */
public class ControlChannel extends Channel{
    public ControlChannel(Peer peer, String addr, int port) throws IOException {
        super(peer, addr, port);
    }
}
