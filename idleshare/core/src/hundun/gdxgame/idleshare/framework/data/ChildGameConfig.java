package hundun.gdxgame.idleshare.framework.data;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.idleshare.framework.model.AchievementPrototype;
import hundun.gdxgame.idleshare.framework.model.construction.base.BaseConstruction;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Getter
@Setter
public abstract class ChildGameConfig {
    Map<String, List<String>> areaControlableConstructionIds;
    Map<String, List<String>> areaShowEntityByOwnAmountConstructionIds;
    Map<String, List<String>> areaShowEntityByOwnAmountResourceIds;
    Map<String, List<String>> areaShowEntityByChangeAmountResourceIds;
    List<BaseConstruction> constructions;
    Map<String, String> screenIdToFilePathMap;
    List<AchievementPrototype> achievementPrototypes;


}
