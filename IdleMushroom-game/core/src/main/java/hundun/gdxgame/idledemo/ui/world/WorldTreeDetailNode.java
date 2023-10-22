package hundun.gdxgame.idledemo.ui.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.ui.screen.BaseDemoPlayScreen;
import hundun.gdxgame.idledemo.ui.screen.WorldPlayScreen;
import hundun.gdxgame.idledemo.ui.shared.BaseCellDetailNodeVM;
import hundun.gdxgame.idleshare.core.starter.ui.component.board.construction.impl.StarterConstructionControlNode.StarterSecondaryInfoBoardCallerClickListener;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class WorldTreeDetailNode extends BaseCellDetailNodeVM {
    BaseDemoPlayScreen parent;
    BaseConstruction model;
    Label constructionNameLabel;

    Label workingLevelLabel;
    Label proficiencyLabel;
    Label positionLabel;

    TextButton upgradeButton;
    TextButton transformButton;




    public WorldTreeDetailNode(
            WorldPlayScreen parent
            ) {
        super();
        final PlayScreenLayoutConst playScreenLayoutConst = parent.getLayoutConst();
        this.parent = parent;



        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.getGame().getMainSkin());
        constructionNameLabel.setWrap(true);


        this.upgradeButton = new TextButton("", parent.getGame().getMainSkin());
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(WorldTreeDetailNode.class.getSimpleName(), "upgradeButton changed");
                model.getUpgradeComponent().doUpgrade();
            }
        });

        this.workingLevelLabel = new Label("", parent.getGame().getMainSkin());
        workingLevelLabel.setAlignment(Align.center);




        this.proficiencyLabel = new Label("", parent.getGame().getMainSkin());
        this.positionLabel = new Label("", parent.getGame().getMainSkin());

        this.transformButton = new TextButton("-", parent.getGame().getMainSkin());
        transformButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getGame().getFrontend().log(this.getClass().getSimpleName(), "transformButton clicked");
                model.getUpgradeComponent().doTransform();
            }
        });

        Container<?> questionMarkArea = new Container<>(new Image(parent.getGame().getIdleMushroomTextureManager().getQuestionMarkTexture()));
        questionMarkArea.setBackground(parent.getGame().getIdleMushroomTextureManager().getDefaultBoardNinePatchDrawable());
        questionMarkArea.setTouchable(Touchable.enabled);
        questionMarkArea.addListener(new StarterSecondaryInfoBoardCallerClickListener(() -> model, parent));

        // ------ leftPart ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT);
        this.add(questionMarkArea);
        this.row();
        this.add(workingLevelLabel).size(CHILD_WIDTH, CHILD_HEIGHT);
        this.add(upgradeButton).size(CHILD_WIDTH, CHILD_HEIGHT);
        this.row();
        this.add(proficiencyLabel).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(transformButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.setBackground(DrawableFactory.createBorderBoard(30, 10, 0.8f, 1));

    }







    private void update() {
        // ------ update show-state ------
        if (model == null) {
            setVisible(false);
            //textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            //textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getName() + " set to its view");
        }
        // ------ update text ------
        constructionNameLabel.setText(JavaFeatureForGwt.stringFormat(
                "%s (%s, %s)",
                model.getName(),
                model.getSaveData().getPosition().getX(),
                model.getSaveData().getPosition().getY()
        ));
        upgradeButton.setText(model.getDescriptionPackage().getUpgradeButtonText());
        workingLevelLabel.setText(model.getLevelComponent().getWorkingLevelDescription());
        proficiencyLabel.setText(model.getProficiencyComponent().getProficiencyDescroption());
        positionLabel.setText(model.getSaveData().getPosition().toShowText());

        // ------ update clickable-state ------
        if (model.getUpgradeComponent().canUpgrade()) {
            upgradeButton.setDisabled(false);
            upgradeButton.getLabel().setColor(Color.WHITE);
        } else {
            upgradeButton.setDisabled(true);
            upgradeButton.getLabel().setColor(Color.RED);
        }
        if (model.getUpgradeComponent().canTransfer())
        {
            transformButton.setDisabled(false);
            transformButton.getLabel().setColor(Color.WHITE);
        }
        else
        {
            transformButton.setDisabled(true);
            transformButton.getLabel().setColor(Color.RED);
        }

        // ------ update model ------
        //model.onLogicFrame();

    }

    @Override
    public void updateForNewConstruction(BaseConstruction construction, GridPosition position) {
        this.model = construction;
        update();
    }

    @Override
    public void subLogicFrame() {
        update();
    }

}
