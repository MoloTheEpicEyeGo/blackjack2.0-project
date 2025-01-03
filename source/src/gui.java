import javax.swing.*;
import java.awt.*;

public class gui {
    public static void main(String[] args)
    {
        //main window
        JFrame frame = new JFrame("Blackjack 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //set frame to middle of the screen

        //workspace inside the window
        JPanel panel = new JPanel();
        panel.setBackground(new Color(53, 101, 77));

        //button panel (where buttons are located)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(101, 29, 29));

        //define individual buttons
        JButton dealButton = new JButton("Deal");
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");

        dealButton.setFocusable(false);
        hitButton.setFocusable(false);
        stayButton.setFocusable(false);

        //add individual buttons to the button panel
        buttonPanel.add(dealButton, BorderLayout.WEST);
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);

        //add components to the frame with layout positions
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER); //add workspace to the center of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH); //add button panel to the bottom of the frame

        //make the frame visible
        frame.setVisible(true);
    }
}