package peers;

import channels.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Joao Pereira on 29-03-2016.
 */

public class Peer {
    private final int mcast_port = 8000;
    private String mcast_address;
    private static int id;
    private String version = "1.0";

    private static OrdersChannel ord_chn;
    private static ControlChannel mc_chn;
    private static BackupChannel mdb_chn;
    private static RestoreChannel mdr_chn;

    public static void main(String[] args) throws UnknownHostException  {
        if (args[0].matches("\\d*") && args[1].matches("(\\d{1,3}.){3}\\d{1,3}") &&
            args[2].matches("\\d{4}") && args[3].matches("(\\d{1,3}.){3}\\d{1,3}") &&
            args[4].matches("\\d{4}") && args[5].matches("(\\d{1,3}.){3}\\d{1,3}") &&
            args[6].matches("\\d{4}")) {

            new Peer(Integer.parseInt(args[0]),args[1],Integer.parseInt(args[2]),args[3],Integer.parseInt(args[4]),args[5],Integer.parseInt(args[6]));
        } else {
            System.out.println("Wrong usage: java Peer <id> <mc_ip> <mc_port> <mdb_ip> <mdb_port> <mdr_ip> <mdr_port>");
            System.exit(-1);
        }
    }

    public Peer(int _id, String ip_mc, int port_mc, String ip_mdb, int port_mdb, String ip_mdr, int port_mdr) throws UnknownHostException {
        id = _id;

        this.mcast_address = new String("224.0.224." + this.id);

        try {
            ord_chn = new OrdersChannel(this, mcast_address, mcast_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ord_chn.start();

        try {
            mc_chn = new ControlChannel(this,ip_mc, port_mc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mc_chn.start();

        try {
            mdb_chn = new BackupChannel(this,ip_mdb, port_mdb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mdb_chn.start();

        try {
            mdr_chn = new RestoreChannel(this,ip_mdr, port_mdr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mdr_chn.start();
    }

    @Override
    public boolean equals(Object peer){
        final Peer other = (Peer) peer;

        if(other.getId() == this.id) return true;
        else return false;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Peer.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
