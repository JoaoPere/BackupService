package message;

/**
 * Created by Joao Pereira on 25-03-2016.
 */
public class Header {
    protected String version;
    protected int sender_id;
    protected String file_id;
    protected int chunk_no;
    protected String message_type;
    protected int rep_degree;
    protected String CRLF  = "\n\r";

    public Header(String message_type, String version, int sender_id, String file_id, int chunk_no, int rep_degree) {
        this.message_type = message_type;
        this.version = version;
        this.sender_id = sender_id;
        this.file_id = file_id;
        this.chunk_no = chunk_no;
        this.rep_degree = rep_degree;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public int getChunk_no() {
        return chunk_no;
    }

    public void setChunk_no(int chunk_no) {
        this.chunk_no = chunk_no;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public int getRep_degree() {
        return rep_degree;
    }

    public void setRep_degree(int rep_degree) {
        this.rep_degree = rep_degree;
    }

    public String getCRLF() {
        return CRLF;
    }

    public void setCRLF(String CRLF) {
        this.CRLF = CRLF;
    }
}
