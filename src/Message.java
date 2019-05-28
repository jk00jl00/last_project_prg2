import java.io.Serializable;

public class Message implements Serializable
// ## Implementation preserve start class extends. 
// ## Implementation preserve end class extends. 

// ## Implementation preserve start class inheritence. 
// ## Implementation preserve end class inheritence. 

{
    public void setType(TYPE type) {
        this.type = type;
    }

    /** Attributes */
    public enum TYPE{
        MSG, ALRT, REQ
    };

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public String getText() {
        return "[" + getTimestamp() + "] " + getUser() + ":\t" + getMsg() + "\n";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public TYPE type;
    private String timestamp;
    private String msg;
    private String user;
    // ## Implementation preserve start class attributes. 
    // ## Implementation preserve end class attributes. 
    // ## Implementation preserve start class associations. 
    // ## Implementation preserve end class associations. 
    // ## Implementation preserve start class other.operations. 
    // ## Implementation preserve end class other.operations. 
}

// ## Implementation preserve start class closing. 
// ## Implementation preserve end class closing. 
