package hundun.gdxgame.idlemushroom.ui.main;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idlemushroom.ui.achievement.OneAchievementNodeVM;
import hundun.gdxgame.idlemushroom.ui.shared.BaseIdleMushroomScreen;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdleScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementInfoPackage;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementState;

import java.util.List;

public class FirstRunningAchievementBoardVM extends Table {

    BaseIdleMushroomScreen parent;


    OneAchievementNodeVM node;

    Label nameStartLabel;
    Label nameValueLabel;
    Label descriptionLabel;
    Label countStartLabel;
    Label countValueLabel;

    List<String> texts;

    public FirstRunningAchievementBoardVM(BaseIdleMushroomScreen parent)
    {
        this.parent = parent;
        this.texts = parent.getGame().getIdleGameplayExport().getGameDictionary()
                .getAchievementTexts(parent.getGame().getIdleGameplayExport().getLanguage());
        this.setBackground(parent.getGame().getTextureManager().getDefaultBoardNinePatchDrawable());


        this.node = new OneAchievementNodeVM(parent, null, false);
        this.add(node);
        updateData();
    }

    public void updateData()
    {
        if (node.getAchievementAndStatus() == null
                || node.getAchievementAndStatus().getSaveData().getState() != AchievementState.RUNNING) {
            AchievementInfoPackage data = parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getAchievementInfoPackage();
            node.setAchievementAndStatus(data.getFirstRunningAchievement());
        }
        node.updateData();
    }

}
