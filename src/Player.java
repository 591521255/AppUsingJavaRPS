import javax.swing.*;

public class Player {
    private String playerName, playerIP;
    private Icon playI;

    Player(String playerName) {
        setPlayerName(playerName);
    }

    public Icon getPlayI() {
        return playI;
    }

    public String getPlayerIP() {
        return playerIP;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayI(Icon playI) {
        this.playI = playI;
    }

    public void setPlayerIP(String playerIP) {
        this.playerIP = playerIP;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
