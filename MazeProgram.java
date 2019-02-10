import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import sun.audio.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MazeProgram extends JPanel implements KeyListener,MouseListener
{
	JFrame frame;
	String[][] maze;
	int squaresize = 20;
	int playersize = 10;
	int x=346, y=346;
	int playerrow = 1, playercol = 0;
	int movecount = 0;
	int playerdir = 2;           //1 = up    2 = right    3 = down    4 = left

	public MazeProgram()
	{
		setBoard();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(2000,1800);
		frame.setVisible(true);
		frame.addKeyListener(this);
		this.addMouseListener(this);

		//MUSIC
		try {
        	AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("MazeMusic.wav"));
        	Clip musicClip = AudioSystem.getClip();
			musicClip.open(inputStream);
			musicClip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(10000); // looping as long as this thread is alive
		}
		catch (Exception e) {
			System.out.println("ERROR");
		}

	}
	public void paintComponent(Graphics g)
	{
		Color slightlyDarkerGray = new Color(100, 100, 100);
		super.paintComponent(g);
		g.setColor(Color.CYAN);	//this will set the background color
		g.fillRect(0,0,2000,1800);

		//other commands that might come in handy
		//g.setFont("Times New Roman",Font.PLAIN,18);
				//you can also use Font.BOLD, Font.ITALIC, Font.BOLD|Font.Italic
		//g.drawOval(x,y,10,10);
		//g.fillRect(x,y,100,100);
		//g.fillOval(x,y,10,10);

		int tempy = 321; //added 300
		for (int row = 0; row < maze.length; row ++) {
			int tempx = 341; //added 320

			for (int col = 0; col < maze[0].length; col ++) {
				g.setColor(Color.CYAN);
				g.drawRect(tempx, tempy, squaresize, squaresize);  //these lines draw the lines in between grid squares
				if (maze[row][col].equals("+") || maze[row][col].equals("-") || maze[row][col].equals("|")){    //if black sqare, set color
					g.setColor(Color.BLACK);
				}
				else if (maze[row][col].equals(" ")) {    //if white square, set color
					g.setColor(Color.WHITE);
				}
				else if (maze[row][col].equals("*") || maze[row][col].equals("%")) {
					g.setColor(Color.GREEN);
				}
				g.fillRect(tempx+1, tempy+1, squaresize-1, squaresize-1);   //draw grid square of color
				tempx += (squaresize);
			}
			tempy += (squaresize);
		}

		g.setColor(Color.BLACK);    //draw the guy
		g.drawRect(x,y,playersize,playersize);
		g.setColor(Color.RED);
		g.fillRect(x+1,y+1,playersize-1,playersize-1);

		g.drawString("Row: " + playerrow, 40, 500);
		g.drawString("Column: " + playercol, 40, 550);
		g.drawString("Move Count: " + movecount, 40, 600);
		g.drawString("Player Direction: " + playerdir, 40, 650);    //output direction with arrow pointing in direction
		g.drawString("Current: \"" + maze[playerrow][playercol] + "\"", 40, 800);
		g.drawString("Rows: " + maze.length, 200, 500);
		g.drawString("Cols: " + maze[0].length, 200, 550);
		switch (playerdir) {
			case 1:
				g.drawString("^", 150, 650);
				break;
			case 2:
				g.drawString(">", 150, 650);
				break;
			case 3:
				g.drawString("v", 150, 650);
				break;
			case 4:
				g.drawString("<", 150, 650);
				break;
			default:
				break;
		}

		if (maze[playerrow][playercol].equals("%")) {      //if reach finish, output congratulatory message
			g.drawString("You did it. Congratulations.", 300, 800);
		}

		/*g.drawLine(0,0,80,50);
		g.drawLine(80, 50, 80, 300);
		g.drawLine(80, 300, 0, 350);
		g.drawLine(0, 350, 0, 0);

		g.drawLine(80, 50, 120, 75);
		g.drawLine(120, 75, 120, 275);
		g.drawLine(120, 275, 80, 300);
		g.drawLine(80, 300, 80, 50);

		g.drawLine(120, 75, 200, 75);
		g.drawLine(200, 75, 200, 275);
		g.drawLine(200, 275, 120, 275);
		g.drawLine(120, 275, 120, 75);

		g.drawLine(200, 75, 240, 50);
		g.drawLine(240, 50, 240, 300);
		g.drawLine(240, 300, 200, 275);
		g.drawLine(200, 275, 200, 75);

		g.drawLine(240, 50, 320, 0);
		g.drawLine(320, 0, 320, 350);
		g.drawLine(320, 350, 240, 300);
		g.drawLine(240, 300, 240, 50);

		//--------------------------------

		g.drawLine(0, 0, 320, 0); //top closest

		g.drawLine(80, 50, 240, 50); //top farthest

		g.drawLine(0, 350, 320, 350);  //bottom closest

		g.drawLine(80, 300, 240, 300);  //bottom farthest*/



		//getting walls in arraylist
		ArrayList<Wall> walls = new ArrayList<Wall>();

		int temprow = playerrow;
		int tempcol = playercol;
		int xmove = 0;
		int ymove = 0;
		switch (playerdir) {
			case 1:  //up
			ymove = -1;
			break;

			case 2:  //right
			xmove = 1;
			break;

			case 3:  //down
			ymove = 1;
			break;

			case 4:  //left
			xmove = -1;
			break;

			default:
			break;
		}


		while(!(maze[playerrow][playercol].equals("%") && playerdir == 2) && !(maze[playerrow][playercol].equals("*") && playerdir == 4) && !maze[temprow+ymove][tempcol+xmove].equals("+") && !maze[temprow+ymove][tempcol+xmove].equals("%") && !maze[temprow+ymove][tempcol+xmove].equals("*")) {
			if (playerdir == 2) {//right
				if (maze[temprow-1][tempcol+xmove].equals("+")) {//wall to the left of me
					walls.add(new Wall("left", (tempcol-playercol)+1));
				}
				if (maze[temprow+1][tempcol+xmove].equals("+")) {//wall to the right of me
					walls.add(new Wall("right", (tempcol-playercol)+1));
				}
				walls.add(new Wall("top", Math.abs((tempcol-playercol))+1));
				walls.add(new Wall("bot", Math.abs((tempcol-playercol)+1)));
				if (maze[temprow-1][tempcol+xmove].equals(" ")) {//nothing to the left of me
					walls.add(new Wall("leftEmpty", (tempcol-playercol)+1));
				}
				if (maze[temprow+1][tempcol+xmove].equals(" ")) {//nothing to the right of me
					walls.add(new Wall("rightEmpty", (tempcol-playercol)+1));
				}
			}
			if (playerdir == 4) {//left
				if (maze[temprow-1][tempcol+xmove].equals("+")) {//wall to the right of me
					walls.add(new Wall("right", Math.abs(tempcol-playercol)+1));
				}
				if (maze[temprow+1][tempcol+xmove].equals("+")) {//wall to the left of me
					walls.add(new Wall("left", Math.abs(tempcol-playercol)+1));
				}
				walls.add(new Wall("top", Math.abs((tempcol-playercol))+1));
				walls.add(new Wall("bot", Math.abs((tempcol-playercol))+1));
				if (maze[temprow-1][tempcol+xmove].equals(" ")) {//nothing to the right of me
					walls.add(new Wall("rightEmpty", Math.abs(tempcol-playercol)+1));
				}
				if (maze[temprow+1][tempcol+xmove].equals(" ")) {//wall to the left of me
					walls.add(new Wall("leftEmpty", Math.abs(tempcol-playercol)+1));
				}
			}
			if (playerdir == 1) {//up
				if (maze[temprow+ymove][tempcol+1].equals("+")) {//wall to the right of me
					walls.add(new Wall("right", Math.abs(temprow-playerrow)+1));
				}
				if (maze[temprow+ymove][tempcol-1].equals("+")) {//wall to the left of me
					walls.add(new Wall("left", Math.abs(temprow-playerrow)+1));
				}
				walls.add(new Wall("top", Math.abs((temprow-playerrow))+1));
				walls.add(new Wall("bot", Math.abs((temprow-playerrow))+1));
				if (maze[temprow+ymove][tempcol+1].equals(" ")) {//nothing to the right of me
					walls.add(new Wall("rightEmpty", Math.abs(temprow-playerrow)+1));
				}
				if (maze[temprow+ymove][tempcol-1].equals(" ")) {//nothing to the left of me
					walls.add(new Wall("leftEmpty", Math.abs(temprow-playerrow)+1));
				}
			}
			if (playerdir == 3) {//down
				if (maze[temprow+ymove][tempcol+1].equals("+")) {//wall to the left of me
					walls.add(new Wall("left", (temprow-playerrow)+1));
				}
				if (maze[temprow+1][tempcol-1].equals("+")) {//wall to the right of me
					walls.add(new Wall("right", (temprow-playerrow)+1));
				}
				walls.add(new Wall("top", Math.abs((temprow-playerrow))+1));
				walls.add(new Wall("bot", Math.abs((temprow-playerrow))+1));
				if (maze[temprow+ymove][tempcol+1].equals(" ")) {//nothing to the left of me
					walls.add(new Wall("leftEmpty", (temprow-playerrow)+1));
				}
				if (maze[temprow+1][tempcol-1].equals(" ")) {//nothing to the right of me
					walls.add(new Wall("rightEmpty", (temprow-playerrow)+1));
				}
			}

			temprow += ymove;
			tempcol += xmove;

		}  //end while loop for adding walls


		//add the wall in front of me

		if (playerdir==1 || playerdir==3) {//up or down
			g.drawString("Temprow: " + temprow, 40, 700);
			g.drawString("Tempcol: " + tempcol, 40, 750);
			walls.add(new Wall("forward", Math.abs(temprow-playerrow)+1));
		}
		if (playerdir==2 || playerdir==4) {//right or left
			g.drawString("Temprow: " + temprow, 40, 700);
			g.drawString("Tempcol: " + tempcol, 40, 750);
			walls.add(new Wall("forward", Math.abs(tempcol-playercol)+1));
		}


		for (int i = 0; i < walls.size(); i ++) {   //output wall information in console for testing
			System.out.println(walls.get(i));
		}


		//ALL WALLS ARE IN ARRAYLIST
		boolean setGreen = false;

		try {
			if (maze[temprow+ymove][tempcol+xmove].equals("%")) {
				setGreen = true;
			}
		}
		catch (Exception e) {
			if (maze[playerrow][playercol].equals("%")) {    //if at the end or beginning looking off screen, throws error - if this happens, if its at end, make green
				setGreen = true;
			}
		}
		for (int i = 0; i < walls.size(); i ++) {
			if  (walls.get(i).getSide().equals("leftEmpty") || walls.get(i).getSide().equals("rightEmpty")) {
				g.setColor(slightlyDarkerGray);  //top triangles
				//g.setColor(Color.GRAY);
				int [] xWallsTop = walls.get(i).getTriangleXTop();
				int [] yWallsTop = walls.get(i).getTriangleYTop();
				g.fillPolygon(xWallsTop, yWallsTop, 3);
				g.setColor(Color.BLACK);
				g.drawPolygon(xWallsTop, yWallsTop, 3);

				g.setColor(slightlyDarkerGray);     //bot triangles
				//g.setColor(Color.GRAY);
				int [] xWallsBot = walls.get(i).getTriangleXBot();
				int [] yWallsBot = walls.get(i).getTriangleYBot();
				g.fillPolygon(xWallsBot, yWallsBot, 3);
				g.setColor(Color.BLACK);
				g.drawPolygon(xWallsBot, yWallsBot, 3);

				//g.setColor(slightlyDarkerGray);
				g.setColor(Color.GRAY);
				int [] xWallsMid = {walls.get(i).getTopLeftX(), walls.get(i).getTopRightX(), walls.get(i).getBotRightX(), walls.get(i).getBotLeftX()};
				int [] yWallsMid = {walls.get(i).getTopLeftY(), walls.get(i).getTopRightY(), walls.get(i).getBotRightY(), walls.get(i).getBotLeftY()};
				g.fillPolygon(xWallsMid, yWallsMid, 4);
				g.setColor(Color.BLACK);
				g.drawPolygon(xWallsMid, yWallsMid, 4);
				g.setColor(Color.GRAY);
			}

			else {
				g.setColor(Color.GRAY);
				if (setGreen && i == walls.size()-1) {    //set the final end spot as a green wall
					g.setColor(Color.GREEN);
				}
				int [] xWalls = {walls.get(i).getTopLeftX(), walls.get(i).getTopRightX(), walls.get(i).getBotRightX(), walls.get(i).getBotLeftX()};
				int [] yWalls = {walls.get(i).getTopLeftY(), walls.get(i).getTopRightY(), walls.get(i).getBotRightY(), walls.get(i).getBotLeftY()};
				g.fillPolygon(xWalls, yWalls, 4);
				g.setColor(Color.BLACK);
				g.drawPolygon(xWalls, yWalls, 4);
				g.setColor(Color.GRAY);

				if (setGreen && i==walls.size()-1) {
					g.setColor(Color.GREEN);
				}
				if (walls.size() == 1) {   //if up against forward wall
					int [] xFill = {0, 320, 320, 0};
					int [] yFill = {0, 0, 350, 350};
					g.fillPolygon(xFill, yFill, 4);
				}
			}
		}




	}
	public void setBoard()
	{
		//choose your maze design

		//pre-fill maze array here

		File name = new File("maze4.txt");
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text = "";
			String output = "";
			while( (text=input.readLine())!= null)
			{
				output += text;
			}
			String[] lines = output.split("\\.");

			maze = new String[lines.length][lines[0].length()];
			for (int row = 0; row < maze.length; row++) {        //builds the 2d array of Strings of letters
				for (int col = 0; col < maze[0].length; col ++) {
					maze[row][col] = Character.toString(lines[row].charAt(col));
					System.out.print(maze[row][col]);
				}
				System.out.println();
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}
	}
	public void keyPressed(KeyEvent e)
	{
		int movedistance = (squaresize);
		/*if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if ((playercol != maze[0].length-1) && (maze[playerrow][playercol+1].equals(" ") || maze[playerrow][playercol+1].equals("*") || maze[playerrow][playercol+1].equals("%"))) {
				x += movedistance;
				playercol++;
				movecount++;
			}
		}*/
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			playerdir ++;
			if (playerdir == 5) {
				playerdir = 1;
			}
		}
		/*if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if ((playercol != 0) && (maze[playerrow][playercol-1].equals(" ") || maze[playerrow][playercol-1].equals("*"))) {
				x -= movedistance;
				playercol--;
				movecount++;
			}
		}*/
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			playerdir --;
			if (playerdir == 0) {
				playerdir = 4;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (playerdir == 1) {
				if (maze[playerrow-1][playercol].equals(" ") || maze[playerrow-1][playercol].equals("*")) {
					y -= movedistance;
					playerrow--;
					movecount++;
				}
			}
			else if (playerdir == 2) {
				if (maze[playerrow][playercol].equals("%")) {
					//do nothing
				}
				else if (maze[playerrow][playercol+1].equals(" ") || maze[playerrow][playercol+1].equals("*") || maze[playerrow][playercol+1].equals("%")) {
					x += movedistance;
					playercol++;
					movecount++;
				}
			}
			else if (playerdir == 3) {
				if (maze[playerrow+1][playercol].equals(" ") || maze[playerrow+1][playercol].equals("*")){
					y += movedistance;
					playerrow++;
					movecount++;
				}
			}
			else if (playerdir == 4) {
				if (maze[playerrow][playercol].equals("*")) {
					//do nothing
				}
				if ((playercol != 0) && (maze[playerrow][playercol-1].equals(" ") || maze[playerrow][playercol-1].equals("*"))) {
					x -= movedistance;
					playercol--;
					movecount++;
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (playerdir == 3) {
				if (maze[playerrow-1][playercol].equals(" ") || maze[playerrow-1][playercol].equals("*")) {
					y -= movedistance;
					playerrow--;
					movecount++;
				}
			}
			else if (playerdir == 4) {
				if ((playercol != maze[0].length-1) && (maze[playerrow][playercol+1].equals(" ") || maze[playerrow][playercol+1].equals("%"))) {
					x += movedistance;
					playercol++;
					movecount++;
				}
			}
			else if (playerdir == 1) {
				if (maze[playerrow+1][playercol].equals(" ") || maze[playerrow+1][playercol].equals("*")){
					y += movedistance;
					playerrow++;
					movecount++;
				}
			}
			else if (playerdir == 2) {
				if ((playercol != 0) && (maze[playerrow][playercol-1].equals(" ") || maze[playerrow][playercol-1].equals("*"))) {
					x -= movedistance;
					playercol--;
					movecount++;
				}
			}
		}

		/*if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (maze[playerrow-1][playercol].equals(" ") || maze[playerrow-1][playercol].equals("*")) {
				y -= movedistance;
				playerrow--;
				movecount++;
			}
		}*/
		/*if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (maze[playerrow+1][playercol].equals(" ") || maze[playerrow+1][playercol].equals("*")){
				y += movedistance;
				playerrow++;
				movecount++;
			}
		}*/
		System.out.println("--------------");
		repaint();
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String args[])
	{
		MazeProgram app=new MazeProgram();
	}
}