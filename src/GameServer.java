import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    ServerSocket server = null;
    ServerThread thread;
    Socket you = null;


    GameServer() {
        while (true) {
            if (server == null) {
                try {
                    server = new ServerSocket(8888);    //启动监听
                    System.out.println("开始监听");
                } catch (IOException e1) {
                    System.out.println("启动服务器失败");   //ServerSocket对象不能重复创建
                    e1.printStackTrace();//捕捉异常栈堆
                }
            }

            if (server != null) {
                try {
                    you = server.accept();
                    System.out.println("客户地址ַ" + you.getInetAddress());
                } catch (IOException e) {
                    System.out.println("正在等待");
                }
            }
            if (you != null) {
                //为每个客户启动一个专门的线程
                thread = new ServerThread(you);
                thread.start();
            } else
                continue;
        }
    }

    public static void main(String[] args) {
        new GameServer();
    }

}

class ServerThread extends Thread {

    private Socket socket;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private static int firstAnswer, secondAnswer;
    private static boolean ready;
    private int answer, result;
    private boolean isConnect, isFirst;
    private String client;

    synchronized void setFirstAnswer(int firstAnswer) {
        ServerThread.firstAnswer = firstAnswer;
    }

    synchronized void setSecondAnswer(int secondAnswer) {
        ServerThread.secondAnswer = secondAnswer;
    }

    synchronized int getFirstAnswer() {
        return firstAnswer;
    }

    synchronized int getSecondAnswer() {
        return secondAnswer;
    }

    synchronized void setReady(boolean ready) {
        ServerThread.ready = ready;
    }

    ServerThread(Socket t) {
        socket = t;
        setReady(false);
        isFirst = false;
        client = socket.getInetAddress() + ":" + socket.getPort();
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("接入客户机" + client);
        } catch (IOException e) {
        }
    }

    public void run() {
        while (true) {
            isConnect = !isServerClose(socket);
            if (isConnect) {
                try {
                    System.out.println("准备读取客户机" + client);
                    answer = in.readInt();
                    if (getFirstAnswer() == 0) {
                        setFirstAnswer(answer);
                        isFirst = true;
                    } else {
                        setSecondAnswer(answer);
                        setReady(true);
                    }
                } catch (IOException e) {
                    System.out.println("客户离开" + client);
                    break;
                }

                while (ready == false) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                match();

                try {
                    out.writeInt(result);
                    if (isFirst == true) flush();
                } catch (IOException e) {
                    System.out.println("客户离开" + client);
                    flush();
                    break;
                }
            } else {
                System.out.println("客户离开:" + socket.getInetAddress());
                break;
            }
        }
    }

    private void match() {   //返回本线程可得分数,胜利锝2分，打平1分

        if (isFirst == true) {
            System.out.println("是第一个出拳");
            System.out.println("second:" + secondAnswer);
            System.out.println("first:" + firstAnswer);
            result = firstAnswer - secondAnswer;
        } else {
            System.out.println("是第二个出拳");
            result = secondAnswer - firstAnswer;
        }
        switch (result) {
            case 0:
                result = 1;
                break;//相减为零表示双方相同
            case 1:
            case -2:
                result = 2;
                break;//相减为1或者-2表示胜利
            default:
                result = 0;//相减为1或者-2表示失败
        }
    }

    private synchronized void flush() {
        firstAnswer = 0;
        secondAnswer = 0;
        isFirst = false;
        answer = 0;
        setReady(false);
    }

    public Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }
}
