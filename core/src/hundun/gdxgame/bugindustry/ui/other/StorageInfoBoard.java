package hundun.gdxgame.bugindustry.ui.other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.bugindustry.model.GameArea;
import hundun.gdxgame.bugindustry.model.ResourceType;
import hundun.gdxgame.bugindustry.ui.ILogicFrameListener;
import hundun.gdxgame.bugindustry.ui.screen.GameScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard extends Table {
    
    public static int BOARD_DISTANCE_TO_FRAME_TOP = 50;
    public static int BOARD_DISTANCE_TO_FRAME_SIDE = 10;
    public static int BOARD_HEIGHT = 50;
    
    Map<GameArea, List<ResourceType>> areaShownResources; 
    List<ResourceType> farmAreaShownResources;
    
    private void init() {
        areaShownResources = new HashMap<>();
        farmAreaShownResources = Arrays.asList(
                ResourceType.COIN,
                ResourceType.WOOD,
                ResourceType.BEE,
                ResourceType.HONEY
                );
        
        areaShownResources.put(GameArea.BEE_FARM, farmAreaShownResources);
        areaShownResources.put(GameArea.BEE_BUFF, Arrays.asList(
                ResourceType.GENE_POINT
                ));
        areaShownResources.put(GameArea.FOREST_FARM, farmAreaShownResources);
        
    }
    
    Label mainLabel;
    GameScreen parent;
    public StorageInfoBoard(GameScreen parent) {
        this.parent = parent;
        this.setBackground(parent.tableBackgroundDrawable);
        this.setBounds(
                BOARD_DISTANCE_TO_FRAME_SIDE, 
                Gdx.graphics.getHeight() - BOARD_DISTANCE_TO_FRAME_TOP - BOARD_HEIGHT, 
                Gdx.graphics.getWidth() - 2 * BOARD_DISTANCE_TO_FRAME_SIDE, 
                BOARD_HEIGHT);
        this.mainLabel = new Label("", parent.game.getButtonSkin());
        this.add(mainLabel);
        
        init();
    }

    public void updateViewData() {
        List<ResourceType> shownResources = areaShownResources.get(parent.getArea());
        if (shownResources == null) {
            mainLabel.setText("Unkonwn area");
            return;
        }
        
        String text = shownResources.stream()
                .map(shownResource -> parent.game.getModelContext().getStorageManager().getResourceDescription(shownResource))
                .collect(Collectors.joining("    "));
        text += "\nBuffs = " + parent.game.getModelContext().getBuffManager().getBuffAmounts();
        mainLabel.setText(text);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateViewData();
        super.draw(batch, parentAlpha);
    }
    
    
}
