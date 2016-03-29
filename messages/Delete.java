package messages;

import message.*;

/**
 * Created by Joao Pereira on 25-03-2016.
 */
public class Delete{
    private Header header;
    private Body body;
    private String CRLF  = "\n\r";

    public Delete(String message_type, String version, int sender_id, String file_id, int chunk_no, int rep_degree){
        header = new Header(message_type, version, sender_id, file_id, chunk_no, rep_degree);
    }

    @Override
    public String toString() {
        return header.getMessage_type() + " " + header.getVersion() + " " + header.getSender_id() + " " + header.getFile_id() +  " " + CRLF + CRLF + body.getBody();
    }
}
