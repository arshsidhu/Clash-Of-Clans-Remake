//Main.java
//Arshdeep Sidhu
//This is the main class, this class initializes the GamePanel. It also has our frame counter which is essential to gaining food and gold

import java.awt.*; //imports
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*; 
import java.io.*;
import javax.imageio.*;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.*;

public class Main extends JFrame implements ActionListener{
	
	static GamePanel gp; //Game panel
	int frameCounter; //Used to keep a track of time
	javax.swing.Timer myTimer; //Timer
		
	public Main(){
		super("Clash of Clans (Remake)"); //Game title
		setLayout(null);
		myTimer = new javax.swing.Timer(10,this); //Creates a timer
		gp = new GamePanel(this); //Creates the game panel
		gp.setSize(775,720); //Sets it size and location
		gp.setLocation(0,0);
		add(gp);
		setSize(775,720); //Sets origincal size
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[]args){
		Main m = new Main(); //Creates main, starting the game
	}
	
	public void start(){ //Starts the timer
		myTimer.start();
	}
		
	@Override
	public void actionPerformed(ActionEvent evt){ //Action performed
		frameCounter+=1; //keeps track of how many frames pass
		gp.repaint();
		if(frameCounter==500){ //If 500 frames pass
			gp.addFood(); //Adds food
			gp.addGold(); //Adds gold
			frameCounter=0; //Resets frame
		}
	}
}

//GamePanel.java
//Arshdeep Sidhu
//This is the game panel, where the real graphics go down. This panel is where the game takes place. 
class GamePanel extends JPanel implements MouseMotionListener, MouseListener{
	
	Main main; //Reference back to main
	Stats st; //Stats object, used to display building stats
	Attack target; //Attack object, used to attack another base
	Building currentStats; //Building currently on display
	
	int food; //Vital variables
	int gold = 1000 ; //Start you off with enough gold
	int overallAtk; 
	int overallDef = 500; //Starts you off with 500 because I gave you a wall
	
	javax.swing.Timer rTimer;
	ArrayList<Building>buildingsList; //List of buildings on your base
	ArrayList<Integer>armyList; //List of your army
	//---------modes--------
	int mode; 
	final int STANDBY = 0; 
	final int SELECT = 1;
	final int DRAG = 2;
	final int DROP = 3;
	//--------booleans-----------
	boolean click; //If clicked
	boolean released; //if mouse released
	boolean menuOpen; //If building menu is open
	boolean buildingStats; //If the stats object is visible
	boolean spawnMenu; //If the spawn menu is visible
	boolean atk1; //If you are attacking the first base
	boolean destroyed1; //if the first base is destroyed
	boolean atk2; //If you are attacking the second base
	boolean destroyed2; //If the second base is destroyed
	boolean firstbuild; //Checks if you built town hall first
	boolean showArmy; //If youre showing menu 
	//---------images-----------
	Image selected; //Selected image being moved
	Image background; 
	Image warriorPic; //Picture of warrior
	Image archerPic; // "         " archer
	Image wizardPic; // "         " wizard
	Image buildImage; //Build button pic
	Image blank; //Just a blank pic so i dont have to return null
	Image barracks; //Holds army
	Image armory; //Increases defense
	Image farm; //Provides food
	Image blacksmith; //Increases attack
	Image cannontower; //Increases def
	Image townhall; //Overall level
	Image trainingground; //Spawns soldiers
	Image university; //Allows for new spawning
	Image mine; //Provides gold
	Image attackButton; //Attack other
	Image armyDisplay; //Army view button;
	
	Image buildings[] = new Image[7]; //List of the buildings you can build
	//------------------ everything for the wall ----------------
	Image wall1;
	Image wall2;
	Image wall3;
	Image wall4;
	Image wall5;
	Image wall6;
	Image wall7;
	Image wall8;
	Image wall9;
	Image wall10;

	Image walls[] = new Image[10];
	//-------------------------------------------------------------
	
	//--------Shapes------------
	Polygon base; //The base
	ArrayList<Integer>gridpointsx; //The points on the base
	ArrayList<Integer>gridpointsy;
	Rectangle buildButton; 
	Rectangle choice1; //building choice 1
	Rectangle choice2; //building choice 2
	Rectangle choice3;
	Rectangle choice4;
	Rectangle choice5;
	Rectangle choice6;
	Rectangle warrior; //Spawning the warrior button
	Rectangle archer; //Spawing the archer button
	Rectangle wizard;
	Rectangle attack1; //Attacking the first base button
	Rectangle attack2;
	Rectangle spawnButton; //spawing button
	Rectangle armyButton; //army button
	//-------- Mouse pos---------------
	int mx,my; 
	Point mpos; 
		
	String consoleMSG; //string for console 

	public GamePanel(Main m){
	
		main = m;
		addMouseMotionListener(this);
		addMouseListener(this);
		// ----- setting booleans ----- 
		menuOpen = false;
		firstbuild=false;
		buildingStats = false;
		showArmy = false;
		gridpointsx = new ArrayList<Integer>();
		gridpointsy = new ArrayList<Integer>();
		buildingsList = new ArrayList<Building>();
		armyList = new ArrayList<Integer>();
		getPoints();
		//------------------Shapes-------------------
		int xPoly[] = {90, 370, 650, 370};
        int yPoly[] = {410, 550, 410, 270};
        base = new Polygon(xPoly, yPoly, xPoly.length);
        buildButton = new Rectangle(687,110,80,80);
        armyButton = new Rectangle(687,200,80,80);
        choice1 = new Rectangle(680,200,65,60);
        choice2 = new Rectangle(680,280,65,60);
        choice3 = new Rectangle(680,360,65,60);
        choice4 = new Rectangle(680,440,65,60);
        choice5 = new Rectangle(680,520,65,60);
        choice6 = new Rectangle(680,600,65,60);
        warrior = new Rectangle(675,335,80,80);
        archer = new Rectangle(675,420,80,80);
        wizard = new Rectangle(675,505,80,80);
        spawnButton = new Rectangle(675,310,80,20);
        //-----------------Images---------------------- 
        background = new ImageIcon("background1.png").getImage();
        buildImage = new ImageIcon("build.png").getImage();
        blank = new ImageIcon("blank.png").getImage();
        barracks = new ImageIcon("test.png").getImage();
        armory = new ImageIcon("ARMORY_STRUCTURE.png").getImage();
		farm = new ImageIcon("FARM_STRUCTURE.png").getImage();
		blacksmith = new ImageIcon("BLACKSMITH_STRUCTURE.png").getImage();
		cannontower = new ImageIcon("CANNON_TOWER_STRUCTURE.png").getImage();
		townhall = new ImageIcon("TOWNCENTER_STRUCTURE.png").getImage();
		trainingground = new ImageIcon("TRAINING_GROUND_STRUCTURE.png").getImage();
		university = new ImageIcon("UNIVERSITY_STRUCTURE.png").getImage();
		mine = new ImageIcon("REFINERY_STRUCTURE.png").getImage();
		attackButton = new ImageIcon("Troop-attack-1.png").getImage();
		warriorPic  = new ImageIcon("warrior.png").getImage();
		archerPic  = new ImageIcon("archer.png").getImage();
		wizardPic  = new ImageIcon("wizard.png").getImage();
		armyDisplay = new ImageIcon("army.png").getImage();
		attack1 = new Rectangle(30,112,attackButton.getWidth(this),attackButton.getHeight(this));
		attack2 = new Rectangle(315,95,attackButton.getWidth(this),attackButton.getHeight(this));
		destroyed1 = false;
		destroyed2 = false;
		buildings[0]=townhall;
		buildings[1]=farm;
		buildings[2]=mine;
		buildings[3]=trainingground; 
		buildings[4]=barracks;
		buildings[5]=armory;
		buildings[6]=cannontower;
		
		//---------Load wall images ---------------------
		wall1 = new ImageIcon("wall_e.png").getImage();
		wall2 = new ImageIcon("wall_n.png").getImage();
		wall3 = new ImageIcon("wall_ne_sw.png").getImage();
		wall4 = new ImageIcon("wall_nw_se.png").getImage();
		wall5 = new ImageIcon("wall_o_1.png").getImage();
		wall6 = new ImageIcon("wall_o_2.png").getImage();
		wall7 = new ImageIcon("wall_o_3.png").getImage();
		wall8 = new ImageIcon("wall_o_4.png").getImage();
		wall9 = new ImageIcon("wall_s.png").getImage();
		wall10 = new ImageIcon("wall_w.png").getImage();		
		walls[0]=wall1;
		walls[1]=wall2;
		walls[2]=wall3;
		walls[3]=wall4;
		walls[4]=wall5;
		walls[5]=wall6;
		walls[6]=wall7;
		walls[7]=wall8;
		walls[8]=wall9;
		walls[9]=wall10;
		
		consoleMSG = "";
		
	}
	
	public void buildCheck(){
		if(buildButton.contains(mx,my)){
			mode = SELECT;
			menuOpen = (menuOpen == true) ? false : true;
		}
	}
	
	public void armyViewCheck(){
		if(armyButton.contains(mx,my)){
			showArmy = (menuOpen == true) ? false : true;
		}
	}
	
	public void spawnCheck(){
		if(buildingStats==true && currentStats.name.equals("Training Ground") && spawnButton.contains(mx,my)){
			spawnMenu = true;
			System.out.println(spawnMenu);
		}
	}
	
	public void attackCheck(){
		
		if(attack1.contains(mx,my) && destroyed1==false){
			atk1 =true;
		}
		else if(attack2.contains(mx,my) && destroyed2 == false){
			atk2 =true;
		}
	}
	
	public void attack(Rectangle a){
		if( a == attack1){
			target = new Attack("Mackenzie's Base",1500,1500,1000,1000,main,this);
			target.setVisible(true);
			target.setSize(200,200);
			target.setLocation(30,112);
			this.add(target);
		}
		else{
			target = new Attack("Alex's Base", 750,750,400,400,main,this);
			target.setVisible(true);
			target.setSize(200,200);
			target.setLocation(315,95);
			this.add(target);
		}
	}
	
	public void spawn(){
		if(warrior.contains(mx,my) && (currentStats.level==1 || currentStats.level==2 || currentStats.level == 3)){
			consoleMSG = "You spawned a Warrior";
			armyList.add(1);
			overallAtk+=25;
			food-=200;
		}
		else if(archer.contains(mx,my) && (currentStats.level==2 || currentStats.level == 3)){
			consoleMSG = "You spawned a Archer";
			armyList.add(2);
			overallAtk+=50;
			food-=300;
		}
		else if(wizard.contains(mx,my) && currentStats.level==3){
			consoleMSG = "You spawned a Wizard";
			armyList.add(3);
			overallAtk+=100;
			food-=400;
		}
	}
	
	public void build(Graphics g){
		if(firstbuild == false){
			selected = displayTownHall(g);
		}
		else{
			selected = displayBuildings(g);
		}
		
		mode = DRAG;
	}
	
	public Image displayTownHall(Graphics g){
		g.setColor(Color.red);
		g.drawPolygon(base);
        drawGrid(g);
        g.drawImage(buildings[0],680,200,this);
        g.drawString("Town Hall",680,280);
        
        if(choice1.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				
				return buildings[0];
			}
		}
		
		return blank;
	}
	
	public Image displayBuildings(Graphics g){
		g.setColor(Color.red);
		g.drawPolygon(base);
        drawGrid(g);
		g.drawString("Farm", 680,260);
		g.drawString("Mine", 680, 350);
		g.drawString("Training Ground", 670, 430);
		g.drawString("Barracks", 680, 512);
		g.drawString("Armory", 680, 590);
		g.drawString("Cannon Tower", 615, 675);

		int x = 680;
		int y = 200;
		for(int i=1;i<buildings.length;i++){
			g.drawImage(buildings[i],x,y,this);
			y+=80;
		}
		//----------Collides-------------
		if(choice1.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				return buildings[1];
			}
		}
		if(choice2.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				return buildings[2];
			}
		}
		if(choice3.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				return buildings[3];
			}
		}
		if(choice4.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				return buildings[4];
			}
		}
		if(choice5.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				return buildings[5];
			}
		}
		if(choice6.contains(mx,my)){
			if(click == true){
				menuOpen = false;
				return buildings[6];
			}
		}
		//----------------------------
		return blank;
	}
	
	public void drawBuildings(Graphics g){
		if(mode == DRAG){
			g.drawPolygon(base);
        	drawGrid(g);
			g.drawImage(selected,mx-35,my-35,this);
			if(base.contains(mx, my) && released == true){
				if(selected == buildings[0]){
					Building tmp = new Building("Town Hall", mx-35,my-35,1,0,0,selected,this);
					consoleMSG = "You built a Town Hall";
					gold -= 500;
					firstbuild = true;
					buildingsList.add(tmp);
					
				}
				if(selected == buildings[1]){
					Building tmp = new Building("Farm", mx-35,my-35,1,10,10,selected,this);
					consoleMSG = "You built a Farm";
					gold -= 250;
					buildingsList.add(tmp);
					overallDef +=10;
					overallAtk +=10;				
				}
				if(selected == buildings[2]){
					Building tmp = new Building("Mine", mx-35,my-35,1,10,10,selected,this);
					consoleMSG = "You built a Mine";
					gold -= 250;
					buildingsList.add(tmp);
					overallAtk+=10;
					overallDef+=10;
				}
				if(selected == buildings[3]){
					Building tmp = new Building("Training Ground", mx-35,my-35,1,0,0,selected,this);
					consoleMSG = "You built a Training Ground";
					gold -= 400;
					buildingsList.add(tmp);
					
				}
				if(selected == buildings[4]){
					gold -= 400;
					Building tmp = new Building("Barracks", mx-35,my-35,1,60,0,selected,this);
					consoleMSG = "You built a Barracks";
					buildingsList.add(tmp);
					overallDef +=60;
					
				}
				if(selected == buildings[5]){
					gold -= 600;
					Building tmp = new Building("Armory", mx-35,my-35,1,60,0,selected,this);
					consoleMSG = "You built a Armory";
					buildingsList.add(tmp);
					overallDef +=60;
				}
				if(selected == buildings[6]){
					gold -= 700;
					Building tmp = new Building("Cannon Tower", mx-35,my-35,1,100,0,selected,this);
					consoleMSG = "You built a Cannon Tower";
					buildingsList.add(tmp);
					overallDef +=100;
				}
				mode = STANDBY;
			}
			if(released == true){
				selected = null;
			}
		}
	}
	
	public void getPoints(){
		int x = 330;
		int y = 250;
		int x2 = 0;
		int y2 = 0;
		for(int i=0;i<8;i++){
			x+=40;
			y+=20;
			x2 = x;
			y2 = y;
			for(int j=0;j<8;j++){
				gridpointsx.add(x2);
				gridpointsy.add(y2);
				x2-=40;
				y2+=20;
			}
		}
	}
	
//	public Point snap(int x, int y){
//		if(gridpointsx.contains(x)&& gridpointsy.contains(y) && gridpointsx.indexOf(x)==gridpointsy.indexOf(y)){
//			Point tmp = new Point(x,y);
//			return tmp;
//		}
//		else{
//			int diffx =20;
//			int diffy =20;
//			int selectedx =0;
//			int selectedy =0;
//			for(int i=0;i<gridpointsx.size();i++){
//				for(int j=0;j<gridpointsy.size();j++){
//					if(diffx>=Math.abs(x-i) && diffy>=Math.abs(y-j) && gridpointsx.indexOf(i)==gridpointsy.indexOf(j)){
//						diffx = Math.abs(x-i);
//						diffy = Math.abs(y-j);
//						selectedx = gridpointsx.get(i);
//						selectedy = gridpointsy.get(j);
//					}
//				}
//			}
//			Point tmp = new Point(selectedx,selectedy);
//			return tmp;
//		}
//	}
	
	public void buildingPress(Building b, Graphics g){
		if(b.pos.contains(mx,my)){
			if(click == true){
//				g.setColor(Color.green);
//				g.fillRect(500,0,50,50);
				buildingStats = true;
				currentStats = b;
				statsDisplay(b);
			}
		}
	}
	
	public void statsDisplay(Building b){
		if(buildingStats == true){
			st = new Stats(b,main);
			st.setSize(100,100);
			st.setLocation(675,210);
			this.add(st);			
		}
	}
	
	public int buildingCount(ArrayList<Building>bList, String name){
		int counter = 0;
		for(Building b: bList){
			if(b.name.equals(name)){
				counter+=1;
				if(b.level == 2){
					counter+=1;
				}
				else if(b.level == 3){
					counter+=2;
				}
			}
		}
		return counter;
	}
	
	public void addFood(){
		int farmNum = buildingCount(buildingsList, "Farm");
		food += 10 * farmNum;
	}
	
	public void addGold(){
		int mineNum = buildingCount(buildingsList, "Mine");
		gold += 10 * mineNum;
	}
	
	
	public void addNotify(){
        super.addNotify();
        requestFocus();
        main.start();
    }
	
	public void drawGrid(Graphics g){
		int x =130;
		int y =430;
		while(y<540){
			g.drawLine(x,y,x+280,y-140);
			x+=40;
			y+=20;
		}
		
		int x2 = 130;
		int y2 = 390;
		while(y2>260){
			g.drawLine(x2,y2,x2+280,y2+140);
			x2+=40;
			y2-=20;
		}
	}
	
	public void paintComponent(Graphics g){
		Font f = new Font("Calibri", Font.PLAIN, 12);
		g.setFont(f);
		g.drawImage(background,0,0,this);
		
		g.drawImage(walls[1],325,245,this);
		g.drawImage(walls[3],272,275,this);
		g.drawImage(walls[3],220,300,this);
		g.drawImage(walls[3],166,328,this);
		g.drawImage(walls[3],140,340,this);
		g.drawImage(walls[2],380,275,this);
		g.drawImage(walls[2],415,295,this);
		g.drawImage(walls[2],450,315,this);		
		g.drawImage(walls[2],485,335,this);		
		g.drawImage(walls[0],540,360,this);
		g.drawImage(walls[3],522,405,this);
		g.drawImage(walls[3],471,430,this);
		g.drawImage(walls[3],418,458,this);
		g.drawImage(walls[3],400,467,this);
		g.drawImage(walls[9],90,360,this);
		g.drawImage(walls[2],108,405,this);
		g.drawImage(walls[2],161,432,this);
		g.drawImage(walls[2],215,460,this);
		g.drawImage(walls[2],234,469,this);
		g.drawImage(walls[8],315,490,this);
			
		g.fillRect(675,0,100,720);
		g.fillRect(0,650,775,120);
		
		g.setColor(Color.red);
		g.drawString("Gold: ", 680, 12);
		g.drawString("> "+gold, 695, 24);
		g.drawString("Food: ", 680, 36);
		g.drawString("> "+food, 695, 48);
		g.drawString("Attack: ", 680, 60);
		g.drawString("> "+overallAtk, 695, 72);
		g.drawString("Defense: ", 680, 84);
		g.drawString("> "+overallDef, 695, 96);
		
		g.drawString("Console: ", 15, 665);
		g.drawString(""+consoleMSG, 65, 665);
		
		g.drawImage(buildImage,680,110,this);
		g.drawString("Build",680,185);
		if(menuOpen == false && buildingStats == false){
			g.drawImage(armyDisplay,680,200,this);
			g.drawString("View Army",680,285);
			if(showArmy == true){
				int war=0;
				int arch=0;
				int wiz=0;
				for(int i: armyList){
					if(i==1){
						war+=1;
					}
					if(i==2){
						arch+=1;
					}
					if(i==3){
						wiz+=1;
					}
				}
				g.drawString("Your army: ", 680, 300);
				g.drawString("> "+war+" warriors", 690, 314);
				g.drawString("> "+arch+" archers", 690, 326);
				g.drawString("> "+wiz+" wizards", 690, 338);
			}
	
		}
		
		if(buildingStats == true && currentStats.name.equals("Training Ground")){
			g.setColor(Color.red);
			g.drawRect(675,310,80,20);
			g.drawString("Spawn Troops",678,325);
		}
		if(spawnMenu==true){
			g.setColor(Color.red);
			if(currentStats.level==1){
				g.drawRect(675,335,80,80);
				g.drawImage(warriorPic,675,335,this);
				g.drawString("Warrior",680,415);
			}
			else if(currentStats.level==2){
				g.drawRect(675,335,80,80);
				g.drawImage(warriorPic,675,335,this);
				g.drawString("Warrior",680,415);
	        	g.drawRect(675,420,80,80);
	        	g.drawImage(archerPic,675,420,this);
	        	g.drawString("Archer",680,500);
			}
			else if(currentStats.level==3){
				g.drawRect(675,335,80,80);
	        	g.drawImage(warriorPic,675,335,this);
	        	g.drawString("Warrior",680,415);
	        	g.drawRect(675,420,80,80);
	        	g.drawImage(archerPic,675,420,this);
	        	g.drawString("Archer",680,500);
	        	g.drawRect(675,505,80,80);
	        	g.drawImage(wizardPic,675,505,this);
	        	g.drawString("Wizard",680,585);
			}   
		}
		
		if(destroyed1==false){
			g.drawImage(attackButton,30,112,this);	
		}
		if(destroyed2==false){
			g.drawImage(attackButton,315,95,this);
		}
		
		
		if(click == true){
			System.out.println(mx+","+my);
		}

        //g.drawImage(attackButton,250,300,this);
        
        if(menuOpen == true){
        	build(g);
        }
        
        if(atk1 == true){
        	attack(attack1);
        }
        if(atk2 == true){
        	attack(attack2);
        }
        drawBuildings(g);
        for(Building b : buildingsList){
        	g.drawImage(b.pic, b.x,b.y,this);
        	buildingPress(b,g);
        }
	}
	
	//---------------------Mouse Listener-------------------
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){
		click = false; 
		released = true;
//		mode = SELECT;
	}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		mx = e.getX();
		my = e.getY(); 
		click = true;
		if(buildingStats == true && !spawnButton.contains(mx,my) && !warrior.contains(mx,my) && !archer.contains(mx,my) && !wizard.contains(mx,my)){
			buildingStats = false;
			spawnMenu = false;
			st.setVisible(false);
		}
		if(atk1 == true){
			atk1 = false;
			target.setVisible(false);
		}
		if(atk2 == true){
			atk2 = false;
			target.setVisible(false);
		}
//		mode = DRAG;
		buildCheck();
		spawnCheck();
		if(spawnMenu == true){
        	spawn();
        }
		attackCheck();
		armyViewCheck();
		
	}
	//---------------------Motion Listener-------------------
	public void mouseDragged(MouseEvent e){
		mx = e.getX(); 
		my = e.getY(); 
		mpos = e.getPoint();
	}
	
	public void mouseMoved(MouseEvent e){
		mx = e.getX(); 
		my = e.getY(); 
		mpos = e.getPoint(); 
		released = false;
	}

}
