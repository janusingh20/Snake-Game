import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int WIDTH = 600;
	static final int HEIGHT =600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE);
	static final int DELAY=75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		
		timer = new Timer(DELAY, this);
		timer.start();	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	
	public void draw(Graphics g) {
	if (running) {
		for (int i=0; i<HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);
			g.drawLine(0, i*UNIT_SIZE,WIDTH,i*UNIT_SIZE);
			
	}
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		for(int i=0; i<bodyParts; i++) {
			if (i==0) {
				g.setColor(Color.green);
				g.fillRect(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45,180,0));
				g.fillRect(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
			}
		}
		
		g.setColor(Color.red);
		g.setFont(new Fond("Ink Free", Font.BOLD,40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten,(WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
		
	
	}
	else {
		gameOver(g);
	}
		}	
			
	
	public void newApple() {
		appleX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	public void move() {
		for(int i=bodyParts;i>0;i--) {
				x[i] = x[i-1];
				y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			
		}	
	}
	
	
	public void checkApple() {
		if ((x[0]==appleX)&& (y[0]==appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
	 for(int i=bodyParts; i>0;i--) {
	 	if ((x[0]==x[i])&& (y[0]==y[i])) {
	 		running = false;
	 	}
	 }
	 if (x[0]<0) {
	 	running = false;
	}
	if(x[0]> WIDTH) {
		running = false;
	}
	if (y[0]<0) {
		running = false;
	}
	if(x[0]> HEIGHT) {
		running = false;
	}
	
	if (!running) {
		timer.stop();
		
	}
}

	public void gameOver(Graphics g) {
	
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten,(WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
			
	
		g.setColor(Color.red);
		g.setFont(new Fond("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over",(WIDTH-metrics2.stringWidth("Game Over"))/2,HEIGHT);
		
	
}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case keyEvent.VK_LEFT:
				if (direction!='R') {
					direction = 'L';
				}
				break;
			case keyEvent.VK_RIGHT:
				if (direction!='L') {
					direction = 'R';
				}
				break;
			case keyEvent.VK_UP:
				if (direction!='D') {
					direction = 'U';
				}
				break;
			case keyEvent.VK_DOWN:
				if (direction!='U') {
					direction = 'D';
				}
				break;
					
			}
		}
	}
}	
					
					
					