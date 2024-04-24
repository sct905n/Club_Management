import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class LogInGUI implements ActionListener {
  JFrame frame = new JFrame();
  JLabel label = new JLabel();
  JButton sortByTimesPaidButton;
  JButton sortByTimesAttendButton;
  JButton showMembersButton;
  JButton showMemsButton;
  JButton sendMessage;
  JButton financeReportButton;

  JTextArea message;

  ClubApp myClub;
  ClubApp originClub;

  LogInGUI(ClubApp club) {
    myClub = new ClubApp(club);
    originClub = new ClubApp(myClub);

    frame.setTitle("Club");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1280, 720);
    frame.setResizable(false);
    frame.getContentPane().setBackground(Color.DARK_GRAY);
    frame.setLayout(null);
  }

  public void treasurerLogin() {
    frame.setTitle("Club - Treasurer");

    sortByTimesPaidButton = new JButton();
    sortByTimesPaidButton.setText("Sort by times paid");
    sortByTimesPaidButton.addActionListener(this);
    sortByTimesPaidButton.setBounds(550, 600, 200, 50);

    sortByTimesAttendButton = new JButton();
    sortByTimesAttendButton.setText("Sort by times attend");
    sortByTimesAttendButton.addActionListener(this);
    sortByTimesAttendButton.setBounds(300, 600, 200, 50);

    showMembersButton = new JButton();
    showMembersButton.setText("Show members");
    showMembersButton.addActionListener(this);
    showMembersButton.setBounds(50, 600, 200, 50);

    financeReportButton = new JButton();
    financeReportButton.setText("Finance Report");
    financeReportButton.addActionListener(this);
    financeReportButton.setBounds(800, 600, 200, 50);

    label.setBounds(100, 0, 300, 300);
    //label.setText("You're a treasurer");
    label.setForeground(Color.WHITE);
    label.setFont(new Font("MV Bolis", Font.BOLD, 20));

    frame.add(label);
    frame.add(sortByTimesPaidButton);
    frame.add(sortByTimesAttendButton);
    frame.add(showMembersButton);
    frame.add(financeReportButton);
    frame.setVisible(true);
  }

  public void memberLogin() {
    frame.setTitle("Club - Member");

    label.setBounds(100, 0, 300, 300);
    label.setText("You're a member");
    label.setForeground(Color.WHITE);
    label.setFont(new Font("MV Bolis", Font.BOLD, 20));

    frame.add(label);
    frame.setVisible(true);
  }

  public void coachLogin() {
    frame.setTitle("Club - Coach");

    sendMessage = new JButton("Send message");
    sendMessage.setBounds(500, 220, 200, 50);
    sendMessage.addActionListener(this);

    JPanel panel = new JPanel();
    panel.setBounds(0, 0, 700, 200);
    panel.setLayout(new BorderLayout());
    message = new JTextArea();
    JScrollPane scroll = new JScrollPane(
      message,
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );
    //scroll.add(message);
    //scroll.setBounds(500, 500, 100, 100);
    message.setLineWrap(true);
    message.setWrapStyleWord(true);
    message.setFont(new Font("Consolas", Font.PLAIN, 18));
    //message.setBounds(0, 0, 500, 100);

    showMemsButton = new JButton();
    showMemsButton.setText("Show members");
    showMemsButton.addActionListener(this);
    showMemsButton.setBounds(50, 600, 200, 50);

    label.setBounds(100, 0, 300, 300);
    label.setText("You're a coach");
    label.setForeground(Color.WHITE);
    label.setFont(new Font("MV Bolis", Font.BOLD, 20));

    panel.add(scroll);

    frame.add(label);
    frame.add(showMemsButton);
    frame.add(panel);
    frame.add(sendMessage);
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == sortByTimesPaidButton) {
      myClub.sortbByTimesPaid();

      System.out.println("Sorted:");
      System.out.println(myClub);
    }
    if (e.getSource() == sortByTimesAttendButton) {
      myClub.sortByTimesAttended();
      System.out.println("Sorted:");
      System.out.println(myClub);
    }
    if (e.getSource() == showMembersButton) {
      originClub.getMemberList();
    }
    if (e.getSource() == showMemsButton) {
      originClub.showMembers();
    }
    if (e.getSource() == sendMessage) {
      System.out.println(message.getText());
    }
    if (e.getSource() == financeReportButton) {
      new FinancialTracker(myClub);
    }
  }
}
