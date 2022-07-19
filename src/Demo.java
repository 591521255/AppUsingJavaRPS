import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Demo {

    public static void main(String[] args) {
        Play play = new Play("我方");
        Play play2 = new Play("对方");
    }
}

class Play extends JPanel
        implements ActionListener {
    Table table;
    AnswerPanel answerPanel;
     JFrame jFrame;

    Play(String userName) {
        answerPanel = new AnswerPanel();
        jFrame = new JFrame(userName);
        table = new Table(userName,this);
        jFrame.setTitle(userName + "(胜局" + 0 + ")");

        answerPanel.stone.addActionListener(this);
        answerPanel.scissors.addActionListener(this);
        answerPanel.cloth.addActionListener(this);

        jFrame.setBounds(100, 100, 500, 500);
        jFrame.add(table, BorderLayout.CENTER);
        jFrame.add(answerPanel, BorderLayout.EAST);
        jFrame.setVisible(true);
        jFrame.validate();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == answerPanel.stone) {

            if (table.isConnect == true && (Objects.equals(table.play.getText(), "请出拳"))) {
                System.out.println("石头");
                table.sendAnswer(1);
                table.playerPanel1.reloadAnswerI(1);
                table.play.setText("等待对方中");
            }
        } else if (e.getSource() == answerPanel.scissors) {

            if (table.isConnect == true && (Objects.equals(table.play.getText(), "请出拳"))) {
                System.out.println("剪刀");
                table.sendAnswer(2);
                table.playerPanel1.reloadAnswerI(2);
                table.play.setText("等待对方中");
            }
        }
        if (e.getSource() == answerPanel.cloth) {

            if (table.isConnect == true && (Objects.equals(table.play.getText(), "请出拳"))) {
                System.out.println("布");
                table.sendAnswer(3);
                table.playerPanel1.reloadAnswerI(3);
                table.play.setText("等待对方中");
            }
        }
    }
}
