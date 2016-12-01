import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*; 
import java.io.*;
import javax.imageio.*;
import java.awt.MouseInfo;
import java.util.ArrayList;

public class Stats extends JPanel implements ActionListener{
	
	Main main;
	Building b;
	JButton levelup;
	
	public Stats(Building b, Main m){
		
		main = m;
		this.b = b;
		setLayout(null);
		levelup = new JButton("level up");
		levelup.setSize(80,20);
		levelup.setLocation(0,70);
		if(b.level<3){
			add(levelup);
		}
		levelup.addActionListener(this);
		
	}
	
	public void paintComponent(Graphics g){
		Font f = new Font("Calibri", Font.PLAIN, 12);
		Font name = new Font("Calibri", Font.PLAIN, 10);
		g.setFont(f);
		g.setColor(Color.red);
		g.drawRect(0,0,80,95);
		g.drawString("Name: ",5,17);
		g.setFont(name);
		g.drawString("> "+b.name,5,29);
		g.setFont(f);
		g.drawString("Level: "+b.level,5,41);
		g.drawString("Attack: "+b.atk,5,53);
		g.drawString("Defense: "+b.defense,5,65);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt){
		System.out.println(evt.getActionCommand());
		b.levelUp();
	}
}