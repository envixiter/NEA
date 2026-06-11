import java.awt.event.ItemEvent;
import java.io.*;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class Swing extends JFrame implements ActionListener{
    private boolean metronomeActive = false;
    private Thread metronomeThread;


    public void startup() {
        //initialise all frames here
        // coordinates start from top left corner


        // main page
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton metronomeToggle = new JButton();
        metronomeToggle.setBounds(100, 100, 20, 20);

        JLabel metronomeLabel = new JLabel();
        metronomeLabel.setBounds(100, 120, 100, 40);
        metronomeLabel.setText("Metronome");

        frame.add(metronomeToggle);
        frame.add(metronomeLabel);
        frame.setSize(700, 800);
        frame.setLayout(null);

        // metronome page
        JFrame Metframe = new JFrame();

        JButton BackButton = new JButton();
        BackButton.setBounds(100, 100, 20, 20);
        JButton MetronomeEnabled = new JButton();
        MetronomeEnabled.setText("Start Metronome");
        MetronomeEnabled.setBounds(400, 300, 150, 40);
        JLabel MetLabel = new JLabel();
        MetLabel.setText("Toggle Metronome");
        JLabel BackLabel = new JLabel();
        BackLabel.setBounds(100, 120, 100, 40);
        BackLabel.setText("Back");

        // bpm input
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(true);
        numberFormatter.setCommitsOnValidEdit(true);
        JFormattedTextField positiveIntField = new JFormattedTextField(numberFormatter);
        JLabel inputLabel = new JLabel("Input a BPM");
        inputLabel.setBounds(200, 320, 90, 20);
        positiveIntField.setColumns(10);
        positiveIntField.setBounds(200, 350, 50, 50);
        positiveIntField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);


        Metframe.add(inputLabel);
        Metframe.add(MetLabel);
        Metframe.add(MetronomeEnabled);
        Metframe.add(BackButton);
        Metframe.add(BackLabel);
        Metframe.setSize(700, 800);
        Metframe.setLayout(null);
        Metframe.add(positiveIntField);


        metronomeToggle.addActionListener(e -> {
            Metframe.setVisible(true);
            frame.setVisible(false);
        });
        BackButton.addActionListener(e -> {
            frame.setVisible(true);
            Metframe.setVisible(false);
        });
        MetronomeEnabled.addActionListener(e -> {
            if (!metronomeActive) {
                Object value = positiveIntField.getValue();
                System.out.println("Metronome started at " + value + " BPM");
                if (value == null) {
                    JOptionPane.showMessageDialog(null, "Invalid BPM/Data Type!  Try a positive integer", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if ((Integer) value < 20 | (Integer) value > 300) {
                        JOptionPane.showMessageDialog(null, "BPM must be between 20 and 300!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        startMetronome((Integer) value);
                        MetronomeEnabled.setText("Stop Metronome");
                    }
                }
            } else {
                stopMetronome();
                MetronomeEnabled.setText("Start Metronome");
            }
        });
        frame.setVisible(true);
    }
    private void startMetronome(int bpm) {
        metronomeActive = true;
        metronomeThread = new Thread(() -> {
            long interval = 60000L / bpm; // ms per beat
            double duration = 0.5; // seconds
            double frequency = 987.77 / 2; // Hz
            int current = 0;
            while (metronomeActive) {
                try {
                    if(current == 0) {
                        sinegenerator.generate(frequency * 2, duration, 44100, 0.5); // b5
                    }
                    else{
                        sinegenerator.generate(frequency, duration, 44100, 0.5);
                    }
                    current ++;
                    if(current == 4){
                        current = 0; // reset loop
                    }
                    System.out.println("Tick");

                    // sleep for the remainder of the beat
                    Thread.sleep(interval);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });

        metronomeThread.setDaemon(true); // optional: stops with app
        metronomeThread.start();
    }
    private void stopMetronome() {
        metronomeActive = false;
        if (metronomeThread != null) {
            metronomeThread.interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}