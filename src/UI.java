import java.awt.event.ActionListener;

public abstract class UI
{
    /**
     * Operation
     *
     */
    public abstract void enterLoginPage(  );
    /**
     * Operation
     *
     */
    public abstract void enterRegisterPage();
    /**
     * Operation
     *
     */
    public abstract void enterChatPage(String[] c, String[] f);
    /**
     * Operation
     *
     * @param msg
     */
    public abstract void updateChat(Message msg);
    /**
     * Operation
     *
     * @param c
     */
    public abstract void setChat(Chat c);
    /**
     * Operation
     *
     */
    public abstract void clearText();

    public abstract String getUsername();

    public abstract String getPassword();

    public abstract String getEmail();

    public abstract String getChatInput();


    public abstract void addActionListener(ActionListener a);
}
