package com.nathan.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.util.Properties;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FilesUI extends JFrame {
    //private GridLayout layoutMain = new GridLayout();
    private JPanel panelCenter = new JPanel();
    private JLabel statusBar = new JLabel();
    private JLabel isUseConfig_label = new JLabel();
    private JCheckBox isUseConfig = new JCheckBox();
    private JLabel isConsistent_label = new JLabel();
    private JCheckBox isConsistent = new JCheckBox();
    private JLabel configPath_label = new JLabel();
    private JTextField configPath = new JTextField();
    private JLabel srcPath_label = new JLabel();
    private JTextField srcPath = new JTextField();
    private JLabel destPath_label = new JLabel();
    private JTextField destPath = new JTextField();
    private JSeparator jSeparator1 = new JSeparator();
    private JButton start_button = new JButton();
    private JProgressBar jProgressBar1 = new JProgressBar();
    private JTextArea logs = new JTextArea();
    private JScrollPane   jScrollPane   =   new   JScrollPane(logs);
    private JLabel logs_label = new JLabel();
    private Set paths;
    private String runPath;
    private JButton reset_button = new JButton();
   


    public FilesUI(String runPath) {
        try {
            this.runPath = runPath;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        panelCenter.setLayout( null );
        panelCenter.setAutoscrolls(true);
        this.setSize( new Dimension(500, 400) );
        this.setTitle( "Files Synchronizer" );
        panelCenter.setSize(500, 400);
        
        isUseConfig_label.setText("是否使用配置文件:");
        isUseConfig_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        isUseConfig_label.setBounds(33,30,150,20);
        isUseConfig.setBorderPaintedFlat(true);
        isUseConfig.setSelected(true);
        isUseConfig.setBounds(180,30,50,20);
        
        isConsistent_label.setText("保持同步目录一致性:");
        isConsistent_label.setBounds(new Rectangle(240, 30, 150, 20));
        isConsistent.setBounds(new Rectangle(400, 30, 50, 20));
        isConsistent.setBorderPaintedFlat(true);
        isConsistent.setSelected(true);
        
        configPath_label.setText("选择配置文件:");
        configPath_label.setBounds(60,70,100,20);
        configPath.setBounds(new Rectangle(180, 70, 285, 25));
        
        srcPath_label.setText("原地址路径:");
        srcPath_label.setBounds(73,120,100,20);
        srcPath.setBounds(new Rectangle(180, 120, 285, 25));
        srcPath.setEnabled(false);
        
        destPath_label.setText("目标地址路径:");
        destPath_label.setBounds(60,170,100,20);
        destPath.setBounds(new Rectangle(180, 170, 285, 25));
        destPath.setEnabled(false);
        
        
        reset_button.setText("重置");
        reset_button.setBounds(new Rectangle(68, 220, 85, 25));
        start_button.setText("开始");
        start_button.setBounds(new Rectangle(180, 220, 98, 25));
        
        jProgressBar1.setBounds(new Rectangle(315, 221, 150, 22));
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);
        jProgressBar1.setValue(0);
        jProgressBar1.setPreferredSize(new Dimension(300, 20));
        jProgressBar1.setBorderPainted(true);
        jProgressBar1.setBackground(new Color(255, 247, 214));
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setForeground(new Color(82, 255, 82));
        jProgressBar1.setToolTipText("执行进度");
        jSeparator1.setBounds(new Rectangle(0, 270, 500, 400));
       
        logs_label.setText("同步状态日志:");
        
        logs_label.setBounds(60,290,100,20);
        logs.setBounds(new Rectangle(180, 285, 285, 70));
        logs.setEditable(false);
        logs.setLineWrap(true);
        logs.setAutoscrolls(true);
        logs.setText("--程序运行目录：[" + runPath + "]");
        jScrollPane.setBounds(new Rectangle(180, 285, 285, 70));

        ValuesChangedListener vcListener = new  ValuesChangedListener();
        FileActionListener  faListener = new FileActionListener();
        isUseConfig.addChangeListener(vcListener);
        start_button.addActionListener(faListener);
        reset_button.addActionListener(faListener);
       // this.getContentPane().add( statusBar);
     
        panelCenter.add(isConsistent, null);
        panelCenter.add(isConsistent_label, null);
        panelCenter.add(reset_button, null);
        panelCenter.add(isUseConfig, null);
        panelCenter.add(isUseConfig_label, null);
        
        panelCenter.add(configPath, null);
        panelCenter.add(configPath_label, null);
        
        panelCenter.add(srcPath, null);
        panelCenter.add(srcPath_label, null);
        
        panelCenter.add(destPath, null);
        panelCenter.add(destPath_label, null);
        
        panelCenter.add(start_button, null);
        panelCenter.add(jProgressBar1, null);
        
        panelCenter.add(jSeparator1, null);
       
        panelCenter.add(logs_label, null);
        panelCenter.add(jScrollPane, null);
        this.getContentPane().add(panelCenter,null);
       // paths = DirConfigLoader.load("D:\\JdeMywork\\FilesTools\\FilesSynchronizer\\classes\\com\\nathan\\utility\\configuration.xml");
        paths = DirConfigLoader.load("configuration.xml");
    }
    
    private class ValuesChangedListener implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            if(e.getSource() == isUseConfig){
                if(isUseConfig.isSelected()){
                   configPath.setEnabled(true);
                   srcPath.setEnabled(false);
                   destPath.setEnabled(false);
                }
                else{
                  configPath.setEnabled(false);
                  srcPath.setEnabled(true);
                  destPath.setEnabled(true);
                }
            }
          if(e.getSource() == srcPath){
             
          }
        }
        
    }
    
    private class FileActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
           if(e.getSource() == reset_button){
             start_button.setText("开始");
             start_button.setEnabled(true);
             logs.setText("");
             jProgressBar1.setValue(0);
             ProgressPeer.setDistribute(0.00);
             ProgressPeer.setProgress(0);
            }
            if(e.getSource() == start_button){
              ProgressMonitor pm = new ProgressMonitor();
              Executer ect = new Executer();
              pm.start();
              ect.start();  
            }
        }
    }
    
    private class ProgressMonitor extends Thread{
        
        public void run() {
            while(ProgressPeer.getProgress() <= 100){
              jProgressBar1.setValue(ProgressPeer.getProgress());
            }
        }

    }
    
    private class Executer extends Thread{
      public void run() {
        start_button.setText("同步中...");
        start_button.setEnabled(false);
        int endIndex = logs.getText().indexOf("]");
        String message = logs.getText().substring(0, endIndex + 1);
        logs.setText( message + "\n--开始执行文件同步操作... \n");

        ProgressPeer.setProgress(1);
       // logs.setText(logs.getText() + "--original paths" + paths.size() + " \n");
        if(isUseConfig.isSelected()){
            String path = configPath.getText().trim();
            if(!path.equals("")){
             // logs.setText(logs.getText() + "--pathCon" + path + " \n");
              paths = DirConfigLoader.load(path);
            }
 
          ProgressPeer.setProgress(5);
        }
        else{
             try {
                 if (srcPath.getText().trim().equals("")) {
                     
                     throw new FilesSynchronizeException("srcPath is null,must not be null");
                 }
                 if (destPath.getText().trim().equals("")) {
                     throw new FilesSynchronizeException("destPath is null,must not be null");
                 }
             } catch (FilesSynchronizeException fse) {
                 
                 logs.setText(logs.getText() + "--错误：" + fse.getMessage() + "\n");
                 fse.printStackTrace();
             }
           // logs.setText(logs.getText() + "--src:" + srcPath.getText().trim() + " \n");
             paths =
                     DirConfigLoader.load(srcPath.getText().trim(), destPath.getText().trim());
            
         // logs.setText(logs.getText() + "--paths size:" + paths.size() + " \n");
           // jProgressBar1.setValue(5);
          ProgressPeer.setProgress(5);

        }
        //logs.setText(logs.getText() + "--paths size:" + paths.size() + " \n");
        logs.setText(logs.getText() + "--同步路径配置完毕... \n");
        FilesProcess fp = new FilesProcess(isConsistent.isSelected());
        // jProgressBar1.setValue(20);
        ProgressPeer.setProgress(10);
        if(paths.size() < 1){logs.setText(logs.getText() + "--警告：没有可同步的文件夹，请检查配置文件或输入需要同步的路径 \n");  return;}
        logs.setText(logs.getText() + "--文件同步中... \n");
        fp.execute(paths);
       // System.out.println("ProgressPeer:" + ProgressPeer.getProgress());
        ProgressPeer.setProgress(100);
        fp = null;
       // System.out.println("ProgressPeer:" + ProgressPeer.getProgress());
    
       //jProgressBar1.setValue(ProgressPeer.getProgress());
            try {
                this.sleep(2000);
            } catch (InterruptedException ie) {
                // TODO: Add catch code
                ie.printStackTrace();
            }

        start_button.setText("结束");
        logs.setText(logs.getText() + "--同步操作完成！ \n");
        ProgressPeer.setProgress(101);
        
      }
    }
    
    
    public static void main(String arg[]){
       FilesUI files = new FilesUI(System.getProperty("user.dir"));
        files.setBounds(300, 200, 500, 420);
       files.setMinimumSize(new Dimension(500,420));
       
       files.addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent e){
             System.exit(0);
           }
       });
       
       files.pack();
       files.setVisible(true);
    }
}
