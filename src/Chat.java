import java.util.ArrayList;

public class Chat
{
    /** Attributes */
    private Message[] msgs;
    private ArrayList<Server.ClientListener> users;
    private int id;
    // ## Implementation preserve start class attributes. 
    // ## Implementation preserve end class attributes.
    /**
     * Operation
     *
     * @param msg
     */
    public void addMsg ( Message msg )
    {
        // ## Implementation preserve start class method.addMsg@void@@@Message 
        // ## Implementation preserve end class method.addMsg@void@@@Message 
    }
    /**
     * Operation
     *
     * @param usr
     */
    public void addUser ( String usr )
    {
        // ## Implementation preserve start class method.addUser@void@@@String 
        // ## Implementation preserve end class method.addUser@void@@@String 
    }
    /**
     * Operation
     *
     * @param usr
     */
    public void rmvUser ( Server.ClientListener usr )
    {
        // ## Implementation preserve start class method.rmvUser@void@@@ClientListener 
        // ## Implementation preserve end class method.rmvUser@void@@@ClientListener 
    }

    public int getLength() {
        return this.msgs.length;
    }

    public Message[] getMsgs() {
        return msgs;
    }

    public int getId(){
        return id;
    }

    public Server.ClientListener[] getUsers() {
        return users.toArray(new Server.ClientListener[0]);
    }
}
