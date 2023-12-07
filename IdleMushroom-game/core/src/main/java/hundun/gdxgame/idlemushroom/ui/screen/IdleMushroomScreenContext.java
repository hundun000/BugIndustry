package hundun.gdxgame.idlemushroom.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.DemoScreenId;
import hundun.gdxgame.idlemushroom.ui.main.GameEntityFactory.IdleMushroomGameEntityFactoryLayoutConst;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2023/02/17
 */
public class IdleMushroomScreenContext {

    protected IdleMushroomGame game;
    IdleMushroomMenuScreen menuScreen;
    @Getter
    IdleMushroomMainPlayScreen mainPlayScreen;
    WorldPlayScreen worldPlayScreen;
    DemoAchievementScreen achievementScreen;



    public static class IdleMushroomPlayScreenLayoutConst {
        public final float DESK_WIDTH = 100;
        public final float DESK_HEIGHT = 100;
        public int CONSTRUCION_BOARD_ROOT_BOX_HEIGHT = 200;
        public int CONSTRUCION_CHILD_WIDTH = 100;
        public int CONSTRUCION_CHILD_BUTTON_HEIGHT = 30;
        public int CONSTRUCION_CHILD_NAME_HEIGHT = 50;

        public int STORAGE_BOARD_BORDER_HEIGHT = 60;
        public int AREA_BOARD_BORDER_WIDTH = 100;
        public int AREA_BOARD_CELL_HEIGHT = 50;
        //    public static final int STORAGE_BOARD_CONTAIN_WIDTH = 100;
//    public static final int STORAGE_BOARD_CONTAIN_HEIGHT = 50;

        IdleMushroomGameEntityFactoryLayoutConst gameEntityFactoryLayoutConst;

        public int RESOURCE_AMOUNT_PAIR_NODE_HEIGHT = 25;
        public int RESOURCE_AMOUNT_PAIR_NODE_WIDTH = 70;
        public int FIRST_LOCKED_ACHIEVEMENT_BOARD_WIDTH = 200;
        //public int FIRST_LOCKED_ACHIEVEMENT_BOARD_HEIGHT = 150;

        public int ALL_ACHIEVEMENT_BOARD_SCROLL_BORDER_OFFSET = 40;
        public int ALL_ACHIEVEMENT_BOARD_NODE_WIDTH = 400;
        public int ALL_ACHIEVEMENT_BOARD_NODE_HEIGHT = 150;
        public int WorldConstructionCellDetailNodeWidth = 800;
        public int WorldConstructionCellDetailNodeHeight = CONSTRUCION_BOARD_ROOT_BOX_HEIGHT - 20;
        public float popupInfoBoardWidth = 400;
        public float popupInfoBoardHeight = 200;
        public float WorldConstructionCellTablePad = 20;

        public float questionMarkAreaSize = 32;
        public int EPOCH_PART_CHILD_WIDTH = 150;
        public int SELLER_PART_CHILD_WIDTH = 150;
        public int EpochInfoArea_CHILD_WIDTH = 200;
        public float menuButtonWidth = 200;
        public float menuButtonHeight = 75;

        public IdleMushroomPlayScreenLayoutConst(int gameLogicWidth, int gameLogicHeight) {
            this.gameEntityFactoryLayoutConst = IdleMushroomGameEntityFactoryLayoutConst.builder()
                    .EXPECTED_DRAW_MIN_X(0)
                    .EXPECTED_DRAW_MAX_X(gameLogicWidth)
                    .EXPECTED_DRAW_MIN_Y(CONSTRUCION_BOARD_ROOT_BOX_HEIGHT)
                    .EXPECTED_DRAW_MAX_Y(gameLogicHeight - STORAGE_BOARD_BORDER_HEIGHT)
                    .build();

            CONSTRUCION_CHILD_WIDTH = 150;
        }

    }

    public IdleMushroomScreenContext(IdleMushroomGame game) {
        this.game = game;
    }

    public void lazyInit() {
        this.menuScreen = new IdleMushroomMenuScreen(game);
        this.mainPlayScreen = new IdleMushroomMainPlayScreen(game);
        this.worldPlayScreen = new WorldPlayScreen(game);
        this.achievementScreen = new DemoAchievementScreen(game);

        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MENU, menuScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_MAIN, mainPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_WORLD, worldPlayScreen);
        game.getScreenManager().addScreen(DemoScreenId.SCREEN_ACHIEVEMENT, achievementScreen);
    }

}
