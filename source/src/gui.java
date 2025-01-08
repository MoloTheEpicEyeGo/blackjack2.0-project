import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class gui
{
    //vars
    private final int cardWidth = 110;
    private final int cardLength = 154;
    private boolean cardReveal = false;
    private final dealer dealer;
    private final Cards deck;
    private final player player;
    private final JLabel balanceLabel;
    private final JTextField betField;
    int currentBet = 0;
    int playerScore = 0;

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
        frame.setSize(1200, 600);
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
                for (int i = 0; i < dealerHand.size(); i++)
                {
                    String card = dealerHand.get(i);

                    //first card is visible, second card hidden
                    if (i == 1)
                    {
                        if (cardReveal)
                        {
                            Image cardImage = new ImageIcon(getClass().getResource("./cards/" + card + ".png")).getImage();
                            g.drawImage(cardImage, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                        }
                        else
                        {
                            Image hiddenCard = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                            g.drawImage(hiddenCard, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                        }
                    }
                    else
                    {
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
        leftPanel.add(new JLabel("bet"));
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
        stayButton.setEnabled(false);
        hitButton.setEnabled(false);
        middlePanel.add(hitButton);
        middlePanel.add(stayButton);


        //right of button panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setBackground(new Color(255, 255, 255));
        balanceLabel = new JLabel("Balance: $" + player.getMoney());
        rightPanel.add(balanceLabel);

        //adding actions to buttons

            //TODO DEAL ACTIONS
        dealButton.addActionListener(e -> {
            try
            {
                int bet = Integer.parseInt(betField.getText());
                currentBet = bet;
                if (currentBet < 25)
                {
                    JOptionPane.showMessageDialog(null, "minimum bet is 25$", "", JOptionPane.ERROR_MESSAGE);
                }
                else if (bet > player.getMoney())
                {
                    JOptionPane.showMessageDialog(null, "bet exceeds balance", "", JOptionPane.ERROR_MESSAGE);
                }
                else
                {

                    //rest game
                    dealer.clearHand();
                    player.clearHand();
                    panel.repaint(); //clears the screen cause theres nothing in both dealer$players hand

                    //iff less than 10 cards, reshuffles
                    if (deck.remainingCards() < 20)
                    {
                        deck.shuffleDeck();
                    }

                    player.bet(bet);
                    balanceLabel.setText("Balance: $" + player.getMoney());
                    //gives two cards to their respective array
                    dealer.firstTwo(deck);
                    player.firstTwo(deck);

                    //calls the panel to update the gui window
                    panel.repaint();


                    //make hit and stand ava
                    hitButton.setEnabled(true);
                    stayButton.setEnabled(true);

                    //make deal un-ava so you cant deal mid-game
                    dealButton.setEnabled(false);

                    //blackjack checker for both player & dealer
                    if (util.calculateHand(player.hand) == 21)
                    {
                        //resetgame
                        cardReveal = false;
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false);
                        dealButton.setEnabled(true);
                        player.blackjackWin(currentBet);
                        JOptionPane.showMessageDialog(null, "win!", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (util.calculateHand(dealer.hand) == 21)
                    {
                        cardReveal = true;

                        //updates frame to show blackjack for dealer
                        panel.repaint();

                        //resetgame
                        cardReveal = false;
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false);
                        dealButton.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "dealer has blackjack!", "", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            } catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "enter valid number", "", JOptionPane.ERROR_MESSAGE);
            }
        });

        //TODO HIT ACTIONS
        hitButton.addActionListener(e ->
        {
            player.hit(deck);
            panel.repaint();

            //calcs players hand
            playerScore = util.calculateHand(player.getHand());

            if (playerScore > 21)
            {
                JOptionPane.showMessageDialog(null, "you bust!", "", JOptionPane.ERROR_MESSAGE);

                //resetgame
                cardReveal = false;
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                dealButton.setEnabled(true);
            }
        });

        //TODO STAND ACTIONS
        stayButton.addActionListener(e -> {
            stayButton.setEnabled(false);
            //set cardReveal to true so the hidden card is shown
            cardReveal = true;
            panel.repaint(); //update panel to reveal the hidden card

            //disable the hit button
            hitButton.setEnabled(false);

            //timer to handle the dealer's moves with delay
            Timer dealerTimer = new Timer(800, null); // 800ms delay
            dealerTimer.addActionListener(timerEvent -> {
                int dealerScore = util.calculateHand(dealer.getHand());

                //dealer keeps hitting while under 17
                if (dealerScore < 17) {
                    dealer.hit(deck);
                    panel.repaint(); //reveals hidden card

                    //check dealer busts
                    if (dealerScore > 21) {
                        dealerTimer.stop(); // Stop the timer
                        JOptionPane.showMessageDialog(null, "dealer bust!", "", JOptionPane.ERROR_MESSAGE);
                        player.winMoney(currentBet);


                        balanceLabel.setText("Balance: $" + player.getMoney());
                        //resetgame
                        cardReveal = false;
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false);
                        dealButton.setEnabled(true);
                        return;
                    }
                } else {
                    //dealers turn is done
                    dealerTimer.stop(); //stop the timer
                    int playerScore = util.calculateHand(player.getHand());

                    //comparing scores
                    if (dealerScore > 21) {
                        JOptionPane.showMessageDialog(null, "dealer bust!", "", JOptionPane.INFORMATION_MESSAGE);
                        player.winMoney(currentBet);
                    } else if (dealerScore == playerScore) {
                        JOptionPane.showMessageDialog(null, "push!", "", JOptionPane.INFORMATION_MESSAGE);
                        player.pushMoney(currentBet);
                    } else if (dealerScore > playerScore) {
                        JOptionPane.showMessageDialog(null, "lose!", "", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "win!", "", JOptionPane.INFORMATION_MESSAGE);
                        player.winMoney(currentBet);
                    }

                    //update money
                    balanceLabel.setText("Balance: $" + player.getMoney());
                    //resetgame
                    cardReveal = false;
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                    dealButton.setEnabled(true);
                }
            });

            dealerTimer.setRepeats(true); // Timer repeats for dealer's actions
            dealerTimer.start(); // Start the timer
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