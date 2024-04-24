import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClubGUI extends JFrame implements ActionListener {
  JPanel panelN = new JPanel();
  JPanel panelS = new JPanel();
  JPanel panelW = new JPanel();
  JPanel panelE = new JPanel();
  JPanel panelC = new JPanel();
  JPanel[] panels = { panelN, panelS, panelW, panelE, panelC };

  JTextField usernameBox = new JTextField();
  JPasswordField passwordBox = new JPasswordField();

  JTextField usernameRegisterBox = new JTextField();
  JPasswordField passwordRegisterBox = new JPasswordField();
  JPasswordField reEnterPasswordRegisterBox = new JPasswordField();

  JButton logInButton = new JButton("Log in");
  JButton signInButton = new JButton("Sign up");

  ClubApp myClub = new ClubApp();

  private String username;
  //private String password;
  private char[] newPassword;
  private char[] reNewPassword;

  ClubGUI(ClubApp club) {
    this.setTitle("Club");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(1280, 720);
    this.getContentPane().setBackground(Color.DARK_GRAY);
    this.setLayout(new BorderLayout());
    this.setVisible(true);
    this.setResizable(false);

    myClub = club;

    usernameBox.setFont(new Font("Consolas", Font.PLAIN, 18));
    passwordBox.setFont(new Font("Consolas", Font.PLAIN, 18));
    usernameRegisterBox.setFont(new Font("Consolas", Font.PLAIN, 18));
    passwordRegisterBox.setFont(new Font("Consolas", Font.PLAIN, 18));
    reEnterPasswordRegisterBox.setFont(new Font("Consolas", Font.PLAIN, 18));

    logInButton.addActionListener(this);
    signInButton.addActionListener(this);

    panelN.setBackground(Color.red);
    panelS.setBackground(Color.green);
    panelW.setBackground(Color.yellow);
    panelE.setBackground(Color.CYAN);
    panelC.setBackground(Color.darkGray);
    panelC.setLayout(null);

    panelN.setPreferredSize(new Dimension(50, 50));
    panelS.setPreferredSize(new Dimension(50, 50));
    panelW.setPreferredSize(new Dimension(50, 50));
    panelE.setPreferredSize(new Dimension(50, 50));
    panelC.setPreferredSize(new Dimension(50, 50));

    JLabel logIn = new JLabel();
    logIn.setText("username:");
    logIn.setForeground(Color.WHITE);
    logIn.setBounds(0, 0, 100, 50);

    JLabel pass = new JLabel();
    pass.setText("password:");
    pass.setForeground(Color.white);
    pass.setBounds(0, 50, 100, 50);

    JLabel signIn = new JLabel();
    signIn.setText("username:");
    signIn.setForeground(Color.WHITE);
    signIn.setBounds(0, 0, 100, 50);

    JLabel newPass = new JLabel();
    newPass.setText("password:");
    newPass.setForeground(Color.white);
    newPass.setBounds(0, 50, 100, 50);

    JLabel reenterPass = new JLabel();
    reenterPass.setText("reenter password:");
    reenterPass.setForeground(Color.white);
    reenterPass.setBounds(0, 100, 120, 50);

    usernameBox.setBounds(70, 10, 200, 30);
    passwordBox.setBounds(70, 60, 200, 30);
    passwordBox.setEchoChar('*');
    usernameRegisterBox.setBounds(120, 10, 200, 30);
    passwordRegisterBox.setBounds(120, 60, 200, 30);
    reEnterPasswordRegisterBox.setBounds(120, 110, 200, 30);
    passwordRegisterBox.setEchoChar('*');
    reEnterPasswordRegisterBox.setEchoChar('*');

    logInButton.setBounds(0, 150, 200, 30);
    signInButton.setBounds(0, 170, 200, 30);

    JPanel subPanelC1 = new JPanel();
    subPanelC1.setLayout(null);
    subPanelC1.setBounds(50, 50, 500, 500);
    subPanelC1.setBackground(Color.darkGray);

    subPanelC1.add(logIn);
    subPanelC1.add(usernameBox);
    subPanelC1.add(pass);
    subPanelC1.add(passwordBox);
    subPanelC1.add(logInButton);

    JPanel subPanelC2 = new JPanel();
    subPanelC2.setLayout(null);
    subPanelC2.setBounds(600, 50, 500, 500);
    subPanelC2.setBackground(Color.darkGray);

    subPanelC2.add(signIn);
    subPanelC2.add(usernameRegisterBox);
    subPanelC2.add(newPass);
    subPanelC2.add(passwordRegisterBox);
    subPanelC2.add(reenterPass);
    subPanelC2.add(reEnterPasswordRegisterBox);
    subPanelC2.add(signInButton);

    panelC.add(subPanelC1);
    panelC.add(subPanelC2);

    this.add(panelS, BorderLayout.SOUTH);
    this.add(panelN, BorderLayout.NORTH);
    this.add(panelW, BorderLayout.WEST);
    this.add(panelE, BorderLayout.EAST);
    this.add(panelC, BorderLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == logInButton) {
      username = usernameBox.getText();

      switch (username) {
        case "treasurer":
          this.dispose();
          LogInGUI newWindow1 = new LogInGUI(myClub);
          newWindow1.treasurerLogin();
          break;
        case "coach":
          this.dispose();
          LogInGUI newWindow2 = new LogInGUI(myClub);
          newWindow2.coachLogin();
          break;
        default:
          this.dispose();
          LogInGUI newWindow3 = new LogInGUI(myClub);
          newWindow3.memberLogin();
          break;
      }
      System.out.println(username);
    }

    if (e.getSource() == signInButton) {
      newPassword = passwordRegisterBox.getPassword();
      reNewPassword = reEnterPasswordRegisterBox.getPassword();
      if (!Arrays.equals(newPassword, reNewPassword)) {
        System.out.println("Password does not match");
        JOptionPane.showMessageDialog(
          null,
          "Password does not match",
          "Register",
          JOptionPane.ERROR_MESSAGE
        );
      } else {
        System.out.println("Signing in succeed");
        JOptionPane.showMessageDialog(
          null,
          "Register successfully",
          "Register",
          JOptionPane.PLAIN_MESSAGE
        );
      }
    }
  }
}
