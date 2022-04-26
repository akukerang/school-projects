package fl.ed.suncoast.swing.jumping.animated;

import javax.swing.JFrame;

//The main frame. Program starts here.
public class JumpingFrame 
{
	//Program starts and stops here.
	//Adds the main frame and puts a panel on it.
	public static void main(String[] args)
	{
		JFrame jf_main = new JFrame("Super IBCS3 World");
		jf_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add panel to frame.
		jf_main.getContentPane().add(new JumpingPanel());
		
		//Pack and set visible.
		jf_main.pack();
		jf_main.setResizable(false);
		jf_main.setVisible(true);
	}
}
