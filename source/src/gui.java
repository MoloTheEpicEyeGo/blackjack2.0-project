import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class gui
{
    //vars
    private final int cardWidth = 165;  // Increased from 110 (1.5x)
    private final int cardLength = 231; // Increased from 154 (1.5x)
    private boolean cardReveal = false;
    private boolean doubleDownUsed = false;
    private final dealer dealer;
    private final Cards deck;
    private final player player;
    private final JLabel balanceLabel;
    private final JTextField betField;
    double currentBet = 0;
    int playerScore = 0;

    public gui ()
    {
        //instances
        dealer = new dealer();
        deck = new Cards();
        player = new player(500.0);
        deck.shuffleDeck();

        //main window
        JFrame frame = new JFrame("Blackjack 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1000);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //set frame to middle of the screen

        // Set larger font for message dialogs
        Font messageFont = new Font("Arial", Font.PLAIN, 28);
        UIManager.put("OptionPane.messageFont", messageFont);
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 24));

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
                            Image cardImage = new ImageIcon(getClass().getResource("/cards/" + card + ".png")).getImage();
                            g.drawImage(cardImage, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                        }
                        else
                        {
                            Image hiddenCard = new ImageIcon(getClass().getResource("/cards/BACK.png")).getImage();
                            g.drawImage(hiddenCard, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                        }
                    }
                    else
                    {
                        Image cardImage = new ImageIcon(getClass().getResource("/cards/" + card + ".png")).getImage();
                        g.drawImage(cardImage, 20 + (i * (cardWidth + 20)), 20, cardWidth, cardLength, null);
                    }
                }

                //draw players hand
                ArrayList<String> playerHand = player.getHand();
                for (int i = 0; i < playerHand.size(); i++) {
                    String card = playerHand.get(i);

                    //draws cards
                    Image cardImage = new ImageIcon(getClass().getResource("/cards/" + card + ".png")).getImage();
                    int x = 20 + (i * (cardWidth + 20));
                    int y = 550;  // Increased from 350 for better spacing in larger window

                    if (doubleDownUsed && i == playerHand.size() - 1) {
                        Graphics2D g2 = (Graphics2D) g;
                        AffineTransform oldTransform = g2.getTransform();
                        double centerX = x + (cardWidth / 2.0);
                        double centerY = y + (cardLength / 2.0);
                        g2.rotate(Math.toRadians(90), centerX, centerY);
                        g2.drawImage(cardImage, x, y, cardWidth, cardLength, null);
                        g2.setTransform(oldTransform);
                    } else {
                        g.drawImage(cardImage, x, y, cardWidth, cardLength, null);
                    }

                }

            }

        };
        panel.setBackground(new Color(53, 101, 77));

        //button panel (where buttons are located)
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setPreferredSize(new Dimension(1800, 85)); // Reduced height further

        // Create larger font for UI elements
        Font largeFont = new Font("Arial", Font.PLAIN, 24);
        Font buttonFont = new Font("Arial", Font.BOLD, 22);

        //leftside of button panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout()); //making it "flow" so we can add to left of butpanel
        leftPanel.setBackground(new Color(255, 255, 255));
        betField = new JTextField(4);
        betField.setFont(largeFont);
        betField.setPreferredSize(new Dimension(100, 50));
        JLabel betLabel = new JLabel("bet");
        betLabel.setFont(largeFont);
        JButton dealButton = new JButton("deal");
        dealButton.setFocusable(false);
        dealButton.setFont(buttonFont);
        dealButton.setPreferredSize(new Dimension(120, 60));
        leftPanel.add(betLabel);
        leftPanel.add(betField);
        leftPanel.add(dealButton);

        //middle of button panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());
        middlePanel.setBackground(new Color(255, 255, 255));
        JButton hitButton = new JButton("hit");
        hitButton.setFocusable(false);
        hitButton.setFont(buttonFont);
        hitButton.setPreferredSize(new Dimension(120, 60));
        JButton doubleDownButton = new JButton("double");
        doubleDownButton.setFocusable(false);
        doubleDownButton.setFont(buttonFont);
        doubleDownButton.setPreferredSize(new Dimension(120, 60));
        doubleDownButton.setEnabled(false);
        JButton surrenderButton = new JButton("surrender");
        surrenderButton.setFocusable(false);
        surrenderButton.setFont(buttonFont);
        surrenderButton.setPreferredSize(new Dimension(150, 60));
        surrenderButton.setEnabled(false);
        JButton stayButton = new JButton("stand");
        stayButton.setFocusable(false);
        stayButton.setFont(buttonFont);
        stayButton.setPreferredSize(new Dimension(120, 60));
        stayButton.setEnabled(false);
        hitButton.setEnabled(false);
        middlePanel.add(hitButton);
        middlePanel.add(doubleDownButton);
        middlePanel.add(surrenderButton);
        middlePanel.add(stayButton);


        //right of button panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setBackground(new Color(255, 255, 255));
        balanceLabel = new JLabel("Balance: $" + String.format("%.2f", player.getMoney()));
        balanceLabel.setFont(largeFont);
        rightPanel.add(balanceLabel);

        //adding actions to buttons

            //TODO DEAL ACTIONS
        dealButton.addActionListener(e -> {
            try
            {
                double bet = Double.parseDouble(betField.getText());
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
                    doubleDownUsed = false;
                    panel.repaint(); //clears the screen cause theres nothing in both dealer$players hand

                    //if less than 10 cards, reshuffles
                    if (deck.remainingCards() < 20)
                    {
                        deck.initializeDeck();
                        JOptionPane.showMessageDialog(null, "deck shuffled", "", JOptionPane.INFORMATION_MESSAGE);
                    }


                    player.bet(bet);
                    balanceLabel.setText("Balance: $" + String.format("%.2f", player.getMoney()));
                    //gives two cards to their respective array
                    dealer.firstTwo(deck);
                    player.firstTwo(deck);

                    //calls the panel to update the gui window
                    panel.repaint();


                    //make hit and stand ava
                    hitButton.setEnabled(true);
                    stayButton.setEnabled(true);
                    doubleDownButton.setEnabled(true);
                    surrenderButton.setEnabled(true);

                    //make deal un-ava so you cant deal mid-game
                    dealButton.setEnabled(false);

                    //blackjack checker for both player & dealer
                    if (util.calculateHand(player.hand) == 21)
                    {
                        cardReveal = true;
                        panel.repaint(); //update panel to reveal the hidden card

                        JOptionPane.showMessageDialog(null, "win!", "", JOptionPane.INFORMATION_MESSAGE);

                        //resetgame
                        cardReveal = false;
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false);
                        doubleDownButton.setEnabled(false);
                        surrenderButton.setEnabled(false);
                        dealButton.setEnabled(true);
                        player.blackjackWin(currentBet);
                        doubleDownUsed = false;
                    }
                    else if (util.calculateHand(dealer.hand) == 21)
                    {
                        cardReveal = true;

                        //updates frame to show blackjack for dealer
                        panel.repaint();
                        
                        //force repaint to complete
                        panel.paintImmediately(panel.getBounds());

                        //disable buttons
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false);
                        doubleDownButton.setEnabled(false);
                        surrenderButton.setEnabled(false);
                        dealButton.setEnabled(true);
                        
                        //show message dialog with card revealed (modal dialog gives time for user to see card)
                        JOptionPane.showMessageDialog(null, "dealer has blackjack!", "", JOptionPane.INFORMATION_MESSAGE);
                        
                        //reset game state after message is closed
                        cardReveal = false;
                        doubleDownUsed = false;
                        panel.repaint();
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
            if (player.getHand().size() > 2)
            {
                doubleDownButton.setEnabled(false);
                surrenderButton.setEnabled(false);
            }

            //calcs players hand
            playerScore = util.calculateHand(player.getHand());

            if (playerScore > 21)
            {
                cardReveal = true;
                panel.repaint(); //update panel to reveal the hidden card
                JOptionPane.showMessageDialog(null, "you bust!", "", JOptionPane.ERROR_MESSAGE);

                //resetgame
                cardReveal = false;
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                doubleDownButton.setEnabled(false);
                surrenderButton.setEnabled(false);
                dealButton.setEnabled(true);
                doubleDownUsed = false;
            }
        });

        //TODO DOUBLE DOWN ACTIONS
        doubleDownButton.addActionListener(e -> {
            if (player.getHand().size() != 2)
            {
                JOptionPane.showMessageDialog(null, "double down only after first two cards", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (player.getMoney() < currentBet)
            {
                JOptionPane.showMessageDialog(null, "not enough balance to double down", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            player.bet(currentBet);
            currentBet += currentBet;
            balanceLabel.setText("Balance: $" + String.format("%.2f", player.getMoney()));

            doubleDownUsed = true;
            player.hit(deck);
            panel.repaint();

            //check if player busts on the double down hit
            playerScore = util.calculateHand(player.getHand());
            if (playerScore > 21)
            {
                cardReveal = true;
                panel.repaint();
                JOptionPane.showMessageDialog(null, "you bust!", "", JOptionPane.ERROR_MESSAGE);

                //resetgame
                cardReveal = false;
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                doubleDownButton.setEnabled(false);
                surrenderButton.setEnabled(false);
                dealButton.setEnabled(true);
                doubleDownUsed = false;
                return;
            }

            hitButton.setEnabled(false);
            doubleDownButton.setEnabled(false);
            surrenderButton.setEnabled(false);
            stayButton.doClick();
        });

        //TODO STAND ACTIONS
        stayButton.addActionListener(e -> {
            stayButton.setEnabled(false);
            //set cardReveal to true so the hidden card is shown
            cardReveal = true;
            panel.repaint(); //update panel to reveal the hidden card

            //disable the hit button
            hitButton.setEnabled(false);
            doubleDownButton.setEnabled(false);
            surrenderButton.setEnabled(false);

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


                        balanceLabel.setText("Balance: $" + String.format("%.2f", player.getMoney()));
                        //resetgame
                        cardReveal = false;
                        hitButton.setEnabled(false);
                        stayButton.setEnabled(false);
                        doubleDownButton.setEnabled(false);
                        surrenderButton.setEnabled(false);
                        dealButton.setEnabled(true);
                        doubleDownUsed = false;
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
                    balanceLabel.setText("Balance: $" + String.format("%.2f", player.getMoney()));
                    //resetgame
                    cardReveal = false;
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                    doubleDownButton.setEnabled(false);
                    surrenderButton.setEnabled(false);
                    dealButton.setEnabled(true);
                    doubleDownUsed = false;
                }
            });

            dealerTimer.setRepeats(true); // Timer repeats for dealer's actions
            dealerTimer.start(); // Start the timer
        });

        //TODO SURRENDER ACTIONS
        surrenderButton.addActionListener(e -> {
            //only allow surrender after initial deal (2 cards)
            if (player.getHand().size() != 2)
            {
                JOptionPane.showMessageDialog(null, "surrender only available after first two cards", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //return half the bet to the player (they already bet the full amount, so add back half)
            double halfBet = currentBet / 2.0;
            player.pushMoney(halfBet);
            balanceLabel.setText("Balance: $" + String.format("%.2f", player.getMoney()));

            //reveal dealer's hole card
            cardReveal = true;
            panel.repaint();

            //show surrender message
            JOptionPane.showMessageDialog(null, "surrendered", "", JOptionPane.INFORMATION_MESSAGE);

            //reset game
            cardReveal = false;
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
            doubleDownButton.setEnabled(false);
            surrenderButton.setEnabled(false);
            dealButton.setEnabled(true);
            doubleDownUsed = false;
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