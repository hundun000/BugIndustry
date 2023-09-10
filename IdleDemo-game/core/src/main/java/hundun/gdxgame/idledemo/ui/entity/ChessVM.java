package hundun.gdxgame.idledemo.ui.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idledemo.ui.screen.ForestPlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import lombok.Getter;


public class ChessVM extends Table {

    DemoIdleGame game;

    DeskAreaVM deskAreaVM;
    @Getter
    BaseConstruction deskData;
    public ForestPlayScreen parent;

    Label mainLabel;
    Image image;

    public ChessVM(ForestPlayScreen parent, DeskAreaVM deskAreaVM, BaseConstruction deskData) {
        this.game = deskAreaVM.screen.getGame();
        this.deskAreaVM = deskAreaVM;
        this.deskData = deskData;


        this.image = new Image();
        image.setBounds(
                0,
                0,
                deskAreaVM.screen.getLayoutConst().DESK_WIDTH,
                deskAreaVM.screen.getLayoutConst().DESK_HEIGHT
        );
        this.addActor(image);
        /*this.setBackground(new TextureRegionDrawable(new TextureRegion(TextureFactory.getSimpleBoardBackground(
                this.game.getScreenContext().getLayoutConst().DESK_WIDTH,
                this.game.getScreenContext().getLayoutConst().DESK_HEIGHT
        ))));*/

        this.mainLabel = new Label("", game.getMainSkin());
        this.add(mainLabel);

        updateUI();
    }



    public void updateUI(){

        this.mainLabel.setText(deskData.getId());
        image.setDrawable(new TextureRegionDrawable(game.getTextureManager().getConstructionHexImage(deskData.getPrototypeId())));
        Vector2 uiPosition = calculatePosition(deskData.getSaveData().getPosition().getX(), deskData.getSaveData().getPosition().getY());
        this.setBounds(
                uiPosition.x,
                uiPosition.y,
                deskAreaVM.screen.getLayoutConst().DESK_WIDTH,
                deskAreaVM.screen.getLayoutConst().DESK_HEIGHT
        );
    }

    private static Vector2 calculatePosition(int gridX, int gridY)
    {
        float hexBaseSize = 100;
        Vector2 newposition = new Vector2(0, 0);
        newposition.y += hexBaseSize * 0.75f * gridY;
        newposition.x += hexBaseSize * Math.sqrt(3.0f) / 2  * (gridX - (Math.abs(gridY) % 2) / 2.0f);
        return newposition;
    }


}