package hundun.gdxgame.idleframe.model.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.idleframe.BaseIdleGame;
import hundun.gdxgame.idleframe.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleframe.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.idleframe.model.entity.GameEntity;

/**
 * 管理GameEntity在内存中的数量，不负责绘制。
 * @author hundun
 * Created on 2021/12/04
 */
public class GameEntityManager {
    
    private BaseIdleGame game;
    
    private Map<String, Queue<GameEntity>> gameEntitiesOfConstructionIds = new HashMap<>();
    // ------ replace-lombok ------
    public Map<String, Queue<GameEntity>> getGameEntitiesOfConstructionIds() {
        return gameEntitiesOfConstructionIds;
    }
    
    private Map<String, Queue<GameEntity>> gameEntitiesOfResourceIds = new HashMap<>();
    // ------ replace-lombok ------
    public Map<String, Queue<GameEntity>> getGameEntitiesOfResourceIds() {
        return gameEntitiesOfResourceIds;
    }
    
    private Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds;
    // ------ replace-lombok ------
    public Map<String, List<String>> getAreaShowEntityByOwnAmountConstructionIds() {
        return areaShowEntityByOwnAmountConstructionIds;
    }
    
    private Map<String, List<String>> areaShowEntityByOwnAmountResourceIds;
    // ------ replace-lombok ------
    public Map<String, List<String>> getAreaShowEntityByOwnAmountResourceIds() {
        return areaShowEntityByOwnAmountResourceIds;
    }
    
    private Map<String, List<String>> areaShowEntityByChangeAmountResourceIds;
    // ------ replace-lombok ------
    public Map<String, List<String>> getAreaShowEntityByChangeAmountResourceIds() {
        return areaShowEntityByChangeAmountResourceIds;
    }
    
    public GameEntityManager(BaseIdleGame game) {
        super();
        this.game = game;
    }
    
    public void allEntityMoveForFrame() {
        for (Entry<String, Queue<GameEntity>> entry : gameEntitiesOfConstructionIds.entrySet()) {
            Queue<GameEntity> queue = entry.getValue();
            queue.forEach(entity -> {
                entity.frameLogic();
                positionChange(entity);
            });
        }
        
        for (Entry<String, Queue<GameEntity>> entry : gameEntitiesOfResourceIds.entrySet()) {
            Queue<GameEntity> queue = entry.getValue();
            queue.removeIf(entity -> {
                boolean remove = entity.checkRemove();
                if (remove) {
                    Gdx.app.log(this.getClass().getSimpleName(), "entity removed by self check");
                }
                return remove;
                });
            queue.forEach(entity -> {
                entity.frameLogic();
                positionChange(entity);
            });

        }
    }
    
    public void areaEntityCheckByOwnAmount(String gameArea, BaseGameEntityFactory gameEntityFactory) {
        List<String> shownConstructionIds = this.areaShowEntityByOwnAmountConstructionIds.get(gameArea);
        if (shownConstructionIds != null) {
            for (String shownConstructionId : shownConstructionIds) {
                checkConstructionEntityByOwnAmount(shownConstructionId, gameEntityFactory);
            }
        }
        
        List<String> shownResourceIds = this.areaShowEntityByOwnAmountResourceIds.get(gameArea);
        if (shownResourceIds != null) {
            for (String resourceId : shownResourceIds) {
                checkResourceEntityByOwnAmount(resourceId, gameEntityFactory);
            }
        }
    }
    
    public void areaEntityCheckByChangeAmount(String gameArea, BaseGameEntityFactory gameEntityFactory, Map<String, Long> changeMap) {

        List<String> shownResourceIds = this.areaShowEntityByChangeAmountResourceIds.get(gameArea);
        if (shownResourceIds != null) {
            for (String resourceId : shownResourceIds) {
                if (changeMap.getOrDefault(resourceId, 0L) > 0) {
                    addResourceEntityByChangeAmount(resourceId, gameEntityFactory, changeMap.get(resourceId).intValue());
                }
            }
        }
    }
    
    private void positionChange(GameEntity entity) {
        if (entity.isMoveable()) {
            entity.setX(entity.getX() + entity.getSpeedX());
            entity.setY(entity.getY() + entity.getSpeedY());
        }
    }
    
    private void checkResourceEntityByOwnAmount(String resourceId, BaseGameEntityFactory gameEntityFactory) {
        long resourceNum = game.getModelContext().getStorageManager().getResourceNumOrZero(resourceId);
        int drawNum = gameEntityFactory.calculateResourceDrawNum(resourceId, resourceNum);
        
        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new LinkedList<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkResourceEntityByOwnAmount " + resourceId + " remove for " + gameEntities.size() + " -> " + drawNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId, newIndex);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                Gdx.app.log(this.getClass().getSimpleName(), "checkResourceEntityByOwnAmount " + resourceId + " new for " + gameEntities.size() + " -> " + drawNum);
            } else {
                break;
            }
        }
    }
    
    private void addResourceEntityByChangeAmount(String resourceId, BaseGameEntityFactory gameEntityFactory, int addAmount) {
        int drawNum = addAmount;
        
        gameEntitiesOfResourceIds.computeIfAbsent(resourceId, k -> new LinkedList<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfResourceIds.get(resourceId);
        for (int i = 0; i < drawNum; i++) {
            GameEntity gameEntity = gameEntityFactory.newResourceEntity(resourceId, i);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                Gdx.app.log(this.getClass().getSimpleName(), "addResourceEntityByChangeAmount " + resourceId + " new for change:" + drawNum);
            } else {
                break;
            }
        }
    }
    
    private void checkConstructionEntityByOwnAmount(String id, BaseGameEntityFactory gameEntityFactory) {
        BaseConstruction construction = game.getModelContext().getConstructionFactory().getConstruction(id);
        int resourceNum = construction.getSaveData().getWorkingLevel();
        int MAX_DRAW_NUM = construction.getMaxDrawNum();
        int drawNum = gameEntityFactory.calculateConstructionDrawNum(id, resourceNum, MAX_DRAW_NUM);
        gameEntitiesOfConstructionIds.computeIfAbsent(id, k -> new LinkedList<>());
        Queue<GameEntity> gameEntities = gameEntitiesOfConstructionIds.get(id);
        while (gameEntities.size() > drawNum) {
            Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " remove for " + gameEntities.size() + " -> " + drawNum);
            gameEntities.remove();
        }
        while (gameEntities.size() < drawNum) {
            int newIndex = gameEntities.size();
            GameEntity gameEntity = gameEntityFactory.newConstructionEntity(id, newIndex);
            if (gameEntity != null) {
                gameEntities.add(gameEntity);
                Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " new for " + gameEntities.size() + " -> " + drawNum);
            } else {
                //Gdx.app.log(this.getClass().getSimpleName(), "checkConstructionEntityByOwnAmount " + id + " , cannot create new entity.");
                break;
            }
        }
    }

    public void lazyInit(Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds,
            Map<String, List<String>> areaShowEntityByOwnAmountResourceIds,
            Map<String, List<String>> areaShowEntityByChangeAmountResourceIds) {
        this.areaShowEntityByOwnAmountConstructionIds = areaShowEntityByOwnAmountConstructionIds;
        this.areaShowEntityByOwnAmountResourceIds = areaShowEntityByOwnAmountResourceIds;
        this.areaShowEntityByChangeAmountResourceIds = areaShowEntityByChangeAmountResourceIds;
    }


    

}
