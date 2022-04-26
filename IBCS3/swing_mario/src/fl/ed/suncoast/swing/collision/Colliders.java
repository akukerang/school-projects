package fl.ed.suncoast.swing.collision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Colliders
{
	//Program starts and stops here.
	//Adds the main frame and puts a panel on it.
	public static void main(String[] args)
	{
		JFrame jf_main = new JFrame("Box Wars: The Final Conflict");
		jf_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add panel to frame.
		jf_main.getContentPane().add(new ColliderPanel());
		
		//Pack and set visible.
		jf_main.pack();
		jf_main.setResizable(false);
		jf_main.setVisible(true);
	}

	
	//The panel which goes on the main frame.
	private static class ColliderPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		private final int P_WIDTH = 500, P_HEIGHT = 300;
		private final int DELAY = 16, IMAGE_SIZE = 35;
		
		private final boolean DEBUG = false;
		
		private Timer t_timer, t_timer2;
		private int x, y, dx, dy, ddx, ddy;
		private int x2, y2, dx2, dy2, ddx2, ddy2;
		private final int DX_MAX = 3, DY_MAX = 3, DDX = 3, DDY = 3;
		private boolean b_isAMoving = false, b_isBMoving = false;
		
		//Tracks currently pressed keys.
		private ArrayList<String> al_keysPressed = new ArrayList<String>();
		
		ReboundListener rl = new ReboundListener();
		
		//Constructor
		public ColliderPanel()
		{
			//Set player assets.
			this.t_timer = new Timer(DELAY, rl);
			
			//Set enemy assets.
			this.t_timer2 = new Timer(DELAY, rl);
			
			//Set initial conditions.
			this.x = 20;
			this.y = 20;
			this.dx = this.dy = 0;
			this.ddx = this.ddy = 0;
			
			this.x2 = P_WIDTH - IMAGE_SIZE - this.x;
			this.y2 = P_HEIGHT - IMAGE_SIZE - this.y;
			this.dx2 = this.dy2 = 0;
			this.ddx2 = this.ddy2 = 0;
			
			//Set panel attributes.
			this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
			this.setBackground(Color.BLACK);
			
			//Set key bindings.
			this.setKeyBindings();
			
			
			//Start the timers.
			this.t_timer2.start();
		}
		
		
		
		//Predefines actions to be taken on each key press.
		public void setKeyBindings()
		{
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,false), "moveAUp");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0,false), "moveADown");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0,false), "moveALeft");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0,false), "moveARight");
			
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,true), "stopAUp");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0,true), "stopADown");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0,true), "stopALeft");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0,true), "stopARight");
			
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false), "moveBUp");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false), "moveBDown");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false), "moveBLeft");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false), "moveBRight");
			
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,true), "stopBUp");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true), "stopBDown");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true), "stopBLeft");
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true), "stopBRight");
	
	
			this.getActionMap().put("moveAUp", new InputAction("W",false));
			this.getActionMap().put("moveADown", new InputAction("S",false));
			this.getActionMap().put("moveALeft", new InputAction("A",false));
			this.getActionMap().put("moveARight", new InputAction("D",false));
			
			this.getActionMap().put("stopAUp", new InputAction("W",true));
			this.getActionMap().put("stopADown", new InputAction("S",true));
			this.getActionMap().put("stopALeft", new InputAction("A",true));
			this.getActionMap().put("stopARight", new InputAction("D",true));
			
			this.getActionMap().put("moveBUp", new InputAction("UP",false));
			this.getActionMap().put("moveBDown", new InputAction("DOWN",false));
			this.getActionMap().put("moveBLeft", new InputAction("LEFT",false));
			this.getActionMap().put("moveBRight", new InputAction("RIGHT",false));
			
			this.getActionMap().put("stopBUp", new InputAction("UP",true));
			this.getActionMap().put("stopBDown", new InputAction("DOWN",true));
			this.getActionMap().put("stopBLeft", new InputAction("LEFT",true));
			this.getActionMap().put("stopBRight", new InputAction("RIGHT",true));
			
		}
		
		private class InputAction extends AbstractAction
		{
			private static final long serialVersionUID = 1L;
			
			private String s_key;
			private boolean b_released;
			
			public InputAction(String key, boolean released)
			{
				this.s_key = key;
				this.b_released = released;
			}
			
			//When a key is pressed or released, do this.
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(!this.b_released)
				{
					if(!al_keysPressed.contains(this.s_key))
					{					
						al_keysPressed.add(this.s_key);
						switch(this.s_key)
						{
							case "W": ddy -= DDY; b_isAMoving = true; break;
							case "S": ddy += DDY; b_isAMoving = true; break;
							case "A": ddx -= DDX; b_isAMoving = true; break;
							case "D": ddx += DDX; b_isAMoving = true; break;
							case "UP": ddy2 -= DDY; b_isBMoving = true; break;
							case "DOWN": ddy2 += DDY; b_isBMoving = true; break;
							case "LEFT": ddx2 -= DDX; b_isBMoving = true; break;
							case "RIGHT": ddx2 += DDX; b_isBMoving = true; break;
							default:
						}
					}
					if(!t_timer.isRunning()) t_timer.start();
					if(!t_timer2.isRunning()) t_timer2.start();
				}
				else
				{	
					if(al_keysPressed.contains(this.s_key))
					{	
						al_keysPressed.remove(this.s_key);
						switch(this.s_key)
						{
							case "W": ddy += DDY; dy = 0; b_isAMoving = false; break;
							case "S": ddy -= DDY; dy = 0; b_isAMoving = false; break;
							case "A": ddx += DDX; dx = 0; b_isAMoving = false; break;
							case "D": ddx -= DDX; dx = 0; b_isAMoving = false; break;
							case "UP": ddy2 += DDY; dy2 = 0; b_isBMoving = false; break;
							case "DOWN": ddy2 -= DDY; dy2 = 0; b_isBMoving = false; break;
							case "LEFT": ddx2 += DDX; dx2 = 0; b_isBMoving = false; break;
							case "RIGHT": ddx2 -= DDX; dx2 = 0; b_isBMoving = false; break;
							default:
						}
					}
				}
			}
		}
		
		
		//Draws to screen.
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			//Draw images.
			g.setColor(Color.BLUE);
			g.fillRect(this.x, this.y, this.IMAGE_SIZE, this.IMAGE_SIZE);
			g.setColor(Color.GREEN);
		
			int l = IMAGE_SIZE / 2;
			int l2 = IMAGE_SIZE / 2;
			int w = IMAGE_SIZE / 2;
			int w2 = IMAGE_SIZE / 2;
			
			
			boolean b_isColliding = false;
		
			if((Math.abs(this.x - this.x2) < l + l2) && ((Math.abs(this.y - this.y2) < w + w2))) {
				b_isColliding = true;
			}
			
			
			
			
			
			if(b_isColliding) {
				g.setColor(Color.RED);
			}
			g.fillRect(this.x2, this.y2, this.IMAGE_SIZE, this.IMAGE_SIZE);

			//Debug
			if(DEBUG)
			{
				g.setColor(Color.RED);
				g.drawString("A Movement: " + this.b_isAMoving, 0, 80);
				g.drawString("B Movement: " + this.b_isBMoving, 0, 100);
				if(this.al_keysPressed.contains("UP")) g.drawString("^", 10, 20);
				if(this.al_keysPressed.contains("LEFT")) g.drawString("<", 0, 30);
				if(this.al_keysPressed.contains("RIGHT")) g.drawString(">", 20, 30);
				if(this.al_keysPressed.contains("DOWN")) g.drawString("v", 10, 40);
				
				
			}
		}		
		
		//Listens for, and processes events.
		private class ReboundListener implements ActionListener
		{	
			//Enemy action occurs automatically every DELAY ms.
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				//Move players when needed.
				if(ae.getSource() == t_timer)
				{
					//Keys pressed. Move image.
					dx += ddx;
					dy += ddy;
					
					//Bound velocity.
					if(dx < -DX_MAX) dx = -DX_MAX;
					if(dx > DX_MAX) dx = DX_MAX;
					if(dy < -DY_MAX) dy = -DY_MAX;
					if(dy > DY_MAX) dy = DY_MAX;
					
					x += dx;
					y += dy;
					
					//Bound position.
					if(x < 0) x = 0;
					if(x > P_WIDTH - IMAGE_SIZE) x = P_WIDTH - IMAGE_SIZE;
					if(y < 0) y = 0;
					if(y > P_HEIGHT - IMAGE_SIZE) y = P_HEIGHT - IMAGE_SIZE;
				}
				
				if(ae.getSource() == t_timer2)
				{
					//Move image.
					dx2 += ddx2;
					dy2 += ddy2;
					
					//Bound velocity.
					if(dx2 < -DX_MAX) dx2 = -DX_MAX;
					if(dx2 > DX_MAX) dx2 = DX_MAX;
					if(dy2 < -DY_MAX) dy2 = -DY_MAX;
					if(dy2 > DY_MAX) dy2 = DY_MAX;
					
					x2 += dx2;
					y2 += dy2;
					
					//Bound position.
					if(x2 < 0) x2 = 0;
					if(x2 > P_WIDTH - IMAGE_SIZE) x2 = P_WIDTH - IMAGE_SIZE;
					if(y2 < 0) y2 = 0;
					if(y2 > P_HEIGHT - IMAGE_SIZE) y2 = P_HEIGHT - IMAGE_SIZE;	
				}
				
				//Redraw to screen.
				repaint();
			}	
		}	
	}
}