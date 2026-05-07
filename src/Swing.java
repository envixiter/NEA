import java.awt.event.ItemEvent;
import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swing extends JFrame implements ActionListener{
    private boolean active = false;
    public void frame(boolean buttons){
        JFrame frame = new JFrame();
        // coordinates start from top left corner
        if(buttons){addbuttons(frame);}
        addbuttons(frame);
        frame.setSize(700, 800);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    public void addbuttons(JFrame frame){
        JButton metronomeToggle = new JButton();
        metronomeToggle.setBounds(100, 100, 20, 20);
        metronomeToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = !active;
                System.out.println("Metronome active = " + active);
                if(active){
                    metronomeWindow();
                }
                else{

                }
            }
        });

        JLabel metronomeLabel = new JLabel();
        metronomeLabel.setBounds(100, 120, 100, 40);
        metronomeLabel.setText("Metronome");


        frame.add(metronomeToggle);
        frame.add(metronomeLabel);
    }
    public void metronomeWindow(){
        JFrame frame = new JFrame();
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add button clicked!");
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
