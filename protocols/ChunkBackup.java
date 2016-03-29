package protocols;

import java.util.*;

import messages.*;
import channels.*;
import peers.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Joao Pereira on 22-03-2016.
 */

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.security.MessageDigest;

public class ChunkBackup extends Thread {
    public BackupChannel backup_channel;
    public ControlChannel control_channel;

    protected String packet_parsed_received;
    protected DatagramPacket packet_sent;

    String fileId;
    String filename;
    Path path;

    int rep_degree;
    Peer peer;

    int num_tries = 0;
    int current_replication = 0;
    int time;


    public ChunkBackup(String file, int replication, BackupChannel b_channel, ControlChannel c_channel, Peer peer){
        filename = file;
        rep_degree = replication;

        backup_channel = b_channel;
        control_channel = c_channel;

        this.peer = peer;

        fileId = getFileId(filename);
    }

    public void run(){
        time = 1000;
        int chunkNo = 0, count = 0;

        path = Paths.get(filename);

        byte[] total = null, data = null;
        try {
            total = Files.readAllBytes(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        int times = (int) Math.ceil((double)total.length / 64000);

        if((double)total.length % 64000 == 0) {
            times =+ 1;
        }

        while(num_tries < 5 && chunkNo < times) {
            data = null;

            if((chunkNo+1) == times) {
                int lastsize = total.length - 64000*chunkNo;
                data = splitFile(path, chunkNo, lastsize);
            }

            data = splitFile(path, chunkNo, 64000);

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            PutChunk put_chunk = new PutChunk("PUTCHUNK",peer.getVersion(),peer.getId(),fileId,chunkNo,rep_degree,data);

            byte[] buf;
            buf = put_chunk.toString().getBytes();
            packet_sent = new DatagramPacket(buf, buf.length);

            try {
                num_tries++;
                backup_channel.getMcSocket().send(packet_sent);

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                control_channel.getMcSocket().setSoTimeout(time);

                while(true) {
                    receive();
                }

            } catch (SocketTimeoutException e) {
                count++;
                time *= 2;
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] splitFile(Path path, int chunkNo, int size){
        byte fileContent[] = null;

        try {
            fileContent = Files.readAllBytes(path);
            } catch (IOException e) {
        }

        byte chunk[] = Arrays.copyOfRange(fileContent, 64000*chunkNo, (64000*chunkNo)+size);

        return chunk;

    }

    public void receive() {
        packet_parsed_received = control_channel.receive();

        if (packet_parsed_received != null) {
            String[] parsed = parse(packet_parsed_received);

        }
    }

    public String[] parse(String header)
    {
        header = header.replaceAll("[\n|\r]", "");
        String[] fields = null;
        fields = header.split(" ");
        return fields;
    }

    /*public boolean checkSender(String sender){
        for (int i = 0; i < current_replication ; i++) {
            if(peers.get(i).equals(sender))
                return true;
        }
        return false;
    }*/

    public static String getFileId(String file){
        try{
            MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mdigest.digest(file.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < result.length; i++) {
                String hex = Integer.toHexString(0xff & result[i]);

                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
