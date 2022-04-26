package fl.ed.suncoast.swing.jumping.animated;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Player assets and attributes.
public class Player 
{
	//Sprites.
	private BufferedImage bi_spritesheet, bi_currentSprite, bi_extraSprite;
	private final int P_WIDTH, P_HEIGHT, IMAGE_WIDTH = 16, IMAGE_HEIGHT = 32;
	private boolean b_isDucking, b_isLookingUp, b_isFacingRight;
	private int frameCounter;
	
	//Position, velocity, and acceleration.
	private double x, y, dx, dxmax, dy, dymax, ddx, ddy;
	private boolean b_isRunning, b_isSprinting, b_isJumping;
	private int pMeter;
	
	


	
	//Velocity and acceleration parameters.
	private final double 	DX_MAX_WALKING 			= 1.25,
							DX_MAX_RUNNING			= 2.25,
							DX_MAX_SPRINTING		= 3.00,
							DDX_WALKING 			= 0.09375,
							DDX_RUNNING				= 0.09375,
							DDX_STOPPING 			= 0.0625,
							DDX_SKID_DECEL_WALKING	= 0.15625,
							DDX_SKID_DECEL_RUNNING	= 0.3125,

							DY_MAX_GRAVITY			= 4.00,
							DDY_GRAVITY_JUMPING		= 0.1875,
							DDY_GRAVITY_FALLING		= 0.375;
	
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
		STATE_STOPPING_RIGHT
	};
	private StateX state_x = StateX.STATE_STOPPED;
	
	enum StateY
	{
		STATE_ON_GROUND,
		STATE_JUMPING,
		STATE_FALLING
	};
	
	enum Sprite {
		SPRITE_IDLE, 
		SPRITE_WALKING_1,
		SPRITE_WALKING_2, 
		SPRITE_SPRINTING_1, 
		SPRITE_SPRINTING_2,
		SPRITE_SPRINTING_3, 
		SPRITE_DUCKING, 
		SPRITE_JUMP_IDLE, 
		SPRITE_JUMPING,
		SPRITE_FALLING, 
		SPRITE_SPRINTING_JUMP, 
		SPRITE_NULL, SPRITE_SKIDDING,
		SPRITE_LOOK_UP
	};
	
	

	
	private StateY state_y = StateY.STATE_ON_GROUND;
					
	private int currentSpriteID = 0;
		
	//Constructor
	public Player(int width, int height)
	{
		this.P_WIDTH = width;
		this.P_HEIGHT = height;
		
		//Try to load image.
		try 
		{
			this.bi_spritesheet = ImageIO.read(new File("sprites.png"));
			this.setCurrentSprite(this.currentSpriteID);
			this.setExtraSprite(0);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Set initial conditions.
		
		this.x = (width-IMAGE_WIDTH)/2.0;
		this.y = height-IMAGE_HEIGHT;
		this.dx = this.dy = this.ddx = this.ddy = 0.0;
		this.dxmax = this.DX_MAX_WALKING;
		this.dymax = this.DY_MAX_GRAVITY;
		
		this.setFacingRight(true);
		this.b_isDucking = this.b_isLookingUp = false;
		this.b_isRunning = this.b_isSprinting = this.b_isJumping = false;
		this.resetFrameCounter();
		
		this.pMeter = 0;
	}
	
	
	//Image functions.
	public BufferedImage getflippedSprite(BufferedImage bi) 
	{
        int w = bi.getWidth();
        int h = bi.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(bi, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return flippedImage;
    }
	
	
	public void resetFrameCounter()
	{
		this.setFrameCounter(0);
	}
	
	public void incrementFrame()
	{
		this.frameCounter++;
		//All animations go here.
		if(this.state_y != StateY.STATE_ON_GROUND) {
			//All air animations go here.
			if(this.b_isDucking) {
				this.setCurrentSprite(Sprite.SPRITE_DUCKING.ordinal());
			} else if (this.b_isSprinting){
				this.setCurrentSprite(Sprite.SPRITE_SPRINTING_JUMP.ordinal());
				
			} else if (dy < 0) {
				this.setCurrentSprite(Sprite.SPRITE_JUMPING.ordinal());
			} else if (dy > 0) {
				this.setCurrentSprite(Sprite.SPRITE_FALLING.ordinal());
			}
			
			
		} else {
			//All ground animations go here.
			if(!this.b_isSprinting) {
				switch(this.state_x) {
				case STATE_STOPPED: {
					if(b_isDucking) { 
						this.setCurrentSprite(Sprite.SPRITE_DUCKING.ordinal());
					} 
					else if (b_isLookingUp) {
						this.setCurrentSprite(Sprite.SPRITE_LOOK_UP.ordinal());
					}
					else {
						this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());
						
					}
					break;

				}
				//Handle all other cases here
				case STATE_WALKING:
				case STATE_STOPPING_LEFT:
				case STATE_STOPPING_RIGHT:{
					if(b_isDucking) {
						this.setCurrentSprite(Sprite.SPRITE_DUCKING.ordinal());
					} else {
						if(this.frameCounter <= 3) {
							this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());
						} else if (this.frameCounter <= 13 && this.frameCounter > 3) {
							this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());
						} else {
							if(((this.frameCounter -13) % 18 ) < 6) {
								this.setCurrentSprite(Sprite.SPRITE_WALKING_2.ordinal());

							} else if(((this.frameCounter -13) % 18 ) < 12) {
								this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());

							} else {
								this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());

							}
						}	
					}
					
				} break;
				
				case STATE_RUNNING: {
					if(this.frameCounter <= 3) {
						this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());
					} else if (this.frameCounter <= 13 && this.frameCounter >3) {
						this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());
					} else if (this.frameCounter <= 19 && this.frameCounter >13) {
						this.setCurrentSprite(Sprite.SPRITE_WALKING_2.ordinal());
					} else if (this.frameCounter <= 23 && this.frameCounter >19) {
						this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());
					} else if (this.frameCounter <= 27 && this.frameCounter >23) {
						this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());
					} else if (this.frameCounter <= 30 && this.frameCounter >27) {
						this.setCurrentSprite(Sprite.SPRITE_WALKING_2.ordinal());
					} else {
						if(((this.frameCounter - 30) % 9 ) < 3) {
							this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());
						} else if(((this.frameCounter - 30) % 9 ) < 6) {
							this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());
						} else {
							this.setCurrentSprite(Sprite.SPRITE_WALKING_2.ordinal());
						}				
					}
	
				} break; 
				
				case STATE_WALKING_SKID_LEFT:
					 STATE_WALKING_SKID_RIGHT:
					 STATE_RUNNING_SKID_LEFT:
					 STATE_RUNNING_SKID_RIGHT: {
					 if(!b_isDucking) {
						 this.setCurrentSprite(Sprite.SPRITE_SKIDDING.ordinal());
						 
					 } else {
						 this.setCurrentSprite(Sprite.SPRITE_DUCKING.ordinal());
					 }
					} break;
				
				default:
				}
				
				
				
			}
			else {
				if(this.frameCounter <= 1) {
					this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());
				} else if (this.frameCounter <= 3 && this.frameCounter >2) {
					this.setCurrentSprite(Sprite.SPRITE_IDLE.ordinal());
				} else if (this.frameCounter == 4) {
					this.setCurrentSprite(Sprite.SPRITE_WALKING_1.ordinal());
				} else if (this.frameCounter <= 6 && this.frameCounter >5) {
					this.setCurrentSprite(Sprite.SPRITE_WALKING_2.ordinal());
				} else {

					if(((this.frameCounter - 6) % 5 ) < 2) {
						this.setCurrentSprite(Sprite.SPRITE_SPRINTING_1.ordinal());
				

					} else if(((this.frameCounter - 6) % 5 ) < 4) {
						this.setCurrentSprite(Sprite.SPRITE_SPRINTING_2.ordinal());


					} else if(((this.frameCounter - 6) % 5 ) < 5) {
						this.setCurrentSprite(Sprite.SPRITE_SPRINTING_3.ordinal());


					}

			}
			
		}
		}
		
	}
		
		
	//Movement functions.
	public void moveLeft()
	{
		//Update P-Meter.
		this.pMeter += Math.abs(this.dx) >= 2.25?2:-1;
		if(this.pMeter < 0) this.pMeter = 0;
		if(this.pMeter > 112) this.pMeter = 112;
		
		if(this.state_y == StateY.STATE_ON_GROUND)
		{
			if(this.pMeter == 112 && !this.b_isSprinting) this.setSprinting(true);
			if(this.pMeter < 112 && this.b_isSprinting) this.setSprinting(false);
		}
				
		//Update state.
		switch(this.state_x)
		{
			case STATE_STOPPED: 
			{
				//Player stopped. Start to move, if able.
				if(!this.b_isDucking || this.state_y != StateY.STATE_ON_GROUND)
				{
					//Start to move and set acceleration.
					if(!this.b_isRunning)
					{
						//Start to walk.
						this.setStateX(StateX.STATE_WALKING);
						this.ddx = -1*this.DDX_WALKING;
					}
					else
					{
						//Start to run.
						this.setStateX(StateX.STATE_RUNNING);
						this.ddx = -1*this.DDX_RUNNING;
					}
				}
				else
				{
					this.dx = 0;
					this.ddx = 0;
				}
				break;
			}
			case STATE_WALKING: 
			{				
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player currently walking. Check if motion is left or right.
				if(this.dx < 0)
				{
					//Motion is left. Continue accelerating.
				}
				else
				{
					//Motion is right. Start to skid left.
					this.setStateX(StateX.STATE_WALKING_SKID_LEFT);
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
					this.setStateX(StateX.STATE_RUNNING_SKID_LEFT);
					this.ddx = -1*this.DDX_SKID_DECEL_RUNNING;
				}
				break;
			}
			case STATE_WALKING_SKID_LEFT: 
			{
				if(this.b_isSprinting) this.setSprinting(false);				
				
				//Player is pressing left to skid; continue skidding until stopped.
				if(this.dx <= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.setStateX(StateX.STATE_STOPPED);
				}
				break;
			}
			case STATE_WALKING_SKID_RIGHT:
			{
				if(this.b_isSprinting) this.setSprinting(false);		
				
				//Motion is left. Player was pressing right to skid, but stopped. Resume left motion.
				this.setStateX(StateX.STATE_WALKING);
				this.ddx = -1*this.DDX_WALKING;
				break;
			}
			case STATE_RUNNING_SKID_LEFT: 
			{
				if(this.b_isSprinting) this.setSprinting(false);		
				
				//Player is pressing left to skid; continue skidding until stopped.
				if(this.dx <= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.setStateX(StateX.STATE_STOPPED);
				}
				break;
			}
			case STATE_RUNNING_SKID_RIGHT:
			{
				if(this.b_isSprinting) this.setSprinting(false);		
				
				//Motion is left. Player was pressing right to skid, but stopped. Resume left motion.
				this.setStateX(StateX.STATE_RUNNING);
				this.ddx = -1*this.DDX_RUNNING;
				break;
			}
			case STATE_STOPPING_LEFT: 
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player released left. Decelerate.
				this.ddx = this.DDX_STOPPING;
				if(this.dx >= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.setStateX(StateX.STATE_STOPPED);
				}
				break;
			}
			case STATE_STOPPING_RIGHT:
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player stopping right. Skid left.
				if(this.b_isRunning)
				{
					this.setStateX(StateX.STATE_RUNNING_SKID_LEFT);
					this.ddx = -1*this.DDX_SKID_DECEL_RUNNING;	
				}
				else
				{
					this.setStateX(StateX.STATE_WALKING_SKID_LEFT);
					this.ddx = -1*this.DDX_SKID_DECEL_WALKING;	
				}
				break;			
			}
			default:
		}
		
		if(this.b_isSprinting)
		{
			this.dxmax = this.DX_MAX_SPRINTING;
			this.dx = -1*this.DX_MAX_SPRINTING;
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
		if(this.dx == 0) this.setStateX(StateX.STATE_STOPPED);
	}
	
	public void moveRight()
	{
		//Update P-Meter.
		this.pMeter += Math.abs(this.dx) >= 2.25?2:-1;
		if(this.pMeter < 0) this.pMeter = 0;
		if(this.pMeter > 112) this.pMeter = 112;
		
		if(this.state_y == StateY.STATE_ON_GROUND)
		{
			if(this.pMeter == 112 && !this.b_isSprinting) this.setSprinting(true);
			if(this.pMeter < 112 && this.b_isSprinting) this.setSprinting(false);
		}
				
		//Update state.
		switch(this.state_x)
		{
			case STATE_STOPPED: 
			{
				//Player stopped. Start to move, if able.
				if(!this.b_isDucking || this.state_y != StateY.STATE_ON_GROUND)
				{
					//Start to move and set acceleration.	
					if(!this.b_isRunning)
					{
						//Start to walk.
						this.setStateX(StateX.STATE_WALKING);
						this.ddx = this.DDX_WALKING;
					}
					else
					{
						//Start to run.
						this.setStateX(StateX.STATE_RUNNING);
						this.ddx = this.DDX_RUNNING;
					}
				}
				else
				{
					this.dx = 0;
					this.ddx = 0;
				}
				break;
			}
			case STATE_WALKING: 
			{				
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player currently walking. Check if motion is left or right.
				if(this.dx > 0)
				{
					//Motion is right. Continue accelerating.
				}
				else
				{
					//Motion is left. Start to skid right.
					this.setStateX(StateX.STATE_WALKING_SKID_RIGHT);
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
					this.setStateX(StateX.STATE_RUNNING_SKID_RIGHT);
					this.ddx = this.DDX_SKID_DECEL_RUNNING;
				}
				break;
			}
			case STATE_WALKING_SKID_LEFT:
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Motion is right. Player was pressing left to skid, but stopped. Resume right motion.
				this.setStateX(StateX.STATE_WALKING);
				this.ddx = this.DDX_WALKING;
				break;
			}
			case STATE_WALKING_SKID_RIGHT: 
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player is pressing right to skid; continue skidding until stopped.
				if(this.dx >= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.setStateX(StateX.STATE_STOPPED);
				}
				break;
			}		
			case STATE_RUNNING_SKID_LEFT:
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Motion is right. Player was pressing left to skid, but stopped. Resume right motion.
				this.setStateX(StateX.STATE_RUNNING);
				this.ddx = this.DDX_RUNNING;
				break;
			}
			case STATE_RUNNING_SKID_RIGHT: 
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player is pressing right to skid; continue skidding until stopped.
				if(this.dx >= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.setStateX(StateX.STATE_STOPPED);
				}
				break;
			}
			case STATE_STOPPING_LEFT:
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player stopping left. Skid right.
				if(this.b_isRunning)
				{
					this.setStateX(StateX.STATE_RUNNING_SKID_RIGHT);
					this.ddx = this.DDX_SKID_DECEL_RUNNING;	
				}
				else
				{
					this.setStateX(StateX.STATE_WALKING_SKID_RIGHT);
					this.ddx = this.DDX_SKID_DECEL_WALKING;	
				}
				break;			
			}
			case STATE_STOPPING_RIGHT: 
			{
				if(this.b_isSprinting) this.setSprinting(false);	
				
				//Player released right. Decelerate.
				this.ddx = -1*this.DDX_STOPPING;
				if(this.dx <= 0)
				{
					//Player completely stopped.
					this.ddx = 0;
					this.dx = 0;
					this.setStateX(StateX.STATE_STOPPED);
				}
				break;
			}
			default:
		}
		
		if(this.b_isSprinting)
		{
			this.dxmax = this.dx = this.DX_MAX_SPRINTING;
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
		if(this.dx == 0) this.setStateX(StateX.STATE_STOPPED);
	}
	
	public void jump()
	{
		//Update state.
		switch(this.state_y)
		{
			case STATE_ON_GROUND:
			{
				//Player is on the ground. Do nothing, unless a jump is starting.
				if(this.b_isJumping)
				{
					//Player is starting a jump. Set initial vertical acceleration.
					this.state_y = StateY.STATE_JUMPING;
					this.ddy = -(4.8125 + Math.floor(Math.abs(0.0625*this.dx)) + Math.abs(0.25*this.dx));
				
					if(this.b_isSprinting)
					{
						this.setExtraSprite(0);	
					}
				}
				break;
			}
			case STATE_JUMPING:
			{
				//Player is currently jumping.
				this.ddy = this.DDY_GRAVITY_JUMPING;
				break;
			}
			case STATE_FALLING:
			{
				//Player is currently falling.
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
		if(this.y >= this.P_HEIGHT - this.IMAGE_HEIGHT) 
		{
			this.y = this.P_HEIGHT - this.IMAGE_HEIGHT;
			this.state_y = StateY.STATE_ON_GROUND;
			this.b_isJumping = false;
			this.dy = 0;
			this.ddy = 0;
			
			if(this.b_isDucking)
			{
				//Also stop horizontal motion if ducking.
				this.dx = 0;
				this.ddx = 0;
			}
		}
	}

	
	//Getters
	public BufferedImage getSpritesheet()
	{
		return bi_spritesheet;
	}
	
	public BufferedImage getCurrentSprite()
	{
		return bi_currentSprite;
	}
	
	public BufferedImage getExtraSprite()
	{
		return bi_extraSprite;
	}
	
	public int getCurrentSpriteID()
	{
		return currentSpriteID;
	}
	
	public boolean getDucking()
	{
		return b_isDucking;
	}
	
	public boolean getLookingUp()
	{
		return b_isLookingUp;
	}
	
	public int getFrameCounter()
	{
		return frameCounter;
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

	public double getDy()
	{
		return dy;
	}

	public double getDdx()
	{
		return ddx;
	}

	public double getDdy()
	{
		return ddy;
	}
	
	public boolean getRunning()
	{
		return b_isRunning;
	}
	
	public boolean getSprinting()
	{
		return b_isSprinting;
	}
	
	public boolean getJumping()
	{
		return b_isJumping;
	}
	
	public int getPMeter()
	{
		return pMeter;
	}
	
	public StateX getStateX()
	{
		return this.state_x;
	}
	
	public StateY getStateY()
	{
		return this.state_y;
	}

	
	//Setters
	public void setSpritesheet(BufferedImage spritesheet)
	{
		this.bi_spritesheet = spritesheet;
	}
	
	public void setCurrentSprite(int n)
	{
		this.bi_currentSprite = this.bi_spritesheet.getSubimage(this.IMAGE_WIDTH*(n%7), (int)(this.IMAGE_HEIGHT*(Math.floor(n/7))), IMAGE_WIDTH, IMAGE_HEIGHT);
		this.currentSpriteID = n;
		
		if(this.b_isFacingRight) {
			this.bi_currentSprite = this.getflippedSprite(this.bi_currentSprite);
		}
	}
	
	public void setExtraSprite(int n)
	{
		this.bi_extraSprite = this.bi_spritesheet.getSubimage(64 + 8*n, 48, 8, 16);
		if(this.b_isFacingRight) {
			this.bi_extraSprite = this.getflippedSprite(this.bi_extraSprite);
		}
	}
	
	public void setDucking(boolean isDucking)
	{
		this.b_isDucking = isDucking;
		
		//Also enter a stopped state.
		this.setStateX(this.dx>0?StateX.STATE_STOPPING_RIGHT:this.dx<0?StateX.STATE_STOPPING_LEFT:StateX.STATE_STOPPED);
			
	}
	
	public void setLookingUp(boolean isLookingUp)
	{
		this.b_isLookingUp = isLookingUp;
	}
	
	public void setFrameCounter(int frameCounter)
	{
		this.frameCounter = frameCounter;
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

	public void setDy(double dy)
	{
		this.dy = dy;
	}

	public void setDdx(double ddx)
	{
		this.ddx = ddx;
	}

	public void setDdy(double ddy)
	{
		this.ddy = ddy;
	}
	
	public void setRunning(boolean isRunning)
	{
		this.b_isRunning = isRunning;
		
		//Also update state, if needed.
		if(this.b_isRunning)
		{
			//Now running. If in a state of walking, change to the corresponding running state.
			switch(this.state_x)
			{
				case STATE_WALKING: 			this.setStateX(StateX.STATE_RUNNING); break;
				case STATE_WALKING_SKID_LEFT: 	this.setStateX(StateX.STATE_RUNNING_SKID_LEFT); break;
				case STATE_WALKING_SKID_RIGHT:	this.setStateX(StateX.STATE_RUNNING_SKID_RIGHT); break;
				default:
			}
			this.dxmax = this.DX_MAX_RUNNING;
		}
		else
		{
			//Now walking. If in a state of running, change to the corresponding walking state.
			switch(this.state_x)
			{
				case STATE_RUNNING: 			this.setStateX(StateX.STATE_WALKING); break;
				case STATE_RUNNING_SKID_LEFT: 	this.setStateX(StateX.STATE_WALKING_SKID_LEFT); break;
				case STATE_RUNNING_SKID_RIGHT:	this.setStateX(StateX.STATE_WALKING_SKID_RIGHT); break;
				default:
			}
			this.dxmax = this.DX_MAX_WALKING;
			
			//Also stop sprinting.
			this.setSprinting(false);
		}
	}
	
	public void setSprinting(boolean isSprinting)
	{
		this.b_isSprinting = isSprinting;
		
		if(this.b_isSprinting)
		{
			this.setExtraSprite(1);	
		}
	}
	
	public void setJumping(boolean isJumping)
	{
		this.b_isJumping = isJumping;
	}
	
	public void setPMeter(int pMeter)
	{
		this.pMeter = pMeter;
	}
	
	public void setStateX(StateX state_x)
	{
		this.state_x = state_x;
		
		//Also reset frame counter.
		this.resetFrameCounter();
	}
	
	public void setStateY(StateY state_y)
	{
		this.state_y = state_y;
	}


	public boolean getFacingRight() {
		return b_isFacingRight;
	}


	public void setFacingRight(boolean isFacingRight) {
		if(this.b_isFacingRight != isFacingRight) {
			this.bi_currentSprite = this.getflippedSprite(this.bi_currentSprite);
			this.bi_extraSprite = this.getflippedSprite(this.bi_extraSprite);
		}
		
		
		
		this.b_isFacingRight = isFacingRight;
	}
}

