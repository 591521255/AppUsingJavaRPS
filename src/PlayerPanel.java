import javax.swing.*;
import java.awt.*;

class PlayerPanel extends JPanel {

    private JButton playerB, answerB;
    private JLabel playerLable;
    private Icon playI, answerI;
    private Player player;

    PlayerPanel(Player player) {
        this.player = player;
        setBounds(0, 0, 120, 75);
        setLayout(null);
        setBackground(Color.blue);
        playerB = new JButton();
        playerB.setIcon(this.player.getPlayI());
        playerB.setBounds(5, 5, 50, 50);
        answerB = new JButton();
        answerB.setBounds(65, 5, 50, 50);
        //answerB.setEnabled(false);
        answerB.setIcon(setAnswerI(0));
        playerLable = new JLabel();
        playerLable.setBounds(5, 50, 100, 30);
        playerLable.setText(this.player.getPlayerName());
        add(playerB);
        add(answerB);
        add(playerLable);
        validate();
    }

    public void reloadPlayer(Player player) {
        this.player = player;
        playerB.setIcon(this.player.getPlayI());
        playerLable.setText(this.player.getPlayerName());
        validate();
    }

    public void reloadAnswerI(int answer) {
        answerB.setIcon(setAnswerI(answer));
        answerB.revalidate();
    }

    public Icon setAnswerI(int playI) {
        Icon icon;
        String imageName[] = {"石头.png", "剪刀.png", "布.png"};
        String imagePath = "随机动画.gif";
        switch (playI) {
            case 1:
                imagePath = imageName[0];
                break;
            case 2:
                imagePath = imageName[1];
                break;
            case 3:
                imagePath = imageName[2];
                break;
        }
        icon = new ImageIcon(imagePath);
        validate();
        return icon;
    }
}
