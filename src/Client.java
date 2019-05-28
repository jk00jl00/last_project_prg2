import javax.print.DocFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client
// ## Implementation preserve start class extends. 
// ## Implementation preserve end class extends. 

// ## Implementation preserve start class inheritence. 
// ## Implementation preserve end class inheritence. 

{
    /** Attributes */
    private UI ui;
    private ObjectInputStream serverInput;
    private ObjectOutputStream serverOutput;
    private Chat chat;
    private String serverAddress;
    private int serverPort;
    ServerListener listener;
    private Thread listenerThread;

    public Client(String serverAddress, int port, int ui) {
        this.serverAddress = serverAddress;
        this.serverPort = port;

        this.listener = new ServerListener();

        listenerThread = new Thread(listener);
        listenerThread.start();

        //Checks for the what UI to use as specified in the ui in.
        this.ui = (ui == 0) ? new ConsoleUI() : new ClientUI();
        this.ui.addActionListener(new GUIListener());
    }
    // ## Implementation preserve start class attributes. 
    // ## Implementation preserve end class attributes.



    /**
     * Attempts to log into the server.
     *
     * @param 
     */
    private void attemptLogin ()
    {
        Message m = new Message();

        m.setType(Message.TYPE.REQ);
        m.setUser(ui.getUsername());
        m.setMsg("login " + ui.getPassword());

        listener.sendMessage(m);
    }
    /**
     * Operation
     *
     */
    private void attemptNewUser (  )
    {
        // ## Implementation preserve start class method.attemptNewUser@void@@ 
        // ## Implementation preserve end class method.attemptNewUser@void@@ 
    }
    /**
     * Operation
     *
     */
    private void setRegister (  )
    {
        // ## Implementation preserve start class method.setRegister@void@@ 
        // ## Implementation preserve end class method.setRegister@void@@ 
    }
    /**
     * Operation
     *
     */
    private void setLogin (  )
    {
        // ## Implementation preserve start class method.setLogin@void@@ 
        // ## Implementation preserve end class method.setLogin@void@@ 
    }
    /**
     * Operation
     *
     */
    private void setChatWindow (  )
    {
        // ## Implementation preserve start class method.setChatWindow@void@@ 
        // ## Implementation preserve end class method.setChatWindow@void@@ 
    }
    /**
     * Operation
     *
     * @param msg
     */

    /**
     * Operation
     *
     */
    private void addFriend (  )
    {
        // ## Implementation preserve start class method.addFriend@void@@ 
        // ## Implementation preserve end class method.addFriend@void@@ 
    }
    /**
     * Operation
     *
     */
    private void addChat (  )
    {
        // ## Implementation preserve start class method.addChat@void@@ 
        // ## Implementation preserve end class method.addChat@void@@ 
    }
    /**
     * Operation
     *
     */
    private void changeChat (  )
    {
        // ## Implementation preserve start class method.changeChat@void@@ 
        // ## Implementation preserve end class method.changeChat@void@@ 
    }
    /**
     * Operation
     *
     */
    private void logout (  )
    {
        // ## Implementation preserve start class method.logout@void@@ 
        // ## Implementation preserve end class method.logout@void@@ 
    }
    /**
     * Operation
     *
     * @param msg
     */
    private void addToCurrentChat ( Message msg )
    {
        // ## Implementation preserve start class method.addToCurrentChat@void@@@Message 
        // ## Implementation preserve end class method.addToCurrentChat@void@@@Message 
    }
    // ## Implementation preserve start class other.operations. 
    // ## Implementation preserve end class other.operations.
    class ServerListener implements Runnable
    {

        private Socket socket;

        private boolean listening;

        public ServerListener(){
            listening = true;
            try {
                socket = new Socket(serverAddress, serverPort);
                serverInput = new ObjectInputStream(socket.getInputStream());
                serverOutput = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessage ( Message msg )
        {
            try {
                serverOutput.writeObject(msg);
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
            try {
                while (listening){
                    System.out.println("hej");
                    Message m = (Message) serverInput.readObject();

                    System.out.println(m.type);

                    switch (m.type){
                        case MSG:
                            break;
                        case REQ:
                            handleReq(m);
                            break;
                        case ALRT:
                            break;
                    }

                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void stop(){
            listening = false;
        }

        @Override
        public void run() {
            listen();
        }
    }

    private void handleReq(Message m) {
        switch (m.getMsg().split(" ")[0]){
            case "loggedIn":
                System.out.println(m.getText());
                String ars = m.getMsg().replaceAll("loggedIn ", "");

                Pattern pattern = Pattern.compile("(\\[[\\w,#]*\\])");
                Matcher matcher = pattern.matcher(ars);
                if(!matcher.find()) System.out.println("error");;

                String chats = matcher.group();


                if(!matcher.find()) System.out.println("error");
                String friends = matcher.group();


                chats = chats.replaceAll("[\\[\\]]*", "");
                friends = friends.replaceAll("[\\[\\]]*", "");

                String[] chatar;
                String[] friendar;

                if(chats.isEmpty()) chatar = new String[]{"No chats"};
                else chatar = chats.split(",");

                if(friends.isEmpty()) friendar = new String[]{"No firends"};
                else friendar = friends.split(",");

                ui.enterChatPage(chatar, friendar);
                break;
        }
    }

    private class GUIListener implements ActionListener
// ## Implementation preserve start class extends.
// ## Implementation preserve end class extends.

// ## Implementation preserve start class inheritence.
// ## Implementation preserve end class inheritence.

    {
        // ## Implementation preserve start class attributes.
        // ## Implementation preserve end class attributes.
        // ## Implementation preserve start class associations.
        // ## Implementation preserve end class associations.
        /**
         * Operation
         *
         * @param ActionEvent e
         *
         */
        @Override
        public void actionPerformed (  ActionEvent e )
        {
            UI u = ui;
            if(u instanceof ConsoleUI) {

                switch(e.getActionCommand()){
                    case "login":
                        onLogIn();
                        break;
                    case "newuser":
                        onNewUser();
                        break;
                    case "register":
                        break;
                    case "back":
                        onBack();
                        break;
                }
            }
        }
        /**
         * Operation
         *
         */
        public void onAddChat (  )
        {
            // ## Implementation preserve start class method.onAddChat@void@@
            // ## Implementation preserve end class method.onAddChat@void@@
        }
        /**
         * Operation
         *
         */
        public void onSelectedChat (  )
        {
            // ## Implementation preserve start class method.onSelectedChat@void@@
            // ## Implementation preserve end class method.onSelectedChat@void@@
        }
        /**
         * Operation
         *
         */
        private void onChatEnter (  )
        {
            // ## Implementation preserve start class method.onChatEnter@void@@
            // ## Implementation preserve end class method.onChatEnter@void@@
        }
        /**
         * Operation
         *
         * @return
         */
        private void onLogIn (  )
        {
            attemptLogin();
        }
        /**
         * Operation
         *
         */
        private void onNewUser (  )
        {
            ui.enterRegisterPage();
        }
        /**
         * Operation
         *
         */
        private void onBack (  )
        {
            ui.enterLoginPage();
        }
        /**
         * Operation
         *
         */
        private void onLogOut (  )
        {
            // ## Implementation preserve start class method.onLogOut@void@@
            // ## Implementation preserve end class method.onLogOut@void@@
        }
        /**
         * Operation
         *
         */
        private void onAddFriend (  )
        {
            // ## Implementation preserve start class method.onAddFriend@void@@
            // ## Implementation preserve end class method.onAddFriend@void@@
        }
        // ## Implementation preserve start class other.operations.
        // ## Implementation preserve end class other.operations.
    }
}

// ## Implementation preserve start class closing. 
// ## Implementation preserve end class closing. 
