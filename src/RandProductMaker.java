import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    String name;
    String desc;
    String id;
    double cost;
    static RandomAccessFile raf;
    String path = "src/RAFProductData.txt";
    File usrFile = new File(path);
    int records = 0;
    JPanel mainPnl;
    JPanel formPnl;
    JLabel nameLbl;
    static JTextField inName;
    JLabel descLbl;
    static JTextField inDesc;
    JLabel idLbl;
    static JTextField inID;
    JLabel costLbl;
    static JTextField inCost;
    JPanel ctrlPnl;
    JTextField recordCntField;
    JLabel recordCntLbl;
    JButton quitBtn;
    JButton addBtn;
    JButton clearBtn;
    public RandProductMaker(){
        mainPnl = new JPanel();
        setTitle("File Streams");
        CreateFormPnl();
        mainPnl.add(formPnl);
        CreateCtrlPnl();
        mainPnl.add(ctrlPnl, BorderLayout.SOUTH);
        add(mainPnl);
        setSize(975, 575);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void CreateFormPnl(){
        formPnl = new JPanel();
        formPnl.setLayout(new GridLayout(10, 1));
        nameLbl = new JLabel("Product Name:");
        nameLbl.setBounds(10,20,80,25);
        inName = new JTextField(35);
        inName.setBounds(100, 50, 165, 25);
        descLbl = new JLabel("Product Description:");
        descLbl.setBounds(10, 50, 100, 25);
        inDesc = new JTextField(75);
        inDesc.setBounds(100, 20, 165, 25);
        idLbl = new JLabel("Product ID:");
        idLbl.setBounds(10, 80, 80, 25);
        inID = new JTextField(6);
        inID.setBounds(100, 80, 165, 25);
        costLbl = new JLabel("Product Cost:");
        costLbl.setBounds(10, 110, 80, 25);
        inCost = new JTextField(20);
        inCost.setBounds(100, 110, 165, 25);
        recordCntLbl = new JLabel("Record Count");
        recordCntField = new JTextField(10);
        recordCntField.setEditable(false);
        formPnl.add(nameLbl);
        formPnl.add(inName);
        formPnl.add(descLbl);
        formPnl.add(inDesc);
        formPnl.add(idLbl);
        formPnl.add(inID);
        formPnl.add(costLbl);
        formPnl.add(inCost);
        formPnl.add(recordCntLbl);
        formPnl.add(recordCntField);
    }

    private void CreateCtrlPnl(){
        ctrlPnl = new JPanel();
        ctrlPnl.setLayout(new GridLayout(1, 3));
        addBtn = new JButton("Add");
        addBtn.addActionListener((ActionEvent ae) ->{
            if(validInp()){
                name = namePadding(inName.getText());
                desc = descPadding(inDesc.getText());
                id = idPadding(inID.getText());
                cost = Double.parseDouble(inCost.getText());
                try {
                    raf = new RandomAccessFile(usrFile, "rw");
                    raf.seek(raf.length());
                    raf.writeBytes(id + ", " + name + "," + desc + "," + String.format("%.2f", cost) + "\n");
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                records++;
                recordCntField.setText(String.valueOf(records));
                inName.setText(null);
                inDesc.setText(null);
                inID.setText(null);
                inCost.setText(null);
                JOptionPane.showMessageDialog(this, "Record wrote to file", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (!validInp()){
                JOptionPane.showMessageDialog(this, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) ->{
            inName.setText(null);
            inDesc.setText(null);
            inID.setText(null);
            inCost.setText(null);
            recordCntField.setText(null);
            JOptionPane.showMessageDialog(this, "Product Information Cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));
        ctrlPnl.add(addBtn);
        ctrlPnl.add(clearBtn);
        ctrlPnl.add(quitBtn);
    }
    public static String namePadding(String name){
        do{
            name += " ";
        }while(name.length() < 35);
        return name;
    }
    public static String descPadding(String desc){
        do{
            desc += " ";
        }while(desc.length() < 75);
        return desc;
    }
    public static String idPadding(String id){
        do{
            id += " ";
        }while(id.length() < 6);
        return id;
    }
    public static boolean validInp(){
        String name = inName.getText();
        String desc = inDesc.getText();
        String id = inID.getText();
        double cost = Double.parseDouble(inCost.getText());
        if(name.length() <= 35 && desc.length() <= 75 && id.length() <= 6 && inCost != null){
            return true;
        }
        else{
            return false;
        }
    }
}