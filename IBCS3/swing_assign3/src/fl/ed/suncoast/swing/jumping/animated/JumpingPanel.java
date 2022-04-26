package fl.ed.suncoast.swing.jumping.animated;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import fl.ed.suncoast.swing.jumping.animated.Player.StateX;
import fl.ed.suncoast.swing.jumping.animated.Player.StateY;

//The panel which goes on the main frame.
public class JumpingPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;
	
	//Panel attributes.
	private final int P_WIDTH = 448, P_HEIGHT = 322;
	private final int DELAY = 16;
	
	//Background.
	private BufferedImage bi_bg;
		
	//Player assets.
	private Player p;
	private Timer t_timer;
	
	//Tracks currently pressed keys.
	private ArrayList<String> al_keysPressed = new ArrayList<String>();
	
	//Updates internal logic.
	TimerListener tl_globalTimer = new TimerListener();
	
	//Constructor
	public JumpingPanel()
	{
		//Set panel attributes.
		this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
		this.setBackground(Color.BLACK);
		
		//Try to load image.
		try 
		{
			this.bi_bg = ImageIO.read(new File("background.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
				
		//Set player assets.
		this.p = new Player(this.P_WIDTH, this.P_HEIGHT);
		this.t_timer = new Timer(DELAY, this.tl_globalTimer);
				
		//Set key bindings.
		this.setKeyBindings();
	}
	
	

	//Predefines actions to be taken on each key press.
	public void setKeyBindings()
	{
		//Bind up to look up, and down to duck.
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false), "lookUp");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,true), "lookForward");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false), "duck");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true), "stand");
		
		this.getActionMap().put("lookUp", new InputAction("UP",false));
		this.getActionMap().put("lookForward", new InputAction("UP",true));
		this.getActionMap().put("duck", new InputAction("DOWN",false));
		this.getActionMap().put("stand", new InputAction("DOWN",true));
		
		//Bind left and right to horizontal movement.
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false), "accelLeft");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true), "decelLeft");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false), "accelRight");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true), "decelRight");
		
		this.getActionMap().put("accelLeft", new InputAction("LEFT",false));
		this.getActionMap().put("decelLeft", new InputAction("LEFT",true));
		this.getActionMap().put("accelRight", new InputAction("RIGHT",false));		
		this.getActionMap().put("decelRight", new InputAction("RIGHT",true));		

		//Bind Z to run.
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0,false), "run");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0,true), "walk");

		this.getActionMap().put("run", new InputAction("Z",false));
		this.getActionMap().put("walk", new InputAction("Z",true));
		
		//Bind space to jump.
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,false), "jump");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,true), "land");
		

		this.getActionMap().put("jump", new InputAction("SPACE",false));
		this.getActionMap().put("land", new InputAction("SPACE",true));
	}


	//Draws to screen.
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		//Draw background.
		g2d.drawImage(this.bi_bg, null, 0, 0);
				
		//Draw image.
		g2d.drawImage(this.p.getCurrentSprite(), null, (int)this.p.getX(), (int)this.p.getY());
		//Also draw extra sprite if needed.
		switch(this.p.getCurrentSpriteID())
		{
			case 3:
			case 4:
			case 5:
			case 10:
			{
				g2d.drawImage(this.p.getExtraSprite(), null, (int)this.p.getX() + 16, (int)this.p.getY() + 16);
				break;
			}
			default:
		}
		
		//Debug
		if(DEBUG)
		{
			g2d.drawImage(this.p.getExtraSprite(), null, 0,0);
			g2d.setColor(Color.RED);
			if(this.al_keysPressed.contains("UP")) g2d.drawString("^", 10, 20);
			if(this.al_keysPressed.contains("LEFT")) g2d.drawString("<", 0, 30);
			if(this.al_keysPressed.contains("RIGHT")) g2d.drawString(">", 20, 30);
			if(this.al_keysPressed.contains("DOWN")) g2d.drawString("v", 10, 40);
			g2d.drawString("Ducking: " + this.p.getDucking(), 0, 60);
			g2d.drawString("dx: " + this.p.getDx(), 0, 80);
			g2d.drawString("dy: " + this.p.getDy(), 0, 100);
			g2d.drawString("Sprite: " + this.p.getCurrentSpriteID(), 0, 120);
			g2d.drawString("StateX: " + this.p.getStateX(), 0, this.P_HEIGHT - 40);
			g2d.drawString("StateY: " + this.p.getStateY(), 0, this.P_HEIGHT - 20);
		}
		
		g2d.dispose();
	}
	
	//Listens for and processes input events.
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
					//Disallow left+right.
					if(!(this.s_key == "LEFT" && al_keysPressed.contains("RIGHT")) && !(this.s_key == "RIGHT" && al_keysPressed.contains("LEFT")))
					{
						al_keysPressed.add(this.s_key);
						switch(this.s_key)
						{
							case "UP": p.setLookingUp(true); break;
							case "DOWN": p.setDucking(true); break;
							case "LEFT": p.moveLeft(); break;
							case "RIGHT": p.moveRight(); break;
							case "SPACE": p.setJumping(true); p.jump(); break;
							case "Z": p.setRunning(true); break;
							default:
						}
					}
				}
				if(!t_timer.isRunning()) t_timer.start();
			}
			else
			{
				if(al_keysPressed.contains(this.s_key))
				{	
					al_keysPressed.remove(this.s_key);
					switch(this.s_key)
					{
						case "UP": p.setLookingUp(false); break;
						case "DOWN": p.setDucking(false); break;
						case "LEFT": p.setStateX(StateX.STATE_STOPPING_LEFT); break;
						case "RIGHT": p.setStateX(StateX.STATE_STOPPING_RIGHT); break;
						case "SPACE": if(p.getStateY() == StateY.STATE_JUMPING) p.setStateY(StateY.STATE_FALLING); break;
						case "Z": p.setRunning(false); break;
						default:
					}
				}
			}
		}
	}
	
	
	//Listens for and processes timer events.
	private class TimerListener implements ActionListener
	{

		//Enemy action occurs automatically every DELAY ms.
		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			//Move player when needed.
			if(ae.getSource() == t_timer)
			{				
				//Move left if needed.
				if(al_keysPressed.contains("LEFT") || p.getStateX() == StateX.STATE_STOPPING_LEFT)
				{
					p.moveLeft();
				}
				
				//Move right if needed.
				if(al_keysPressed.contains("RIGHT") || p.getStateX() == StateX.STATE_STOPPING_RIGHT)
				{
					p.moveRight();
				}
				
				//Continue jumping if needed.
				if(p.getJumping()) 
				{
					p.jump();
				}
				
				//Drop P=Meter if needed.
				if(p.getStateX() == StateX.STATE_STOPPED && p.getPMeter() > 0)
				{
					p.setPMeter(p.getPMeter() -1);
				}
			}	

			//Try to animate sprite.
			p.incrementFrame();
			
			//Redraw to screen.
			repaint();
		}		
	}	
}


