package Slumlord;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import encapsulation.Robot;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import javax.swing.DebugGraphics;
import javax.swing.DefaultListModel;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class playSlumlord {

	private JFrame frmSlumlord;
	private static DefaultListModel<character> listModel;
	private JList<character> list;
	JButton two = new JButton();
	JButton three = new JButton();
	JButton four = new JButton();
	private int players;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					playSlumlord window = new playSlumlord();
					window.frmSlumlord.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public playSlumlord() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSlumlord = new JFrame();
		frmSlumlord.setBackground(Color.WHITE);
		frmSlumlord.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\salty\\Desktop\\Background 1.jpg"));
		frmSlumlord.setTitle("Slumlord");
		frmSlumlord.setBounds(100, 100, 1920, 1080);
		frmSlumlord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSlumlord.getContentPane().setLayout(null);
		
		JLabel lblWelcomeToSlumlord = new JLabel("Welcome to Slumlord");
		lblWelcomeToSlumlord.setFont(new Font("Tahoma", Font.PLAIN, 85));
		lblWelcomeToSlumlord.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToSlumlord.setBounds(72, 72, 1920, 100);
		frmSlumlord.getContentPane().add(lblWelcomeToSlumlord);
		
		
		JFrame frame = new JFrame();
		JButton btnRules = new JButton("Rules");
		frame.setTitle("Rules of Play");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame.getComponent(0), "Rules Go Here");
			}
		});
		btnRules.setBounds(1046, 249, 89, 23);
		frmSlumlord.getContentPane().add(btnRules);
		
		listModel = new DefaultListModel<character>();
		
		list = new JList<character>(listModel);
		list.setVisible(false);
		list.setOpaque(false);
		list.setBackground(Color.WHITE);
		list.setBounds(701, 304, 700, 205);
		frmSlumlord.getContentPane().add(list);
		
		JButton two = new JButton("2 Players");
		two.setOpaque(false);
		two.setVisible(false);
		two.setBounds(749, 520, 89, 23);
		frmSlumlord.getContentPane().add(two);
		
		JButton three = new JButton("3 Players");
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 3;
			}
		});
		three.setOpaque(false);
		three.setVisible(false);
		three.setBounds(987, 520, 89, 23);
		frmSlumlord.getContentPane().add(three);
		
		JButton four = new JButton("4 Players");
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 4;
			}
		});
		four.setVisible(false);
		four.setOpaque(false);
		four.setBounds(1195, 520, 89, 23);
		frmSlumlord.getContentPane().add(four);
		
		character Chester = new character("Chester", "Descript", "Skill",2,4,6,"Blue");
		character Bobby = new character("Bobby", "Descript", "Skill",2,4,6,"Green");
		character a = new character("Please Select a Character", "","",2,4,6,"");
		listModel.add(0,a);
		listModel.add(1,Chester);
		listModel.add(2,Bobby);
		
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				two.setVisible(true);
				three.setVisible(true);
				four.setVisible(true);
			}
		});
		btnStartGame.setBounds(890, 249, 89, 23);
		frmSlumlord.getContentPane().add(btnStartGame);
		
		JList charList = new JList();
		charList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		charList.setVisibleRowCount(4);
		charList.setBounds(10, 11, 193, 126);
		frmSlumlord.getContentPane().add(charList);
		
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 2;
				list.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
			}
		});
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 3;
				list.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
			}
		});
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 4;
				list.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
			}
		});
		
	}
	
}
