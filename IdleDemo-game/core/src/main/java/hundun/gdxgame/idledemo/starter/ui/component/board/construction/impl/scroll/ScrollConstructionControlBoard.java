package hundun.gdxgame.idledemo.starter.ui.component.board.construction.impl.scroll;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.idledemo.BaseIdleGame;
import hundun.gdxgame.idledemo.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.idledemo.starter.ui.component.board.construction.impl.StarterConstructionControlNode;
import hundun.gdxgame.idledemo.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.callback.ISecondaryInfoBoardCallback;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ScrollConstructionControlBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends AbstractConstructionControlBoard<T_GAME, T_SAVE> {



    public final int LR_BUTTON_HEIGHT;
    public final int LR_BUTTON_WIDTH = 10;


    static final int NUM_NODE_MIN = 1;

    ImageButton leftButton;
    ImageButton rightButton;
    Table childTable;




    public ScrollConstructionControlBoard(BaseIdleScreen<T_GAME, T_SAVE> parent, ISecondaryInfoBoardCallback<BaseConstruction> callback) {
        super(parent, callback);

        this.LR_BUTTON_HEIGHT = parent.getLayoutConst().CONSTRUCION_BOARD_ROOT_BOX_HEIGHT;


        leftButton = new ImageButton(DrawableFactory.createBorderBoard(LR_BUTTON_WIDTH, LR_BUTTON_HEIGHT, 0.8f, 3));
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        rightButton = new ImageButton(DrawableFactory.createBorderBoard(LR_BUTTON_WIDTH, LR_BUTTON_HEIGHT, 0.8f, 3));
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });

        childTable = new Table();
        childTable.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());
        ScrollPane scrollPane = new ScrollPane(childTable, parent.getGame().getMainSkin());
        scrollPane.setScrollingDisabled(false, true);


        this.add(leftButton);
        this.add(scrollPane);
        this.add(rightButton);
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());

        if (parent.getGame().debugMode) {
            this.debugCell();
        }
    }

    @Override
    protected int initChild(int areaShownConstructionsSize) {

        constructionControlNodes.clear();
        childTable.clearChildren();

        for (int i = 0; i < areaShownConstructionsSize; i++) {
            StarterConstructionControlNode<T_GAME, T_SAVE> constructionView = new StarterConstructionControlNode<>(parent, callback, i, parent.getLayoutConst());
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }
        return areaShownConstructionsSize;
    }




}
