package fl.ed.suncoast.swing.runner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//Player assets and attributes.
public class Player 
{
	//Image.
	private BufferedImage bi_image;
	private final int P_WIDTH, P_HEIGHT, IMAGE_WIDTH = 35, IMAGE_HEIGHT = 35;
	
	//Position, velocity, and acceleration.
	private double x, y, dx, dxmax, ddx, dy, dymax, ddy;
	private boolean b_isRunning;
	private boolean b_isJumping;
	
	//Velocity and acceleration parameters.
	private final double 	DX_MAX_WALKING 			= 1.25,
							DX_MAX_RUNNING			= 2.25,
							DDX_WALKING 			= 0.09375,
							DDX_RUNNING 			= DDX_WALKING,
							DDX_STOPPING 			= 0.0625,
							DDX_SKID_DECEL_WALKING	= 0.15625,
							DDX_SKID_DECEL_RUNNING	= 0.3125,
							DY_MAX_GRAVITY = 4.00, 
							DDY_GRAVITY_JUMPING =0.1875, 
							DDY_GRAVITY_FALLING = 0.375;
	
	//States of being.
	enum StateX
	{
		STATE_STOPPED,
		STATE_WALKING,
		STATE_RUNNING,
		STATE_WALKING_SKID_LEFT,
		STATE_WALKING_SKID_RIGHT,
		STATE_RUNNING_SKID_LEFT,
		STATE_RUNNING_SKID_RIGHT,
		STATE_STOPPING_LEFT,
		STATE_STOPPING_RIGHT, 
	};
	
	enum StateY{
		STATE_ON_GROUND,
		STATE_JUMPING,
		STATE_FALLING,
	};
	
	
	private StateX state_x = StateX.STATE_STOPPED;
	private StateY state_y = StateY.STATE_ON_GROUND;
				
	//Constructor
	public Player(int width, int height)
	{
		this.P_WIDTH = width;
		this.P_HEIGHT = height;
		
		//Try to load image.
		try 
		{
			this.bi_image = ImageIO.read(new File("face.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Set initial conditions.
		this.x = (P_WIDTH-IMAGE_WIDTH)/2.0;
		this.y = P_HEIGHT-IMAGE_HEIGHT;
		this.dx = this.ddx = 0.0;
		this.dy= this.ddy= 0.0;
		
		this.dymax= this.DY_MAX_GRAVITY;
		this.dxmax = this.DX_MAX_WALKING;
		this.b_isRunning= false;
		this.b_isJumping= false;
	}
	
	
	//Movement functions.
	public void moveLeft()
	{
		//Update state.
		switch(this.state_x)
		{
			case STATE_STOPPED: 
			{
				if(b_isRunning) {
					
					//Start to run 
					this.state_x = StateX.STATE_RUNNING;
					this.ddx = -1*this.DDX_RUNNING;
				}else {
					//Start to walk.
					this.state_x = StateX.STATE_WALKING;
					this.ddx = -1*this.DDX_WALKING;
				}
				
				break;
			}
			case STATE_WALKING: 
			{				
				//Player currently walking. Check if motion is left or right.
				if(this.dx < 0)
				{
					//Motion is left. Continue accelerating.
				}
				else
				{
					//Motion is right. Start to skid left.
					this.state_x = StateX.STATE_WALKING_SKID_LEFT;
					this.ddx = -1*this.DDX_SKID_DECEL_WALKING;
				}
				break;
			}
			case STATE_RUNNING: 
			{				
				//Player currently walking. Check if motion is left or right.
				if(this.dx < 0)
				{
					//Motion is left. Continue accelerating.
				}
				else
				{
					//Motion is right. Start to skid left.
					this.state_x = StateX.STATE_RUNNING_SKID_LEFT;
					this.ddx = -1*this.DDX_SKID_DECEL_RUNNING;
				}
				break;
			}
			case STATE_WALKING_SKID_LEFT: 
			case STATE_RUNNING_SKID_LEFT:
			{
				//Player is pressing left to skid; continue skidding until stopped.
				if(this.dx <= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.state_x = StateX.STATE_STOPPED;
				}
				break;
			}
			case STATE_WALKING_SKID_RIGHT:
			{
				//Motion is left. Player was pressing right to skid, but stopped. Resume left motion.
				this.state_x = StateX.STATE_WALKING;
				this.ddx = -1*this.DDX_WALKING;
				break;
			}
			case STATE_RUNNING_SKID_RIGHT:
			{
				//Motion is left. Player was pressing right to skid, but stopped. Resume left motion.
				this.state_x = StateX.STATE_RUNNING;
				this.ddx = -1*this.DDX_RUNNING;
				break;
			}
			case STATE_STOPPING_LEFT: 
			{
				//Player released left. Decelerate.
				this.ddx = this.DDX_STOPPING;
				if(this.dx >= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.state_x = StateX.STATE_STOPPED;
				}
				break;
			}
			case STATE_STOPPING_RIGHT:
			{
				if(b_isRunning) {

					//Player stopping right. Skid left.				
					this.state_x = StateX.STATE_RUNNING_SKID_LEFT;
					this.ddx = -1*this.DDX_SKID_DECEL_RUNNING;	
					
				}else {
					//Player stopping right. Skid left.				
					this.state_x = StateX.STATE_WALKING_SKID_LEFT;
					this.ddx = -1*this.DDX_SKID_DECEL_WALKING;		
				}		
				break;	
			}
			default:
		}
		
		//Apply acceleration.
		this.dx += this.ddx;
		
		//Bound velocity.
		if(this.dx < -this.dxmax) this.dx = -this.dxmax;
		if(this.dx > this.dxmax) this.dx = this.dxmax;
		
		//Actually move.
		this.x += this.dx;
		
		//Bound position.
		if(this.x < 0) 
		{
			this.x = 0;
			this.dx = 0;
			this.ddx = 0;
		}
		if(this.dx == 0) this.state_x = StateX.STATE_STOPPED;
	}
	
	public void moveRight()
	{
		//Update state.
		switch(this.state_x)
		{
			case STATE_STOPPED: 
			{			
				if(b_isRunning) {
					//Start to walk.
					this.state_x = StateX.STATE_RUNNING;
					this.ddx = this.DDX_RUNNING;
				}else {
					//Start to walk.
					this.state_x = StateX.STATE_WALKING;
					this.ddx = this.DDX_WALKING;
				}
				
				break;
			}
			case STATE_WALKING: 
			{				
				//Player currently walking. Check if motion is left or right.
				if(this.dx > 0)
				{
					//Motion is right. Continue accelerating.
				}
				else
				{
					//Motion is left. Start to skid right.
					this.state_x = StateX.STATE_WALKING_SKID_RIGHT;
					this.ddx = this.DDX_SKID_DECEL_WALKING;
				}
				break;
			}
			case STATE_RUNNING: 
			{				
				//Player currently walking. Check if motion is left or right.
				if(this.dx > 0)
				{
					//Motion is right. Continue accelerating.
				}
				else
				{
					//Motion is left. Start to skid right.
					this.state_x = StateX.STATE_RUNNING_SKID_RIGHT;
					this.ddx = this.DDX_SKID_DECEL_RUNNING;
				}
				break;
			}
			case STATE_WALKING_SKID_LEFT:
			{
				//Motion is right. Player was pressing left to skid, but stopped. Resume right motion.
				this.state_x = StateX.STATE_WALKING;
				this.ddx = this.DDX_WALKING;
				break;
			}
			case STATE_RUNNING_SKID_LEFT:
			{
				//Motion is right. Player was pressing left to skid, but stopped. Resume right motion.
				this.state_x = StateX.STATE_WALKING;
				this.ddx = this.DDX_RUNNING;
				break;
			}
			case STATE_RUNNING_SKID_RIGHT: 
			case STATE_WALKING_SKID_RIGHT: 
			{
				//Player is pressing right to skid; continue skidding until stopped.
				if(this.dx >= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.state_x = StateX.STATE_STOPPED;
				}
				break;
			}	
			case STATE_STOPPING_LEFT:
			{
				if(b_isRunning) {
					//Player stopping left. Skid right.
					this.state_x = StateX.STATE_RUNNING_SKID_RIGHT;
					this.ddx = this.DDX_SKID_DECEL_RUNNING;	
				}else{
					//Player stopping left. Skid right.
					this.state_x = StateX.STATE_WALKING_SKID_RIGHT;
					this.ddx = this.DDX_SKID_DECEL_WALKING;	
				}
				break;			
			}
			case STATE_STOPPING_RIGHT: 
			{
				//Player released right. Decelerate.
				this.ddx = -1*this.DDX_STOPPING;
				if(this.dx <= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.state_x = StateX.STATE_STOPPED;
				}
				break;
			}
			default:
		}
		
		//Apply acceleration.
		this.dx += this.ddx;
		
		//Bound velocity.
		if(this.dx < -this.dxmax) this.dx = -this.dxmax;
		if(this.dx > this.dxmax) this.dx = this.dxmax;
		
		//Actually move.
		this.x += this.dx;
		
		//Bound position.
		if(this.x > this.P_WIDTH - this.IMAGE_WIDTH) 
		{
			this.x = this.P_WIDTH - this.IMAGE_WIDTH;
			this.dx = 0;
			this.ddx = 0;
		}
		if(this.dx == 0) this.state_x = StateX.STATE_STOPPED;
	}	

	public void jump() {
		//jump
				switch(this.state_y)
				{
					case STATE_ON_GROUND: 
					{			
						if(b_isJumping) { //if it's on the ground and b_isJumping is true
							
							//Start to jump.
							this.state_y = StateY.STATE_JUMPING;
							this.ddy = -(4.8125 + Math.abs(Math.floor((.0625 * this.dx))) + Math.abs(.25 * this.dx));
						
						}
						
						break;
					}
					case STATE_JUMPING: 
					{				
							//actual jumping 
							this.state_y = StateY.STATE_JUMPING;
							this.ddy = this.DDY_GRAVITY_JUMPING;
						
						break;
					}
					
					
					case STATE_FALLING: 
					{	
							//makes it fall
							this.state_y = StateY.STATE_FALLING;
							this.ddy = this.DDY_GRAVITY_FALLING;
							break;
						}
						
						default:
				}
				
				
				//Apply acceleration.
				this.dy += this.ddy;
				
				//Bound velocity.
				if(this.dy > this.dymax) this.dy = this.dymax;
				
				//Actually move.
				this.y += this.dy;
				
				//Bound position.
				if(this.y > this.P_HEIGHT - this.IMAGE_HEIGHT) 
				{
					this.y = this.P_HEIGHT - this.IMAGE_HEIGHT;
					this.dy = 0;
					this.ddy = 0;
				}
				if(this.dy == 0) this.state_y = StateY.STATE_ON_GROUND;
			}	

		
	//Getters
	public BufferedImage getImage()
	{
		return bi_image;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getDx()
	{
		return dx;
	}
	
	public double getDxMax()
	{
		return dxmax;
	}
	
	public double getDdx()
	{
		return ddx;
	}
	
	public StateX getStateX()
	{
		return this.state_x;
	}
	
	public StateY getStateY()
	{
		return this.state_y;
	}

	public boolean getRunning() {
		return b_isRunning;
	}
	

	public boolean getJumping() {
		return b_isJumping;
	}

	
	//Setters
	public void setBi_image(BufferedImage bi_image)
	{
		this.bi_image = bi_image;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public void setDx(double dx)
	{
		this.dx = dx;
	}
	
	public void setDxMax(double dxmax)
	{
		this.dxmax = dxmax;
	}

	public void setDdx(double ddx)
	{
		this.ddx = ddx;
	}
	
	public void setStateX(StateX state_x)
	{
		this.state_x = state_x;
	}

	public void setStateY(StateY state_y)
	{
		this.state_y = state_y;
	}
	
	public void setRunning(boolean isRunning) {
		this.b_isRunning = isRunning;
		
		//Also update state, if needed.
		if(this.b_isRunning) {
			
			//Now running. If in state of walking, change to corresponding running state.
			switch(this.state_x) {
			case STATE_WALKING: this.state_x= StateX.STATE_RUNNING; break;
			case STATE_WALKING_SKID_LEFT: this.state_x= StateX.STATE_RUNNING_SKID_LEFT; break;
			case STATE_WALKING_SKID_RIGHT: this.state_x= StateX.STATE_RUNNING_SKID_RIGHT; break;
			default:
			}
			this.dxmax= this.DX_MAX_RUNNING;
			
		}else{
			
			//Now walking. If in state of running, change to corresponding walking state.
		
			switch(this.state_x) {
			case STATE_RUNNING: this.state_x= StateX.STATE_WALKING; break;
			case STATE_RUNNING_SKID_LEFT: this.state_x= StateX.STATE_WALKING_SKID_LEFT; break;
			case STATE_RUNNING_SKID_RIGHT: this.state_x= StateX.STATE_WALKING_SKID_RIGHT; break;
			default:
			}
			this.dxmax= this.DX_MAX_WALKING;
		}
			
	}

	public void setJumping(boolean isJumping) {
		this.b_isJumping = isJumping; //changes isJumping based off it spacebar is pressed or not
	}

}