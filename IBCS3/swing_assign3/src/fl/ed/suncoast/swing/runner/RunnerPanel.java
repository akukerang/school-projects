package fl.ed.suncoast.swing.runner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import fl.ed.suncoast.swing.runner.Player.StateX;
import fl.ed.suncoast.swing.runner.Player.StateY;

//The panel which goes on the main frame.
public class RunnerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;
	
	//Panel attributes.
	private final int P_WIDTH = 350, P_HEIGHT = 200;
	private final int DELAY = 16;
	
	//Player assets.
	private Player p;
	private Timer t_timer;
	
	//Tracks currently pressed keys.
	private ArrayList<String> al_keysPressed = new ArrayList<String>();
	
	//Updates internal logic.
	TimerListener tl_globalTimer = new TimerListener();
	
	//Constructor
	public RunnerPanel()
	{
		//Set panel attributes.
		this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
		this.setBackground(Color.BLACK);
		
		//Set player assets.
		this.p = new Player(this.P_WIDTH, this.P_HEIGHT);
		this.t_timer = new Timer(DELAY, this.tl_globalTimer);
				
		//Set key bindings.
		this.setKeyBindings();
	}
	
	

	//Predefines actions to be taken on each key press.
	public void setKeyBindings()
	{
		//Bind left and right to horizontal movement.
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false), "accelLeft");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true), "decelLeft");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false), "accelRight");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true), "decelRight");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false),"jump");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true),"fall");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false),"run");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true),"walk");

		this.getActionMap().put("accelLeft", new InputAction("LEFT",false));
		this.getActionMap().put("decelLeft", new InputAction("LEFT",true));
		this.getActionMap().put("accelRight", new InputAction("RIGHT",false));		
		this.getActionMap().put("decelRight", new InputAction("RIGHT",true));
		this.getActionMap().put("run", new InputAction("Z", false));
		this.getActionMap().put("walk", new InputAction("Z", true));
		this.getActionMap().put("jump", new InputAction("SPACE", false));
		this.getActionMap().put("fall", new InputAction("SPACE", true));
	}


	//Draws to screen.
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		//Draw image.
		g2d.drawImage(this.p.getImage(), null, (int)this.p.getX(), (int)this.p.getY());
		
		//Debug
		if(DEBUG)
		{
			g2d.setColor(Color.RED);
			if(this.al_keysPressed.contains("UP")) g2d.drawString("^", 10, 20);
			if(this.al_keysPressed.contains("LEFT")) g2d.drawString("<", 0, 30);
			if(this.al_keysPressed.contains("RIGHT")) g2d.drawString(">", 20, 30);
			if(this.al_keysPressed.contains("DOWN")) g2d.drawString("v", 10, 40);
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
						case "LEFT": p.moveLeft(); break;
						case "RIGHT": p.moveRight(); break;
						case "Z": p.setRunning(true);break;
						case "SPACE": p.setJumping(true);break;
						
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
					case "LEFT": p.setStateX(StateX.STATE_STOPPING_LEFT); break;
					case "RIGHT": p.setStateX(StateX.STATE_STOPPING_RIGHT); break;
					case "Z": p.setRunning(false);break;
					case "SPACE": p.setJumping(false); break;
					
					default:
					}
				}
			}
		}
	}
	
	
	//Listens for and processes timer events.
	private class TimerListener implements ActionListener
	{

		//When a timer ticks, do this.
		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			//When t_timer ticks, do this.
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
				
				if(al_keysPressed.contains("SPACE") || p.getStateY() == StateY.STATE_JUMPING)
				{
					//if b_isJumping is flagged, call jump() again.
					p.jump();
				}
			}	

			//Redraw to screen.
			repaint();
		}		
	}	
}