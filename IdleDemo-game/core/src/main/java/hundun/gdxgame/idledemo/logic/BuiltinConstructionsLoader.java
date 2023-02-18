package hundun.gdxgame.idledemo.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.idledemo.DemoIdleGame;
import hundun.gdxgame.idleshare.core.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.gamelib.context.IdleGamePlayContext;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseAutoConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseBuffConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseClickGatherConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.LevelComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.OutputComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.UpgradeComponent;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePack;
import hundun.gdxgame.idleshare.gamelib.framework.model.resource.ResourcePair;


public class BuiltinConstructionsLoader {

    public static List<BaseConstruction> initProviders() {
        List<BaseConstruction> constructions = new ArrayList<>();
        // clicker-provider
        {
            BaseConstruction construction = new BaseClickGatherConstruction(ConstructionId.COOKIE_CLICK_PROVIDER);
            construction.detailDescroptionConstPart = "Click gain some cookie";
            construction.descriptionPackage = BaseConstruction.GATHER_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COOKIE, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            constructions.add(construction);
        }
        // auto-provider
        {
            BaseConstruction construction = new BaseAutoConstruction(ConstructionId.COOKIE_AUTO_PROVIDER);
            construction.detailDescroptionConstPart = "Auto gain some cookie";
            construction.descriptionPackage = BaseConstruction.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputGainPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COOKIE, 1
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COIN, 25
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);
            
            construction.setMaxDrawNum(9);
            constructions.add(construction);
        }
        // seller
        {
            BaseConstruction construction = new BaseAutoConstruction(ConstructionId.COOKIE_SELLER);
            construction.detailDescroptionConstPart = "Auto sell cookies";
            construction.descriptionPackage = BaseConstruction.WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
            outputComponent.setOutputCostPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COOKIE, 1
                    )));
            outputComponent.setOutputGainPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COIN, 5
                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COIN, 500
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, true);
            construction.setLevelComponent(levelComponent);
            
            construction.setMaxDrawNum(9);
            constructions.add(construction);
        } 
        // win
        {
            BaseConstruction construction = new BaseBuffConstruction(ConstructionId.WIN_PROVIDER, BuffId.WIN);
            construction.detailDescroptionConstPart = "Get a trophy and win the game";
            construction.descriptionPackage = BaseConstruction.WIN_DESCRIPTION_PACKAGE;
            
            OutputComponent outputComponent = new OutputComponent(construction);
//            outputComponent.setOutputCostPack(toPack(JavaFeatureForGwt.mapOf(
//                    ResourceType.COIN, 500
//                    )));
//            outputComponent.setOutputGainPack(toPack(JavaFeatureForGwt.mapOf(
//                    ResourceType.WIN_TROPHY, 1
//                    )));
            construction.setOutputComponent(outputComponent);
            
            UpgradeComponent upgradeComponent = new UpgradeComponent(construction);
            upgradeComponent.setUpgradeCostPack(toPack(JavaFeatureForGwt.mapOf(
                    ResourceType.COIN, 500
                    )));
            construction.setUpgradeComponent(upgradeComponent);
            
            LevelComponent levelComponent = new LevelComponent(construction, false);
            construction.setLevelComponent(levelComponent);            
            
            construction.setMaxLevel(1);
            constructions.add(construction);
        }
        return constructions;
    }
    
    private static ResourcePack toPack(Map<String, Integer> map) {
        ResourcePack pack = new ResourcePack();
        List<ResourcePair> pairs = new ArrayList<>(map.size());
        map.entrySet().forEach(entry -> pairs.add(new ResourcePair(entry.getKey(), (long)entry.getValue())));
        pack.setBaseValues(pairs);
        return pack;
    }
    
    

}
