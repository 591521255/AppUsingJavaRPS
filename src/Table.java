import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Table extends JPanel implements Runnable, ActionListener {


    Player self, competitor;
    PlayerPanel playerPanel1, playerPanel2;
    JButton connection, play;


    Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;
    Thread thread;
    boolean isConnect;
    int answer, result;

    Play parentJframe;
    String username;

    int vitoryNum = 0;


    Table(String userName, Play parent) {
        this.username = userName;
        this.parentJframe = parent;

        setBounds(5, 5, 400, 400);
        setBackground(Color.PINK);
        setLayout(null);
        setVisible(true);

        isConnect = false;
        connection = new JButton("连接服务器");
        connection.setSize(100, 40);
        connection.setLocation(172, 400);
        connection.addActionListener(this);
        connection.setEnabled(true);
        play = new JButton("开始");
        play.setSize(150, 40);
        play.setLocation(150, 170);
        play.addActionListener(this);
        play.setEnabled(false);

        self = new Player("我方");
        competitor = new Player("对手");
        self.setPlayI(new ImageIcon("我的头像.png"));
        competitor.setPlayI(new ImageIcon("对手头像.png"));
        playerPanel1 = new PlayerPanel(self);
        playerPanel2 = new PlayerPanel(competitor);
        playerPanel1.setLocation(160, 315);
        playerPanel2.setLocation(160, 10);


        add(playerPanel1);
        add(playerPanel2);
        add(connection);
        add(play);
        validate();
    }

    public boolean doConnect() {
        try {
            socket = new Socket();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            InetSocketAddress socketAddress = new InetSocketAddress(address, 8888);
            socket.connect(socketAddress);  //建立连接
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendAnswer(int answer) {
        try {
            out.writeInt(answer);
            this.answer = answer;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connection) {
            if (!isServerClose(socket)) {
                try {
                    socket.close();
                    System.out.println("正在断开连接" + socket.isClosed());
                    isConnect = false;
                    play.setEnabled(false);
                    playerPanel1.reloadAnswerI(0);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                connection.setText("连接服务器");
            } else {
                if (doConnect()) {
                    thread = new Thread(this);
                    thread.start();
                    System.out.println("已连接服务器");
                    connection.setText("断开服务器");
                    play.setEnabled(true);
                    play.setText("开始");
                    isConnect = true;
                }
            }
        }

        if (e.getSource() == play) {
            play.setEnabled(false);
            play.setText("请出拳");
        }
    }

    public void run() {
        System.out.println("启动监听");
        while (true) {
            try {
                result = in.readInt();  //堵塞状态，除非读取到信息
                switch (result) {
                    case 2:
                        playerPanel2.reloadAnswerI(((answer + 1) % 3 + 1));
                        play.setText("遗憾你输了，再来一局");
                        play.setEnabled(true);
                        break;
                    case 1:
                        playerPanel2.reloadAnswerI(answer);
                        play.setText("双方打平，再来一局");
                        play.setEnabled(true);
                        break;
                    case 0:
                        playerPanel2.reloadAnswerI(((answer) % 3 + 1));
                        play.setText("恭喜你赢了，再来一局");
                        play.setEnabled(true);

                        vitoryNum++;
                        parentJframe.jFrame.setTitle(username + "(胜局" + vitoryNum + ")");
                }
                System.out.println("收到服务端应答" + result);
            } catch (IOException e) {
                break;
            }
        }
    }
}
