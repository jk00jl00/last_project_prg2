import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The ConsoleUI class is the console UI used by the client. It handles input and output from and to the user.
 */
public class ConsoleUI extends UI implements Runnable

{
    /** Attributes */
    private String chatInput;
    private String[] chats;
    private String[] friends;
    private String[] chat;
    private String password;
    private String username;
    private ActionListener listener;
    private Thread thread;
    private String passConfirm;
    private String email;

    State state;
    private boolean running = false;

    /**
     * The constructor. Sets up the thread and starts on the login page.
     */
    public ConsoleUI(){
        this.thread = new Thread(this);
        this.running = true;
        this.enterLoginPage();
        this.state = new State("Login");
        this.thread.start();
    }

    /**
     * Checks for the input from the user taking actions that can be made when on the login page.
     * If the user enters login they are asked for username and password. If they enter register they
     * are taken to the registration page and entering exit exits the program.
     * @param in The user input from the main loop.
     */
    private void loginInput(String in){
        //Checking what input was made.
        if(in.matches("login")){
            System.out.print(System.lineSeparator());

            System.out.print("Enter Username: ");
            BufferedReader pin = new BufferedReader(new InputStreamReader(System.in));
            try {
                this.username = pin.readLine();
            }
            catch (IOException e){
                System.out.println("Error trying to read the username!");
                System.exit(1);
            }



            System.out.print("Enter Password: ");
            //For masking the input.
            ConsoleEraser consoleEraser = new ConsoleEraser();
            pin = new BufferedReader(new InputStreamReader(System.in));
            consoleEraser.start();
            try {
                this.password = pin.readLine();
            }
            catch (IOException e){
                System.out.println("Error trying to read your password!");
                System.exit(1);
            }

            consoleEraser.halt();

            //Alerts the Client that a login was attempted.
            this.listener.actionPerformed(new ActionEvent(this, 0, "login"));
        } else if(in.matches("register")){
            //Alerts the Client that the user wishes to register a user.
            this.listener.actionPerformed(new ActionEvent(this, 1, "newuser"));
        } else if(in.matches("exit")) System.exit(0); //Exits the program.

    }

    /**
     * Handles inputs when the user has entered the registration page. If the user enters back they
     * are taken back to the login page. If they press enter without entering anything they start the
     * registration and are prompted to enter the information.
     *
     * @param in The input from the user in the main loop.
     */
    private void registerInput(String in){
        if(in.equals("")){
            System.out.print(System.lineSeparator());

            System.out.print("Enter Username: ");
            BufferedReader pin = new BufferedReader(new InputStreamReader(System.in));
            try {
                this.username = pin.readLine();
            }
            catch (IOException e){
                System.out.println("Error trying to read the username!");
                System.exit(1);
            }

            System.out.print("Enter Password: ");

            ConsoleEraser consoleEraser = new ConsoleEraser();
            pin = new BufferedReader(new InputStreamReader(System.in));
            consoleEraser.start();
            try {
                this.password = pin.readLine();
            }
            catch (IOException e){
                System.out.println("Error trying to read your password!");
                System.exit(1);
            }

            consoleEraser.halt();
            System.out.print("Confirm Password: ");

            consoleEraser = new ConsoleEraser();
            pin = new BufferedReader(new InputStreamReader(System.in));
            consoleEraser.start();
            try {
                this.passConfirm = pin.readLine();
            }
            catch (IOException e){
                System.out.println("Error trying to read your password!");
                System.exit(1);
            }

            consoleEraser.halt();

            //Breaks the process if the user enters the password wrong the second time.
            if(!passConfirm.equals(password)){
                System.out.println("Passwords did not match");
                this.enterRegisterPage();
                return;
            }

            System.out.print("Enter Email: ");
            pin = new BufferedReader(new InputStreamReader(System.in));
            try {
                this.email = pin.readLine();
            }
            catch (IOException e){
                System.out.println("Error trying to read the username!");
                System.exit(1);
            }

            //Alerting the Client of the registration attempt
            this.listener.actionPerformed(new ActionEvent(this, 3, "register"));
        } else if(in.matches("back")){
            this.listener.actionPerformed(new ActionEvent(this, 4, "back")); //Alerting the Client that the user wishes to return to the login page.
        }

    }

    /**
     * Handles the input when the user is in the chat hub page. They can logout by typing logout or add new friends by typing new [friend/chat] [name/#id].
     * They can also enter an existing chat by entering enter [name/#id].
     * @param in The input from the user in the main loop.
     */
    private void chatInput(String in){
        if(in.matches("logout")) this.listener.actionPerformed(new ActionEvent(this, 5, "logout"));
        else if(in.matches("new friend")) this.listener.actionPerformed(new ActionEvent(this, 6, "newfriend"));
        else if(in.matches("new chat")) this.listener.actionPerformed(new ActionEvent(this, 7, "newchat"));
        else if(in.matches("enter chat")) this.listener.actionPerformed(new ActionEvent(this, 8, "enterchat"));

    }

    /**
     * Handles the input when the user is in a chat. They can either typ a message or go back by entering \back
     * @param in The input from the user in the main loop
     */
    private void inChatInput(String in){
        if(in.matches("\\\\back")) this.listener.actionPerformed(new ActionEvent(this, 9, "backchat")); //
        else{
            chatInput = in; //Sets the chat input to the entered text
            this.listener.actionPerformed(new ActionEvent(this, 10, "chatinput")); //Alerting the client that a message is ready.
        }
    }

    /**
     * Started when the thread starts. Waits for user input and handles it depending on what page the user is on.
     */
    @Override
    public void run() {

        while(running){
            Scanner sc = new Scanner(System.in);

            String in = sc.nextLine();

            switch (this.state.page){
                case "Login":
                    loginInput(in);
                    break;
                case "Register":
                    registerInput(in);
                    break;
                case "Chat":
                    chatInput(in);
                    break;
                case "inChat":
                    inChatInput(in);
                    break;
            }
        }
        //When the loop is closed the thread will by joined to the main thread.
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the loop by setting the running boolean to false.
     */
    public synchronized void stop(){
        this.running = false;
    }

    /**
     * Called when the user is entering the login page. Prints relevant information to the user and sets the state to the login page state.
     */
    @Override
    public void enterLoginPage() {
        System.out.print(System.lineSeparator());
        System.out.println("Enter login to login to the server. Enter register to register a new user. Enter exit to close down");
        System.out.print("Input: ");
        this.state = new State("Login");

    }

    /**
     * Called when the user is entering the register page. Prints relevant information and sets the state to the register page state.
     */
    @Override
    public void enterRegisterPage() {
        System.out.print(System.lineSeparator());
        System.out.println("Enter back to go back to the login page, press enter to start registering");
        this.state = new State("Register");

    }

    /**
     * Called when the user is entering the chat hub page. Sets the chat Sting[] and the friends String[] to the new arrays and prints them to the user.
     * Sets the state to the chat page state.
     * @param c The new String[] containing the chat names.
     * @param f The new String[] containing the friend names.
     */
    @Override
    public void enterChatPage(String[] c, String[] f) {
        this.state = new State("Chat");
        this.chats = c;
        this.friends = f;

        System.out.print(System.lineSeparator());
        System.out.print("Enter logout to logout. Type 'chat chatname' to enter the named chat. To add a new friend or chat enter 'new [chat/friend] [name/#id]'\n");
        System.out.print("\tchats\t\tfriends\n");
        System.out.print("\t-----------------------\n");
        int counter = (c.length > f.length) ? c.length : f.length;

        for(int i = 0; i < counter; i++){
            StringBuilder sb = new StringBuilder();

            sb.append("\t");
            if(i < c.length) sb.append(c[i]);
            sb.append("\t\t");
            if(i < f.length) sb.append(f[i]);
            sb.append("\n");

            System.out.print(sb.toString());
        }

    }

    /**
     *
     * @param msg
     */
    @Override
    public void updateChat(Message msg) {
        System.out.print("[" + msg.getTimestamp()+"] " + msg.getUser() + ":\t" + msg.getMsg());
        System.out.print(System.lineSeparator());
    }

    @Override
    public void setChat(Chat c) {
        this.state = new State("inChat");

        this.chat = new String[c.getLength()];



        for(int i = 0; i < c.getMsgs().length; i++)
            chat[i] = c.getMsgs()[i].getText();

        for(String s : chat){
            System.out.print(s);
        }
    }

    @Override
    public void clearText() {

    }

    @Override
    public String getUsername() {
        String user = this.username;

        this.username = "";

        return user;
    }

    @Override
    public String getPassword() {
        String pass = this.password;

        this.password = "";

        return pass;
    }

    @Override
    public String getEmail(){
        String e = this.email;

        this.email = "";

        return  e;
    }

    @Override
    public String getChatInput() {
        String chatI = this.chatInput;

        this.chatInput = "";

        return chatI;
    }

    @Override
    public void addActionListener(ActionListener a) {
        this.listener = a;
    }

    private class State {

        /**
         * The pages that the user can be on
         * Login, Register, Chat, inChat
         */

        String page;

        State(String s){
            this.page = s;
        }
    }

    /**
     * A class for erasing the inputs from the user.
     * user when the user is asked to enter their password
     */
    private static class ConsoleEraser extends Thread {
        private boolean running = true;
        public void run() {
            while (running) {
                System.out.print("\010*");
                try {
                    Thread.currentThread().sleep(1);
                }
                catch(InterruptedException e) {
                    break;
                }
            }
        }
        public synchronized void halt() {
            running = false;
        }
    }
}
