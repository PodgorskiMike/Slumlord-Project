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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class playSlumlord {
	//for character selection
	private JFrame frmSlumlord;
	private static DefaultListModel<character> listModel;
	private JList<character> list;
	//Buttons for player number selection
	JButton two = new JButton();
	JButton three = new JButton();
	JButton four = new JButton();
	//variable to keep track of how many players
	private int players;
	//Arraylist of selected characters playing in game
	public ArrayList<character> playerChars = new ArrayList<character>();
	//variable for which players turn it is
	int playersTurn = 0;
	//variable for what round it is
	int round = 1;
	//variable for tenant selected to move in
	int currentTenant;
	//variable for dice to be rolled
	int numDice = 0;
	//variable to hold avaliable tenant tokens
	tenantTokens avaliable = new tenantTokens();
	//variable for tenant tokens
	tenantTokens game = new tenantTokens();
	//variable for rerolls
	int reroll = 0;
	// variable for current dice results
	dice currentRoll = new dice();
	//arraylist to hold repair cards
	protected ArrayList<repairCard> all = new ArrayList<repairCard>();
	protected ArrayList<repairCard> used = new ArrayList<repairCard>();
	//variable to manage repairs
	int DamageVar = -1;
	//variable for phase control
	int phase = 1;
	
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
		
		//welcome label
		JLabel lblWelcomeToSlumlord = new JLabel("Galactic Slumlord");
		lblWelcomeToSlumlord.setForeground(Color.WHITE);
		lblWelcomeToSlumlord.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 85));
		lblWelcomeToSlumlord.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToSlumlord.setBounds(451, 82, 1100, 100);
		frmSlumlord.getContentPane().add(lblWelcomeToSlumlord);
		
		
		
		JFrame frame = new JFrame();
		
		//Button to display basic rules
		JButton btnRules = new JButton("Rules");
		btnRules.setBorderPainted(false);
		btnRules.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnRules.setForeground(SystemColor.inactiveCaption);
		btnRules.setContentAreaFilled(false);
		btnRules.setBorder(null);
		frame.setTitle("Rules of Play");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame.getComponent(0), "Rules Go Here");
			}
		});
		btnRules.setBounds(1046, 249, 89, 23);
		frmSlumlord.getContentPane().add(btnRules);
		
		//initalizing dice
		dice gameDice = new dice();
		
		//create repair cards
		repairCard aa = new repairCard("Toilet Over Flowing", 1, 0, "Any Property");
		all.add(aa);
		repairCard bb = new repairCard("Door Kicked In", 1, 1,"Any Red Property");
		all.add(bb);
		repairCard cc = new repairCard("Break In", 1, 2,"Any Blue Property");
		all.add(cc);
		
		
		//making character list
		listModel = new DefaultListModel<character>();
		list = new JList<character>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 14));
		list.setVisible(false);
		list.setOpaque(false);
		list.setBackground(Color.WHITE);
		list.setBounds(701, 304, 700, 205);
		frmSlumlord.getContentPane().add(list);
		
		//create all characters
		character Chester = new character("Chester", "desc", "Skill: Preventative Maintaince. Discard First Repair Card Each Round",2,4,6,"Purple");
		character Bobby = new character("Bobby", "Descript", "Skill: Bank Connections. $200 off the Purchase of New Buildings",2,4,6,"Orange");
		//character a = new character("Please Select a Character", "","",2,4,6,"");
		//populating character list
		//listModel.add(0,a);
		listModel.add(0,Chester);
		listModel.add(1,Bobby);
		
		//Buttons for player number selection
		JButton two = new JButton("2 Players");
		two.setContentAreaFilled(false);
		two.setBorderPainted(false);
		two.setBorder(null);
		two.setForeground(SystemColor.inactiveCaption);
		two.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		two.setOpaque(false);
		two.setVisible(false);
		two.setBounds(674, 520, 162, 23);
		frmSlumlord.getContentPane().add(two);
		
		JButton three = new JButton("3 Players");
		three.setContentAreaFilled(false);
		three.setBorderPainted(false);
		three.setBorder(null);
		three.setForeground(SystemColor.inactiveCaption);
		three.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		three.setOpaque(false);
		three.setVisible(false);
		three.setBounds(900, 520, 176, 23);
		frmSlumlord.getContentPane().add(three);
		
		JButton four = new JButton("4 Players");
		four.setContentAreaFilled(false);
		four.setBorderPainted(false);
		four.setBorder(null);
		four.setForeground(SystemColor.inactiveCaption);
		four.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		four.setVisible(false);
		four.setOpaque(false);
		four.setBounds(1108, 520, 176, 23);
		frmSlumlord.getContentPane().add(four);
		
		//start game button
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnStartGame.setContentAreaFilled(false);
		btnStartGame.setBorderPainted(false);
		btnStartGame.setBorder(null);
		btnStartGame.setForeground(SystemColor.inactiveCaption);
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				two.setVisible(true);
				three.setVisible(true);
				four.setVisible(true);
			}
		});
		btnStartGame.setBounds(850, 249, 129, 23);
		frmSlumlord.getContentPane().add(btnStartGame);
		
		//select character initial
		JButton btnSelectCharacter = new JButton("Select Character");
		btnSelectCharacter.setContentAreaFilled(false);
		btnSelectCharacter.setBorderPainted(false);
		btnSelectCharacter.setBorder(null);
		btnSelectCharacter.setForeground(SystemColor.inactiveCaption);
		btnSelectCharacter.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnSelectCharacter.setVisible(false);
		btnSelectCharacter.setBounds(417, 315, 249, 23);
		frmSlumlord.getContentPane().add(btnSelectCharacter);
		
		//main text box initial
		JLabel Turn = new JLabel("");
		Turn.setVisible(false);
		Turn.setHorizontalAlignment(SwingConstants.CENTER);
		Turn.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 23));
		Turn.setBounds(0, 0, 402, 235);
		frmSlumlord.getContentPane().add(Turn);
		
		//make text box to display avaliable tenants each round 
		JLabel avaliableTenants = new JLabel("<html>Tenants Avaliable<br>Red:<br>Blue:<br>Green:</html>");
		avaliableTenants.setForeground(SystemColor.inactiveCaption);
		avaliableTenants.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 17));
		avaliableTenants.setBounds(1611, 61, 222, 153);
		avaliableTenants.setVisible(false);
		frmSlumlord.getContentPane().add(avaliableTenants);
		
		//dice results initial
		JLabel diceResults = new JLabel("");
		diceResults.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		diceResults.setVisible(false);
		diceResults.setFocusable(false);
		diceResults.setBounds(513, 877, 104, 153);
		frmSlumlord.getContentPane().add(diceResults);
		
		//make repair from bank button
		JButton btnTakeBank = new JButton("Take Repairs From Bank");
		btnTakeBank.setForeground(SystemColor.inactiveCaption);
		btnTakeBank.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 14));
		btnTakeBank.setContentAreaFilled(false);
		btnTakeBank.setBorderPainted(false);
		btnTakeBank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank - 200;
				btnTakeBank.setVisible(false);
			}
		});
		btnTakeBank.setVisible(false);
		btnTakeBank.setBounds(31, 806, 257, 23);
		frmSlumlord.getContentPane().add(btnTakeBank);
		
		//Make Card Display panel
		JLabel CardDisplay = new JLabel("");
		CardDisplay.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		CardDisplay.setVisible(false);
		CardDisplay.setBounds(652, 877, 120, 153);
		frmSlumlord.getContentPane().add(CardDisplay);
		
		//initaliza discard button
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.setVisible(false);
		btnDiscard.setForeground(SystemColor.inactiveCaption);
		btnDiscard.setContentAreaFilled(false);
		btnDiscard.setBorderPainted(false);
		btnDiscard.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnDiscard.setBounds(760, 845, 134, 23);
		frmSlumlord.getContentPane().add(btnDiscard);
		
		//initalize next phase button
		JButton btnNextPhase = new JButton("Next Phase");
		btnNextPhase.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnNextPhase.setContentAreaFilled(false);
		btnNextPhase.setBorderPainted(false);
		btnNextPhase.setBorder(null);
		btnNextPhase.setForeground(SystemColor.inactiveCaption);
		btnNextPhase.setVisible(false);
		btnNextPhase.setBounds(615, 843, 147, 23);
		frmSlumlord.getContentPane().add(btnNextPhase);
		
		JButton btnDrawCard = new JButton("Draw Card");
		btnDrawCard.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnDrawCard.setContentAreaFilled(false);
		btnDrawCard.setBorderPainted(false);
		btnDrawCard.setBorder(null);
		btnDrawCard.setForeground(SystemColor.inactiveCaption);
		btnDrawCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentRoll.Red > 0)
				{					
					if(all.size() == 0)
					{
						all = used;
					}
					btnDrawCard.setVisible(false);
					Random generator = new Random();
					repairCard Drawn = new repairCard("",0,0,"");
					int j = generator.nextInt(all.size());
					Drawn = all.get(j);
					all.remove(j);
					used.add(Drawn);
					CardDisplay.setText("<html>" + Drawn.Description + "<br>" + Drawn.Damage + "<br>" + Drawn.Which);
					DamageVar = Drawn.WhichProperty;
					boolean canUse = false;
					if(DamageVar == 0)
					{
						canUse = true;
						Turn.setText("<html> Click on the Property that you<br> want to apply the Damage to.</html>");
					}
					else
					{
						for(int p = 0; p < playerChars.get(playersTurn).AllOwned.size(); p++)
						{
							int q = playerChars.get(playersTurn).AllOwned.get(p).Color;
							if(q == DamageVar)
							{
								canUse = true;
							}
						}
						if(canUse == false)
						{
							Turn.setText("<html>No Properties of that type<br>Click Discard</html>");
							btnDiscard.setVisible(true);
						}
						if(canUse == true)
						{
							Turn.setText("<html> Click on the Property that you<br> want to apply the Damage to.</html>");
						}
					}
				}
			}
		});
		btnDrawCard.setVisible(false);
		btnDrawCard.setBounds(561, 843, 147, 23);
		frmSlumlord.getContentPane().add(btnDrawCard);
		
		//make text box for character card display
		JLabel characterCard = new JLabel("");
		characterCard.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		characterCard.setBounds(10, 877, 493, 153);
		frmSlumlord.getContentPane().add(characterCard);
				
		//Make properties
				property r1 = new property("1",1);
				property r2 = new property("1",1);
		
		//make buttons for each property
		JButton btnRedpropone = new JButton("V");
		btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnRedpropone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).Color) && DamageVar == -1 && r1.Occupied == false && phase == 1)
				{
					//method for moving in green tenant
					if(currentTenant == 1)
					{
						currentRoll = gameDice.Roll(numDice);
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						if(currentRoll.Green >= playerChars.get(playersTurn).LeaseNumRed)
						{
							Turn.setText("<html>You Succsfully Moved the Tenant In!<br>You Rolled Some Red's<br> Click on the Draw Cards Button");
							playerChars.get(playersTurn).RedOccupied = playerChars.get(playersTurn).RedOccupied + 1;
							btnRedpropone.setText("O");
							r1.Occupied = true;
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + playerChars.get(playersTurn).DepRed;
							numDice = 0;
							avaliable.RedTenant = avaliable.RedTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
							
						}
						else if(reroll < 1)
						{
							Turn.setText("<html>Sorry the Dice Hate You <br> You can Reroll your Red dice if you like");
							numDice = currentRoll.Red;
							reroll++;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
						else if(reroll >= 1)
						{
							Turn.setText("<html>Sorry the Dice Still Hate You<br> You Failed to Move the Tenant In<br> Click on the Draw Card Button</html>");
							numDice = 0;
							reroll = 2;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
					
					}
					//method for moving in blue tenant
					if(currentTenant == 2)
					{
						currentRoll = gameDice.Roll(numDice);
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						if(currentRoll.Green >= playerChars.get(playersTurn).LeaseNumBlue)
						{
							Turn.setText("<html>You Succsfully Moved the Tenant In!<br>You Rolled Some Red's<br> Click on the Draw Cards Button");
							playerChars.get(playersTurn).BlueOccupied = playerChars.get(playersTurn).BlueOccupied + 1;
							btnRedpropone.setText("O");
							r1.Occupied = true;
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + playerChars.get(playersTurn).DepBlue;
							numDice = 0;
							avaliable.BlueTenant = avaliable.BlueTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
						else if(reroll < 1)
						{
							Turn.setText("<html>Sorry the Dice Hate You <br> You can Reroll your Red dice if you like");
							numDice = currentRoll.Red;
							reroll++;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
						else if(reroll >= 1)
						{
							Turn.setText("<html>Sorry the Dice Still Hate You<br> You Failed to Move the Tenant In<br> Click on the Draw Card Button</html>");
							numDice = 0;
							reroll = 2;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
					
					}
				}
				//method for moving in green tenant
				if(currentTenant == 3)
				{
					currentRoll = gameDice.Roll(numDice);
					diceResults.setVisible(true);
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
					if(currentRoll.Green >= playerChars.get(playersTurn).LeaseNumGreen)
					{
						Turn.setText("<html>You Succsfully Moved the Tenant In!<br>You Rolled Some Red's<br> Click on the Draw Cards Button");
						playerChars.get(playersTurn).GreenOccupied = playerChars.get(playersTurn).GreenOccupied + 1;
						btnRedpropone.setText("O");
						r1.Occupied = true;
						playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + playerChars.get(playersTurn).DepGreen;
						numDice = 0;
						avaliable.GreenTenant = avaliable.GreenTenant - 1;
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
						}
					}
					else if(reroll < 1)
					{
						Turn.setText("<html>Sorry the Dice Hate You <br> You can Reroll your Red dice if you like");
						numDice = currentRoll.Red;
						reroll++;
					}
					else if(reroll >= 1)
					{
						Turn.setText("<html>Sorry the Dice Still Hate You<br> You Failed to Move the Tenant In<br> Click on the Draw Card Button</html>");
						numDice = 0;
						reroll = 2;
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
						}
					}
				
				}
				//method for adding damage to property
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).Color) && DamageVar != -1 && phase == 1)
				{
					if(DamageVar == 0 || DamageVar == 1)
					{
						if(r1.Damage < 3)
						{
							r1.Damage = r1.Damage +1;
							currentRoll.Red = currentRoll.Red - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						}
						else
						{
							Turn.setText("<html>Cannot take more Damage! <br> Apply to another Property<br> or you can click Take From Bank");
							btnTakeBank.setVisible(true);
							
						}
						if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
						}
						DamageVar = -1;
						if(r1.Occupied == true)
						{
							if(r1.Damage == 1)
							{
								btnRedpropone.setText("<html> X <br> O <br> </html>");
							}
							if(r1.Damage == 2)
							{
								btnRedpropone.setText("<html> XX <br> O <br> </html>");
							}
							if(r1.Damage == 3)
							{
								btnRedpropone.setText("<html> XXX <br> O <br> </html>");
							}
						}
						if(r1.Occupied == false)
						{
							if(r1.Damage == 1)
							{
								btnRedpropone.setText("<html> X </html>");
							}
							if(r1.Damage == 2)
							{
								btnRedpropone.setText("<html> XX  </html>");
							}
							if(r1.Damage == 3)
							{
								btnRedpropone.setText("<html> XXX  </html>");
							}
						}
						
					}
					else
					{
						Turn.setText("Cannot Damage this Property Somthing lieky went wrong");
					}
				}
				
				
				//method for buying
				if(r1.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).buy(r1);
					String Col = playerChars.get(playersTurn).Color;
					characterCard.setText(playerChars.get(playersTurn).charCard() );
					
					if(r1.OwnedBy.equals(playerChars.get(playersTurn).Color))
					{
						if(Col.equals("Purple"))
						{
							btnRedpropone.setForeground(Color.MAGENTA);
						}
						if(Col.equals("Orange"))
						{
							btnRedpropone.setForeground(Color.ORANGE);
						}
					}
				}
				
				//method for repairing
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).Color)&& phase == 4)
				{
					String resp = playerChars.get(playersTurn).repair(r1);
					Turn.setText(resp);
					if(r1.Damage == 0)
					{
						btnRedpropone.setText("<html>  <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
					if(r1.Damage == 1)
					{
						btnRedpropone.setText("<html> X <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
					if(r1.Damage == 2)
					{
						btnRedpropone.setText("<html> XX <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
					if(r1.Damage == 3)
					{
						btnRedpropone.setText("<html> XXX <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
				}
				//Method for upgrading
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).Color) && phase == 5)
				{
					String Upresp = playerChars.get(playersTurn).upgrade(r1);
					Turn.setText(Upresp);
					characterCard.setText(playerChars.get(playersTurn).charCard() );
					if(r1.Upgrades == 1)
					{
						btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 3));
					}
					if(r1.Upgrades == 2)
					{
						btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 6));
					}
					
					if(r1.Upgrades == 3)
					{
						r1.Color = 2;
						btnRedpropone.setBackground(Color.BLUE);
						Turn.setText(Upresp);
						playerChars.get(playersTurn).RedOwned = playerChars.get(playersTurn).RedOwned -1;
						playerChars.get(playersTurn).BlueOwned = playerChars.get(playersTurn).BlueOwned +1;
						if(r1.Occupied == true)
						{
							playerChars.get(playersTurn).RedOccupied = playerChars.get(playersTurn).RedOccupied -1;
							playerChars.get(playersTurn).BlueOccupied = playerChars.get(playersTurn).BlueOccupied +1;
						}
						
					}
					if(r1.Upgrades == 4)
					{
						btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 3));
					}
					if(r1.Upgrades == 5)
					{
						btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 6));
					}
					
					if(r1.Upgrades == 6)
					{
						r1.Color = 3;
						btnRedpropone.setBackground(Color.GREEN);
						playerChars.get(playersTurn).BlueOwned = playerChars.get(playersTurn).BlueOwned -1;
						playerChars.get(playersTurn).GreenOwned = playerChars.get(playersTurn).GreenOwned +1;
					}
					if(r1.Occupied == true)
					{
						playerChars.get(playersTurn).BlueOccupied = playerChars.get(playersTurn).BlueOccupied -1;
						playerChars.get(playersTurn).GreenOccupied = playerChars.get(playersTurn).GreenOccupied +1;
					}
				}
				
				
			}
		});
		btnRedpropone.setVisible(false);
		btnRedpropone.setForeground(Color.BLACK);
		btnRedpropone.setBackground(Color.RED);
		btnRedpropone.setBounds(97, 336, 50, 50);
		frmSlumlord.getContentPane().add(btnRedpropone);
		
		JButton btnRedproptwo = new JButton("V");
		btnRedproptwo.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnRedproptwo.setPreferredSize(new Dimension(50, 50));
		btnRedproptwo.setBackground(Color.RED);
		btnRedproptwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).Color) && DamageVar == -1 && r2.Occupied == false && phase == 1)
				{
					//method for moving in green tenant
					if(currentTenant == 1)
					{
						System.out.println(numDice);
						currentRoll = gameDice.Roll(numDice);
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						if(currentRoll.Green >= playerChars.get(playersTurn).LeaseNumRed)
						{
							Turn.setText("<html>You Succsfully Moved the Tenant In!<br>You Rolled Some Red's<br> Click on the Draw Cards Button");
							playerChars.get(playersTurn).RedOccupied = playerChars.get(playersTurn).RedOccupied + 1;
							btnRedproptwo.setText("O");
							r2.Occupied = true;
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + playerChars.get(playersTurn).DepRed;
							numDice = 0;
							avaliable.RedTenant = avaliable.RedTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
							
						}
						else if(reroll < 1)
						{
							Turn.setText("<html>Sorry the Dice Hate You <br> You can Reroll your Red dice if you like");
							numDice = currentRoll.Red;
							reroll++;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
						else if(reroll >= 1)
						{
							Turn.setText("<html>Sorry the Dice Still Hate You<br> You Failed to Move the Tenant In<br> Click on the Draw Card Button</html>");
							numDice = 0;
							reroll = 2;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
					
					}
					//method for moving in blue tenant
					if(currentTenant == 2)
					{
						currentRoll = gameDice.Roll(numDice);
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						if(currentRoll.Green >= playerChars.get(playersTurn).LeaseNumBlue)
						{
							Turn.setText("<html>You Succsfully Moved the Tenant In!<br>You Rolled Some Red's<br> Click on the Draw Cards Button");
							playerChars.get(playersTurn).BlueOccupied = playerChars.get(playersTurn).BlueOccupied + 1;
							btnRedproptwo.setText("O");
							r2.Occupied = true;
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + playerChars.get(playersTurn).DepBlue;
							numDice = 0;
							avaliable.BlueTenant = avaliable.BlueTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
						else if(reroll < 1)
						{
							Turn.setText("<html>Sorry the Dice Hate You <br> You can Reroll your Red dice if you like");
							numDice = currentRoll.Red;
							reroll++;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
						else if(reroll >= 1)
						{
							Turn.setText("<html>Sorry the Dice Still Hate You<br> You Failed to Move the Tenant In<br> Click on the Draw Card Button</html>");
							numDice = 0;
							reroll = 2;
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
							}
						}
					
					}
				}
				//method for moving in green tenant
				if(currentTenant == 3)
				{
					currentRoll = gameDice.Roll(numDice);
					diceResults.setVisible(true);
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
					if(currentRoll.Green >= playerChars.get(playersTurn).LeaseNumGreen)
					{
						Turn.setText("<html>You Succsfully Moved the Tenant In!<br>You Rolled Some Red's<br> Click on the Draw Cards Button");
						playerChars.get(playersTurn).GreenOccupied = playerChars.get(playersTurn).GreenOccupied + 1;
						btnRedproptwo.setText("O");
						r2.Occupied = true;
						playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + playerChars.get(playersTurn).DepGreen;
						numDice = 0;
						avaliable.GreenTenant = avaliable.GreenTenant - 1;
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
						}
					}
					else if(reroll < 1)
					{
						Turn.setText("<html>Sorry the Dice Hate You <br> You can Reroll your Red dice if you like");
						numDice = currentRoll.Red;
						reroll++;
					}
					else if(reroll >= 1)
					{
						Turn.setText("<html>Sorry the Dice Still Hate You<br> You Failed to Move the Tenant In<br> Click on the Draw Card Button</html>");
						numDice = 0;
						reroll = 2;
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
						}
					}
				
				}
				//method for adding damage to property
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).Color) && DamageVar != -1 && phase == 1)
				{
					if(DamageVar == 0)
					{
						if(r2.Damage <= 3)
						{
							r2.Damage = r2.Damage +1;
							currentRoll.Red = currentRoll.Red - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						}
						else
						{
							Turn.setText("<html>Cannot take more Damage! <br> Apply to another Property<br> or you can click Take From Bank");
							btnTakeBank.setVisible(true);
							
						}
						if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
							}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Click on Next Phase to Tally<br>your Monthly Earnings and Move on <br>to the Buying and Repairing Phase</html>");
						}
						DamageVar = -1;
						if(r2.Occupied == true)
						{
							if(r2.Damage == 1)
							{
								btnRedproptwo.setText("<html> X <br> O <br> </html>");
							}
							if(r2.Damage == 2)
							{
								btnRedproptwo.setText("<html> XX <br> O <br> </html>");
							}
							if(r2.Damage == 3)
							{
								btnRedproptwo.setText("<html> XXX <br> O <br> </html>");
							}
						}
						if(r2.Occupied == false)
						{
							if(r2.Damage == 1)
							{
								btnRedproptwo.setText("<html> X <br> V <br> </html>");
							}
							if(r2.Damage == 2)
							{
								btnRedproptwo.setText("<html> XX <br> V <br> </html>");
							}
							if(r2.Damage == 3)
							{
								btnRedproptwo.setText("<html> XXX <br> V <br> </html>");
							}
						}
						
					}
					else
					{
						Turn.setText("Cannot Damage this Property");
					}
				}
				
				
				//method for buying
				if(r2.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).buy(r2);
					String Col = playerChars.get(playersTurn).Color;
					characterCard.setText(playerChars.get(playersTurn).charCard() );
					
					if(r2.OwnedBy.equals(playerChars.get(playersTurn).Color))
					{
						if(Col.equals("Purple"))
						{
							btnRedproptwo.setForeground(Color.MAGENTA);
						}
						if(Col.equals("Orange"))
						{
							btnRedproptwo.setForeground(Color.ORANGE);
						}
					}
				}
				
				//method for repairing
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).Color)&& phase == 4)
				{
					String resp = playerChars.get(playersTurn).repair(r2);
					Turn.setText(resp);
					if(r2.Damage == 0)
					{
						btnRedproptwo.setText("O");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
					if(r2.Damage == 1)
					{
						btnRedproptwo.setText("<html> X <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
					if(r2.Damage == 2)
					{
						btnRedproptwo.setText("<html> XX <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
					if(r2.Damage == 3)
					{
						btnRedproptwo.setText("<html> XXX <br> O <br> </html>");
						characterCard.setText(playerChars.get(playersTurn).charCard() );
					}
				}
				//Method for upgrading
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).Color) && phase == 5)
				{
					String Upresp = playerChars.get(playersTurn).upgrade(r2);
					Turn.setText(Upresp);
					characterCard.setText(playerChars.get(playersTurn).charCard() );
					if(r2.Upgrades == 1)
					{
						btnRedproptwo.setBorder(new LineBorder(Color.ORANGE, 3));
					}
					if(r2.Upgrades == 2)
					{
						btnRedproptwo.setBorder(new LineBorder(Color.ORANGE, 6));
					}
					
					if(r2.Upgrades == 3)
					{
						r2.Color = 2;
						btnRedproptwo.setBackground(Color.BLUE);
						Turn.setText(Upresp);
						playerChars.get(playersTurn).RedOwned = playerChars.get(playersTurn).RedOwned -1;
						playerChars.get(playersTurn).BlueOwned = playerChars.get(playersTurn).BlueOwned +1;
						if(r2.Occupied == true)
						{
							playerChars.get(playersTurn).RedOccupied = playerChars.get(playersTurn).RedOccupied -1;
							playerChars.get(playersTurn).BlueOccupied = playerChars.get(playersTurn).BlueOccupied +1;
						}
						
					}
					if(r2.Upgrades == 4)
					{
						btnRedproptwo.setBorder(new LineBorder(Color.ORANGE, 3));
					}
					if(r2.Upgrades == 5)
					{
						btnRedproptwo.setBorder(new LineBorder(Color.ORANGE, 6));
					}
					
					if(r2.Upgrades == 6)
					{
						r2.Color = 3;
						btnRedproptwo.setBackground(Color.GREEN);
						playerChars.get(playersTurn).BlueOwned = playerChars.get(playersTurn).BlueOwned -1;
						playerChars.get(playersTurn).GreenOwned = playerChars.get(playersTurn).GreenOwned +1;
					}
					if(r2.Occupied == true)
					{
						playerChars.get(playersTurn).BlueOccupied = playerChars.get(playersTurn).BlueOccupied -1;
						playerChars.get(playersTurn).GreenOccupied = playerChars.get(playersTurn).GreenOccupied +1;
					}
				}
				
				
			}
		});
		btnRedproptwo.setVisible(false);
		btnRedproptwo.setBounds(97, 390, 50, 50);
		frmSlumlord.getContentPane().add(btnRedproptwo);
		
		//Button for round 0 player switch
		JButton btnNextPlayer = new JButton("Next Player");
		btnNextPlayer.setContentAreaFilled(false);
		btnNextPlayer.setBorderPainted(false);
		btnNextPlayer.setBorder(null);
		btnNextPlayer.setForeground(SystemColor.inactiveCaption);
		btnNextPlayer.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnNextPlayer.setVisible(false);
		btnNextPlayer.setBounds(148, 274, 196, 23);
		frmSlumlord.getContentPane().add(btnNextPlayer);
		
		//button for start round one after first property selection
		JButton btnStartRoundOne = new JButton("Start Round One");
		btnStartRoundOne.setContentAreaFilled(false);
		btnStartRoundOne.setBorderPainted(false);
		btnStartRoundOne.setBorder(null);
		btnStartRoundOne.setForeground(SystemColor.inactiveCaption);
		btnStartRoundOne.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnStartRoundOne.setVisible(false);
		btnStartRoundOne.setBounds(138, 249, 249, 23);
		frmSlumlord.getContentPane().add(btnStartRoundOne);
		
		
		//Buttons for dice selection
		JButton btnDice = new JButton("3 Dice");
		btnDice.setContentAreaFilled(false);
		btnDice.setBorderPainted(false);
		btnDice.setBorder(null);
		btnDice.setForeground(SystemColor.inactiveCaption);
		btnDice.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numDice = 3;
				btnDice.setVisible(false);
				Turn.setText("<html> Click the Property You Want <br> to Move This Tenant Into</html>");
			}
		});
		btnDice.setVisible(false);
		btnDice.setBounds(1511, 74, 89, 23);
		frmSlumlord.getContentPane().add(btnDice);
		
		JButton btnDice_1 = new JButton("4 Dice");
		btnDice_1.setContentAreaFilled(false);
		btnDice_1.setBorderPainted(false);
		btnDice_1.setBorder(null);
		btnDice_1.setForeground(SystemColor.inactiveCaption);
		btnDice_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnDice_1.setVisible(false);
		btnDice_1.setBounds(1511, 108, 89, 23);
		frmSlumlord.getContentPane().add(btnDice_1);
		
		JButton btnDice_2 = new JButton("5 Dice");
		btnDice_2.setContentAreaFilled(false);
		btnDice_2.setBorderPainted(false);
		btnDice_2.setBorder(null);
		btnDice_2.setForeground(SystemColor.inactiveCaption);
		btnDice_2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnDice_2.setVisible(false);
		btnDice_2.setBounds(1511, 143, 89, 23);
		frmSlumlord.getContentPane().add(btnDice_2);
		
		//button for red tenant
		JButton btnRed = new JButton("Red");
		btnRed.setContentAreaFilled(false);
		btnRed.setBorderPainted(false);
		btnRed.setBorder(null);
		btnRed.setForeground(SystemColor.inactiveCaption);
		btnRed.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnRed.setVisible(false);
		btnRed.setBounds(1564, 32, 89, 23);
		frmSlumlord.getContentPane().add(btnRed);	
		
		//Button for selecting Blue tenant s
		JButton btnBlue = new JButton("Blue");
		btnBlue.setContentAreaFilled(false);
		btnBlue.setBorderPainted(false);
		btnBlue.setBorder(null);
		btnBlue.setForeground(SystemColor.inactiveCaption);
		btnBlue.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnBlue.setVisible(false);
		btnBlue.setBounds(1663, 32, 89, 23);
		frmSlumlord.getContentPane().add(btnBlue);
		
		//Button for selecting Green Tenants 
		JButton btnGreen = new JButton("Green");
		btnGreen.setContentAreaFilled(false);
		btnGreen.setBorderPainted(false);
		btnGreen.setBorder(null);
		btnGreen.setForeground(SystemColor.inactiveCaption);
		btnGreen.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnGreen.setVisible(false);
		btnGreen.setBounds(1762, 32, 89, 23);
		frmSlumlord.getContentPane().add(btnGreen);
		
		JButton btnBuyProperty = new JButton("Buy'");
		btnBuyProperty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnBuyProperty.setContentAreaFilled(false);
		btnBuyProperty.setBorderPainted(false);
		btnBuyProperty.setBorder(null);
		btnBuyProperty.setForeground(SystemColor.inactiveCaption);
		btnBuyProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phase = 3;
			}
		});
		btnBuyProperty.setVisible(false);
		btnBuyProperty.setFocusCycleRoot(true);
		btnBuyProperty.setBounds(31, 843, 110, 23);
		frmSlumlord.getContentPane().add(btnBuyProperty);
		
		JButton btnRepairProperty = new JButton("Repair");
		btnRepairProperty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnRepairProperty.setContentAreaFilled(false);
		btnRepairProperty.setBorderPainted(false);
		btnRepairProperty.setBorder(null);
		btnRepairProperty.setForeground(SystemColor.inactiveCaption);
		btnRepairProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phase = 4;
			}
		});
		btnRepairProperty.setVisible(false);
		btnRepairProperty.setBounds(178, 841, 110, 23);
		frmSlumlord.getContentPane().add(btnRepairProperty);
		
		JButton btnUpgradeProperty = new JButton("Upgrade");
		btnUpgradeProperty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnUpgradeProperty.setContentAreaFilled(false);
		btnUpgradeProperty.setBorderPainted(false);
		btnUpgradeProperty.setBorder(null);
		btnUpgradeProperty.setForeground(SystemColor.inactiveCaption);
		btnUpgradeProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phase = 5;
			}
		});
		btnUpgradeProperty.setVisible(false);
		btnUpgradeProperty.setBounds(316, 841, 120, 23);
		frmSlumlord.getContentPane().add(btnUpgradeProperty);
		
		
		//Button for ending player turn and cycling rounds
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.setForeground(SystemColor.inactiveCaption);
		btnEndTurn.setContentAreaFilled(false);
		btnEndTurn.setBorderPainted(false);
		btnEndTurn.setVisible(false);
		btnEndTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(playersTurn < players -1)
				{
					playersTurn++;
					btnEndTurn.setVisible(false);
					btnRed.setVisible(true);
					btnBlue.setVisible(true);
					btnGreen.setVisible(true);
					CardDisplay.setVisible(false);
					diceResults.setVisible(false);
					characterCard.setText(playerChars.get(playersTurn).charCard() );
					Turn.setText("<html> Player " + playersTurn + " " + playerChars.get(playersTurn).Name + "'s Turn <br> Pick a Tenant from the Avaliable Tenants </html>");
					currentRoll = new dice();
					//System.out.println(currentRoll);
					reroll = 0;
					phase = 1;
				}
				else 
				{
					if(round < 13)
					{
						btnRed.setVisible(true);
						btnBlue.setVisible(true);
						btnGreen.setVisible(true);
						btnEndTurn.setVisible(false);
						CardDisplay.setVisible(false);
						diceResults.setVisible(false);
						playersTurn = 0;
						round++;
						characterCard.setText(playerChars.get(playersTurn).charCard() );
						Turn.setText("<html> Player " + playersTurn + " " + playerChars.get(playersTurn).Name + "'s Turn <br> Pick a Tenant from the Avaliable Tenants </html>");
						phase = 1;
						currentRoll = new dice();
						reroll = 0;
						game = new tenantTokens();
						avaliable = game.RandomDraw(game, round);
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
					}
				}
			}
		});
		btnEndTurn.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		btnEndTurn.setBounds(461, 843, 129, 23);
		frmSlumlord.getContentPane().add(btnEndTurn);
		
		//actions for red tenant select
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(avaliable.RedTenant > 0)
				{
					currentTenant = 1;
					btnDice.setVisible(true);
					btnRed.setVisible(false);
					btnBlue.setVisible(false);
					btnGreen.setVisible(false);
					Turn.setText("<html>Select How Many Dice to Use</html>");
					if(playerChars.get(playersTurn).RedOwned + playerChars.get(playersTurn).BlueOwned + playerChars.get(playersTurn).GreenOwned > 3)
					{
						btnDice_1.setVisible(true);
					}
					if(playerChars.get(playersTurn).RedOwned + playerChars.get(playersTurn).BlueOwned + playerChars.get(playersTurn).GreenOwned > 4)
					{
						btnDice_2.setVisible(true);
					}
				}
				else
				{
					Turn.setText("Sorry No Red Tenants Avaliable");
				}
			}
		});
		//actions for blue tenant select
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(avaliable.BlueTenant > 0)
				{
					currentTenant = 2;
					btnDice.setVisible(true);
					btnRed.setVisible(false);
					btnBlue.setVisible(false);
					btnGreen.setVisible(false);
					if(playerChars.get(playersTurn).RedOwned + playerChars.get(playersTurn).BlueOwned + playerChars.get(playersTurn).GreenOwned > 3)
					{
						btnDice_1.setVisible(true);
					}
					if(playerChars.get(playersTurn).RedOwned + playerChars.get(playersTurn).BlueOwned + playerChars.get(playersTurn).GreenOwned > 4)
					{
						btnDice_2.setVisible(true);
					}
				}
				else
				{
					Turn.setText("Sorry No Blue Tenants Avaliable");
				}	
			}
		});
		
		//actions for green tenant button
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(avaliable.GreenTenant > 0)
				{
					currentTenant = 3;
					btnDice.setVisible(true);
					btnRed.setVisible(false);
					btnBlue.setVisible(false);
					btnGreen.setVisible(false);
					if(playerChars.get(playersTurn).RedOwned + playerChars.get(playersTurn).BlueOwned + playerChars.get(playersTurn).GreenOwned > 3)
					{
						btnDice_1.setVisible(true);
					}
					if(playerChars.get(playersTurn).RedOwned + playerChars.get(playersTurn).BlueOwned + playerChars.get(playersTurn).GreenOwned > 4)
					{
						btnDice_2.setVisible(true);
					}
				}
				else
				{
					Turn.setText("Sorry No Green Tenants Avaliable");
				}
			}
		});
		
		//actions for player selection button
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 2;
				list.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
				btnSelectCharacter.setVisible(true);
				Turn.setVisible(true);
				Turn.setText("<html>Click a Character in the List<br>then Click Select Character</html>");
			}
		});
		
		
		
		//actions for player selection button
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 3;
				list.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
				btnSelectCharacter.setVisible(true);
			}
		});
		//actions for player selection button
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players = 4;
				list.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
				btnSelectCharacter.setVisible(true);
			}
		});
		
		//actions for round 0 next player button
		btnNextPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playersTurn++;
				Turn.setText("<html>" + playerChars.get(playersTurn).Name + "'s Turn <br> Pick Your First Property <br> (Must be a Red Property)</html>");
				if(playersTurn == players -1)
				{
					btnNextPlayer.setVisible(false);
					btnStartRoundOne.setVisible(true);
				}
			}
		});
		
		//actions for select character button
		btnSelectCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					int j = playerChars.size();
					character p = list.getSelectedValue();
					playerChars.add(p);
					//System.out.println(playerChars.get(i));
					Turn.setText("<html>Next Player<br>Click a Character in the List<br>then Click Select Character</html>");
					if(j == players -1)
					{
						btnSelectCharacter.setVisible(false);
						lblWelcomeToSlumlord.setVisible(false);
						btnRules.setVisible(false);
						btnStartGame.setVisible(false);
						list.setVisible(false);
						Turn.setVisible(true);
						btnRedpropone.setVisible(true);
						btnRedproptwo.setVisible(true);
						btnNextPlayer.setVisible(true);
						//System.out.println(playerChars.get(0));
						Turn.setText("<html>" + playerChars.get(playersTurn).Name + "'s Turn <br> Pick Your First Property <br> (Must be a Red Property)</html>");
						phase = 3;
					}
					
			}
		});
		//initalizes board and starts round 1
		btnStartRoundOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avaliableTenants.setVisible(true);
				playersTurn = 0;
				btnRed.setVisible(true);
				btnBlue.setVisible(true);
				btnGreen.setVisible(true);
				CardDisplay.setVisible(true);
				game = new tenantTokens();
				avaliable = game.RandomDraw(game, round);
				avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
				characterCard.setText(playerChars.get(playersTurn).charCard() );
				Turn.setText("<html>"+ playerChars.get(playersTurn).Name + "'s Turn <br> Pick a Tenant from the Avaliable Tenants </html>");
				btnStartRoundOne.setVisible(false);
				phase = 1;
			}
		});
		btnNextPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNextPhase.setVisible(false);
				int money = (playerChars.get(playersTurn).RedOccupied * playerChars.get(playersTurn).RentRed) + (playerChars.get(playersTurn).BlueOccupied * playerChars.get(playersTurn).RentBlue)
						+ (playerChars.get(playersTurn).GreenOccupied * playerChars.get(playersTurn).RentGreen);
				playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + money;
				characterCard.setText(playerChars.get(playersTurn).charCard() );
				phase = 2;
				btnBuyProperty.setVisible(true);
				btnRepairProperty.setVisible(true);
				btnUpgradeProperty.setVisible(true);
				btnTakeBank.setVisible(false);
				btnEndTurn.setVisible(true);
				Turn.setText("<html>Click on the Buy, Repair, or Upgrade Button<br>Then Click on the Property");
				
			}
		});
		
		
		
		
	}
}











