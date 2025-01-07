import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class gui
{
    //vars
    private final int cardWidth = 110;
    private final int cardLength = 154;
    private dealer dealer;
    private Cards deck;
    private player player;
    private JLabel balanceLabel;
    private JTextField betField;
    int currentBet = 0;


    public gui ()
    {
        //instances
        dealer = new dealer();
        deck = new Cards();
        player = new player(500);
        deck.shuffleDeck();

        //main window
        JFrame frame = new JFrame("Blackjack 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //set frame to middle of the screen

        //workspace inside the window
        JPanel panel = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                //draw dealers hand
                ArrayList<String> dealerHand = dealer.getHand();
                for (int i = 0; i < dealerHand.size(); i++) {
                    String card = dealerHand.get(i);

                    //first card is visible, second card hidden
                    if (i == 1) {
                        Image hiddenCard = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                        g.drawImage(hiddenCard, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                    } else {
                        Image cardImage = new ImageIcon(getClass().getResource("./cards/" + card + ".png")).getImage();
                        g.drawImage(cardImage, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                    }
                }

                //draw players hand
                ArrayList<String> playerHand = player.getHand();
                for (int i = 0; i < playerHand.size(); i++) {
                    String card = playerHand.get(i);

                    //draws cards
                    Image cardImage = new ImageIcon(getClass().getResource("./cards/" + card + ".png")).getImage();
                    g.drawImage(cardImage, 20 + (i * (cardWidth + 20)), 350, cardWidth, cardLength, null);

                }

            }

        };
        panel.setBackground(new Color(53, 101, 77));

        //button panel (where buttons are located)
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(255, 255, 255));

        //leftside of button panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout()); //making it "flow" so we can add to left of butpanel
        leftPanel.setBackground(new Color(255, 255, 255));
        betField = new JTextField(3);
        JButton dealButton = new JButton("deal");
        dealButton.setFocusable(false);
        leftPanel.add(new JLabel("bet$"));
        leftPanel.add(betField);
        leftPanel.add(dealButton);

        //middle of button panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());
        middlePanel.setBackground(new Color(255, 255, 255));
        JButton hitButton = new JButton("hit");
        hitButton.setFocusable(false);
        JButton stayButton = new JButton("stand");
        stayButton.setFocusable(false);
        middlePanel.add(hitButton);
        middlePanel.add(stayButton);


        //right of button panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setBackground(new Color(255, 255, 255));
        balanceLabel = new JLabel("Balance: $" + player.getMoney());
        rightPanel.add(balanceLabel);

        //adding actions to buttons

            //deal actions
        dealButton.addActionListener(e -> {
            try
            {
                int bet = Integer.parseInt(betField.getText());
                if (bet < 25)
                {
                    JOptionPane.showMessageDialog(null, "minimum bet is 25$", "", JOptionPane.ERROR_MESSAGE);
                }
                else if (bet > player.getMoney())
                {
                    JOptionPane.showMessageDialog(null, "bet exceeds balance", "", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    currentBet = bet;
                    player.bet(currentBet);
                    balanceLabel.setText("Balance: $" + player.getMoney());
                    //gives two cards to their respective array
                    dealer.firstTwo(deck);
                    player.firstTwo(deck);

                    //calls the panel to update the gui window
                    panel.repaint();
                }
            } catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "enter valid number", "", JOptionPane.ERROR_MESSAGE);
            }
        });

        //add individual buttons&buttons to the button panel
        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(middlePanel, BorderLayout.CENTER);
        buttonPanel.add(rightPanel, BorderLayout.EAST);


        //add components to the frame with layout positions
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER); //add workspace to the center of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH); //add button panel to the bottom of the frame

        //make the frame visible
        frame.setVisible(true);

    }
}