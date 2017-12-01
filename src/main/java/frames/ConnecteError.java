package frames;

import com.sun.awt.AWTUtilities;
import interfaces.DiyViews;
import override_view.BlankButton;
import override_view.BlankPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConnecteError extends JFrame implements DiyViews, ActionListener {

    private JLabel label_background, label_message;
    private JButton btnClose;
    private BlankPanel blankPanel;
    int mx = 0, my = 0, jfx = 0, jfy = 0;

    public ConnecteError()  {
        initViews();
        setViews();
        addViews();

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - mx + jfx, e.getYOnScreen() -my + jfy);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mx = e.getXOnScreen();
                my = e.getYOnScreen();
                jfx  = e.getX();
                jfy =  e.getY();
            }
        });
    }

    @Override
    public void initViews() {
        Font f = new Font("幼圆", Font.PLAIN, 15);
        UIManager.put("Label.font", f);
        UIManager.put("Label.foreground", Color.black);
        UIManager.put("Button.font", f);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("List.font", f);
        UIManager.put("CheckBox.font", f);
        UIManager.put("RadioButton.font", f);
        UIManager.put("ComboBox.font", f);
        UIManager.put("TextArea.font", f);
        UIManager.put("EditorPane.font", f);
        UIManager.put("ScrollPane.font", f);
        UIManager.put("ToolTip.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("TableHeader.font", f);
        UIManager.put("Table.font", f);

        btnClose = new BlankButton("   确定   ");
        blankPanel = new BlankPanel(225);

        label_message = new JLabel("找不到服务器，请确认IP和端口号正确后再重试");


    }

    @Override
    public void setViews() {
        label_message.setBounds(15,40,340,40);
        btnClose.setBounds(100,100,150,40);
        btnClose.addActionListener(this);
    }

    @Override
    public void addViews() {
        add(label_message);
        add(btnClose);
        add(blankPanel);
        setUndecorated(true);
        setSize(350,150);

        AWTUtilities.setWindowOpaque(this,false);
        setLocationRelativeTo(null);
        setVisible(true
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose){
            ConnecteError.this.dispose();
        }
    }

    public static void main(String[] args) {
        ConnecteError frame  = new ConnecteError();
    }
}
