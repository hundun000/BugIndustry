package hundun.gdxgame.idleshare.starter.ui.component.board.construction.impl.fixed;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.idleshare.starter.ui.component.board.construction.impl.ConstructionControlNode;
import hundun.gdxgame.idleshare.starter.ui.screen.play.BaseIdlePlayScreen;



/**
 * @author hundun
 * Created on 2021/11/05
 */
public class FixedConstructionControlBoard<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends AbstractConstructionControlBoard<T_GAME, T_SAVE> {


    public static int FIXED_NODE_NUM = 5;

    Table childTable;


    public FixedConstructionControlBoard(BaseIdlePlayScreen<T_GAME, T_SAVE> parent) {

        super(parent);

        childTable = new Table();
        childTable.setBackground(parent.getLayoutConst().simpleBoardBackgroundMiddle);

        this.add(childTable);

        this.setBackground(parent.getLayoutConst().simpleBoardBackground);

        if (parent.getGame().debugMode) {
            this.debugCell();
        }
    }

    @Override
    protected int initChild(int areaShownConstructionsSize) {
        int childrenSize = FIXED_NODE_NUM;

        constructionControlNodes.clear();
        childTable.clearChildren();

        for (int i = 0; i < childrenSize; i++) {
            ConstructionControlNode constructionView = new ConstructionControlNode(parent, i, parent.getLayoutConst());
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }

        return childrenSize;

    }





}
