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


    public void startup(){
        //initialise all frames here
        // coordinates start from top left corner


        // main page
        JFrame frame = new JFrame();

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
        MetronomeEnabled.setBounds(400,300,100,40);
        JLabel MetLabel = new JLabel();
        MetLabel.setText("Toggle Metronome");
        JLabel BackLabel = new JLabel();
        BackLabel.setBounds(100, 120, 100, 40);
        BackLabel.setText("Back");

        // test
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);
        JFormattedTextField positiveIntField = new JFormattedTextField(numberFormatter);
        positiveIntField.setColumns(10);
        positiveIntField.setBounds(200,350,50,50);
        positiveIntField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);

        // Add a button to read the field value
        Metframe.add(MetLabel);
        Metframe.add(MetronomeEnabled);
        Metframe.add(BackButton);
        Metframe.add(BackLabel);
        Metframe.setSize(700, 800);
        Metframe.setLayout(null);
        Metframe.setVisible(true);
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
                startMetronome((Integer) value);
                MetronomeEnabled.setText("Stop Metronome");
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
            long interval = 6000L / bpm;
            double duration = 0.5;
            interval = (long) (interval - duration);

            while (metronomeActive) {
                double b4 = 987.77 / 2;
                try {
                    sinegenerator.generate(b4,duration,44100, 0.5);
                    System.out.println("b4");
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {
                }
            }
        });
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