package hundun.gdxgame.bugindustry.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hundun.gdxgame.bugindustry.BugIndustryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
	    BugIndustryGame game = new BugIndustryGame();
	    Runtime.getRuntime().addShutdownHook(new DesktopExitHookTask(game));
	    
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (game.LOGIC_WIDTH * game.desktopScale);
		config.height = (int) (game.LOGIC_HEIGHT * game.desktopScale);
		new LwjglApplication(game, config);
	}
}