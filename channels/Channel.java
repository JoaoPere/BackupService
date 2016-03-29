package channels;

import peers.*;

/**
 * Created by Joao Pereira on 25-03-2016.
 */

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class Channel extends Thread {
    protected int MAX_SIZE = 64100;
    protected InetAddress address;
    protected Integer port;
    protected MulticastSocket mcSocket;
    protected Peer peer;


    public Channel(Peer peer, String ip, Integer port){
        this.port = port;
        this.peer = peer;

        try {
            this.address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("IP is: " + ip);
        try {
            mcSocket = new MulticastSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mcSocket.joinGroup(this.address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(DatagramPacket packet){
        packet.setAddress(address);
        packet.setPort(port);

        try {
            this.mcSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        receive();
    }

    public String receive() {
        byte[] buf = new byte[MAX_SIZE];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        try {
            System.out.println("listening backup channel on port: " + port + ", address: " + address.getHostName());
            mcSocket.receive(packet);

            if (packet.getData() != null) {
                String msg = new String(packet.getData());
                System.out.println("received: " + msg);
                return msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        this.mcSocket.close();
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public MulticastSocket getMcSocket() {
        return mcSocket;
    }

    public void setMcSocket(MulticastSocket mcSocket) {
        this.mcSocket = mcSocket;
    }

    public Peer getPeer() {
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }
}
