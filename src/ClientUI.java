import javax.swing.*;
import java.awt.event.ActionListener;

public class ClientUI extends UI
// ## Implementation preserve start class extends. 
// ## Implementation preserve end class extends. 

// ## Implementation preserve start class inheritence. 
// ## Implementation preserve end class inheritence. 

{
    /** Attributes */
    public JPasswordField passConfirm;
    public JTextField email;
    private int width;
    private int height;
    private String title;
    private JButton loginButton;
    private JButton newUserButton;
    private JButton logoutButton;
    private JTextField username;
    private JPasswordField password;
    private JTextArea chatInput;
    private String[] chats;
    private String[] Friends;
    private JTextArea chat;
    // ## Implementation preserve start class attributes. 
    // ## Implementation preserve end class attributes. 
    /** Associations */
    private Chat unnamed;

    @Override
    public void enterLoginPage() {

    }

    @Override
    public void enterRegisterPage() {

    }

    @Override
    public void enterChatPage(String[] c, String[] f) {

    }

    @Override
    public void updateChat(Message msg) {

    }

    @Override
    public void setChat(Chat c) {

    }

    @Override
    public void clearText() {

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getChatInput() {
        return null;
    }

    @Override
    public void addActionListener(ActionListener a) {

    }
    // ## Implementation preserve start class associations. 
    // ## Implementation preserve end class associations. 
    // ## Implementation preserve start class other.operations. 
    // ## Implementation preserve end class other.operations. 
}

// ## Implementation preserve start class closing. 
// ## Implementation preserve end class closing. 
