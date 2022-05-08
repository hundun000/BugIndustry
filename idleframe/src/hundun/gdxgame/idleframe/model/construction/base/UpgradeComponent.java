package hundun.gdxgame.idleframe.model.construction.base;

import java.util.List;
import java.util.stream.Collectors;

import hundun.gdxgame.idleframe.model.resource.ResourcePack;
import hundun.gdxgame.idleframe.model.resource.ResourcePair;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class UpgradeComponent {
    private final BaseConstruction construction;

    private ResourcePack upgradeCostPack;
    // ------ replace-lombok ------
    public ResourcePack getUpgradeCostPack() {
        return upgradeCostPack;
    }
    public void setUpgradeCostPack(ResourcePack upgradeCostPack) {
        this.upgradeCostPack = upgradeCostPack;
    }
    
    public UpgradeComponent(BaseConstruction construction) {
        super();
        this.construction = construction;
    }
    
    public void updateDescription() {
        if (upgradeCostPack != null) {
            upgradeCostPack.setDescriptionStart(construction.descriptionPackage.getUpgradeCostDescriptionStart());
        }
    }
    
    public void updateModifiedValues() {
        if (upgradeCostPack != null) {
            upgradeCostPack.setModifiedValues(
                    upgradeCostPack.getBaseValues().stream()
                        .map(pair -> {
                                long newAmout = construction.calculateModifiedUpgradeCost(pair.getAmount(), construction.saveData.getLevel());
                                return new ResourcePair(pair.getType(), newAmout);
                            })
                        .collect(Collectors.toList())
            );
            this.upgradeCostPack.setModifiedValuesDescription(
                    upgradeCostPack.getModifiedValues().stream()
                    .map(pair -> pair.getType() + "x" + pair.getAmount())
                    .collect(Collectors.joining(", "))
                    + "; "
            );
        }
    }
    
    protected boolean canUpgrade() {
        if (construction.saveData.getLevel() >= construction.MAX_LEVEL || upgradeCostPack == null) {
            return false;
        }
        
        List<ResourcePair> compareTarget = upgradeCostPack.getModifiedValues();
        return construction.game.getModelContext().getStorageManager().isEnough(compareTarget);
    }
    
}
