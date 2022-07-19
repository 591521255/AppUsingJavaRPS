import javax.swing.*;

public class AnswerPanel extends JPanel {

    JButton stone, scissors, cloth;
    String imageName[] = {"石头.png", "剪刀.png", "布.png"};
    Icon icon[] = new Icon[imageName.length];
    Box box;

    AnswerPanel() {
        setSize(60, 300);
        setVisible(true);

        for (int i = 0; i < icon.length; i++) {
            icon[i] = new ImageIcon(imageName[i]);
        }
        stone = new JButton(icon[0]);
        stone.setSize(50, 50);
        scissors = new JButton(icon[1]);
        scissors.setSize(50, 50);
        cloth = new JButton(icon[2]);
        cloth.setSize(50, 50);
        box = Box.createVerticalBox();
        box.add(stone);
        box.add(scissors);
        box.add(cloth);
        add(box);
        validate();
    }
}
