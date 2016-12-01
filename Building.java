import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*; 
import java.io.*;
import javax.imageio.*;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.awt.image.ImageObserver;

public class Building{
	
	GamePanel gp;
	String name;
	int x;
	int	y;
	int level; 
	int defense;
	int atk;
	boolean move = false;
	boolean build = false;
	Image pic;
	Rectangle pos;
	String error; //Requirement not met
	
	public Building(String n,int xpos,int ypos,int l,int d,int a,Image p, GamePanel g){
		name = n;
		x = xpos;
		y = ypos;
		level = l;
		defense = d;
		atk = a;
		pic = p;
		pos = new Rectangle(x,y,50,50);
		gp = g;
	}
	
	public void move(){
		if (move == true){
			move = false;
		}
		else{
			move = true;
		}
	}
	
	public void build(){
		if(move == false){
			if(build == true){
				build = false;
			}
			else{
				build = true;
			}
		}
	}
	
	public boolean requirements(){
		Building th = gp.buildingsList.get(0);
		if(name.equals("Town Hall")){ //If leveling up town hall.
			if(level == 1 && gp.gold>=300){
				return true;
			}
			else if(level == 2 && gp.gold>=1000){
				return true;
			}
			else{
				error = "You do not have enough Gold for this upgrade, it will cost 300g for level two and 1000g for level 3";
				return false;
			}
		}
		else{
			if(th.level<=level){
				error = "Town Hall not high enough level! Town Hall has to be level " + (level +=1) + " or higher";
				return false;
			}
			else{
					if(level == 1 && gp.gold>=300){
					return true;
				}
				else if(level == 2 && gp.gold>=1000){
					return true;
				}
				else{
					error = "You do not have enough Gold for this upgrade, it will cost 300g for level two and 1000g for level 3";
					return false;
					
				}
			}
		}
	}
	
	public void levelUp(){
		if(requirements()){
			if(level==1){
				level = 2;
				gp.gold-=300;
			}
			else if(level == 2){
				level = 3;
				gp.gold-=1000;
			}
			else{
				level = 3; 
			}
		}
		else{
			System.out.println(error);
		}
	}
	
	public String toString(){
		return ""+name;
	}
}