public class bot{
	public String[] current;
	public bot(String difficulty) {
		difficulty = formatString(difficulty);
		current = chooseDifficulty(difficulty);
	}
	
	public static String[] chooseDifficulty(String difficulty) { // Paper > Rock; Scissor > Paper; Rock > Scissor; God > all;
		if(difficulty.equals("Easy")) {
			return new String[] {"Rock", "Paper", "Scissor"};
		} else if(difficulty.equals("Hard")) {
			return new String[] {"God", "God", "God", "God", "God",
					"God", "God", "God", "God", "God", "God", "God", 
					"God", "God", "God", "God", "God", "God", "God",
					"God","God","God","God","God","God", "Rock", "Paper", "Scissor"};
		} else {
			return new String[] {"Rock","Paper","Scissor"};
		}
		
	}
	
	public String formatString(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
}
