package gameover;

import logic.Integrator;
import objects.PowerProduction;
import graphics.UIButton;
import graphics.UIComponent;
import graphics.UIText;

public class EndPage extends EndObject {
	
	UIText title;
	UIText yourScore;
	UIButton saveScore;
	
	public EndPage(int x, int y) {
		UIText title = new UIText(x, y, 500, 100);
		UIText yourScore = new UIText(x, y+title.getY()+15, 500, 100);
		UIButton saveScore = new UIButton(x, yourScore.getY()+yourScore.getHeight()+15, 500, 100);
		
		title.setText("GAME OVER");
		yourScore.setText("Your score: "+PowerProduction.powerProduced);
		saveScore.setText("Save score and continue...");
		
		ui.add(title);
		ui.add(yourScore);
		ui.add(saveScore);
		
		for(UIComponent comp: ui){
			comp.setVisible(false);
		}
	}
	
	public void update() {
		if (Integrator.gameover) {
			for(UIComponent comp: ui){
				comp.setVisible(true);
			}
		}
		if (saveScore.clicked) {
			//call the next class where the score can be entered
		}
	}
}
