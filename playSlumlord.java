package Slumlord;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.ImageIcon;

import javax.swing.DefaultListModel;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JComboBox;

public class playSlumlord {
	
	private JFrame frmSlumlord;
	//for character selection display
	private static DefaultListModel<character> listModel;
	private JList<character> list;
	//Arraylist of selected characters playing in game
	public ArrayList<character> playerChars = new ArrayList<character>();
		
	//variable to keep track of how many players
	private int players;
	
	//variable for which players turn it is
	int playersTurn = 0;
	
	//variable for what round it is
	int round = 1;
	
	//string for what round
	String roundS = "0";
	
	//variable for tenant selected to move in
	int currentTenant;
	
	//variable to hold available tenant tokens for each round
	tenantTokens avaliable = new tenantTokens();
	
	//variable for tenant tokens pool for the whole game
	tenantTokens game = new tenantTokens();
	
	// variable for current dice results
	dice currentRoll = new dice(0,0,0);
	
	//Arraylists to hold repair cards and event cards New and Used
	protected ArrayList<repairCard> all = new ArrayList<repairCard>();
	protected ArrayList<repairCard> used = new ArrayList<repairCard>();
	protected ArrayList<eCard> allE = new ArrayList<eCard>();
	protected ArrayList<eCard> usedE = new ArrayList<eCard>();
	
	//variable for phase control
	int phase = 1;
	
	//variable for controlling financial loss event cards
	int amountLost = 0;
	
	//Create Game Dice
	dice gameDice = new dice(0,0,0);
	
	int WinnerNumber = -1;
	
	String[] tagList = {"","","",""};
	
	int totalRounds = 0;
	
	static stats m = new stats("",0,0,0,0);
	static stats n = new stats("",0,0,0,0);
	static stats o = new stats("",0,0,0,0);
	static stats p = new stats("",0,0,0,0);
	static stats q = new stats("",0,0,0,0);
	static stats r = new stats("",0,0,0,0);
	static stats s = new stats("",0,0,0,0);
	static stats t = new stats("",0,0,0,0);
	static stats u = new stats("",0,0,0,0);
	static stats v = new stats("",0,0,0,0);
	
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

	// pull from database
	
	
	//create CSV method
	private static void generateCsvFile(String sFileName, String round, String player, String action, String outcome)
	   {

		boolean exists = true;
		try {
			FileWriter writer = new FileWriter(sFileName,exists);
			 writer.append(round);
			 writer.append(player);
			 writer.append(action);
			 writer.append(outcome);
			 writer.append('\n');
			 
			 writer.flush();
			 writer.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSlumlord = new JFrame();
		frmSlumlord.setIconImage(Toolkit.getDefaultToolkit().getImage(playSlumlord.class.getResource("/images/Background 1.jpg")));
		frmSlumlord.setBackground(Color.BLACK);
		frmSlumlord.setTitle("Galactic Slumlord");
		frmSlumlord.setBounds(100, 100, 1920, 1080);
		frmSlumlord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSlumlord.getContentPane().setLayout(null);
		frmSlumlord.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmSlumlord.setUndecorated(true);
		
		//Additional JLables to Display all Game Info
		//welcome label
		JLabel lblWelcomeToSlumlord = new JLabel("Galactic Slumlord");
		lblWelcomeToSlumlord.setForeground(Color.BLUE);
		lblWelcomeToSlumlord.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 85));
		lblWelcomeToSlumlord.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToSlumlord.setBounds(451, 82, 1100, 100);
		frmSlumlord.getContentPane().add(lblWelcomeToSlumlord);
		
		// Frame to hold Rules
		JFrame frame = new JFrame();
		
		//Label for Displaying Winner
		JLabel Winner = new JLabel("");
		Winner.setHorizontalAlignment(SwingConstants.CENTER);
		Winner.setForeground(Color.WHITE);
		Winner.setOpaque(true);
		Winner.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Winner.setBackground(Color.BLACK);
		Winner.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 46));
		Winner.setVisible(false);
		Winner.setBounds(589, 224, 752, 69);
		frmSlumlord.getContentPane().add(Winner);
		
		//Label to Display Card Drawn
		JLabel CardDisplay = new JLabel("");
		CardDisplay.setVerticalAlignment(SwingConstants.TOP);
		CardDisplay.setHorizontalTextPosition(SwingConstants.CENTER);
		CardDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		CardDisplay.setOpaque(true);
		CardDisplay.setBorder(new LineBorder(Color.WHITE, 2, true));
		CardDisplay.setBackground(Color.BLACK);
		CardDisplay.setForeground(Color.WHITE);
		CardDisplay.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 18));
		CardDisplay.setVisible(false);
		CardDisplay.setBounds(677, 859, 157, 199);
		frmSlumlord.getContentPane().add(CardDisplay);
		
		//Label for character card display
		JLabel characterCard = new JLabel("");
		characterCard.setBorder(new LineBorder(Color.WHITE, 1, true));
		characterCard.setVisible(false);
		characterCard.setOpaque(true);
		characterCard.setBackground(SystemColor.desktop);
		characterCard.setForeground(Color.WHITE);
		characterCard.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 14));
		characterCard.setBounds(16, 848, 548, 210);
		frmSlumlord.getContentPane().add(characterCard);
		
		//Label for dice results
		JLabel diceResults = new JLabel("");
		diceResults.setForeground(Color.WHITE);
		diceResults.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		diceResults.setVisible(false);
		diceResults.setFocusable(false);
		diceResults.setBounds(909, 413, 147, 201);
		frmSlumlord.getContentPane().add(diceResults);
		
		//Frame to display available tenants each round 
		JLabel avaliableTenants = new JLabel("<html>Tenants Avaliable<br>Red:<br>Blue:<br>Green:</html>");
		avaliableTenants.setToolTipText("<html>The tenants are randomly drawn each round from a set pool.  <br>If you see a lot of Green or Blue tenants early in the game, <br>expect a shortage in the later rounds.</html>");
		avaliableTenants.setForeground(Color.WHITE);
		avaliableTenants.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		avaliableTenants.setBounds(1611, 90, 165, 172);
		avaliableTenants.setVisible(false);
		frmSlumlord.getContentPane().add(avaliableTenants);
		
		//Frame for main text info box
		JLabel Turn = new JLabel("");
		Turn.setForeground(new Color(255, 255, 255));
		Turn.setVisible(false);
		Turn.setHorizontalAlignment(SwingConstants.CENTER);
		Turn.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 23));
		Turn.setBounds(34, 0, 402, 235);
		frmSlumlord.getContentPane().add(Turn);
		
		//List to display Characters for Selection
		listModel = new DefaultListModel<character>();
		list = new JList<character>(listModel);
		list.setBorder(null);
		list.setForeground(new Color(255, 255, 255));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 20));
		list.setVisible(false);
		list.setOpaque(false);
		list.setBackground(Color.BLACK);
		list.setBounds(549, 308, 883, 278);
		frmSlumlord.getContentPane().add(list);
		
		
		// Creation of all Game Assets (Cards, Characters, Properties)
		//create repair cards
		repairCard aa = new repairCard("Evaporator Broken", 1, 0, "Any Property");
		repairCard bb = new repairCard("Door Blown In", 1, 1,"Any Red Property");
		repairCard cc = new repairCard("Docking Bay Damaged", 1, 2,"Any Blue Property");
		repairCard dd = new repairCard("Plumbing Repair", 1, 0, "Any Property");
		repairCard ee = new repairCard("Unidentified Space Bugs", 1, 0, "Any Property");
		repairCard ff = new repairCard("Pressure Loss", 1, 0, "Any Property");
		repairCard gg = new repairCard("Airlock Stuck", 1, 0, "Any Property");
		repairCard hh = new repairCard("Zero-G Toilet Exploded", 1, 0, "Any Property");
		repairCard ii = new repairCard("Water Supply Vented Into Space", 1, 0, "Any Property");
		repairCard jj = new repairCard("Giant Ant Attack", 1, 0, "Any Property");
		repairCard kk = new repairCard("Airlock Won't Cycle", 1, 0, "Any Property");
		repairCard ll = new repairCard("Water Recycler Cracked", 1, 0, "Any Property");
		repairCard mm = new repairCard("Sand Storm", 1, 0, "Any Property");
		repairCard nn = new repairCard("Protective Shutters Wont Close", 1, 0, "Any Property");
		repairCard oo = new repairCard("Solar Flare Damaged Antenna", 1, 0, "Any Property");
		repairCard pp = new repairCard("Power Cell Empty", 1, 0, "Any Property");
		repairCard qq = new repairCard("Cryo Sleep Tube Cracked", 1, 0, "Any Property");
		repairCard rr = new repairCard("Stray Blaster Fire", 1, 1,"Any Red Property");
		repairCard ss = new repairCard("Drug Raid", 1, 1,"Any Red Property");
		repairCard tt = new repairCard("Nearby Rebel Attack", 1, 1,"Any Red Property");
		repairCard uu = new repairCard("Empire Sweeps Neighborhood", 1, 1,"Any Red Property");
		repairCard vv = new repairCard("Blood Stain Cleanup", 1, 1,"Any Red Property");
		repairCard ww = new repairCard("Solar Panels Cracked", 1, 2,"Any Blue Property");
		repairCard xx = new repairCard("Thermal Controls Fried", 1, 2,"Any Blue Property");
		repairCard yy = new repairCard("Central Power Matrix Damaged", 1, 2,"Any Blue Property");
		repairCard zz = new repairCard("Electric Field Generator Broken", 1, 2,"Any Blue Property");
		repairCard aaa = new repairCard("Plasma Relay Not Functioning", 1, 2,"Any Blue Property");
		repairCard bbb = new repairCard("Heat Exhaust Clogged", 1, 2,"Any Blue Property");
		repairCard ccc = new repairCard("Hydraulic System Malfunction", 1, 2,"Any Blue Property");
		repairCard ddd = new repairCard("Transporter Malfunctioning", 1, 3,"Any Green Property");
		repairCard fee = new repairCard("Food Replicator Damaged", 1, 3,"Any Green Property");
		repairCard fff = new repairCard("Farcaster Shut Down", 1, 3,"Any Green Property");
		repairCard ggg = new repairCard("Space Pirate Attack", 1, 3,"Any Green Property");
		repairCard hhh = new repairCard("Server Droid on Strike", 1, 3,"Any Green Property");
		
		
		// Add all repair cards to Array so they can be drawn at random
		all.add(aa);
		all.add(bb);
		all.add(cc);
		all.add(dd);
		all.add(ee);
		all.add(ff);
		all.add(gg);
		all.add(hh);
		all.add(ii);
		all.add(jj);
		all.add(kk);
		all.add(ll);
		all.add(mm);
		all.add(nn);
		all.add(oo);
		all.add(pp);
		all.add(qq);
		all.add(rr);
		all.add(ss);
		all.add(tt);
		all.add(uu);
		all.add(vv);
		all.add(ww);
		all.add(xx);
		all.add(yy);
		all.add(zz);
		all.add(aaa);
		all.add(bbb);
		all.add(ccc);
		all.add(ddd);
		all.add(fee);
		all.add(fff);
		all.add(ggg);
		all.add(hhh);
		
		//create all event Cards
		eCard eaa = new eCard("Sex Offender", 0, true, 0, 1, "Lose One Tenant From a Red Building");
		eCard ebb = new eCard("Lunar Lotto", 0, false, 500, 0, "You Won $500 Bucks!");
		eCard ecc = new eCard("Anonymous Tip", 0, false, -1000, 0, "Player of Your Choice is Being Audited by the Glactic IRS. They Lose $1000");
		eCard edd = new eCard("Organized Crime", 0, false, 501, 0, "Collect $500 from Each Player");
		eCard eee = new eCard("Galactic Lotto", 0, false, 1000, 0, "You Won $1000 Dollars!");
		eCard eff = new eCard("Reputation Boost", 0, false, 777, 0, "You are Loved by the Masses! 1 Less Green Die Needed for All Move Ins");
		eCard egg = new eCard("Galactic Robin Hood", 0, false, 7, 0, "The Dregs of the Universe Worship You! 1 Less Green Die Needed for Red Move Ins");
		eCard ehh = new eCard("Working Mans Hero", 0, false, 77, 0, "The Working Class Respect Your Moxy! 1 Less Green Die Needed for Blue Move Ins");
		eCard eii = new eCard("'Universal' Housing", 0, false, 1111, 0, "Fill All Unoccupied Red Buildings with Tenants (No Deposits)");
		eCard ejj = new eCard("Solar Storm", 0, true, 0, 1, "Lose One Tenant From a Red Building");
		eCard ekk = new eCard("Gamma Ray Burst", 0, true, 0, 2, "Lose One Tenant From a Blue Building");
		eCard ell = new eCard("Star Collapsed", 0, true, 0, 3, "Lose One Tenant From a Green Building");
		eCard emm = new eCard("Stray Asteroid", 0, true, 0, 2, "Lose One Tenant From a Blue Building");
		eCard enn = new eCard("Alien Attack", 0, true, 0, 3, "Lose One Tenant From a Green Building");
		eCard eoo = new eCard("Radiation Leak", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard epp = new eCard("Hired Space Pirates", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard eqq = new eCard("Lucky Asteroid Strikes Again", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard err = new eCard("You 'Found' a Shippment of Building Material", 0, false, 1000, 0, " $1000 for Upgrades");
		eCard ess = new eCard("You Know a Repair Guy", 0, false, 400, 0, " $400 for Repairs");
		eCard ett = new eCard("Won a Bet!", 0, false, 200, 0, " $200 for Free!");
		eCard euu = new eCard("Alien Popped Out of Tenants Chest", 0, true, 0, 2, "Lose One Tenant From a Blue Building");
		eCard evv = new eCard("Pick a Fight", 0, false, -500, 0, "He Dosent Like You, I Dont Like You Either. Pick Who Loses $500");
		eCard eww = new eCard("Small Loss", 0, false, -200, 0, "Pick Who Loses $200");
		eCard exx = new eCard("Insider Trading", 0, true, 0, 3, "Lose One Tenant From a Green Building");
		eCard eyy = new eCard("Drug Deal Gone Bad", 0, true, 0, 1, "Lose One Tenant From a Red Building");
		eCard eaaa = new eCard("Sabotage", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard ebbb = new eCard("Rogue Spaceship Crashes", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard eccc= new eCard("Comet Tail Debris", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard eeee = new eCard("Space Junk Collision", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard efff = new eCard("Hired Space Ninjas", 0, false, 2001, 0, " One Damage to ANY Property");
		eCard eggg = new eCard("Satelite Crash", 0, false, 2001, 0, " One Damage to ANY Property");
		
		
		// Add all Event cards to Array so they can be drawn at random
		allE.add(eaa);
		allE.add(ebb);
		allE.add(ecc);
		allE.add(edd);
		allE.add(eee);
		allE.add(eff);
		allE.add(egg);
		allE.add(ehh);
		allE.add(eii);
		allE.add(ejj);
		allE.add(ekk);
		allE.add(ell);
		allE.add(emm);
		allE.add(enn);
		allE.add(eoo);
		allE.add(epp);
		allE.add(eqq);
		allE.add(err);
		allE.add(ess);
		allE.add(ett);
		allE.add(euu);
		allE.add(evv);
		allE.add(eww);
		allE.add(exx);
		allE.add(eyy);
		allE.add(eaaa);
		allE.add(ebbb);
		allE.add(eccc);
		allE.add(eeee);
		allE.add(efff);
		allE.add(eggg);
		
		
		//create all characters
		character Scotty = new character("Scotty", "desc", "Skill: Preventative Maintaince. Discard One Repair Card Each Round",2,4,6,"Purple", 1);
		character Burke = new character("Burke", "Descript", "Skill: Company Connections. $200 Off the Purchase of New Buildings",2,4,6,"Orange", 0);
		character Han = new character("Han", "", "Skill: Smuggler, Has Connections. Half Price Repairs",2,4,6, "Yellow",2);
		character Malcolm = new character("Malcolm", "", "Skill: Galactic Robin Hood. Poor Love Him The Rich Hate Him +1R-1G", 1,4,7, "DarkGreen",0);
		character Siri = new character("Siri", "", "Skill: Royalty. The Rich Love Her The Poor Hate Her -2R+2G", 4,4,4, "Pink",0);
		character Leia = new character("Leia", "", "Skill: Hard Working and Respected. Middle Class Admire Her -1R+2B-1G", 3,2,7, "Peach",0);
		character Hoyt = new character("Hoyt", "", "Skill: Ruthless. Willing to Lie cheat and Steal. -2R+1B+1G", 4,3,5, "DarkBlue",0);
		character Martin = new character("Martin", "", "Skill: Rich Parents. A Grown Man, Still Gets An Allowance +$200p/m", 2,4,6, "Brown",0);
		character Ripley = new character("Ripley", "", "Skill: Resourceful Survivor. Can Always Find the Tenant She Wants", 2,4,6, "LimeGreen",3);
		character Jordi = new character("Jordi", "", "Skill: Engeneer. Can Do Upgrades Himself at Half the Cost", 2,4,6, "Ivory",4);
		Burke.BuyRed = 600;
		Burke.BuyBlue = 1000;
		Burke.BuyGreen = 1800;
		Martin.Bank = Martin.Bank + 200;
		
		
		//populating character list
		listModel.add(0,Scotty);
		listModel.add(1,Burke);
		listModel.add(2,Han);
		listModel.add(3,Malcolm);
		listModel.add(4,Siri);
		listModel.add(5,Leia);
		listModel.add(6,Hoyt);
		listModel.add(7,Martin);
		listModel.add(8,Ripley);
		listModel.add(9,Jordi);
		
		//Make properties
		property r1 = new property(1,1,0);
		property r2 = new property(1,1,0);
		property r3 = new property(1,1,0);
		property r4 = new property(1,1,0);
		property r5 = new property(1,1,0);
		property r6 = new property(2,1,0);
		property r7 = new property(2,1,0);
		property r8 = new property(2,1,0);
		property r9 = new property(2,1,0);
		property r10 = new property(2,1,0);
		property r11 = new property(3,1,0);
		property r12 = new property(3,1,0);
		property r13 = new property(3,1,0);
		property r14 = new property(3,1,0);
		property r15 = new property(3,1,0);
		property r16 = new property(4,1,0);
		property r17 = new property(5,1,0);
		property r18 = new property(6,1,0);
		property b1 = new property(7,2,3);
		property b2 = new property(7,2,3);
		property b3 = new property(7,2,3);
		property b4 = new property(7,2,3);
		property b5 = new property(8,2,3);
		property b6 = new property(8,2,3);
		property b7 = new property(8,2,3);
		property b8 = new property(8,2,3);
		property b9 = new property(9,2,3);
		property b10 = new property(9,2,3);
		property b11 = new property(9,2,3);
		property b12 = new property(9,2,3);
		property b13 = new property(10,2,3);
		property g1 = new property(11, 3,6);
		property g2 = new property(11, 3,6);
		property g3 = new property(11, 3,6);
		property g4 = new property(12, 3,6);
		property g5 = new property(12, 3,6);
		property g6 = new property(12, 3,6);
		property g7 = new property(13, 3,6);
		property g8 = new property(13, 3,6);
		property g9 = new property(13, 3,6);
		
		
		//Create All Buttons
		//Button to display basic rules
		JButton btnRules = new JButton("Rules");
		btnRules.setDefaultCapable(false);
		btnRules.setContentAreaFilled(false);
		btnRules.setBorderPainted(false);
		btnRules.setBackground(new Color(0, 0, 0));
		btnRules.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		btnRules.setForeground(Color.WHITE);
		btnRules.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.setTitle("Rules of Play");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame.getComponent(0), "<html><p>First Click 'Start Game' select the number of players and the number of rounds to be played.<br></p> "
						+ "<p>Round 0:<br> Each player selects a character from the character list and clicks the 'Select Character' button. When all the players have selected a character<br>"
						+ " the game board will appear and prompt the players to select their first property. Do so by clicking on the square tiles marked with '*'. Once a player selects a <br>property "
						+ "the marker (*) will change to that players color. Click on 'Next Player'.  When all the players have chosen their first property click 'Start Round One' <br>"
						+ "to begin the game. </p>"
						+ "<p>Gameplay: <br>Each round consists of 2 phases. The Leasing Phase and the Management Phase. The goal of the game is to have the most money after the final round.<br>"
						+ "This final score is the combination of the money in your bank and value of the properties you own minus any repairs needed.</p>"
						+ "<p>Leasing Phase:<br> The player chooses a tenant(Red, Blue or Green) to try and 'move in' then 'Rolls' the virtual dice to attempt the move in. The tenants are drawn <br>"
						+ "randomly from a set pool of tenants determined by the number of players playing. The tenants not used each round are discarded and new tenants are drawn at the start <br>"
						+ "of each round. <br>"
						+ "Each character has different stats that determine the roll needed to succeed. This is shown on the 'Character Card' in the bottom left corner of the screen.  It is easiest to<br>"
						+ " move a tenant into the same color building (ie. Red Tenant into Red Building). The number in the corresponding place on the Character Card is the number of 'Green' rolls <br>"
						+ "the player needs to succeed.  Each virtual die has 6 sides. 2 Red Sides, 1 Blue Side, 2 Green Side and 1 Double Green Side.  Giving the player a 50% chance of rolling <br>"
						+ "at least one Green with each die and a 16.6% chance of rolling 2 Green.  Players must roll at least 3 dice and gain the option to roll up to 8 dice with each building <br>"
						+ "owned over 3.</p>"
						+ "Selecting Tenant and Rolling:<br>"
						+ "To select a tenant and roll to attempt to move them in, simply click on the color in the upper right corner then select the number of dice you would like to roll and <br>"
						+ "click on the property you want to move them into. The results of your roll will be displayed in the center of the screen.  If you rolled enough greens you succeeded and <br>"
						+ "will automatically collect a deposit.  If you fail, you can choose to reroll any dice that came up red by clicking on the property again.  But beware, for every Red and <br>"
						+ "Blue that is rolled you will have to draw a card. Red cards are Damage Cards and Blue are Event Cards.  After you have rolled (successfully or not) you will have to draw <br>"
						+ "cards before you can move to the next phase.  To draw cards click 'Draw Card' and follow the instructions on the card.  When you have finished Drawing cards (or if there <br>"
						+ "none to draw click 'Next Phase'  You will then receive your rent from all occupied building and move to the next phase"
						+ ".</p>"
						+ "<p>Management Phase:<br>"
						+ "In this phase you can Buy, Repair, or Upgrade your properties.  Simply click on the option you want then click on the property. You can buy properties for $800, $1200, and <br>"
						+ "$2000.  You can also hover the mouse over the property for basic information.  Properties can only have 3 damage at any time. It costs $200 to repair each damage marker. <br>"
						+ "If a property is occupied and gets 3 damage marks the tenant will move out. You can only upgrade Red and Blue properties. If you upgrade a property 3 times it will become <br>"
						+ "the next level. Red becomes Blue and Blue becomes Green. If a property is occupied when it is upgraded, the tenant will pay the upgraded rent amount.</html>");
			}
		});
		btnRules.setBounds(1645, 284, 185, 43);
		frmSlumlord.getContentPane().add(btnRules);
		
		//Buttons for player number selection
		JButton two = new JButton("2 Players");
		two.setContentAreaFilled(false);
		two.setBorderPainted(false);
		two.setBorder(null);
		two.setForeground(Color.WHITE);
		two.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		two.setOpaque(false);
		two.setVisible(false);
		two.setBounds(10, 304, 295, 23);
		frmSlumlord.getContentPane().add(two);
		
		//Buttons for player number selection
		JButton three = new JButton("3 Players");
		three.setContentAreaFilled(false);
		three.setBorderPainted(false);
		three.setBorder(null);
		three.setForeground(Color.WHITE);
		three.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		three.setOpaque(false);
		three.setVisible(false);
		three.setBounds(10, 352, 295, 23);
		frmSlumlord.getContentPane().add(three);
		
		//Buttons for player number selection
		JButton four = new JButton("4 Players");
		four.setContentAreaFilled(false);
		four.setBorderPainted(false);
		four.setBorder(null);
		four.setForeground(Color.WHITE);
		four.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		four.setVisible(false);
		four.setOpaque(false);
		four.setBounds(10, 397, 295, 23);
		frmSlumlord.getContentPane().add(four);
		
		JButton btnTotalRounds6 = new JButton("6 Rounds");
		btnTotalRounds6.setVisible(false);
		btnTotalRounds6.setForeground(Color.WHITE);
		btnTotalRounds6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		btnTotalRounds6.setContentAreaFilled(false);
		btnTotalRounds6.setBorderPainted(false);
		btnTotalRounds6.setBounds(10, 304, 277, 23);
		frmSlumlord.getContentPane().add(btnTotalRounds6);
	
		JButton btnTotalRounds12 = new JButton("12 Rounds");
		btnTotalRounds12.setVisible(false);
		btnTotalRounds12.setForeground(Color.WHITE);
		btnTotalRounds12.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		btnTotalRounds12.setContentAreaFilled(false);
		btnTotalRounds12.setBorderPainted(false);
		btnTotalRounds12.setBounds(16, 352, 277, 23);
		frmSlumlord.getContentPane().add(btnTotalRounds12);
		
		JButton btnTotalRounds24 = new JButton("24 Rounds");
		btnTotalRounds24.setVisible(false);
		btnTotalRounds24.setForeground(Color.WHITE);
		btnTotalRounds24.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		btnTotalRounds24.setContentAreaFilled(false);
		btnTotalRounds24.setBorderPainted(false);
		btnTotalRounds24.setBounds(28, 397, 277, 23);
		frmSlumlord.getContentPane().add(btnTotalRounds24);
		
		JLabel statsDisp = new JLabel("");
		statsDisp.setOpaque(true);
		statsDisp.setForeground(Color.WHITE);
		statsDisp.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 18));
		statsDisp.setBackground(Color.BLACK);
		statsDisp.setVisible(false);
		
		//start game button
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setBorderPainted(false);
		btnStartGame.setContentAreaFilled(false);
		btnStartGame.setDefaultCapable(false);
		btnStartGame.setBackground(new Color(0, 0, 0));
		btnStartGame.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		btnStartGame.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnStartGame.setForeground(Color.WHITE);
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Make Player selection visible
				try{
					//System.out.println("Got this far");
					//Get connection to DB
					Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/slumlord?&useSSL=false", "Mike" , "******");
					
					//2. create statement
					Statement myStmt = myConn.createStatement();
					
					//3. Execute SQL query
					ResultSet myRs = myStmt.executeQuery("select * from stats");
					
					
					int place = 0;
					//4. Process the result set
					while (myRs.next()) {
						stats x = new stats(myRs.getString("name"),myRs.getInt("TotalGames"),myRs.getInt("Wins"),myRs.getInt("HighestScore"),myRs.getInt("TotalScore"));
						if(place == 0)
							m = x;
						if(place == 1)
							n = x;
						if(place == 2)
							o = x;
						if(place == 3)
							p = x;
						if(place == 4)
							q = x;
						if(place == 5)
							r = x;
						if(place == 6)
							s = x;
						if(place == 7)
							t = x;
						if(place == 8)
							u = x;
						if(place == 9)
							v = x;
						place ++;
					}
					
					statsDisp.setText("<html><table border=1>"
							+"<tr>"
							+"<th>Name</th>"
							+"<th>Games Played</th>"
							+"<th>Wins</th>"
							+"<th>High Score</th>"
							+"<th>Total Score</th>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ m.name +"</td>"
							+"<td>" + m.totG + "</td>"
							+"<td>" + m.wins + "</td>"
							+"<td>" + m.Hscore + "</td>"
							+"<td>" + m.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ n.name +"</td>"
							+"<td>" + n.totG + "</td>"
							+"<td>" + n.wins + "</td>"
							+"<td>" + n.Hscore + "</td>"
							+"<td>" + n.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ o.name +"</td>"
							+"<td>" + o.totG + "</td>"
							+"<td>" + o.wins + "</td>"
							+"<td>" + o.Hscore + "</td>"
							+"<td>" + o.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ p.name +"</td>"
							+"<td>" + p.totG + "</td>"
							+"<td>" + p.wins + "</td>"
							+"<td>" + p.Hscore + "</td>"
							+"<td>" + p.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ q.name +"</td>"
							+"<td>" + q.totG + "</td>"
							+"<td>" + q.wins + "</td>"
							+"<td>" + q.Hscore + "</td>"
							+"<td>" + q.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ r.name +"</td>"
							+"<td>" + r.totG + "</td>"
							+"<td>" + r.wins + "</td>"
							+"<td>" + r.Hscore + "</td>"
							+"<td>" + r.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ s.name +"</td>"
							+"<td>" + s.totG + "</td>"
							+"<td>" + s.wins + "</td>"
							+"<td>" + s.Hscore + "</td>"
							+"<td>" + s.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ t.name +"</td>"
							+"<td>" + t.totG + "</td>"
							+"<td>" + t.wins + "</td>"
							+"<td>" + t.Hscore + "</td>"
							+"<td>" + t.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ u.name +"</td>"
							+"<td>" + u.totG + "</td>"
							+"<td>" + u.wins + "</td>"
							+"<td>" + u.Hscore + "</td>"
							+"<td>" + u.Tscore + "</td>"
							+"</tr>"
							+"<tr>"
							+"<td>"+ v.name +"</td>"
							+"<td>" + v.totG + "</td>"
							+"<td>" + v.wins + "</td>"
							+"<td>" + v.Hscore + "</td>"
							+"<td>" + v.Tscore + "</td>"
							+"</tr> </html");
					
				}
				catch (Exception exc){
					exc.printStackTrace();
					
				}
				btnStartGame.setVisible(false);
				two.setVisible(true);
				three.setVisible(true);
				four.setVisible(true);
			}
		});
		btnStartGame.setBounds(16, 294, 295, 43);
		frmSlumlord.getContentPane().add(btnStartGame);
		
		//select character button
		JButton btnSelectCharacter = new JButton("Select Character");
		btnSelectCharacter.setContentAreaFilled(false);
		btnSelectCharacter.setBorderPainted(false);
		btnSelectCharacter.setBorder(null);
		btnSelectCharacter.setForeground(Color.WHITE);
		btnSelectCharacter.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
		btnSelectCharacter.setVisible(false);
		btnSelectCharacter.setBounds(537, 591, 336, 23);
		frmSlumlord.getContentPane().add(btnSelectCharacter);
		
		//make repair from bank button
		JButton btnTakeBank = new JButton("Take Repairs From Bank");
		btnTakeBank.setForeground(Color.WHITE);
		btnTakeBank.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
		btnTakeBank.setContentAreaFilled(false);
		btnTakeBank.setBorderPainted(false);
		btnTakeBank.setVisible(false);
		btnTakeBank.setBounds(26, 773, 328, 23);
		frmSlumlord.getContentPane().add(btnTakeBank);
		
		// Draw Card Button
		JButton btnDrawCard = new JButton("Draw Card");
		btnDrawCard.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDrawCard.setContentAreaFilled(false);
		btnDrawCard.setBorderPainted(false);
		btnDrawCard.setBorder(null);
		btnDrawCard.setForeground(Color.WHITE);
		
		//Create next phase button
		JButton btnNextPhase = new JButton("Next Phase");
		btnNextPhase.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnNextPhase.setContentAreaFilled(false);
		btnNextPhase.setBorderPainted(false);
		btnNextPhase.setBorder(null);
		btnNextPhase.setForeground(Color.WHITE);
		btnNextPhase.setVisible(false);
		btnNextPhase.setBounds(385, 814, 190, 23);
		frmSlumlord.getContentPane().add(btnNextPhase);
		
		//Fill all occupied reds
		JButton btnFillReds = new JButton("Fill All Reds");
		btnFillReds.setVisible(false);
		btnFillReds.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnFillReds.setForeground(Color.WHITE);
		btnFillReds.setContentAreaFilled(false);
		btnFillReds.setBorderPainted(false);
		btnFillReds.setBounds(644, 825, 225, 23);
		frmSlumlord.getContentPane().add(btnFillReds);
		
		JButton btnChooseP1 = new JButton("Player 1");
		btnChooseP1.setVisible(false);
		btnChooseP1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnChooseP1.setForeground(Color.WHITE);
		btnChooseP1.setContentAreaFilled(false);
		btnChooseP1.setBorderPainted(false);
		btnChooseP1.setBounds(824, 867, 165, 23);
		frmSlumlord.getContentPane().add(btnChooseP1);
		
		JButton btnChooseP2 = new JButton("Player 2");
		btnChooseP2.setVisible(false);
		btnChooseP2.setForeground(Color.WHITE);
		btnChooseP2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnChooseP2.setContentAreaFilled(false);
		btnChooseP2.setBorderPainted(false);
		btnChooseP2.setBounds(824, 892, 165, 23);
		frmSlumlord.getContentPane().add(btnChooseP2);
		
		JButton btnChooseP3 = new JButton("Player 3");
		btnChooseP3.setVisible(false);
		btnChooseP3.setForeground(Color.WHITE);
		btnChooseP3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnChooseP3.setContentAreaFilled(false);
		btnChooseP3.setBorderPainted(false);
		btnChooseP3.setBounds(824, 922, 165, 23);
		frmSlumlord.getContentPane().add(btnChooseP3);
		
		JButton btnChooseP4 = new JButton("Player 4");
		btnChooseP4.setVisible(false);
		btnChooseP4.setForeground(Color.WHITE);
		btnChooseP4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnChooseP4.setContentAreaFilled(false);
		btnChooseP4.setBorderPainted(false);
		btnChooseP4.setBounds(824, 956, 165, 23);
		frmSlumlord.getContentPane().add(btnChooseP4);
		
		//Event Card Specific Action Responses
		btnFillReds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFillReds.setVisible(false);
				//iterate over current players array of owned properties
				for(int i = 0; i < playerChars.get(playersTurn).AllOwned.size(); i++)
				{
					//if property is a Red property 
					if(playerChars.get(playersTurn).AllOwned.get(i).Color == 1)
					{
						//and red property is Unoccupied, and Does not have 3 damage points
						if(playerChars.get(playersTurn).AllOwned.get(i).Occupied == false  && playerChars.get(playersTurn).AllOwned.get(i).Damage != 3)
						{
							//Add one to players variable for Red occupied properties
							playerChars.get(playersTurn).RedOccupied = playerChars.get(playersTurn).RedOccupied + 1;
							//Change Occupied to True
							playerChars.get(playersTurn).AllOwned.get(i).Occupied = true;
							JButton G = playerChars.get(playersTurn).AllOwned.get(i).ButtonName;
							//Set Button Text to Display proper Damage and Occupied Status
							if(playerChars.get(playersTurn).AllOwned.get(i).Damage == 0)
							{
								G.setHorizontalTextPosition(SwingConstants.CENTER);
								G.setVerticalAlignment(SwingConstants.CENTER);
								G.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
								G.setText("O");
							}
							if(playerChars.get(playersTurn).AllOwned.get(i).Damage == 1)
							{
								G.setHorizontalTextPosition(SwingConstants.CENTER);
								G.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
								G.setText("<html>O<br>X</html>");
							}
							if(playerChars.get(playersTurn).AllOwned.get(i).Damage == 2)
							{
								G.setHorizontalTextPosition(SwingConstants.CENTER);
								G.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
								G.setText("<html>&nbsp;O<br>XX</html>");
							}
						}
					}
				}
				if(currentRoll.Blue > 0)
				{
					btnDrawCard.setVisible(true);
				}
				if(currentRoll.Blue == 0)
				{
					btnNextPhase.setVisible(true);
				}
				
			}
		});
		
		//Buttons for player negative financial events that require a player selection
		btnChooseP1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChooseP1.setVisible(false);
				btnChooseP2.setVisible(false);
				btnChooseP3.setVisible(false);
				btnChooseP4.setVisible(false);
				eCard Holder = new eCard("", 0, false, 0, 0, "");
				Holder.TakeMoney(amountLost, 0, players, playerChars, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
			}});
				
		//Buttons for player negative financial events that require a player selection		
		btnChooseP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChooseP1.setVisible(false);
				btnChooseP2.setVisible(false);
				btnChooseP3.setVisible(false);
				btnChooseP4.setVisible(false);
				eCard Holder = new eCard("", 0, false, 0, 0, "");
				Holder.TakeMoney(amountLost, 1, players, playerChars, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
			}});
				
		//Buttons for player negative financial events that require a player selection		
		btnChooseP3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChooseP1.setVisible(false);
				btnChooseP2.setVisible(false);
				btnChooseP3.setVisible(false);
				btnChooseP4.setVisible(false);
				eCard Holder = new eCard("", 0, false, 0, 0, "");
				Holder.TakeMoney(amountLost, 2, players, playerChars, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
			}});
				
		//Buttons for player negative financial events that require a player selection		
		btnChooseP4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChooseP1.setVisible(false);
				btnChooseP2.setVisible(false);
				btnChooseP3.setVisible(false);
				btnChooseP4.setVisible(false);
				eCard Holder = new eCard("", 0, false, 0, 0, "");
				Holder.TakeMoney(amountLost, 3, players, playerChars, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
			}});
				
		//Buttons for player positive financial events
		JButton btnCollect = new JButton("Collect");
		btnCollect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCollect.setVisible(false);
				//iterate over all players
				for(int i = 0; i< players; i++)
				{
					//Subtract 500 from each player and add to current player(turn)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 500;
					playerChars.get(i).Bank = playerChars.get(i).Bank - 500;
				}
				//Update Character Card
				characterCard.setText(playerChars.get(playersTurn).charCard());
				//remove 1 blue die/card and redisplay roll
				currentRoll.Blue = currentRoll.Blue - 1;
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				//if no more blue die/card move to next phase
				if(currentRoll.Blue > 0)
				{
					btnDrawCard.setVisible(true);
				}
				else
				{
					btnNextPhase.setVisible(true);
					Turn.setText("<html>Click on Next Phase to Continue.<html>");
				}
			}
		});
		btnCollect.setVisible(false);
		btnCollect.setForeground(Color.WHITE);
		btnCollect.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnCollect.setContentAreaFilled(false);
		btnCollect.setBorderPainted(false);
		btnCollect.setBounds(824, 867, 183, 23);
		frmSlumlord.getContentPane().add(btnCollect);
		
		
		//Button for event cards damaging other properties
		JButton btnDamageOthers = new JButton("Deal Damage");
		btnDamageOthers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDamageOthers.setVisible(false);
				//update text
				Turn.setText("<html>Click on Any Property to Add Damage</html>");
				//phase 11 is damage others
				phase = 11;
				//remove one blue die/card and redisplay
				currentRoll.Blue = currentRoll.Blue - 1;
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				//if no more blue die/card move to next phase
				if(currentRoll.Blue > 0)
				{
					btnDrawCard.setVisible(true);
				}
				else
				{
					btnNextPhase.setVisible(true);
					Turn.setText("<html>Click on Next Phase to Continue.<html>");
				}
			}
		});
		btnDamageOthers.setVisible(false);
		btnDamageOthers.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDamageOthers.setForeground(Color.WHITE);
		btnDamageOthers.setContentAreaFilled(false);
		btnDamageOthers.setBorderPainted(false);
		btnDamageOthers.setBounds(625, 825, 263, 23);
		frmSlumlord.getContentPane().add(btnDamageOthers);
		
		
		//Create discard button for when card cant be used by player
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Discarded","");
				//remove card text
				CardDisplay.setText("");
				btnDiscard.setVisible(false);
				
				//Remove one red or blue die/card if they any remain
				if(currentRoll.Red > 0)
				{
					currentRoll.Red = currentRoll.Red - 1;
				}
				else if(currentRoll.Blue > 0)
				{
					currentRoll.Blue= currentRoll.Blue - 1;
				}
				//redisplay new die result 
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				Turn.setText("Discarded");
				//if more draws remain, make button active again
				if(currentRoll.Red > 0)
				{					
					btnDrawCard.setVisible(true);
					playerChars.get(playersTurn).DamageVar = -1;
				}
				if(currentRoll.Blue > 0)
				{
					btnDrawCard.setVisible(true);
					playerChars.get(playersTurn).DamageVar = -1;
				}
				//When all draws are exhausted, make next phase active
				if(currentRoll.Red == 0 && currentRoll.Blue == 0)
				{
					btnNextPhase.setVisible(true);
					playerChars.get(playersTurn).DamageVar = -1;
				}
					
			}});
			
		btnDiscard.setVisible(false);
		btnDiscard.setForeground(Color.WHITE);
		btnDiscard.setContentAreaFilled(false);
		btnDiscard.setBorderPainted(false);
		btnDiscard.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDiscard.setBounds(661, 801, 190, 23);
		frmSlumlord.getContentPane().add(btnDiscard);
		
		//Draw Card Button
		btnDrawCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Always Deals with Red Cards/ Dice first
				//if red dice are present, change display color to red and phase to 2 (damage from repair cards) 
				if(currentRoll.Red > 0)
				{		
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Draw Card",",Red");
					CardDisplay.setForeground(Color.RED);
					phase = 2;
					//if the deck of repair cards have all been used, take all cards from used pile and move back to all pile
					if(all.size() == 0)
					{
						all = used;
					}
					//make discard visible for character (scotty) with corresponding skill 
					if(playerChars.get(playersTurn).SkillNum == 1)
					{
						btnDiscard.setVisible(true);
						playerChars.get(playersTurn).SkillNum = -1;
						
					}
					btnDrawCard.setVisible(false);
					CardDisplay.setVisible(true);
					//create random generator and make variable to store random number no bigger than the size of the remaining deck to be drawn from
					Random generator = new Random();
					repairCard Drawn = new repairCard("",0,0,"");
					int j = generator.nextInt(all.size());
					//pull card from indexed array with random index
					Drawn = all.get(j);
					//remove index from all
					all.remove(j);
					//add to used 
					used.add(Drawn);
					//update card display
					CardDisplay.setText("<html><center><h2>Damage</h2><h3>" + Drawn.Description + "<br>" + Drawn.Damage + "Damage" + "<br>" + Drawn.Which + "</h3></center></html>");
					//Set damageVar. Red = 1, blue = 2 green = 3
					playerChars.get(playersTurn).DamageVar = Drawn.WhichProperty;
					//check to see if player has properties the damageVar can be used on
					boolean canUse = false;
					//damageVar 0 is universal
					if(playerChars.get(playersTurn).DamageVar == 0)
					{
						canUse = true;
						Turn.setText("<html> Click on the Property that you want to apply the Damage to.</html>");
					}
					else
					{
						//iterate over all properties the player owns looking to a property that matches the damageVar
						for(int p = 0; p < playerChars.get(playersTurn).AllOwned.size(); p++)
						{
							int q = playerChars.get(playersTurn).AllOwned.get(p).Color;
							if(q == playerChars.get(playersTurn).DamageVar)
								//if a match is found set canUse to true
							{
								canUse = true;
							}
						}
						//if no match was wound update text and make discard button visible
						if(canUse == false)
						{
							Turn.setText("<html>No Properties of that type Click Discard</html>");
							btnDiscard.setVisible(true);
						}
						//if match was found prompt player to click on property to apply damage
						if(canUse == true)
						{
							Turn.setText("<html> Click on the Property that you want to apply the Damage to.</html>");
						}
					}
				}
				//once all red cards/dice are dealt with move on to blue cards/dice
				else if(currentRoll.Blue > 0)
				{
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Draw Card",",Blue");
					//if all cards have been used, copy usedE into allE
					if(allE.size() == 0)
					{
						allE = usedE;
					}
					//Change display color blue
					CardDisplay.setForeground(Color.BLUE);
					//set phase to o to insure no damage is added to property by accident
					phase = 0;
					btnDrawCard.setVisible(false);
					CardDisplay.setVisible(true);
					//create random generator and make variable to store random number no bigger than the size of the remaining deck to be drawn from
					Random generator = new Random();
					eCard Drawn = new eCard("",0, false, 0, 0,"");
					int j = generator.nextInt(allE.size());
					//use random number to pull random index from array
					Drawn = allE.get(j);
					//remove used card from array
					allE.remove(j);
					//add used card to used array
					usedE.add(Drawn);
					//update display
					CardDisplay.setText("<html><center><h2>Event</h2><h3>" + Drawn.Description + "<br>" + Drawn.Which + "</h3></center></html>");
					//set damage variable for color of property to be affected by card red = 1, blue = 2, green = 3
					playerChars.get(playersTurn).DamageVar = Drawn.WhichProperty;
					//variable to check if property is owned
					boolean canUse = false;
					//variable to check if property is occupied
					boolean occupiedRightColor = false;
					//damageVar 0 is universal, all characters will have a property this can be used on
					if(playerChars.get(playersTurn).DamageVar == 0)
					{
						canUse = true;
						
						//Financial boon Event cards 
						if(Drawn.Money == 500)
						{
							//add money to players bank, update text and player card to reflect
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 500;
							Turn.setText("<html>Free Money! You're Welcome!</html>");
							characterCard.setText(playerChars.get(playersTurn).charCard());
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//if cards remain, draw again, if not, next phase
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						if(Drawn.Money == 400)
						{
							//add money to players bank, update text and player card to reflect
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 400;
							Turn.setText("<html>Free Money! You're Welcome!</html>");
							characterCard.setText(playerChars.get(playersTurn).charCard());
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//if cards remain, draw again, if not, next phase
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						if(Drawn.Money == 200)
						{
							//add money to players bank, update text and player card to reflect
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 200;
							Turn.setText("<html>Free Money! You're Welcome!</html>");
							characterCard.setText(playerChars.get(playersTurn).charCard());
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//if cards remain, draw again, if not, next phase
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						if(Drawn.Money == 1000)
						{
							playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 1000;
							Turn.setText("<html>Free Money! You're Welcome!</html>");
							characterCard.setText(playerChars.get(playersTurn).charCard());
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						
						
						//drawn money 7,77,777 are not actually money but change the characters rolls needed to move tenants in
						if(Drawn.Money == 7)
						{
							//change characters red leasing number by -1
							playerChars.get(playersTurn).LeaseNumRed = playerChars.get(playersTurn).LeaseNumRed - 1;
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//if cards remain, draw again, if not, next phase
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						if(Drawn.Money == 77)
						{
							//change characters blue leasing number by -1
							playerChars.get(playersTurn).LeaseNumBlue = playerChars.get(playersTurn).LeaseNumBlue - 1;
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						if(Drawn.Money == 777)
						{
							//change characters red, blue, and green leasing number by -1
							playerChars.get(playersTurn).LeaseNumRed = playerChars.get(playersTurn).LeaseNumRed - 1;
							playerChars.get(playersTurn).LeaseNumBlue = playerChars.get(playersTurn).LeaseNumBlue - 1;
							playerChars.get(playersTurn).LeaseNumGreen = playerChars.get(playersTurn).LeaseNumGreen - 1;
							//remove blue card/die from tally and redisplay
							currentRoll.Blue = currentRoll.Blue - 1;
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Continue.<html>");
							}
						}
						//actions for removal of money from another character event cards
						//set amount lost and drawn money for use in TakeMoney method
						if(Drawn.Money == -1000)
						{
							amountLost = 1;
							//display correct amount of buttons for number of players
							if(players == 2)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
							}
							if(players == 3)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
								btnChooseP3.setVisible(true);
							}
							if(players == 4)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
								btnChooseP3.setVisible(true);
								btnChooseP4.setVisible(true);
							}
						}
						//set amount lost and drawn money for use in TakeMoney method
						if(Drawn.Money == -500)
						{
							amountLost = 2;
							//display correct amount of buttons for number of players
							if(players == 2)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
							}
							if(players == 3)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
								btnChooseP3.setVisible(true);
							}
							if(players == 4)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
								btnChooseP3.setVisible(true);
								btnChooseP4.setVisible(true);
							}
						}
						//set amount lost and drawn money for use in TakeMoney method
						if(Drawn.Money == -200)
						{
							amountLost = 3;
							//display correct amount of buttons for number of players
							if(players == 2)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
							}
							if(players == 3)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
								btnChooseP3.setVisible(true);
							}
							if(players == 4)
							{
								btnChooseP1.setVisible(true);
								btnChooseP2.setVisible(true);
								btnChooseP3.setVisible(true);
								btnChooseP4.setVisible(true);
							}
						}
						
						//DrawnMoney 501 = collect 500 from all players
						if (Drawn.Money == 501)
						{
							btnCollect.setVisible(true);
						}
						//DrawnMoney 1111 = fill all unoccupied reds
						if(Drawn.Money == 1111)
						{
							btnFillReds.setVisible(true);
						}
						//DrawnMoney 2001 = Damage an opponents property
						if(Drawn.Money == 2001)
						{
							btnDamageOthers.setVisible(true);
						}
					}
					//Handles losing tenants from one of your properties
					else
					{	//loop to check players properties and see if they have a tenant to loose of the proper color
						for(int p = 0; p < playerChars.get(playersTurn).AllOwned.size(); p++)
						{	//look to see if property is the right color
							int q = playerChars.get(playersTurn).AllOwned.get(p).Color;
							if(q == playerChars.get(playersTurn).DamageVar)
							{
								//check if that property is occupied
								boolean occupied = playerChars.get(playersTurn).AllOwned.get(p).Occupied;
								//if yes, set booleans
								if( occupied == true)
								{
									occupiedRightColor = true;
								}
								canUse = true;
							}
						}
						//if no fitting property can be found, make discard avaliable
						if(canUse == false || occupiedRightColor == false)
						{
							Turn.setText("<html>No Properties of that type Click Discard</html>");
							btnDiscard.setVisible(true);
						}
						//if propery property is owned and occupied
						if(occupiedRightColor == true)
						{
							//set phase to 6(remove tenant) and update display
							phase = 6;
							Turn.setText("<html>Click on the Property to Remove Tenant.</html>");	
						}
					}
				}
			}
		});
		btnDrawCard.setVisible(false);
		btnDrawCard.setBounds(661, 825, 190, 23);
		frmSlumlord.getContentPane().add(btnDrawCard);
				
		
		//make buttons for each property
		//initalize properties as vacant and set tooltips and starting fonts
		JButton btnRedpropone = new JButton("*");
		btnRedpropone.setVerticalAlignment(SwingConstants.TOP);
		btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedpropone.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedpropone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Each property has 6 different actions when clicked depending on the phase and variables
				//Method of all move in		
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r1.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r1, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedpropone, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
					//generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,playerChars.get(playersTurn).Name, ",Rolled To Move In");
				}
				//method for adding damage to property
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r1, playerChars, playersTurn, phase, characterCard, btnRedpropone, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r1.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r1, playerChars, playersTurn, phase, characterCard, btnRedpropone);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r1, btnRedpropone, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r1, btnRedpropone, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r1, phase, playerChars,playersTurn, btnRedpropone, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedpropone.setVisible(false);
		btnRedpropone.setForeground(Color.BLACK);
		btnRedpropone.setBackground(new Color(220, 20, 60));
		btnRedpropone.setBounds(337, 441, 50, 50);
		frmSlumlord.getContentPane().add(btnRedpropone);
		
		JButton btnRedproptwo = new JButton("*");
		btnRedproptwo.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedproptwo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedproptwo.setForeground(Color.BLACK);
		btnRedproptwo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRedproptwo.setVerticalAlignment(SwingConstants.TOP);
		btnRedproptwo.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedproptwo.setPreferredSize(new Dimension(50, 50));
		btnRedproptwo.setBackground(new Color(220, 20, 60));
		btnRedproptwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r2.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r2, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedproptwo, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r2, playerChars, playersTurn, phase, characterCard, btnRedproptwo, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r2.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r2, playerChars, playersTurn, phase, characterCard, btnRedproptwo);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r2, btnRedproptwo, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r2, btnRedproptwo, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r2, phase, playerChars,playersTurn, btnRedproptwo, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
					
			}
		});
		btnRedproptwo.setVisible(false);
		btnRedproptwo.setBounds(417, 480, 50, 50);
		frmSlumlord.getContentPane().add(btnRedproptwo);
		
		JButton btnRedProp3 = new JButton("*");
		btnRedProp3.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp3.setForeground(Color.BLACK);
		btnRedProp3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp3.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp3.setVisible(false);
		btnRedProp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r3.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r3, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp3, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2|| phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r3, playerChars, playersTurn, phase, characterCard, btnRedProp3, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r3.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r3, playerChars, playersTurn, phase, characterCard, btnRedProp3);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r3, btnRedProp3, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r3, btnRedProp3, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r3, phase, playerChars,playersTurn, btnRedProp3, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp3.setBackground(new Color(220, 20, 60));
		btnRedProp3.setBounds(652, 397, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp3);
		
		JButton btnRedProp4 = new JButton("*");
		btnRedProp4.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp4.setForeground(Color.BLACK);
		btnRedProp4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp4.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp4.setVisible(false);
		btnRedProp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r4.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r4, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp4, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r4, playerChars, playersTurn, phase, characterCard, btnRedProp4, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r4.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r4, playerChars, playersTurn, phase, characterCard, btnRedProp4);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r4, btnRedProp4, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r4, btnRedProp4, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r4, phase, playerChars,playersTurn, btnRedProp4, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp4.setBackground(new Color(220, 20, 60));
		btnRedProp4.setBounds(500, 493, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp4);
		
		JButton btnRedProp5 = new JButton("*");
		btnRedProp5.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp5.setForeground(Color.BLACK);
		btnRedProp5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp5.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp5.setVisible(false);
		btnRedProp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r5.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r5, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp5, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r5, playerChars, playersTurn, phase, characterCard, btnRedProp5, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r5.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r5, playerChars, playersTurn, phase, characterCard, btnRedProp5);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r5, btnRedProp5, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r5, btnRedProp5, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r5, phase, playerChars,playersTurn, btnRedProp5, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp5.setBackground(new Color(220, 20, 60));
		btnRedProp5.setBounds(585, 459, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp5);
		
		JButton btnRedProp6 = new JButton("*");
		btnRedProp6.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp6.setForeground(Color.BLACK);
		btnRedProp6.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp6.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp6.setVisible(false);
		btnRedProp6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r6.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r6, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp6, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r6, playerChars, playersTurn, phase, characterCard, btnRedProp6, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r6.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r6, playerChars, playersTurn, phase, characterCard, btnRedProp6);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r6, btnRedProp6, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r6, btnRedProp6, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r6, phase, playerChars,playersTurn, btnRedProp6, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp6.setBackground(new Color(220, 20, 60));
		btnRedProp6.setBounds(751, 209, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp6);
		
		JButton btnRedProp7 = new JButton("*");
		btnRedProp7.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp7.setForeground(Color.BLACK);
		btnRedProp7.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp7.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp7.setVisible(false);
		btnRedProp7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r7.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r7, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp7, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r7, playerChars, playersTurn, phase, characterCard, btnRedProp7, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r7.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r7, playerChars, playersTurn, phase, characterCard, btnRedProp7);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r7, btnRedProp7, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r7, btnRedProp7, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r7, phase, playerChars,playersTurn, btnRedProp7, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp7.setBackground(new Color(220, 20, 60));
		btnRedProp7.setBounds(929, 143, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp7);
		
		JButton btnRedProp8 = new JButton("*");
		btnRedProp8.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp8.setForeground(Color.BLACK);
		btnRedProp8.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp8.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp8.setVisible(false);
		btnRedProp8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r8.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r8, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp8, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r8, playerChars, playersTurn, phase, characterCard, btnRedProp8, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r8.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r8, playerChars, playersTurn, phase, characterCard, btnRedProp8);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r8, btnRedProp8, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r8, btnRedProp8, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r8, phase, playerChars,playersTurn, btnRedProp8, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp8.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp8.setBackground(new Color(220, 20, 60));
		btnRedProp8.setBounds(1099, 199, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp8);
		
		JButton btnRedProp9 = new JButton("*");
		btnRedProp9.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp9.setForeground(Color.BLACK);
		btnRedProp9.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp9.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp9.setVisible(false);
		btnRedProp9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r9.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r9, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp9, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r9, playerChars, playersTurn, phase, characterCard, btnRedProp9, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r9.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r9, playerChars, playersTurn, phase, characterCard, btnRedProp9);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r9, btnRedProp9, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r9, btnRedProp9, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r9, phase, playerChars,playersTurn, btnRedProp9, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp9.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp9.setBackground(new Color(220, 20, 60));
		btnRedProp9.setBounds(838, 168, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp9);
		
		JButton btnRedProp10 = new JButton("*");
		btnRedProp10.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp10.setForeground(Color.BLACK);
		btnRedProp10.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp10.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp10.setVisible(false);
		btnRedProp10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r10.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r10, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp10, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r10, playerChars, playersTurn, phase, characterCard, btnRedProp10, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r10.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r10, playerChars, playersTurn, phase, characterCard, btnRedProp10);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r10, btnRedProp10, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r10, btnRedProp10, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r10, phase, playerChars,playersTurn, btnRedProp10, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp10.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp10.setBackground(new Color(220, 20, 60));
		btnRedProp10.setBounds(1020, 168, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp10);
		
		JButton btnRedProp11 = new JButton("*");
		btnRedProp11.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp11.setForeground(Color.BLACK);
		btnRedProp11.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp11.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp11.setVisible(false);
		btnRedProp11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r11.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r11, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp11, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r11, playerChars, playersTurn, phase, characterCard, btnRedProp11, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r11.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r11, playerChars, playersTurn, phase, characterCard, btnRedProp11);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r11, btnRedProp11, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r11, btnRedProp11, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r11, phase, playerChars,playersTurn, btnRedProp11, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp11.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp11.setBackground(new Color(220, 20, 60));
		btnRedProp11.setBounds(1291, 413, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp11);
		
		JButton btnRedProp12 = new JButton("*");
		btnRedProp12.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp12.setForeground(Color.BLACK);
		btnRedProp12.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp12.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp12.setVisible(false);
		btnRedProp12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r12.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r12, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp12, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r12, playerChars, playersTurn, phase, characterCard, btnRedProp12, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r12.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r12, playerChars, playersTurn, phase, characterCard, btnRedProp12);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r12, btnRedProp12, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r12, btnRedProp12, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r12, phase, playerChars,playersTurn, btnRedProp12, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp12.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp12.setBackground(new Color(220, 20, 60));
		btnRedProp12.setBounds(1374, 365, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp12);
		
		JButton btnRedProp13 = new JButton("*");
		btnRedProp13.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp13.setForeground(Color.BLACK);
		btnRedProp13.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp13.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp13.setVisible(false);
		btnRedProp13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r13.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r13, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp13, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r13, playerChars, playersTurn, phase, characterCard, btnRedProp13, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r13.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r13, playerChars, playersTurn, phase, characterCard, btnRedProp13);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r13, btnRedProp13, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r13, btnRedProp13, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r13, phase, playerChars,playersTurn, btnRedProp13, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp13.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp13.setBackground(new Color(220, 20, 60));
		btnRedProp13.setBounds(1247, 480, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp13);
		
		JButton btnRedProp14 = new JButton("*");
		btnRedProp14.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp14.setForeground(Color.BLACK);
		btnRedProp14.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp14.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp14.setVisible(false);
		btnRedProp14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r14.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r14.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r14, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp14, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r14.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r14, playerChars, playersTurn, phase, characterCard, btnRedProp14, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r14.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r14, playerChars, playersTurn, phase, characterCard, btnRedProp14);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r14.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r14, btnRedProp14, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r14.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r14, btnRedProp14, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r14.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r14, phase, playerChars,playersTurn, btnRedProp14, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp14.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp14.setBackground(new Color(220, 20, 60));
		btnRedProp14.setBounds(1459, 377, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp14);
		
		JButton btnRedProp15 = new JButton("*");
		btnRedProp15.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOwn All 5 of the Closest Reds<br>\r\nGet an Extra $400 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp15.setForeground(Color.BLACK);
		btnRedProp15.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp15.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp15.setVisible(false);
		btnRedProp15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r15.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r15.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r15, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp15, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r15.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r15, playerChars, playersTurn, phase, characterCard, btnRedProp15, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r15.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r15, playerChars, playersTurn, phase, characterCard, btnRedProp15);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r15.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r15, btnRedProp15, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r15.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r15, btnRedProp15, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r15.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r15, phase, playerChars,playersTurn, btnRedProp15, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp15.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp15.setBackground(new Color(220, 20, 60));
		btnRedProp15.setBounds(1541, 413, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp15);
		
		JButton btnRedProp16 = new JButton("*");
		btnRedProp16.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp16.setForeground(Color.BLACK);
		btnRedProp16.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp16.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp16.setVisible(false);
		btnRedProp16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r16.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r16.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r16, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp16, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r16.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r16, playerChars, playersTurn, phase, characterCard, btnRedProp16, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r16.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r16, playerChars, playersTurn, phase, characterCard, btnRedProp16);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r16.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r16, btnRedProp16, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r16.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r16, btnRedProp16, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r16.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r16, phase, playerChars,playersTurn, btnRedProp16, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp16.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp16.setBackground(new Color(220, 20, 60));
		btnRedProp16.setBounds(1469, 636, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp16);
		
		JButton btnRedProp17 = new JButton("*");
		btnRedProp17.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp17.setForeground(Color.BLACK);
		btnRedProp17.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp17.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp17.setVisible(false);
		btnRedProp17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r17.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r17.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r17, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp17, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r17.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r17, playerChars, playersTurn, phase, characterCard, btnRedProp17, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r17.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r17, playerChars, playersTurn, phase, characterCard, btnRedProp17);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r17.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r17, btnRedProp17, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r17.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r17, btnRedProp17, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r17.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r17, phase, playerChars,playersTurn, btnRedProp17, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp17.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp17.setBackground(new Color(220, 20, 60));
		btnRedProp17.setBounds(120, 549, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp17);
		
		JButton btnRedProp18 = new JButton("*");
		btnRedProp18.setToolTipText("<html>Price $800<br>\r\n<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnRedProp18.setForeground(Color.BLACK);
		btnRedProp18.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRedProp18.setVerticalAlignment(SwingConstants.TOP);
		btnRedProp18.setVisible(false);
		btnRedProp18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(r18.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && r18.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInRed(r18, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnRedProp18, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(r18.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar != -1 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( r18, playerChars, playersTurn, phase, characterCard, btnRedProp18, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(r18.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( r18, playerChars, playersTurn, phase, characterCard, btnRedProp18);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Red");
				}
				//method for repairing
				if(r18.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(r18, btnRedProp18, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(r18.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(r18, btnRedProp18, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(r18.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(r18, phase, playerChars,playersTurn, btnRedProp18, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnRedProp18.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnRedProp18.setBackground(new Color(220, 20, 60));
		btnRedProp18.setBounds(1385, 785, 50, 50);
		frmSlumlord.getContentPane().add(btnRedProp18);
		
		JButton btnBlueProp1 = new JButton("*");
		btnBlueProp1.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp1.setForeground(Color.BLACK);
		btnBlueProp1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp1.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp1.setVisible(false);
		btnBlueProp1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b1.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b1, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp1, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b1, playerChars, playersTurn, phase, characterCard, btnBlueProp1, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b1.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b1, playerChars, playersTurn, phase, characterCard, btnBlueProp1);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b1, btnBlueProp1, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b1, btnBlueProp1, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b1, phase, playerChars,playersTurn, btnBlueProp1, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp1.setBackground(Color.BLUE);
		btnBlueProp1.setBounds(712, 336, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp1);
		
		JButton btnBlueProp2 = new JButton("*");
		btnBlueProp2.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp2.setForeground(Color.BLACK);
		btnBlueProp2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp2.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp2.setVisible(false);
		btnBlueProp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b2.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b2, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp2, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b2, playerChars, playersTurn, phase, characterCard, btnBlueProp2, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b2.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b2, playerChars, playersTurn, phase, characterCard, btnBlueProp2);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b2, btnBlueProp2, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b2, btnBlueProp2, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b2, phase, playerChars,playersTurn, btnBlueProp2, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp2.setBackground(Color.BLUE);
		btnBlueProp2.setBounds(782, 288, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp2);
		
		JButton btnBlueProp3 = new JButton("*");
		btnBlueProp3.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp3.setForeground(Color.BLACK);
		btnBlueProp3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp3.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp3.setVisible(false);
		btnBlueProp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b3.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b3, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp3, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b3, playerChars, playersTurn, phase, characterCard, btnBlueProp3, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b3.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b3, playerChars, playersTurn, phase, characterCard, btnBlueProp3);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b3, btnBlueProp3, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b3, btnBlueProp3, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b3, phase, playerChars,playersTurn, btnBlueProp3, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp3.setBackground(Color.BLUE);
		btnBlueProp3.setBounds(850, 249, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp3);
		
		JButton btnBlueProp4 = new JButton("*");
		btnBlueProp4.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp4.setForeground(Color.BLACK);
		btnBlueProp4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp4.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp4.setVisible(false);
		btnBlueProp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b4.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b4, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp4, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b4, playerChars, playersTurn, phase, characterCard, btnBlueProp4, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b4.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b4, playerChars, playersTurn, phase, characterCard, btnBlueProp4);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b4, btnBlueProp4, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b4, btnBlueProp4, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b4, phase, playerChars,playersTurn, btnBlueProp4, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp4.setBackground(Color.BLUE);
		btnBlueProp4.setBounds(925, 224, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp4);
		
		JButton btnBlueProp5 = new JButton("*");
		btnBlueProp5.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp5.setForeground(Color.BLACK);
		btnBlueProp5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp5.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp5.setVisible(false);
		btnBlueProp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b5.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b5, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp5, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b5, playerChars, playersTurn, phase, characterCard, btnBlueProp5, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b5.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b5, playerChars, playersTurn, phase, characterCard, btnBlueProp5);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b5, btnBlueProp5, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b5, btnBlueProp5, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b5, phase, playerChars,playersTurn, btnBlueProp5, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp5.setBackground(Color.BLUE);
		btnBlueProp5.setBounds(1193, 564, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp5);
		
		JButton btnBlueProp6 = new JButton("*");
		btnBlueProp6.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp6.setForeground(Color.BLACK);
		btnBlueProp6.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp6.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp6.setVisible(false);
		btnBlueProp6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b6.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b6, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp6, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2|| playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b6, playerChars, playersTurn, phase, characterCard, btnBlueProp6, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b6.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b6, playerChars, playersTurn, phase, characterCard, btnBlueProp6);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b6, btnBlueProp6, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b6, btnBlueProp6, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b6, phase, playerChars,playersTurn, btnBlueProp6, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp6.setBackground(Color.BLUE);
		btnBlueProp6.setBounds(1071, 697, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp6);
		
		JButton btnBlueProp7 = new JButton("*");
		btnBlueProp7.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp7.setForeground(Color.BLACK);
		btnBlueProp7.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp7.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp7.setVisible(false);
		btnBlueProp7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b7.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b7, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp7, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b7, playerChars, playersTurn, phase, characterCard, btnBlueProp7, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b7.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b7, playerChars, playersTurn, phase, characterCard, btnBlueProp7);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b7, btnBlueProp7, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b7, btnBlueProp7, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b7, phase, playerChars,playersTurn, btnBlueProp7, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp7.setBackground(Color.BLUE);
		btnBlueProp7.setBounds(1145, 636, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp7);
		
		JButton btnBlueProp8 = new JButton("*");
		btnBlueProp8.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp8.setForeground(Color.BLACK);
		btnBlueProp8.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp8.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp8.setVisible(false);
		btnBlueProp8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b8.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b8, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp8, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b8, playerChars, playersTurn, phase, characterCard, btnBlueProp8, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b8.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b8, playerChars, playersTurn, phase, characterCard, btnBlueProp8);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b8, btnBlueProp8, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b8, btnBlueProp8, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b8, phase, playerChars,playersTurn, btnBlueProp8, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp8.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp8.setBackground(Color.BLUE);
		btnBlueProp8.setBounds(987, 730, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp8);
		
		JButton btnBlueProp9 = new JButton("*");
		btnBlueProp9.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp9.setForeground(Color.BLACK);
		btnBlueProp9.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp9.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp9.setVisible(false);
		btnBlueProp9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b9.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b9, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp9, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b9, playerChars, playersTurn, phase, characterCard, btnBlueProp9, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b9.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b9, playerChars, playersTurn, phase, characterCard, btnBlueProp9);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b9, btnBlueProp9, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b9, btnBlueProp9, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b9, phase, playerChars,playersTurn, btnBlueProp9, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp9.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp9.setBackground(Color.BLUE);
		btnBlueProp9.setBounds(1603, 480, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp9);
		
		JButton btnBlueProp10 = new JButton("*");
		btnBlueProp10.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp10.setForeground(Color.BLACK);
		btnBlueProp10.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp10.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp10.setVisible(false);
		btnBlueProp10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b10.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b10, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp10, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b10, playerChars, playersTurn, phase, characterCard, btnBlueProp10, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b10.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b10, playerChars, playersTurn, phase, characterCard, btnBlueProp10);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b10, btnBlueProp10, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b10, btnBlueProp10, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b10.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b10, phase, playerChars,playersTurn, btnBlueProp10, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp10.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp10.setBackground(Color.BLUE);
		btnBlueProp10.setBounds(1688, 717, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp10);
		
		JButton btnBlueProp11 = new JButton("*");
		btnBlueProp11.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp11.setForeground(Color.BLACK);
		btnBlueProp11.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp11.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp11.setVisible(false);
		btnBlueProp11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b11.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b11, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp11, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b11, playerChars, playersTurn, phase, characterCard, btnBlueProp11, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b11.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b11, playerChars, playersTurn, phase, characterCard, btnBlueProp11);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b11, btnBlueProp11, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b11, btnBlueProp11, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b11.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b11, phase, playerChars,playersTurn, btnBlueProp11, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp11.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp11.setBackground(Color.BLUE);
		btnBlueProp11.setBounds(1645, 549, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp11);
		
		JButton btnBlueProp12 = new JButton("*");
		btnBlueProp12.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOwn All 4 of the Closest Blues<br>\r\nGet an Extra $600 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp12.setForeground(Color.BLACK);
		btnBlueProp12.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp12.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp12.setVisible(false);
		btnBlueProp12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b12.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b12, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp12, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b12, playerChars, playersTurn, phase, characterCard, btnBlueProp12, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b12.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b12, playerChars, playersTurn, phase, characterCard, btnBlueProp12);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b12, btnBlueProp12, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b12, btnBlueProp12, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b12.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b12, phase, playerChars,playersTurn, btnBlueProp12, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp12.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp12.setBackground(Color.BLUE);
		btnBlueProp12.setBounds(1675, 636, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp12);
		
		JButton btnBlueProp13 = new JButton("*");
		btnBlueProp13.setToolTipText("<html>Price $1200<br>\r\n<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnBlueProp13.setForeground(Color.BLACK);
		btnBlueProp13.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlueProp13.setVerticalAlignment(SwingConstants.TOP);
		btnBlueProp13.setVisible(false);
		btnBlueProp13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(b13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && b13.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInBlue(b13, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnBlueProp13, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(b13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 2 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( b13, playerChars, playersTurn, phase, characterCard, btnBlueProp13, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(b13.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( b13, playerChars, playersTurn, phase, characterCard, btnBlueProp13);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Blue");
				}
				//method for repairing
				if(b13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(b13, btnBlueProp13, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(b13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(b13, btnBlueProp13, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(b13.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(b13, phase, playerChars,playersTurn, btnBlueProp13, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnBlueProp13.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnBlueProp13.setBackground(Color.BLUE);
		btnBlueProp13.setBounds(347, 655, 50, 50);
		frmSlumlord.getContentPane().add(btnBlueProp13);
		
		JButton btnGreenProp1 = new JButton("*");
		btnGreenProp1.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp1.setForeground(Color.BLACK);
		btnGreenProp1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp1.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp1.setVisible(false);
		btnGreenProp1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g1.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g1, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp1, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g1, playerChars, playersTurn, phase, characterCard, btnGreenProp1, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g1.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g1, playerChars, playersTurn, phase, characterCard, btnGreenProp1);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g1, btnGreenProp1, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g1, btnGreenProp1, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g1.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g1, phase, playerChars,playersTurn, btnGreenProp1, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp1.setBackground(new Color(0, 128, 0));
		btnGreenProp1.setBounds(1171, 352, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp1);
		
		JButton btnGreenProp2 = new JButton("*");
		btnGreenProp2.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp2.setForeground(Color.BLACK);
		btnGreenProp2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp2.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp2.setVisible(false);
		btnGreenProp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g2.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g2, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp2, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g2, playerChars, playersTurn, phase, characterCard, btnGreenProp2, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g2.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g2, playerChars, playersTurn, phase, characterCard, btnGreenProp2);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g2, btnGreenProp2, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g2, btnGreenProp2, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g2.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g2, phase, playerChars,playersTurn, btnGreenProp2, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp2.setBackground(new Color(0, 128, 0));
		btnGreenProp2.setBounds(1020, 255, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp2);
		
		JButton btnGreenProp3 = new JButton("*");
		btnGreenProp3.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp3.setForeground(Color.BLACK);
		btnGreenProp3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp3.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp3.setVisible(false);
		btnGreenProp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g3.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g3, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp3, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g3, playerChars, playersTurn, phase, characterCard, btnGreenProp3, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g3.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g3, playerChars, playersTurn, phase, characterCard, btnGreenProp3);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g3, btnGreenProp3, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g3, btnGreenProp3, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g3.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g3, phase, playerChars,playersTurn, btnGreenProp3, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp3.setBackground(new Color(0, 128, 0));
		btnGreenProp3.setBounds(1099, 304, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp3);
		
		JButton btnGreenProp4 = new JButton("*");
		btnGreenProp4.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp4.setForeground(Color.BLACK);
		btnGreenProp4.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp4.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp4.setVisible(false);
		btnGreenProp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g4.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g4, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp4, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g4, playerChars, playersTurn, phase, characterCard, btnGreenProp4, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g4.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g4, playerChars, playersTurn, phase, characterCard, btnGreenProp4);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g4, btnGreenProp4, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g4, btnGreenProp4, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g4.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g4, phase, playerChars,playersTurn, btnGreenProp4, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp4.setBackground(new Color(0, 128, 0));
		btnGreenProp4.setBounds(284, 377, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp4);
		

		JButton btnGreenProp5 = new JButton("*");
		btnGreenProp5.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp5.setForeground(Color.BLACK);
		btnGreenProp5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp5.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp5.setVisible(false);
		btnGreenProp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g5.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g5, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp5, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g5, playerChars, playersTurn, phase, characterCard, btnGreenProp5, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g5.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g5, playerChars, playersTurn, phase, characterCard, btnGreenProp5);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g5, btnGreenProp5, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g5, btnGreenProp5, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g5.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g5, phase, playerChars,playersTurn, btnGreenProp5, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp5.setBackground(new Color(0, 128, 0));
		btnGreenProp5.setBounds(233, 316, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp5);
		
		JButton btnGreenProp6 = new JButton("*");
		btnGreenProp6.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp6.setForeground(Color.BLACK);
		btnGreenProp6.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp6.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp6.setVisible(false);
		btnGreenProp6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g6.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g6, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp6, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g6, playerChars, playersTurn, phase, characterCard, btnGreenProp6, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g6.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g6, playerChars, playersTurn, phase, characterCard, btnGreenProp6);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g6, btnGreenProp6, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g6, btnGreenProp6, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g6.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g6, phase, playerChars,playersTurn, btnGreenProp6, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp6.setBackground(new Color(0, 128, 0));
		btnGreenProp6.setBounds(192, 249, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp6);
		
		JButton btnGreenProp7 = new JButton("*");
		btnGreenProp7.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp7.setForeground(Color.BLACK);
		btnGreenProp7.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp7.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp7.setVisible(false);
		btnGreenProp7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g7.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g7, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp7, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g7, playerChars, playersTurn, phase, characterCard, btnGreenProp7, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g7.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g7, playerChars, playersTurn, phase, characterCard, btnGreenProp7);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g7, btnGreenProp7, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g7, btnGreenProp7, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g7.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g7, phase, playerChars,playersTurn, btnGreenProp7, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp7.setBackground(new Color(0, 128, 0));
		btnGreenProp7.setBounds(775, 655, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp7);
		
		JButton btnGreenProp8 = new JButton("*");
		btnGreenProp8.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp8.setForeground(Color.BLACK);
		btnGreenProp8.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp8.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp8.setVisible(false);
		btnGreenProp8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g8.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g8, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp8, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g8, playerChars, playersTurn, phase, characterCard, btnGreenProp8, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g8.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g8, playerChars, playersTurn, phase, characterCard, btnGreenProp8);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g8, btnGreenProp8, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g8, btnGreenProp8, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g8.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g8, phase, playerChars,playersTurn, btnGreenProp8, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp8.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp8.setBackground(new Color(0, 128, 0));
		btnGreenProp8.setBounds(701, 591, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp8);
		
		JButton btnGreenProp9 = new JButton("*");
		btnGreenProp9.setToolTipText("<html>Price $2000<br>\r\n<br>\r\nOwn All 3 of the Closest Greens<br>\r\nGet an Extra $1000 a Round<br>\r\nOccupied Rent<br>\r\nRed Tenant $400<br>\r\nBlue Tenant $600<br>\r\nGreen Tenant $1000</html>");
		btnGreenProp9.setForeground(Color.BLACK);
		btnGreenProp9.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGreenProp9.setVerticalAlignment(SwingConstants.TOP);
		btnGreenProp9.setVisible(false);
		btnGreenProp9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(g9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == -1 && g9.Occupied == false && phase == 1 && playerChars.get(playersTurn).numDice != 0)
				{
					currentRoll = playerChars.get(playersTurn).MoveInGreen(g9, playerChars, playersTurn, phase, currentTenant,  currentRoll, gameDice, diceResults, Turn, 
							btnGreenProp9, avaliable, avaliableTenants, round, btnDrawCard, btnNextPhase, roundS);
				}
				//method for adding damage to property
				if(g9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && playerChars.get(playersTurn).DamageVar == 3 || playerChars.get(playersTurn).DamageVar == 0 && phase == 2 || phase == 11)
				{
					playerChars.get(playersTurn).AddDamage( g9, playerChars, playersTurn, phase, characterCard, btnGreenProp9, currentRoll, diceResults, Turn, 
							btnTakeBank, btnDrawCard, btnNextPhase, roundS);
					CardDisplay.setText("");
				}
				//method for buying
				if(g9.OwnedBy.equals("")&& phase == 3)
				{
					playerChars.get(playersTurn).Buying( g9, playerChars, playersTurn, phase, characterCard, btnGreenProp9);
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Bought Property,","Green");
				}
				//method for repairing
				if(g9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor)&& phase == 4)
				{
					playerChars.get(playersTurn).Repairing(g9, btnGreenProp9, playerChars, playersTurn, phase, Turn, characterCard, roundS );
				}
				//Method for upgrading
				if(g9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 5)
				{
					playerChars.get(playersTurn).Upgrading(g9, btnGreenProp9, playerChars, playersTurn, phase, Turn, characterCard, roundS);
				}
				//method for removing tenant of your own
				if(g9.OwnedBy.equals(playerChars.get(playersTurn).PlayerColor) && phase == 6)
				{
					playerChars.get(playersTurn).evictOwn(g9, phase, playerChars,playersTurn, btnGreenProp9
							, currentRoll, diceResults, btnDrawCard, btnNextPhase, Turn);
				}
			}
		});
		btnGreenProp9.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
		btnGreenProp9.setBackground(new Color(0, 128, 0));
		btnGreenProp9.setBounds(850, 700, 50, 50);
		frmSlumlord.getContentPane().add(btnGreenProp9);
		
		
		//set button names (now that buttons have been established) for reference when changing property display status
		r1.ButtonName = btnRedpropone;
		r2.ButtonName = btnRedproptwo;
		r3.ButtonName = btnRedProp3;
		r4.ButtonName = btnRedProp4;
		r5.ButtonName = btnRedProp5;
		r6.ButtonName = btnRedProp6;
		r7.ButtonName = btnRedProp7;
		r8.ButtonName = btnRedProp8;
		r9.ButtonName = btnRedProp9;
		r10.ButtonName = btnRedProp10;
		r11.ButtonName = btnRedProp11;
		r12.ButtonName = btnRedProp12;
		r13.ButtonName = btnRedProp13;
		r14.ButtonName = btnRedProp14;
		r15.ButtonName = btnRedProp15;
		r16.ButtonName = btnRedProp16;
		r17.ButtonName = btnRedProp17;
		r18.ButtonName = btnRedProp18;
		
		
		
		
		//Button for round 0 player switch
		JButton btnNextPlayer = new JButton("Next Player");
		btnNextPlayer.setContentAreaFilled(false);
		btnNextPlayer.setBorderPainted(false);
		btnNextPlayer.setBorder(null);
		btnNextPlayer.setForeground(Color.WHITE);
		btnNextPlayer.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnNextPlayer.setVisible(false);
		btnNextPlayer.setBounds(344, 814, 225, 23);
		frmSlumlord.getContentPane().add(btnNextPlayer);
		
		//button for start round one after first property selection
		JButton btnStartRoundOne = new JButton("Start Round One");
		btnStartRoundOne.setContentAreaFilled(false);
		btnStartRoundOne.setBorderPainted(false);
		btnStartRoundOne.setBorder(null);
		btnStartRoundOne.setForeground(Color.WHITE);
		btnStartRoundOne.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnStartRoundOne.setVisible(false);
		btnStartRoundOne.setBounds(292, 814, 277, 23);
		frmSlumlord.getContentPane().add(btnStartRoundOne);
		
		
		//Buttons for dice selection
		JButton btnDice3 = new JButton("3 Dice");
		btnDice3.setToolTipText("<html>3 Dice<br>\r\nPerfect Roll 6 Green<br><br>\r\nEach die has:<br>\r\n2 Red Sides<br>\r\n1 Blue Side<br>\r\n2 Green Sides<br>\r\n1 Double Green Side</html>\r\n");
		btnDice3.setContentAreaFilled(false);
		btnDice3.setBorderPainted(false);
		btnDice3.setBorder(null);
		btnDice3.setForeground(Color.WHITE);
		btnDice3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDice3.setVisible(false);
		btnDice3.setBounds(1498, 74, 102, 23);
		frmSlumlord.getContentPane().add(btnDice3);
		
		JButton btnDice4 = new JButton("4 Dice");
		btnDice4.setToolTipText("<html>3 Dice<br>\r\nPerfect Roll 8 Green<br><br>\r\nEach die has:<br>\r\n2 Red Sides<br>\r\n1 Blue Side<br>\r\n2 Green Sides<br>\r\n1 Double Green Side</html>\r\n");
		btnDice4.setContentAreaFilled(false);
		btnDice4.setBorderPainted(false);
		btnDice4.setBorder(null);
		btnDice4.setForeground(Color.WHITE);
		btnDice4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDice4.setVisible(false);
		btnDice4.setBounds(1498, 108, 102, 23);
		frmSlumlord.getContentPane().add(btnDice4);
		
		JButton btnDice5 = new JButton("5 Dice");
		btnDice5.setToolTipText("<html>3 Dice<br>\r\nPerfect Roll 10 Green<br><br>\r\nEach die has:<br>\r\n2 Red Sides<br>\r\n1 Blue Side<br>\r\n2 Green Sides<br>\r\n1 Double Green Side</html>\r\n");
		btnDice5.setContentAreaFilled(false);
		btnDice5.setBorderPainted(false);
		btnDice5.setBorder(null);
		btnDice5.setForeground(Color.WHITE);
		btnDice5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDice5.setVisible(false);
		btnDice5.setBounds(1498, 143, 102, 23);
		frmSlumlord.getContentPane().add(btnDice5);
		
		JButton btnDice6 = new JButton("6 Dice");
		btnDice6.setToolTipText("<html>3 Dice<br>\r\nPerfect Roll 12 Green<br><br>\r\nEach die has:<br>\r\n2 Red Sides<br>\r\n1 Blue Side<br>\r\n2 Green Sides<br>\r\n1 Double Green Side</html>\r\n");
		btnDice6.setContentAreaFilled(false);
		btnDice6.setBorderPainted(false);
		btnDice6.setBorder(null);
		btnDice6.setForeground(Color.WHITE);
		btnDice6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDice6.setVisible(false);
		btnDice6.setBounds(1498, 177, 103, 23);
		frmSlumlord.getContentPane().add(btnDice6);
		
		JButton btnDice7 = new JButton("7 Dice");
		btnDice7.setToolTipText("<html>3 Dice<br>\r\nPerfect Roll 14 Green<br><br>\r\nEach die has:<br>\r\n2 Red Sides<br>\r\n1 Blue Side<br>\r\n2 Green Sides<br>\r\n1 Double Green Side</html>\r\n");
		btnDice7.setContentAreaFilled(false);
		btnDice7.setBorderPainted(false);
		btnDice7.setBorder(null);
		btnDice7.setForeground(Color.WHITE);
		btnDice7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDice7.setVisible(false);
		btnDice7.setBounds(1498, 212, 102, 23);
		frmSlumlord.getContentPane().add(btnDice7);
		
		JButton btnDice8 = new JButton("8 Dice");
		btnDice8.setToolTipText("<html>3 Dice<br>\r\nPerfect Roll 16 Green<br><br>\r\nEach die has:<br>\r\n2 Red Sides<br>\r\n1 Blue Side<br>\r\n2 Green Sides<br>\r\n1 Double Green Side</html>\r\n");
		btnDice8.setContentAreaFilled(false);
		btnDice8.setBorderPainted(false);
		btnDice8.setBorder(null);
		btnDice8.setForeground(Color.WHITE);
		btnDice8.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnDice8.setVisible(false);
		btnDice8.setBounds(1498, 249, 102, 23);
		frmSlumlord.getContentPane().add(btnDice8);
		
		//button for red tenant
		JButton btnRed = new JButton("Red");
		btnRed.setContentAreaFilled(false);
		btnRed.setBorderPainted(false);
		btnRed.setBorder(null);
		btnRed.setForeground(Color.WHITE);
		btnRed.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnRed.setVisible(false);
		btnRed.setBounds(1523, 30, 89, 23);
		frmSlumlord.getContentPane().add(btnRed);	
		
		//Button for selecting Blue tenant
		JButton btnBlue = new JButton("Blue");
		btnBlue.setContentAreaFilled(false);
		btnBlue.setBorderPainted(false);
		btnBlue.setBorder(null);
		btnBlue.setForeground(Color.WHITE);
		btnBlue.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnBlue.setVisible(false);
		btnBlue.setBounds(1622, 30, 89, 23);
		frmSlumlord.getContentPane().add(btnBlue);
		
		//Button for selecting Green Tenant
		JButton btnGreen = new JButton("Green");
		btnGreen.setContentAreaFilled(false);
		btnGreen.setBorderPainted(false);
		btnGreen.setBorder(null);
		btnGreen.setForeground(Color.WHITE);
		btnGreen.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnGreen.setVisible(false);
		btnGreen.setBounds(1721, 30, 89, 23);
		frmSlumlord.getContentPane().add(btnGreen);
		
		//button to select property to buy, changes phase to 3(buy)
		JButton btnBuyProperty = new JButton("Buy");
		btnBuyProperty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnBuyProperty.setContentAreaFilled(false);
		btnBuyProperty.setBorderPainted(false);
		btnBuyProperty.setBorder(null);
		btnBuyProperty.setForeground(Color.WHITE);
		btnBuyProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phase = 3;
			}
		});
		btnBuyProperty.setVisible(false);
		btnBuyProperty.setFocusCycleRoot(true);
		btnBuyProperty.setBounds(10, 814, 110, 23);
		frmSlumlord.getContentPane().add(btnBuyProperty);
		
		//button to select property to repair, changes phase to 4 (repair)
		JButton btnRepairProperty = new JButton("Repair");
		btnRepairProperty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnRepairProperty.setContentAreaFilled(false);
		btnRepairProperty.setBorderPainted(false);
		btnRepairProperty.setBorder(null);
		btnRepairProperty.setForeground(Color.WHITE);
		btnRepairProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phase = 4;
			}
		});
		btnRepairProperty.setVisible(false);
		btnRepairProperty.setBounds(130, 814, 110, 23);
		frmSlumlord.getContentPane().add(btnRepairProperty);
		
		//button to select property to upgrade, changes phase to 5 (upgrade)
		JButton btnUpgradeProperty = new JButton("Upgrade");
		btnUpgradeProperty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnUpgradeProperty.setContentAreaFilled(false);
		btnUpgradeProperty.setBorderPainted(false);
		btnUpgradeProperty.setBorder(null);
		btnUpgradeProperty.setForeground(Color.WHITE);
		btnUpgradeProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phase = 5;
			}
		});
		btnUpgradeProperty.setVisible(false);
		btnUpgradeProperty.setBounds(267, 814, 120, 23);
		frmSlumlord.getContentPane().add(btnUpgradeProperty);
		
		//button to skip leasing phase
		JButton btnSkipLeasing = new JButton("Skip Leasing");
		btnSkipLeasing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSkipLeasing.setVisible(false);
				btnNextPhase.setVisible(true);
				btnRed.setVisible(false);
				btnBlue.setVisible(false);
				btnGreen.setVisible(false);
				Turn.setText("<html>Click on Next Phase to Continue</html>");
			}
		});
		btnSkipLeasing.setVisible(false);
		btnSkipLeasing.setContentAreaFilled(false);
		btnSkipLeasing.setBorderPainted(false);
		btnSkipLeasing.setForeground(Color.WHITE);
		btnSkipLeasing.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnSkipLeasing.setBounds(1588, 287, 208, 23);
		frmSlumlord.getContentPane().add(btnSkipLeasing);
		
		
		//Button Actions:
		//Button for ending player turn and cycling rounds
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.setForeground(Color.WHITE);
		btnEndTurn.setContentAreaFilled(false);
		btnEndTurn.setBorderPainted(false);
		btnEndTurn.setVisible(false);
		btnEndTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if at the end of one players turn but not the entire round
				if(playersTurn < players -1)
				{
					//reset damage variables so no damage can be done unintentionally
					playerChars.get(playersTurn).DamageVar = -1;
					//increment players turn
					playersTurn++;
					playerChars.get(playersTurn).DamageVar = -1;
					//reset all buttons to begening of turn format
					btnBuyProperty.setVisible(false);
					btnRepairProperty.setVisible(false);
					btnUpgradeProperty.setVisible(false);
					btnEndTurn.setVisible(false);
					btnRed.setVisible(true);
					btnBlue.setVisible(true);
					btnGreen.setVisible(true);
					btnSkipLeasing.setVisible(true);
					CardDisplay.setVisible(false);
					diceResults.setVisible(false);
					//change character card to next player and set text 
					characterCard.setText(playerChars.get(playersTurn).charCard() );
					Turn.setText("<html>"+ playerChars.get(playersTurn).Name + "'s Turn Pick a Tenant from the Avaliable Tenants </html>");
					//Reinitialize dice to 0's
					currentRoll = new dice(0,0,0);
					//reset rerolls
					playerChars.get(playersTurn).reroll = 0;
					//reset phase to 1 (tenant move in)
					phase = 1;
					playerChars.get(playersTurn).DamageVar = -1;
					//show discard button if character is scotty
					if(playerChars.get(playersTurn).SkillNum == -1)
					{
						playerChars.get(playersTurn).SkillNum = 1;
						
					}
				}
				//if it is the end of the round 
				else 
				{	
					
					//and not the end of the game
					if(round < totalRounds +1)
					{
						//cycle player turn order(player 1 becomes 2, 2 becomes 3 ect..) to ensure players have equal shot at the tenants and properties
						if(players == 2)
						{
							character hold0 = new character("","","",1,1,1,"",1);
							character hold1 = new character("","","",1,1,1,"",1);
							hold0 = playerChars.get(0);
							hold1 = playerChars.get(1);
							playerChars.add(0,hold1);
							playerChars.add(1,hold0);
						}
						//cycle player turn order(player 1 becomes 2, 2 becomes 3 ect..) to ensure players have equal shot at the tenants and properties
						if(players == 3)
						{
							character hold0 = new character("","","",1,1,1,"",1);
							character hold1 = new character("","","",1,1,1,"",1);
							character hold2 = new character("","","",1,1,1,"",1);
							hold0 = playerChars.get(0);
							hold1 = playerChars.get(1);
							hold2 = playerChars.get(2);
							playerChars.add(0,hold1);
							playerChars.add(1,hold2);
							playerChars.add(2,hold0);	
						}
						//cycle player turn order(player 1 becomes 2, 2 becomes 3 ect..) to ensure players have equal shot at the tenants and properties
						if(players == 4)
						{
							character hold0 = new character("","","",1,1,1,"",1);
							character hold1 = new character("","","",1,1,1,"",1);
							character hold2 = new character("","","",1,1,1,"",1);
							character hold3 = new character("","","",1,1,1,"",1);
							hold0 = playerChars.get(0);
							hold1 = playerChars.get(1);
							hold2 = playerChars.get(2);
							hold3 = playerChars.get(3);
							playerChars.add(0,hold3);
							playerChars.add(1,hold0);
							playerChars.add(2,hold1);	
							playerChars.add(3,hold2);
						}
						//reset buttons and frames to start of round 
						btnBuyProperty.setVisible(false);
						btnRepairProperty.setVisible(false);
						btnUpgradeProperty.setVisible(false);
						btnRed.setVisible(true);
						btnBlue.setVisible(true);
						btnGreen.setVisible(true);
						btnSkipLeasing.setVisible(true);
						btnEndTurn.setVisible(false);
						CardDisplay.setVisible(false);
						diceResults.setVisible(false);
						//reset damage variables so no damage can be done unintentionally
						playerChars.get(playersTurn).DamageVar = -1;
						//set player turns back to zero
						playersTurn = 0;
						playerChars.get(playersTurn).DamageVar = -1;
						//increment round
						round++;
						if(round == 1)
							roundS = "1";
						if(round == 2)
							roundS = "2";
						if(round == 3)
							roundS = "3";
						if(round == 4)
							roundS = "4";
						if(round == 5)
							roundS = "5";
						if(round == 6)
							roundS = "6";
						if(round == 7)
							roundS = "7";
						if(round == 8)
							roundS = "8";
						if(round == 9)
							roundS = "9";
						if(round == 10)
							roundS = "10";
						if(round == 11)
							roundS = "11";
						if(round == 12)
							roundS = "12";
						//display proper character card and text
						characterCard.setText(playerChars.get(playersTurn).charCard() );
						Turn.setText("<html>" + playerChars.get(playersTurn).Name + "'s Turn Pick a Tenant from the Avaliable Tenants </html>");
						//set phase to 1 (tenant move in)
						phase = 1;
						//Reset dice to 0's
						currentRoll = new dice(0,0,0);
						//reset rerolls
						playerChars.get(playersTurn).reroll = 0;
						//Reset tenant tokens
						game = new tenantTokens();
						//draw new set of tenant tokens for new round
						avaliable = game.RandomDraw(game, round, players);
						//display new set of tenant tokens
						avaliableTenants.setText("<html>Round&nbsp;" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						//add money to martin character because of his unique skill
						Martin.Bank = Martin.Bank + 200;
						//reset scottys skill number so he will have the discard option again
						if(playerChars.get(playersTurn).SkillNum == -1)
						{
							playerChars.get(playersTurn).SkillNum = 1;
							
						}
					}
					//if game is over, make all property buttons dissappear
					if(round == totalRounds + 1)
					{
						btnRedpropone.setVisible(false);
						btnRedproptwo.setVisible(false);
						btnRedProp3.setVisible(false);
						btnRedProp4.setVisible(false);
						btnRedProp5.setVisible(false);
						btnRedProp6.setVisible(false);
						btnRedProp7.setVisible(false);
						btnRedProp8.setVisible(false);
						btnRedProp9.setVisible(false);
						btnRedProp10.setVisible(false);
						btnRedProp11.setVisible(false);
						btnRedProp12.setVisible(false);
						btnRedProp13.setVisible(false);
						btnRedProp14.setVisible(false);
						btnRedProp15.setVisible(false);
						btnRedProp16.setVisible(false);
						btnRedProp17.setVisible(false);
						btnRedProp18.setVisible(false);
						btnBlueProp1.setVisible(false);
						btnBlueProp2.setVisible(false);
						btnBlueProp3.setVisible(false);
						btnBlueProp4.setVisible(false);
						btnBlueProp5.setVisible(false);
						btnBlueProp6.setVisible(false);
						btnBlueProp7.setVisible(false);
						btnBlueProp8.setVisible(false);
						btnBlueProp9.setVisible(false);
						btnBlueProp10.setVisible(false);
						btnBlueProp11.setVisible(false);
						btnBlueProp12.setVisible(false);
						btnBlueProp13.setVisible(false);
						btnGreenProp1.setVisible(false);
						btnGreenProp2.setVisible(false);
						btnGreenProp3.setVisible(false);
						btnGreenProp4.setVisible(false);
						btnGreenProp5.setVisible(false);
						btnGreenProp6.setVisible(false);
						btnGreenProp7.setVisible(false);
						btnGreenProp8.setVisible(false);
						btnGreenProp9.setVisible(false);
						btnNextPlayer.setVisible(false);
						//calculate winning scores
						for(int e = 0; e < players; e++)
						{
							//make variable for all characters property damages combined
							int allDamage = 0;
							//iterate over all characters properties, adding up damage
							for(int g = 0; g < playerChars.get(e).AllOwned.size(); g++)
							{
								
								int finalDamage = playerChars.get(e).AllOwned.get(g).Damage;
								allDamage = allDamage + finalDamage;
							}
							//take the total damage times 200 and subtract from players bank
							playerChars.get(e).Bank = playerChars.get(e).Bank - (allDamage * 200);
						}
						//iterate over all players and add up their total properties owned times the cost and add to their bank
						for( int f = 0; f < players; f++)
						{
							int R = playerChars.get(f).RedOwned * 800;
							int B = playerChars.get(f).BlueOwned * 1200;
							int G = playerChars.get(f).GreenOwned * 2000;
							playerChars.get(f).Bank = playerChars.get(f).Bank + R + B + G;
							if(playerChars.get(f).Name == "Scotty")
							{
								m.totG = m.totG + 1;
								m.Tscore = m.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > m.Hscore)
								{
									m.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Burke")
							{
								n.totG = n.totG + 1;
								n.Tscore = n.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > n.Hscore)
								{
									n.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Han")
							{
								o.totG = o.totG + 1;
								o.Tscore = o.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > o.Hscore)
								{
									o.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Malcolm")
							{
								p.totG = p.totG + 1;
								p.Tscore = p.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > p.Hscore)
								{
									p.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Siri")
							{
								q.totG = q.totG + 1;
								q.Tscore = q.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > q.Hscore)
								{
									q.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Leia")
							{
								r.totG = r.totG + 1;
								r.Tscore = r.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > r.Hscore)
								{
									r.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Hoyt")
							{
								s.totG = s.totG + 1;
								s.Tscore = s.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > s.Hscore)
								{
									s.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Martin")
							{
								t.totG = t.totG + 1;
								t.Tscore = t.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > t.Hscore)
								{
									t.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Ripley")
							{
								u.totG = u.totG + 1;
								u.Tscore = u.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > u.Hscore)
								{
									u.Hscore = playerChars.get(f).Bank;
								}
							}
							if(playerChars.get(f).Name == "Jordi")
							{
								v.totG = v.totG + 1;
								v.Tscore = v.Tscore + playerChars.get(f).Bank;
								if(playerChars.get(f).Bank > v.Hscore)
								{
									v.Hscore = playerChars.get(f).Bank;
								}
							}
							
							
						}
						//set up winner display based on who has the most money in the bank
						if(players == 2)
						{
							if(playerChars.get(0).Bank >= playerChars.get(1).Bank)
							{
								if(playerChars.get(0).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(0).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(0).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(0).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(0).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(0).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(0).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(0).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(0).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(0).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(0).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(0).PlayerNumber;
							}
							else
							{
								if(playerChars.get(1).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(1).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(1).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(1).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(1).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(1).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(1).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(1).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(1).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(1).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(1).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(1).PlayerNumber;
							}
							
							Turn.setBounds(700, 300, 500, 400);
							Turn.setOpaque(true);
							Turn.setBorder(new LineBorder(Color.WHITE, 2, true));
							Turn.setBackground(Color.BLACK);
							Turn.setText("<html>" + playerChars.get(0).Name + "<br>" + " Red Buildings Owned " + playerChars.get(0).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(0).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(0).GreenOwned + "<br>" + " Total Bank " + playerChars.get(0).Bank
							+ "<br>" + playerChars.get(1).Name + "<br>" + " Red Buildings Owned " + playerChars.get(1).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(1).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(1).GreenOwned + "<br>" + " Total Bank " + playerChars.get(1).Bank);
						}
						if(players == 3)
						{
							if(playerChars.get(0).Bank >= playerChars.get(1).Bank && playerChars.get(0).Bank >= playerChars.get(2).Bank)
							{
								if(playerChars.get(0).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(0).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(0).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(0).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(0).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(0).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(0).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(0).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(0).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(0).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(0).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(0).PlayerNumber;
								
							}
							if(playerChars.get(1).Bank >= playerChars.get(0).Bank && playerChars.get(1).Bank >= playerChars.get(2).Bank)
							{
								if(playerChars.get(1).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(1).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(1).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(1).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(1).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(1).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(1).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(1).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(1).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(1).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(1).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(1).PlayerNumber;
							}
							if(playerChars.get(2).Bank >= playerChars.get(0).Bank && playerChars.get(2).Bank >= playerChars.get(1).Bank)
							{
								if(playerChars.get(2).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(2).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(2).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(2).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(2).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(2).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(2).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(2).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(2).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(2).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(2).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(2).PlayerNumber;
							}
							Turn.setBounds(700, 300, 550, 450);
							Turn.setOpaque(true);
							Turn.setBorder(new LineBorder(Color.WHITE, 2, true));
							Turn.setBackground(Color.BLACK);
							Turn.setText("<html>" + playerChars.get(0).Name + "<br>" + " Red Buildings Owned " + playerChars.get(0).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(0).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(0).GreenOwned + "<br>" + " Total Bank " + playerChars.get(0).Bank
							+ "<br>" + playerChars.get(1).Name + "<br>" + " Red Buildings Owned " + playerChars.get(1).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(1).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(1).GreenOwned + "<br>" + " Total Bank " + playerChars.get(1).Bank
							+ "<br>" + playerChars.get(2).Name + "<br>" + " Red Buildings Owned " + playerChars.get(2).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(2).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(2).GreenOwned + "<br>" + " Total Bank " + playerChars.get(1).Bank);
						}
						if(players == 4)
						{
							if(playerChars.get(0).Bank >= playerChars.get(1).Bank && playerChars.get(0).Bank >= playerChars.get(2).Bank && playerChars.get(0).Bank >= playerChars.get(3).Bank)
							{
								if(playerChars.get(0).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(0).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(0).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(0).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(0).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(0).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(0).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(0).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(0).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(0).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(0).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(0).PlayerNumber;
							}
							if(playerChars.get(1).Bank >= playerChars.get(0).Bank && playerChars.get(1).Bank >= playerChars.get(2).Bank && playerChars.get(1).Bank >= playerChars.get(3).Bank)
							{
								if(playerChars.get(1).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(1).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(1).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(1).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(1).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(1).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(1).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(1).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(1).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(1).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(1).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(1).PlayerNumber;
							}
							if(playerChars.get(2).Bank >= playerChars.get(0).Bank && playerChars.get(2).Bank >= playerChars.get(1).Bank && playerChars.get(2).Bank >= playerChars.get(3).Bank)
							{
								if(playerChars.get(2).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(2).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(2).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(2).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(2).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(2).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(2).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(2).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(2).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(2).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(2).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(2).PlayerNumber;
							}
							if(playerChars.get(3).Bank >= playerChars.get(0).Bank && playerChars.get(3).Bank >= playerChars.get(1).Bank && playerChars.get(3).Bank >= playerChars.get(2).Bank)
							{
								if(playerChars.get(3).Name == "Scotty")
									m.wins = m.wins + 1;
								if(playerChars.get(3).Name == "Burke")
									n.wins = n.wins + 1;
								if(playerChars.get(3).Name == "Han")
									o.wins = o.wins + 1;
								if(playerChars.get(3).Name == "Malcolm")
									p.wins = p.wins + 1;
								if(playerChars.get(3).Name == "Siri")
									q.wins = q.wins + 1;
								if(playerChars.get(3).Name == "Leia")
									r.wins = r.wins + 1;
								if(playerChars.get(3).Name == "Hoyt")
									s.wins = s.wins + 1;
								if(playerChars.get(3).Name == "Martin")
									t.wins = t.wins + 1;
								if(playerChars.get(3).Name == "Ripley")
									u.wins = u.wins + 1;
								if(playerChars.get(3).Name == "Jordi")
									v.wins = v.wins + 1;
								Winner.setVisible(true);
								Winner.setText(playerChars.get(3).Name + " Is The Winner!");
								WinnerNumber = playerChars.get(3).PlayerNumber;
							}
							Turn.setBounds(700, 300, 550, 700);
							Turn.setOpaque(true);
							Turn.setBorder(new LineBorder(Color.WHITE, 2, true));
							Turn.setBackground(Color.BLACK);
							Turn.setText("<html>" + playerChars.get(0).Name + "<br>" + " Red Buildings Owned " + playerChars.get(0).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(0).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(0).GreenOwned + "<br>" + " Total Bank " + playerChars.get(0).Bank
							+ "<br>" + playerChars.get(1).Name + "<br>" + " Red Buildings Owned " + playerChars.get(1).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(1).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(1).GreenOwned + "<br>" + " Total Bank " + playerChars.get(1).Bank
							+ "<br>" + playerChars.get(2).Name + "<br>" + " Red Buildings Owned " + playerChars.get(2).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(2).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(2).GreenOwned + "<br>" + " Total Bank " + playerChars.get(2).Bank
							+ "<br>" + playerChars.get(3).Name + "<br>" + " Red Buildings Owned " + playerChars.get(3).RedOwned + "<br>" + " Blue Buildings Owned " + playerChars.get(3).BlueOwned + "<br>" + " Green Building Owned " + playerChars.get(3).GreenOwned + "<br>" + " Total Bank " + playerChars.get(3).Bank);
						}
						
						try{
							//System.out.println("Got this far");
							//Get connection to DB
							Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/slumlord?&useSSL=false", "Mike" , "********");
							
							//2. create statement
							Statement myStmt = myConn.createStatement();
							
							//UPDATE slumlord.stats SET TotalGames = '2',Wins = '1',HighestScore = '100', TotalScore = '100' where idStats = '1';
							//3. Execute SQL query
							String sql = "UPDATE slumlord.stats SET TotalGames = '"
									+ m.totG +"',Wins ='" + m.wins +"',HighestScore = '" + m.Hscore +"', TotalScore = '" + m.Tscore + "' WHERE idStats = '1'";
							String sqla = "UPDATE slumlord.stats SET TotalGames = '"
									+ n.totG +"',Wins ='" + n.wins +"',HighestScore = '" + n.Hscore +"', TotalScore = '" + n.Tscore + "' WHERE idStats = '2'";
							String sqlb = "UPDATE slumlord.stats SET TotalGames = '"
									+ o.totG +"',Wins ='" + o.wins +"',HighestScore = '" + o.Hscore +"', TotalScore = '" + o.Tscore + "' WHERE idStats = '3'";
							String sqlc = "UPDATE slumlord.stats SET TotalGames = '"
									+ p.totG +"',Wins ='" + p.wins +"',HighestScore = '" + p.Hscore +"', TotalScore = '" + p.Tscore + "' WHERE idStats = '4'";
							String sqld = "UPDATE slumlord.stats SET TotalGames = '"
									+ q.totG +"',Wins ='" + q.wins +"',HighestScore = '" + q.Hscore +"', TotalScore = '" + q.Tscore + "' WHERE idStats = '5'";
							String sqle = "UPDATE slumlord.stats SET TotalGames = '"
									+ r.totG +"',Wins ='" + r.wins +"',HighestScore = '" + r.Hscore +"', TotalScore = '" + r.Tscore + "' WHERE idStats = '6'";
							String sqlf = "UPDATE slumlord.stats SET TotalGames = '"
									+ s.totG +"',Wins ='" + s.wins +"',HighestScore = '" + s.Hscore +"', TotalScore = '" + s.Tscore + "' WHERE idStats = '7'";
							String sqlg = "UPDATE slumlord.stats SET TotalGames = '"
									+ t.totG +"',Wins ='" + t.wins +"',HighestScore = '" + t.Hscore +"', TotalScore = '" + t.Tscore + "' WHERE idStats = '8'";
							String sqlh = "UPDATE slumlord.stats SET TotalGames = '"
									+ u.totG +"',Wins ='" + u.wins +"',HighestScore = '" + u.Hscore +"', TotalScore = '" + u.Tscore + "' WHERE idStats = '9'";
							String sqli = "UPDATE slumlord.stats SET TotalGames = '"
									+ v.totG +"',Wins ='" + v.wins +"',HighestScore = '" + v.Hscore +"', TotalScore = '" + v.Tscore + "' WHERE idStats = '10'";
							
							myStmt.executeUpdate(sql);
							myStmt.executeUpdate(sqla);
							myStmt.executeUpdate(sqlb);
							myStmt.executeUpdate(sqlc);
							myStmt.executeUpdate(sqld);
							myStmt.executeUpdate(sqle);
							myStmt.executeUpdate(sqlf);
							myStmt.executeUpdate(sqlg);
							myStmt.executeUpdate(sqlh);
							myStmt.executeUpdate(sqli);
							statsDisp.setText("<html><table border=1>"
									+"<tr>"
									+"<th>Name</th>"
									+"<th>Games Played</th>"
									+"<th>Wins</th>"
									+"<th>High Score</th>"
									+"<th>Total Score</th>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ m.name +"</td>"
									+"<td>" + m.totG + "</td>"
									+"<td>" + m.wins + "</td>"
									+"<td>" + m.Hscore + "</td>"
									+"<td>" + m.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ n.name +"</td>"
									+"<td>" + n.totG + "</td>"
									+"<td>" + n.wins + "</td>"
									+"<td>" + n.Hscore + "</td>"
									+"<td>" + n.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ o.name +"</td>"
									+"<td>" + o.totG + "</td>"
									+"<td>" + o.wins + "</td>"
									+"<td>" + o.Hscore + "</td>"
									+"<td>" + o.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ p.name +"</td>"
									+"<td>" + p.totG + "</td>"
									+"<td>" + p.wins + "</td>"
									+"<td>" + p.Hscore + "</td>"
									+"<td>" + p.Tscore + "</td>"
									+"</tr>"
									+"<tr>" 
									+"<td>"+ q.name +"</td>"
									+"<td>" + q.totG + "</td>"
									+"<td>" + q.wins + "</td>"
									+"<td>" + q.Hscore + "</td>"
									+"<td>" + q.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ r.name +"</td>"
									+"<td>" + r.totG + "</td>"
									+"<td>" + r.wins + "</td>"
									+"<td>" + r.Hscore + "</td>"
									+"<td>" + r.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ s.name +"</td>"
									+"<td>" + s.totG + "</td>"
									+"<td>" + s.wins + "</td>"
									+"<td>" + s.Hscore + "</td>"
									+"<td>" + s.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ t.name +"</td>"
									+"<td>" + t.totG + "</td>"
									+"<td>" + t.wins + "</td>"
									+"<td>" + t.Hscore + "</td>"
									+"<td>" + t.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ u.name +"</td>"
									+"<td>" + u.totG + "</td>"
									+"<td>" + u.wins + "</td>"
									+"<td>" + u.Hscore + "</td>"
									+"<td>" + u.Tscore + "</td>"
									+"</tr>"
									+"<tr>"
									+"<td>"+ v.name +"</td>"
									+"<td>" + v.totG + "</td>"
									+"<td>" + v.wins + "</td>"
									+"<td>" + v.Hscore + "</td>"
									+"<td>" + v.Tscore + "</td>"
									+"</tr> </html");
							statsDisp.setBounds(1290, 710, 606, 348);
							statsDisp.setVisible(true);
							
						}
						catch (Exception exc){
							exc.printStackTrace();
							
						}
						try{
							//Get connection to DB
							Connection myConn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/slumlord?&useSSL=false", "Mike" , "***********");
							
							//2. create statement
							Statement myStmt1 = myConn1.createStatement();
							
							String sql = "UPDATE slumlord.gamertag SET GamesWon = GamesWon + 1 WHERE GamerTag = '" + tagList[WinnerNumber] + "'";
										
						myStmt1.executeUpdate(sql);
						}
						catch (Exception exc){
							exc.printStackTrace();
						}
							
					}
				}
			}
		});
		btnEndTurn.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 24));
		btnEndTurn.setBounds(396, 814, 176, 23);
		frmSlumlord.getContentPane().add(btnEndTurn);
		
		
		
		statsDisp.setBounds(635, 659, 606, 348);
		frmSlumlord.getContentPane().add(statsDisp);
		
		//action for total rounds
		
		btnTotalRounds6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setVisible(true);
				statsDisp.setVisible(true);
				btnSelectCharacter.setVisible(true);
				Turn.setVisible(true);
				Turn.setText("<html>Click a Character in the List then Click Select Character</html>");
				btnTotalRounds6.setVisible(false);
				btnTotalRounds12.setVisible(false);
				btnTotalRounds24.setVisible(false);
				totalRounds = 1;
			}
		});
		
		btnTotalRounds12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setVisible(true);
				statsDisp.setVisible(true);
				btnSelectCharacter.setVisible(true);
				Turn.setVisible(true);
				Turn.setText("<html>Click a Character in the List then Click Select Character</html>");
				btnTotalRounds6.setVisible(false);
				btnTotalRounds12.setVisible(false);
				btnTotalRounds24.setVisible(false);
				totalRounds = 12;
			}
		});
		btnTotalRounds24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setVisible(true);
				statsDisp.setVisible(true);
				btnSelectCharacter.setVisible(true);
				Turn.setVisible(true);
				Turn.setText("<html>Click a Character in the List then Click Select Character</html>");
				btnTotalRounds6.setVisible(false);
				btnTotalRounds12.setVisible(false);
				btnTotalRounds24.setVisible(false);
				totalRounds = 24;
			}
		});
		
		
		
		
		JButton btnNewPlayer = new JButton("Player ID");
		btnNewPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i =0; i<players; i++)
				{
					String gamerTag = (String)JOptionPane.showInputDialog(
						frmSlumlord,
						"Enter Your Unique Gamer Tag",
						"GamerTag", JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						"");
					tagList[i] = gamerTag;
					try{
					
						//Get connection to DB
						Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/slumlord?&useSSL=false", "Mike" , "*****");
						
						//2. create statement
						Statement myStmt = myConn.createStatement();
						
						//UPDATE slumlord.stats SET TotalGames = '2',Wins = '1',HighestScore = '100', TotalScore = '100' where idStats = '1';
						//3. Execute SQL query
						ResultSet myRs = myStmt.executeQuery("SELECT * FROM slumlord.gamertag WHERE GamerTag = '" + tagList[i] + "'" );
						
						if (myRs.next() == true ) {
								Turn.setText("<html>Welcome Back " + (myRs.getString("GamerTag") + " You Have Played " + myRs.getInt("GamesPlayed") + " games. You have Won " + myRs.getInt("GamesWon") + " times!</html>"));
								Turn.setVisible(true);	
								
								try{
									//Get connection to DB
									Connection myConn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/slumlord?&useSSL=false", "Mike" , "********");
									
									//2. create statement
									Statement myStmt1 = myConn1.createStatement();
									
									String sql = "UPDATE slumlord.gamertag SET GamesPlayed = GamesPlayed + 1 WHERE GamerTag = '" + myRs.getString("GamerTag") + "'";
												
								myStmt1.executeUpdate(sql);
								}
								catch (Exception exc){
									exc.printStackTrace();
								}
						}
						else
						{
							try{
								//Get connection to DB
								Connection myConn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/slumlord?&useSSL=false", "Mike" , "********");
								
								//2. create statement
								Statement myStmt1 = myConn1.createStatement();
								
								String sql = "INSERT into gamertag" 
										+ " (GamerTag, GamesWon, GamesPlayed)"
										+ " values ('" + tagList[i] + "', '0', '1')";
								
							myStmt1.executeUpdate(sql);
							}
							catch (Exception exc){
								exc.printStackTrace();
							}
							
						Turn.setText("A New Player! Welcome");
						Turn.setVisible(true);
						}
					}
					catch (Exception exc){
						exc.printStackTrace();
					}		
			}
				
				btnTotalRounds6.setVisible(true);
				btnTotalRounds12.setVisible(true);
				btnTotalRounds24.setVisible(true);
				btnNewPlayer.setVisible(false);
			}
		});
		btnNewPlayer.setVisible(false);
		btnNewPlayer.setContentAreaFilled(false);
		btnNewPlayer.setBorderPainted(false);
		btnNewPlayer.setForeground(Color.WHITE);
		btnNewPlayer.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 34));
		btnNewPlayer.setBounds(48, 294, 219, 43);
		frmSlumlord.getContentPane().add(btnNewPlayer);
		
		
		
	
		//set background to galaxy game board
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(playSlumlord.class.getResource("/images/Background 1.jpg")));
		lblNewLabel.setBounds(0, 0, 1920, 1080);
		frmSlumlord.getContentPane().add(lblNewLabel);
		
		//actions for red tenant select
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if red tenant is available
				if(avaliable.RedTenant > 0 || playerChars.get(playersTurn).SkillNum == 3)
				{
					//set current tenant to 1(red)
					currentTenant = 1;
					//check how many properties the player owns and make appropriate dice selection available
					if(playerChars.get(playersTurn).AllOwned.size() >= 4)
					{
						btnDice4.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 5)
					{
						btnDice5.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 6)
					{
						btnDice6.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 7)
					{
						btnDice7.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 8)
					{
						btnDice8.setVisible(true);
					}
					btnDice3.setVisible(true);
					btnRed.setVisible(false);
					btnBlue.setVisible(false);
					btnGreen.setVisible(false);
					Turn.setText("<html>Select How Many Dice to Use</html>");
				}
				//if none of those tenants are available, update player
				else
				{
					Turn.setText("Sorry No Red Tenants Avaliable");
				}
			}
		});
		//actions for blue tenant select
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if Blue tenant is available
				if(avaliable.BlueTenant > 0 || playerChars.get(playersTurn).SkillNum == 3)
				{
					//set current tenant to 2(blue)
					currentTenant = 2;
					//check how many properties the player owns and make appropriate dice selection available
					if(playerChars.get(playersTurn).AllOwned.size() >= 4)
					{
						btnDice4.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 5)
					{
						btnDice5.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 6)
					{
						btnDice6.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 7)
					{
						btnDice7.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 8)
					{
						btnDice8.setVisible(true);
					}
					btnDice3.setVisible(true);
					btnRed.setVisible(false);
					btnBlue.setVisible(false);
					btnGreen.setVisible(false);
				}
				else
				{
					//if none of those tenants are available, update player
					Turn.setText("Sorry No Blue Tenants Avaliable");
				}	
			}
		});
		
		//actions for green tenant button
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if Green tenant is available
				if(avaliable.GreenTenant > 0 || playerChars.get(playersTurn).SkillNum == 3)
				{
					//set current tenant to 3(green)
					currentTenant = 3;
					//check how many properties the player owns and make appropriate dice selection available
					if(playerChars.get(playersTurn).AllOwned.size() >= 4)
					{
						btnDice4.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 5)
					{
						btnDice5.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 6)
					{
						btnDice6.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 7)
					{
						btnDice7.setVisible(true);
					}
					if(playerChars.get(playersTurn).AllOwned.size() >= 8)
					{
						btnDice8.setVisible(true);
					}
					btnDice3.setVisible(true);
					btnRed.setVisible(false);
					btnBlue.setVisible(false);
					btnGreen.setVisible(false);
				}
				else
				{
					//if none of those tenants are available, update player
					Turn.setText("Sorry No Green Tenants Avaliable");
				}
			}
		});
		
		//actions for player selection button
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Sets players to 2
				players = 2;
				btnNewPlayer.setVisible(true);
				
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
				
			}
		});
		
		
		
		//actions for player selection button
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Sets players to 3
				players = 3;
				btnNewPlayer.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
				
			}
		});
		//actions for player selection button
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Sets players to 4
				players = 4;
				btnNewPlayer.setVisible(true);
				two.setVisible(false);
				three.setVisible(false);
				four.setVisible(false);
				
			}
		});
		
		//actions for round 0 next player button
		btnNextPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add to CSV file
				//generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", "0," ,playerChars.get(playersTurn).Name, ",Selected First Property","");
				//increments player and updates player card
				playersTurn++;
				characterCard.setText(playerChars.get(playersTurn).charCard());
				Turn.setText("<html>" + playerChars.get(playersTurn).Name + "'s Turn Pick Your First Property (Must be a Red Property)</html>");
				//sets start round one when all players have selected
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
					//adds selected character to array 
					int j = playerChars.size();
					character p = list.getSelectedValue();
					playerChars.add(p);
					Turn.setText("<html>Next Player Click a Character in the List then Click Select Character</html>");
					//once all players have chosen a character initialize game board
					if(j == players -1)
					{
						statsDisp.setVisible(false);
						btnSelectCharacter.setVisible(false);
						lblWelcomeToSlumlord.setVisible(false);
						btnRules.setVisible(false);
						btnStartGame.setVisible(false);
						list.setVisible(false);
						Turn.setVisible(true);
						btnRedpropone.setVisible(true);
						btnRedproptwo.setVisible(true);
						btnRedProp3.setVisible(true);
						btnRedProp4.setVisible(true);
						btnRedProp5.setVisible(true);
						btnRedProp6.setVisible(true);
						btnRedProp7.setVisible(true);
						btnRedProp8.setVisible(true);
						btnRedProp9.setVisible(true);
						btnRedProp10.setVisible(true);
						btnRedProp11.setVisible(true);
						btnRedProp12.setVisible(true);
						btnRedProp13.setVisible(true);
						btnRedProp14.setVisible(true);
						btnRedProp15.setVisible(true);
						btnRedProp16.setVisible(true);
						btnRedProp17.setVisible(true);
						btnRedProp18.setVisible(true);
						btnBlueProp1.setVisible(true);
						btnBlueProp2.setVisible(true);
						btnBlueProp3.setVisible(true);
						btnBlueProp4.setVisible(true);
						btnBlueProp5.setVisible(true);
						btnBlueProp6.setVisible(true);
						btnBlueProp7.setVisible(true);
						btnBlueProp8.setVisible(true);
						btnBlueProp9.setVisible(true);
						btnBlueProp10.setVisible(true);
						btnBlueProp11.setVisible(true);
						btnBlueProp12.setVisible(true);
						btnBlueProp13.setVisible(true);
						btnGreenProp1.setVisible(true);
						btnGreenProp2.setVisible(true);
						btnGreenProp3.setVisible(true);
						btnGreenProp4.setVisible(true);
						btnGreenProp5.setVisible(true);
						btnGreenProp6.setVisible(true);
						btnGreenProp7.setVisible(true);
						btnGreenProp8.setVisible(true);
						btnGreenProp9.setVisible(true);
						btnNextPlayer.setVisible(true);
						Turn.setText("<html>" + playerChars.get(playersTurn).Name + "'s Turn Pick Your First Property (Must be a Red Property)</html>");
						//set phase to 3 so players can select their first property before round one starts
						phase = 3;
						//create strings to use as player color identifiers
						String a = "Ivory";
						String b = "Yellow";
						String c = "LimeGreen";
						String d = "DarkBlue";
						
					
							//set selected characters to most visible colors	
							if(players == 2)
							{
								playerChars.get(0).PlayerColor = a;
								playerChars.get(1).PlayerColor = b;
							}
							if(players == 3)
							{
								playerChars.get(0).PlayerColor = a;
								playerChars.get(1).PlayerColor = b;
								playerChars.get(2).PlayerColor = c;
							}
								
							if(players == 4)
							{
								playerChars.get(0).PlayerColor = a;
								playerChars.get(1).PlayerColor = b;
								playerChars.get(2).PlayerColor = c;
								playerChars.get(3).PlayerColor = d;
							}
								
					}
					
			}
		});
		//Initializes board and starts round 1
		btnStartRoundOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roundS = "1";
				//set player turn to 0
				//generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", "0," ,playerChars.get(playersTurn).Name, ",Selected First Property","");
				playersTurn = 0;
				//iterates over all characters and gives them initial 500 to start game with
				for( int i = 0; i < players; i++)
				{
					playerChars.get(i).Bank = playerChars.get(i).Bank + 500;
					playerChars.get(i).PlayerNumber = i;
				}
				//make labels and buttons visible
				statsDisp.setVisible(false);
				avaliableTenants.setVisible(true);
				btnSkipLeasing.setVisible(true);
				btnRed.setVisible(true);
				btnBlue.setVisible(true);
				btnGreen.setVisible(true);
				//randomly choose the first grouping of tenant tokens
				game = new tenantTokens();
				avaliable = game.RandomDraw(game, round, players);
				//display tenant tokens and character cards
				avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
				characterCard.setText(playerChars.get(playersTurn).charCard());
				Turn.setText("<html>"+ playerChars.get(playersTurn).Name + "'s Turn Pick a Tenant from the Avaliable Tenants </html>");
				btnStartRoundOne.setVisible(false);
				//change to phase 1(tenant move in) to start round 1
				phase = 1;
								
			}
		});
		btnNextPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//at the end of leasing round, next phase appears
				btnNextPhase.setVisible(false);
				diceResults.setVisible(false);
				btnDiscard.setVisible(false);
				btnSkipLeasing.setVisible(false);
				//tallies up money based on properties occupied and adds to bank
				int money = (playerChars.get(playersTurn).RedOccupied * playerChars.get(playersTurn).RentRed) + (playerChars.get(playersTurn).BlueOccupied * playerChars.get(playersTurn).RentBlue)
						+ (playerChars.get(playersTurn).GreenOccupied * playerChars.get(playersTurn).RentGreen);
				//set variables to count how many of each cluster of properties is owned
				int ones = 0;
				int twos = 0;
				int threes = 0;
				int sevens = 0;
				int eights = 0;
				int nines = 0;
				int elevens = 0;
				int twelves = 0;
				int thirteens = 0;
				
				//for all players
				for(int i = 0; i < playerChars.get(playersTurn).AllOwned.size(); i++)
				{	//iterate through all properties and recored how many of each cluster(neighborhood) is owned
					int r = playerChars.get(playersTurn).AllOwned.get(i).Hood;
					if( r == 1)
						ones = ones +1;
					if( r == 2)
						twos = twos +1;
					if( r == 3)
						threes = threes +1;
					if( r == 7)
						sevens = sevens +1;
					if( r == 8)
						eights = eights +1;
					if( r == 9)
						nines = nines +1;
					if( r == 11)
						elevens = elevens +1;
					if( r == 12)
						twelves = twelves +1;
					if( r == 13)
						thirteens = thirteens +1;
				}
				//if all 5 red, 4 blue, 3 green of each hood is owned, add a bonus money to bank
				if(ones == 5)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 400;
				if(twos == 5)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 400;
				if(threes== 5)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 400;
				if(sevens == 4)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 600;
				if(eights == 4)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 600;
				if(nines == 4)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 600;
				if(elevens == 3)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 1000;
				if(twelves == 3)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 1000;
				if(thirteens == 3)
					playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + 1000;
					
				//update character card display
				playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank + money;
				characterCard.setText(playerChars.get(playersTurn).charCard() );
				//set phase to 9 (blank phase) so nothing can be done to a property without first selecting an action
				phase = 9;
				//set option buttons to visible and update player text
				btnBuyProperty.setVisible(true);
				btnRepairProperty.setVisible(true);
				btnUpgradeProperty.setVisible(true);
				btnTakeBank.setVisible(false);
				btnEndTurn.setVisible(true);
				CardDisplay.setVisible(false);
				Turn.setText("<html>Click on the Buy, Repair, or Upgrade Button Then Click on the Property");
				
			}
		});
		//set dice number for tenant move in roll
		btnDice3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerChars.get(playersTurn).numDice = 3;
				btnDice3.setVisible(false);
				btnDice4.setVisible(false);
				btnDice5.setVisible(false);
				btnDice6.setVisible(false);
				btnDice7.setVisible(false);
				btnDice8.setVisible(false);
				Turn.setText("<html> Click the Property You Want to Move This Tenant Into</html>");
			}
		});
		//set dice number for tenant move in roll
		btnDice4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerChars.get(playersTurn).numDice = 4;
				btnDice3.setVisible(false);
				btnDice4.setVisible(false);
				btnDice5.setVisible(false);
				btnDice6.setVisible(false);
				btnDice7.setVisible(false);
				btnDice8.setVisible(false);
				Turn.setText("<html> Click the Property You Want to Move This Tenant Into</html>");
			}
		});
		//set dice number for tenant move in roll
		btnDice5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerChars.get(playersTurn).numDice = 5;
				btnDice3.setVisible(false);
				btnDice4.setVisible(false);
				btnDice5.setVisible(false);
				btnDice6.setVisible(false);
				btnDice7.setVisible(false);
				btnDice8.setVisible(false);
				Turn.setText("<html> Click the Property You Want to Move This Tenant Into</html>");
			}
		});
		//set dice number for tenant move in roll
		btnDice6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerChars.get(playersTurn).numDice = 6;
				btnDice3.setVisible(false);
				btnDice4.setVisible(false);
				btnDice5.setVisible(false);
				btnDice6.setVisible(false);
				btnDice7.setVisible(false);
				btnDice8.setVisible(false);
				Turn.setText("<html> Click the Property You Want to Move This Tenant Into</html>");
			}
		});
		//set dice number for tenant move in roll
		btnDice7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerChars.get(playersTurn).numDice = 7;
				btnDice3.setVisible(false);
				btnDice4.setVisible(false);
				btnDice5.setVisible(false);
				btnDice6.setVisible(false);
				btnDice7.setVisible(false);
				btnDice8.setVisible(false);
				Turn.setText("<html> Click the Property You Want to Move This Tenant Into</html>");
			}
		});
		//set dice number for tenant move in roll
		btnDice8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerChars.get(playersTurn).numDice = 8;
				btnDice3.setVisible(false);
				btnDice4.setVisible(false);
				btnDice5.setVisible(false);
				btnDice6.setVisible(false);
				btnDice7.setVisible(false);
				btnDice8.setVisible(false);
				Turn.setText("<html> Click the Property You Want to Move This Tenant Into</html>");
			}
		});
		//action for take bank button
		btnTakeBank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if a player has no property to damage and draws a repair card
				//subtract 200 from their bank
				playerChars.get(playersTurn).Bank = playerChars.get(playersTurn).Bank - 200;
				//update player card
				characterCard.setText(playerChars.get(playersTurn).charCard() );
				btnTakeBank.setVisible(false);
				btnDrawCard.setVisible(false);
				//subtract 1 from red dice/cards and update label
				currentRoll.Red = currentRoll.Red - 1;
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				//if cards/dice remain draw again
				if(currentRoll.Red > 0)
				{
					btnDrawCard.setVisible(true);
				}
				//if no red but blues are available, draw blue
				else if(currentRoll.Blue > 0)
				{
					btnDrawCard.setVisible(true);
					playerChars.get(playersTurn).DamageVar = -1;
				}
				//if neither are available move to next phase
				else
				{
					btnNextPhase.setVisible(true);
					Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
				}
			}
		});
	}
}










