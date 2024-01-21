
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends  JFrame implements ActionListener {
    JLabel heading, clockLabel;
    Font font = new Font("", Font.BOLD, 40);
    JPanel mainPanel;

    JButton[] btns = new JButton[9];

    //game instance variable
    int gameChances[] = {2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;
    int wps[][]={
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
int winner=2;
boolean gameOver = false;
String won;
    MyGame() {
        System.out.println("Creating instance of a game");
        setTitle("My Tic Tack Toe Game..");
        ImageIcon icon = new ImageIcon("img/ticktack.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI() {
        this.getContentPane().setBackground(Color.decode("#2196f3"));
        this.setLayout(new BorderLayout());

        heading = new JLabel("Tic Tack Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.white);
        this.add(heading, BorderLayout.NORTH);

        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.white);

        this.add(clockLabel, BorderLayout.SOUTH);

        Thread thread = new Thread() {
            public void run() {
                try {
                    while (true) {
                        String date = new Date().toLocaleString();
                        clockLabel.setText(date);
                        Thread.sleep(1000);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        };
        thread.start();

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton();
//            btn.setIcon(new ImageIcon("img/O.png"));
            btn.setBackground(Color.decode("#90caf9"));
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i - 1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i - 1));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton = (JButton) e.getSource();

        String nameStr = currentButton.getName();

        int name = Integer.parseInt(nameStr.trim());

        if(gameOver){
            JOptionPane.showMessageDialog(this,"Game over");
            return;
        }

        if(gameChances[name]==2){
            if(activePlayer==0){
                currentButton.setIcon((new ImageIcon("img/O.png")));
                gameChances[name]=activePlayer;
                activePlayer=1;

                //find winner
                for (int[] temp: wps){
                    if (
                            (gameChances[temp[0]]== gameChances[temp[1]])
                        &&
                            (gameChances[temp[1]]==gameChances[temp[2]])
                        &&
                            (gameChances[temp[2]]!=2)
                    ){
                        gameOver=true;
                        winner=gameChances[temp[0]];
                        won = winner==0?"0":"X";
                        JOptionPane.showMessageDialog(null,"Player "+ won + " has won ");
                                int q = JOptionPane.showConfirmDialog(this, "do you want to restart?");
                                System.out.println(q);
                                if(q==0){
                                    this.setVisible(false);
                                    new MyGame();

                                }else if(q==1){
                                System.exit(0);
                                }else{

                                }
                                break;
                    }else{
                        
                    }
                }


            }else{
                currentButton.setIcon((new ImageIcon("img/X.png")));
                gameChances[name]=activePlayer;
                activePlayer=0;

            }

            // draw logic
        int c = 0;
            for (int i:gameChances){
                if(i==2){
                    c++;
                    break;
                }
            }
            if(c==0 && !gameOver){
                JOptionPane.showMessageDialog(null,"Its a draw");
                int opt = JOptionPane.showConfirmDialog(this,"Play again?");
                if(opt==0){
                    this.setVisible(false);
                    new MyGame();
                }else if(opt==1){
                    System.exit(0);
                }else{

                }
gameOver=true;
            }

        }else{
            JOptionPane.showMessageDialog(this,"Position already occupied");
        }

    }
}
