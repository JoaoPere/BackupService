package channels;

import peers.*;

import java.io.IOException;

/**
 * Created by Joao Pereira on 29-03-2016.
 */
public class OrdersChannel extends Channel {
    public OrdersChannel(Peer peer, String addr, int port) throws IOException {
        super(peer, addr, port);
    }
}
