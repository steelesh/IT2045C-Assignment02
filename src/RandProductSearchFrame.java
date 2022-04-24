import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RandProductSearchFrame extends JFrame {
    JPanel mainPnl;
    JPanel searchPnl;
    JTextField searchField;
    JTextArea resArea;
    JScrollPane scroller;
    JPanel titlePnl;
    JLabel title;
    JPanel ctrlPnl;
    JButton searchBtn;
    JButton quitBtn;
    String res;
    ArrayList<String> rafIn = new ArrayList<>();
    StringBuffer sb;
    Stream<String> stream;
    String path = "src/RAFProductData.txt";
    File usrFile = new File(path);
    public RandProductSearchFrame() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        setTitle("Product Search");
        setSize(1100, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateTitlePnl();
        mainPnl.add(titlePnl, BorderLayout.NORTH);
        CreateSearchPnl();
        mainPnl.add(searchPnl, BorderLayout.CENTER);
        CreateCtrlPnl();
        mainPnl.add(ctrlPnl, BorderLayout.SOUTH);
        add(mainPnl);
        setVisible(true);
    }
    private void CreateTitlePnl(){
        titlePnl = new JPanel();
        title = new JLabel("Search Product:");
        searchField = new JTextField("", 20);
        titlePnl.add(title);
        titlePnl.add(searchField);
    }
    private void CreateSearchPnl(){
        searchPnl = new JPanel();
        resArea = new JTextArea(30, 60);
        resArea.setEditable(false);
        scroller = new JScrollPane(resArea);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        searchPnl.add(scroller);
    }
    private void CreateCtrlPnl(){
        ctrlPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) ->{
            System.exit(0);
        });
        searchBtn = new JButton("Search");
        searchBtn.addActionListener((ActionEvent ae ) ->{
            res = searchField.getText();
            if(res.isEmpty()){
                JOptionPane.showMessageDialog(this, "Cannot search for empty string!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                sb = new StringBuffer();
                try {
                    RandomAccessFile raf = new RandomAccessFile(usrFile, "rw");
                    while(raf.getFilePointer() < raf.length()){
                        rafIn.add(raf.readLine() + "\n");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stream = rafIn.stream().filter(s -> s.contains(res));
            stream.forEach(s -> resArea.append(s));
        });
        ctrlPnl.add(searchBtn);
        ctrlPnl.add(quitBtn);
    }
}