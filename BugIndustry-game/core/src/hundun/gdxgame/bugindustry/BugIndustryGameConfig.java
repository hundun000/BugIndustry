package hundun.gdxgame.bugindustry;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.bugindustry.logic.ConstructionId;
import hundun.gdxgame.bugindustry.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.bugindustry.logic.GameArea;
import hundun.gdxgame.bugindustry.logic.ResourceType;
import hundun.gdxgame.bugindustry.logic.ScreenId;
import hundun.gdxgame.bugindustry.ui.screen.PlayScreen;
import hundun.gdxgame.idleframe.data.ChildGameConfig;
import hundun.gdxgame.idleframe.data.StarterData;
import hundun.gdxgame.idleframe.model.AchievementPrototype;
import hundun.gdxgame.idlestarter.ConstructionsFileLoader;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class BugIndustryGameConfig extends ChildGameConfig {
    
    public BugIndustryGameConfig(BugIndustryGame game) {
//        File configFile = Gdx.files.internal("constructions.json").file();
//        ConstructionsFileLoader constructionsLoader = new ConstructionsFileLoader(game, game.getGameDictionary(), configFile);
        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.BEE_FARM, Arrays.asList(
            ConstructionId.BEE_GATHER_HOUSE,
            ConstructionId.SMALL_BEEHIVE,
            ConstructionId.MID_BEEHIVE,
            ConstructionId.BIG_BEEHIVE,
            ConstructionId.QUEEN_BEEHIVE
        ));
        areaShownConstructionIds.put(GameArea.FOREST_FARM, Arrays.asList(
            ConstructionId.WOOD_GATHER_HOUSE,
            ConstructionId.WOOD_KEEPING,
            ConstructionId.WOOD_BOARD_MAKER,
            ConstructionId.WIN_THE_GAME
        ));
        areaShownConstructionIds.put(GameArea.SHOP, Arrays.asList(
            ConstructionId.WOOD_SELL_HOUSE,
            ConstructionId.WOOD_BOARD_SELL_HOUSE,
            ConstructionId.BEE_SELL_HOUSE,
            ConstructionId.HONEY_SELL_HOUSE,
            ConstructionId.BEEWAX_SELL_HOUSE
        ));
        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.BEE_FARM, Arrays.asList(
            ResourceType.BEE
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        var starterData = new StarterData();
        var constructionStarterLevelMap = Map.of(ConstructionId.WOOD_SELL_HOUSE, 1);
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        var constructionStarterWorkingLevelMap = Map.of(ConstructionId.WOOD_SELL_HOUSE, Boolean.FALSE);
        starterData.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
        this.setStarterData(starterData); 
        
        Map<String, String> screenIdToFilePathMap = Map.of(
                ScreenId.MENU, "audio/Loop-Menu.wav",
                ScreenId.PLAY, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        var achievementPrototypes = Arrays.asList(
                new AchievementPrototype("Game win", "You win the game!",
                        null,
                        Map.of(ResourceType.WIN_TROPHY, 1)
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }
}