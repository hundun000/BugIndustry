package hundun.gdxgame.idledemo.ui.sub;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hundun.gdxgame.gamelib.starter.listerner.IGameAreaChangeListener;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.core.starter.ui.screen.play.BaseIdlePlayScreen;
import hundun.gdxgame.idleshare.gamelib.framework.model.manager.AchievementManager.AchievementInfoPackage;

import java.util.List;

public class FirstLockedAchievementBoardVM<T_GAME extends BaseIdleGame<T_SAVE>, T_SAVE> extends Table implements IGameAreaChangeListener {

    BaseIdlePlayScreen<T_GAME, T_SAVE> parent;

    Label nameStartLabel;
    Label nameValueLabel;
    Label descriptionLabel;
    Label countStartLabel;
    Label countValueLabel;

    List<String> texts;

    public FirstLockedAchievementBoardVM(BaseIdlePlayScreen<T_GAME, T_SAVE> parent)
    {
        this.parent = parent;
        this.texts = parent.getGame().getIdleGameplayExport().getGameDictionary()
                .getAchievementTexts(parent.getGame().getIdleGameplayExport().getLanguage());
        this.setBackground(new TextureRegionDrawable(parent.getGame().getTextureManager().getDefaultBoardNinePatchTexture()));


        nameStartLabel = new Label("", parent.getGame().getMainSkin());
        this.add(nameStartLabel).center();
        nameValueLabel = new Label("", parent.getGame().getMainSkin());
        this.add(nameValueLabel).center().row();
        descriptionLabel = new Label("", parent.getGame().getMainSkin());
        this.add(descriptionLabel).center().row();
        countStartLabel = new Label("", parent.getGame().getMainSkin());
        this.add(countStartLabel).center().row();
        countValueLabel = new Label("", parent.getGame().getMainSkin());
        this.add(countValueLabel).center().row();

        nameStartLabel.setText(texts.get(0));
        countStartLabel.setText(texts.get(1));
        updateData();
    }

    private void updateData()
    {
        AchievementInfoPackage data = parent.getGame().getIdleGameplayExport().getGameplayContext().getAchievementManager().getAchievementInfoPackage();
        if (data.getFirstLockedAchievement() != null)
        {
            nameValueLabel.setText(data.getFirstLockedAchievement().getName());
            descriptionLabel.setText(data.getFirstLockedAchievement().getDescription());
        }
        else
        {
            nameValueLabel.setText("");
            descriptionLabel.setText(texts.get(5));
        }

        countValueLabel.setText(" " + data.getUnLockedSize() + "/" + data.getTotal());


    }

    @Override
    public void onGameAreaChange(String last, String current) {
        updateData();
    }
}
