package dev.michael;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Layout extends JFrame implements ActionListener{

    public Layout(String title) throws IOException {
        super(title);
        this.setSize(850, 800);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.white));

        //OKHttp instance
        OkHttp okHttp = new OkHttp();

        //JButtons
        JButton prev = new JButton("Prev");
        JButton next = new JButton("Next");

        //Colors
        Color nasaBlue = new Color(10, 61, 145);
        Color nasaRed = new Color(252, 61, 34);

        //Container
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(8,6));
        container.setBackground(nasaBlue);

        //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBorder(new LineBorder(Color.white, 3));
        topPanel.setBackground(nasaBlue);
        topPanel.setLayout(new FlowLayout(5));
        topPanel.add(prev);
        topPanel.add(next);

        //JTextArea for image title
        JTextArea title1 = new JTextArea();
        title1.setBackground(nasaBlue);
        title1.setSize(100,100);
        title1.setForeground(Color.white);
        title1.setText("                           " + okHttp.getDate() + " : " + okHttp.getTitle());
        topPanel.add(title1);

        container.add(topPanel, BorderLayout.NORTH);

        //JLabel for imageIcon
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBorder(new LineBorder((nasaRed), 3));
        label.setBounds(100,100,850,600);

        //ImageIcon for APOD
        String apod = okHttp.getHdUrl();
        URL apodUrl = new URL(apod);
        BufferedImage urlImage = ImageIO.read(apodUrl);
        ImageIcon icon = new ImageIcon(urlImage);
        //Scaling the image
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaled = new ImageIcon(imgScale);
        label.setIcon(scaled);

        container.add(label);

        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(3));

        //Bottom Text Area for Image Explanation
        JTextArea text = new JTextArea();
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setSize(800, 300);
        text.setText(okHttp.getExplanation());
        text.setFont(Font.getFont("arial"));
        bottomPanel.add(text);
        bottomPanel.setBorder(new LineBorder(nasaBlue, 5));
        container.add(bottomPanel, BorderLayout.SOUTH);

        //prev and next button action listeners
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] newData = okHttp.getPrevDateData();
                    URL newUrl = new URL(newData[0]);
                    BufferedImage urlImage = ImageIO.read(newUrl);
                    ImageIcon icon = new ImageIcon(urlImage);
                    label.setIcon(icon);
                    Image img = icon.getImage();
                    Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaled = new ImageIcon(imgScale);
                    label.setIcon(scaled);
                    //title
                    title1.setText("                           " + newData[3] + " : " + newData[1]);
                    //explanation
                    text.setText(newData[2]);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] newData = okHttp.getNextDateData();
                    URL newUrl = new URL(newData[0]);
                    BufferedImage urlImage = ImageIO.read(newUrl);
                    ImageIcon icon = new ImageIcon(urlImage);
                    label.setIcon(icon);
                    Image img = icon.getImage();
                    Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaled = new ImageIcon(imgScale);
                    label.setIcon(scaled);
                    //title
                    title1.setText("                              " + newData[3] + " : " + newData[1]);
                    //explanation
                    text.setText(newData[2]);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void main(String[] args) throws IOException {
        Layout layout = new Layout("NASA ASTRONOMY PICTURE OF THE DAY");
        layout.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
