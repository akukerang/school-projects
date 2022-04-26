package fl.ed.suncoast.swing.runner;

import javax.swing.JFrame;

//The main frame. Program starts here.
public class RunnerFrame 
{
	//Program starts and stops here.
	//Adds the main frame and puts a panel on it.
	public static void main(String[] args)
	{
		JFrame jf_main = new JFrame("no");
		jf_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add panel to frame.
		jf_main.getContentPane().add(new RunnerPanel());
		
		//Pack and set visible.
		jf_main.pack();
		jf_main.setResizable(false);
		jf_main.setVisible(true);
		
		//Program continues to run until frame is closed.
	}
}
