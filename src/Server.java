import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server

{
    /** Attributes */
    private ArrayList<Chat> activeChats;
    private ArrayList<ClientListener> activeClients;
    private boolean running;
    private final int port;
    private final String adress;
    private ServerSocket serverSocket;
    private static int idCounter = 0;
    private ServerConsole console;

    public Server(String address, int port){
        activeClients = new ArrayList<>();
        this.port = port;
        this.adress = address;
        this.console = new ServerConsole();
        this.running = true;
        try {
            serverSocket = new ServerSocket(port);
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects clients that do not respond.
     * @param cl The non responding client.
     */
    private void handleUnexpectedDisconnect (ClientListener cl)
    {
        cl.close();

        System.out.println(cl.id + ": disconnect");

    }

    /**
     * Handles a message that has come to the server.
     *
     * @param msg The message that has been received.
     * @param chat That chat the message is sent in and therefore that chat it will be sent to.
     */
    private void handleMsg(Message msg, int chat){
        //Checks if the message is private and if it is it only sends it to that user.
        if (msg.getMsg().startsWith("@")){
            String usr = msg.getMsg().split(" ", 1)[0];

            if(!sendPrivateMessage(msg, usr, chat));
        } else{
            broadcastToChat(msg, chat);
        }
    }

    /**
     * Handles the alert to users as in when someone logs in or disconnects.
     * @param msg The message containing the alert message
     * @param c The chat that gets the alert
     */
    private void handleAlrt(Message msg, int c){
        //Checks if the alert is private and if it is it only sends it to that user.
        if (msg.getMsg().startsWith("@")){
            String usr = msg.getMsg().split(" ", 1)[0];

            if(!sendPrivateMessage(msg, usr, c));
        } else{
            broadcastToChat(msg, c);
        }
    }

    /**
     * Handles requests such as logins and registrations.
     * @param msg The Message with the request.
     */
    private void handleReq(Message msg, ClientListener cl){
        String[] request = msg.getMsg().split(" ");
        switch (request[0]){
            case "login":
                Message m = new Message();
                m.setType(Message.TYPE.REQ);
                m.setMsg("loggedIn [hello,I,Am,Chat],[I,am,friend]");
                respond(m, cl);
                break;
            case "newchat":
                break;
            case "enterchat":
                break;
        }
    }

    private void respond(Message msg, ClientListener cl) {
        cl.sendToClient(msg);
    }

    /**
     * Contains the main loop of the server that checks for new connections.
     *
     */
    private void run (  ) throws IOException
    {
        while(running){
            Socket s = serverSocket.accept();
            ClientListener c = new ClientListener(s);

            if(c != null)
                activeClients.add(c);
        }
    }
    /**
     * Closes all the clients and the server after saving the chats.
     *
     */
    private void close (  )
    {
        for(ClientListener cl : activeClients){
            cl.close();
        }

        for(Chat c: activeChats){
            //save content
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.running = false;
    }
    /**
     * Broadcasts a message to an entire chat
     *
     * @param msg The message to be sent
     * @param chat The id of the chat the message will be sent to.
     */
    private void broadcastToChat (Message msg, int chat )
    {
        Chat sendTo = null;

        for(Chat c: activeChats){
            if(c.getId() == chat) {
                sendTo = c;
                break;
            }
        }

        if(sendTo == null) return;

        for(ClientListener cl: sendTo.getUsers()){
            cl.sendToClient(msg);
        }
    }
    /**
     * Sends a message only to a specific user.
     * @param msg The message that will be sent
     * @param usr The target user
     * @param chat That chat they are expected to be in.
     * @return boolean Returns true if the user was found in the chat and the message sent.
     */
    private boolean sendPrivateMessage (Message msg, String usr, int chat )
    {

        return true;
    }

    class ClientListener implements Runnable{

        /** Attributes */
        private String username;
        private int chatId;
        private int id;
        private ObjectOutputStream outStream;
        private ObjectInputStream inStream;
        private Socket socket;
        private Thread thread;
        private boolean running;

        public ClientListener(Socket s) {
            this.socket = s;
            this.id = idCounter++;
            try {
                this.outStream = new ObjectOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.thread = new Thread(this);
            this.thread.start();
        }

        @Override
        public void run() {
            this.running = true;
            listen();
        }

        /**
         * Operation
         *
         * @param msg
         */
        public void sendToClient ( Message msg )
        {
            try {
                outStream.writeObject(msg);
            } catch (IOException e) {
                handleUnexpectedDisconnect(this);
            }
        }
        /**
         * Operation
         *
         */
        public synchronized void close (  )
        {
            this.running = false;
            try {
                this.outStream.close();
                this.inStream.close();
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        /**
         * Operation
         *
         */
        private void listen (  )
        {
            while(this.running){
                Message msg = null;
                try {
                    this.inStream = new ObjectInputStream(socket.getInputStream());

                    msg = (Message) inStream.readObject();

                } catch (IOException | ClassNotFoundException e) {
                    handleUnexpectedDisconnect(this);
                }

                if(msg != null) {
                    switch (msg.type){
                        case MSG:
                            handleMsg(msg, chatId);
                            break;
                        case ALRT:
                            handleAlrt(msg, chatId);
                            break;
                        case REQ:
                            handleReq(msg, this);
                            break;
                    }
                }
            }
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
