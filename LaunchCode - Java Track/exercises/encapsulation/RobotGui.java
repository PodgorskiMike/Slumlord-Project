package encapsulation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RobotGui {

	private JFrame frmFunWithRobots;
	private DefaultListModel<Robot> listModel;
	private JList list;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotGui window = new RobotGui();
					window.frmFunWithRobots.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RobotGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFunWithRobots = new JFrame();
		frmFunWithRobots.setTitle("Fun With Robots");
		frmFunWithRobots.setBounds(100, 100, 646, 455);
		frmFunWithRobots.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnCreateARobot = new JButton("Create a Robot");
		btnCreateARobot.setBounds(10, 11, 118, 23);
		btnCreateARobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = (String)JOptionPane.showInputDialog(
					frmFunWithRobots,
					"What is this Robots Name?",
					"Name Dialog", JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"");
				int x = getValue("Starting Point on the X", "Position X Value");
				int y = getValue("Starting Point on the Y", "Position Y Value");
				int s = getValue("How Fast is this Robot", "Speed Value");
				int o = getValue("What direction is this Robot facing (1=N, 2=E, 3=S, 4=W", "Starting Orientation");
				int i = 0;
				char z = 'Q';
				if(o == 1)
					z = 'N';
				else if(o == 2)
					z = 'E';
				else if(o == 3)
					z = 'S';
				else if(o == 4)
					z = 'W';
				Robot r = new Robot(name, x,y,s,z);
				listModel.add(i,r);
				i++;
			}
		});
		frmFunWithRobots.getContentPane().setLayout(null);
		frmFunWithRobots.getContentPane().add(btnCreateARobot);
		
		JButton btnDisplayAllRobots = new JButton("Display All Robots");
		btnDisplayAllRobots.setBounds(10, 45, 118, 23);
		frmFunWithRobots.getContentPane().add(btnDisplayAllRobots);
		
		JButton btnMoveARobot = new JButton("Move a Robot");
		btnMoveARobot.setBounds(10, 79, 118, 23);
		frmFunWithRobots.getContentPane().add(btnMoveARobot);
		
		JButton btnRotateARobot = new JButton("Rotate a Robot");
		btnRotateARobot.setBounds(10, 113, 118, 23);
		frmFunWithRobots.getContentPane().add(btnRotateARobot);
		
		JButton btnDistanceBetween = new JButton("Distance Between 2 Robots");
		btnDistanceBetween.setBounds(10, 145, 165, 23);
		frmFunWithRobots.getContentPane().add(btnDistanceBetween);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(10, 179, 89, 23);
		frmFunWithRobots.getContentPane().add(btnExit);
		
		listModel = new DefaultListModel<Robot>();
		list = new JList(listModel);
		list.setBounds(230, 14, 390, 391);
		frmFunWithRobots.getContentPane().add(list);
		
		JList list_1 = new JList();
		list_1.setBounds(10, 213, 199, 178);
		frmFunWithRobots.getContentPane().add(list_1);
	}
	public int getValue(String prompt, String title)
	{
		String s = (String)JOptionPane.showInputDialog(
				frmFunWithRobots,
				prompt,
				title, JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"");
		int t = Integer.parseInt(s);
		return t;
				
				
	}
}
