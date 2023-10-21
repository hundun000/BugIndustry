package hundun.gdxgame.idleshare.gamelib.export;

import java.util.List;
import java.util.stream.Collectors;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler.ISubGameplaySaveHandler;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler.ISubSystemSettingSaveHandler;
import hundun.gdxgame.idleshare.gamelib.framework.IdleGameplayContext;
import hundun.gdxgame.idleshare.gamelib.framework.data.ChildGameConfig;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.SystemSettingSaveData;
import hundun.gdxgame.idleshare.gamelib.framework.model.achievement.IBuiltinAchievementsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.IBuiltinConstructionsLoader;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.IGameDictionary;
import hundun.gdxgame.idleshare.gamelib.framework.util.text.Language;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2023/02/21
 */
public class IdleGameplayExport implements ILogicFrameListener, 
        ISubGameplaySaveHandler<GameplaySaveData>, 
        ISubSystemSettingSaveHandler<SystemSettingSaveData>  {

    @Getter
    private final IdleGameplayContext gameplayContext;
    private final IBuiltinConstructionsLoader builtinConstructionsLoader;
    private final IBuiltinAchievementsLoader builtinAchievementsLoader;
    private final ChildGameConfig childGameConfig;
    @Getter
    private final IGameDictionary gameDictionary;
    @Setter
    @Getter
    private Language language;

    public String stageId;

    public IdleGameplayExport(
            IFrontend frontEnd, 
            IGameDictionary gameDictionary,
            IBuiltinConstructionsLoader builtinConstructionsLoader,
            IBuiltinAchievementsLoader builtinAchievementsLoader,
            int LOGIC_FRAME_PER_SECOND, ChildGameConfig childGameConfig) {
        this.gameDictionary = gameDictionary;
        this.builtinConstructionsLoader = builtinConstructionsLoader;
        this.builtinAchievementsLoader = builtinAchievementsLoader;
        this.childGameConfig = childGameConfig;
        this.gameplayContext = new IdleGameplayContext(frontEnd, gameDictionary, LOGIC_FRAME_PER_SECOND);
    }

    @Override
    public void onLogicFrame() {
        gameplayContext.getConstructionManager().onSubLogicFrame();
        gameplayContext.getStorageManager().onSubLogicFrame();
    }
    
    @Override

    
    public void applyGameplaySaveData(GameplaySaveData gameplaySaveData) {
        this.stageId = gameplaySaveData.getStageId();

        gameplaySaveData.getConstructionSaveDataMap().values().forEach(it -> {
            gameplayContext.getConstructionManager().loadInstance(it);
        });

        gameplayContext.getStorageManager().setUnlockedResourceTypes(gameplaySaveData.getUnlockedResourceTypes());
        gameplayContext.getStorageManager().setOwnResoueces(gameplaySaveData.getOwnResources());
        gameplayContext.getAchievementManager().subApplyGameplaySaveData(
                builtinAchievementsLoader.getProviderMap(language),
                gameplaySaveData.getAchievementSaveDataMap()
        );
    }

    @Override
    public void currentSituationToGameplaySaveData(GameplaySaveData gameplaySaveData) {
        gameplaySaveData.setStageId(this.stageId);
        List<BaseConstruction> constructions = gameplayContext.getConstructionManager().getAllConstructionInstances();
        gameplaySaveData.setConstructionSaveDataMap(constructions.stream()
                .collect(Collectors.toMap(
                        it -> it.getId(), 
                        it -> it.getSaveData()
                        ))
                );
        gameplaySaveData.setUnlockedResourceTypes(gameplayContext.getStorageManager().getUnlockedResourceTypes());
        gameplaySaveData.setOwnResources(gameplayContext.getStorageManager().getOwnResoueces());
        gameplaySaveData.setAchievementSaveDataMap(gameplayContext.getAchievementManager().getAchievementSaveDataMap());
    }

    @Override
    public void applySystemSetting(SystemSettingSaveData systemSettingSave) {
        this.language = systemSettingSave.getLanguage();
        gameplayContext.allLazyInit(
                systemSettingSave.getLanguage(), 
                childGameConfig,
                builtinConstructionsLoader.getProviderMap(language),
                builtinAchievementsLoader.getProviderMap(language)
                );
        gameplayContext.getFrontEnd().log(this.getClass().getSimpleName(), "applySystemSetting done");
    }

    @Override
    public void currentSituationToSystemSetting(SystemSettingSaveData systemSettingSave) {
        systemSettingSave.setLanguage(this.getLanguage());
    }

}
