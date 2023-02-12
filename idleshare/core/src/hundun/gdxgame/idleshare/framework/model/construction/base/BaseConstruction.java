package hundun.gdxgame.idleshare.framework.model.construction.base;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Random;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.corelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.idleshare.framework.BaseIdleGame;
import hundun.gdxgame.idleshare.framework.data.ConstructionSaveData;
import hundun.gdxgame.idleshare.framework.listener.IBuffChangeListener;
import hundun.gdxgame.idleshare.framework.model.construction.base.DescriptionPackage.ILevelDescroptionProvider;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseConstruction implements ILogicFrameListener, IBuffChangeListener {

    protected static final int DEFAULT_MAX_LEVEL = 99;
    @Getter
    @Setter
    protected int maxLevel = DEFAULT_MAX_LEVEL;


    protected static final int DEFAULT_MAX_DRAW_NUM = 5;
    @Getter
    @Setter
    protected int maxDrawNum = DEFAULT_MAX_DRAW_NUM;

    public static final DescriptionPackage WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "AutoCost", "AutoGain", "UpgradeCost", "Upgrade",
            ILevelDescroptionProvider.WORKING_LEVEL_IMP);

    public static final DescriptionPackage MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "AutoCost", "AutoGain", "UpgradeCost", "Upgrade",
            ILevelDescroptionProvider.ONLY_LEVEL_IMP);

    public static final DescriptionPackage SELLING_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "Sell", "Gain", "UpgradeCost", "Upgrade",
            ILevelDescroptionProvider.WORKING_LEVEL_IMP);


    public static final DescriptionPackage GATHER_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "Pay", "Gain", null, "Gather",
            ILevelDescroptionProvider.EMPTY_IMP);

    public static final DescriptionPackage WIN_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, "Pay", "Unlock",
            ILevelDescroptionProvider.LOCK_IMP);

    protected Random random = new Random();
    protected final BaseIdleGame game;

    /**
     * NotNull
     */
    @Getter
    @Setter
    protected ConstructionSaveData saveData;

    @Getter
    public String name;

    @Getter
    public String id;

    @Getter
    public String detailDescroptionConstPart;

    @Getter
    public DescriptionPackage descriptionPackage;


    /**
     * NotNull
     */
    @Getter
    @Setter
    protected UpgradeComponent upgradeComponent;


    /**
     * NotNull
     */
    @Getter
    @Setter
    protected OutputComponent outputComponent;

    /**
     * NotNull
     */
    @Getter
    @Setter
    protected LevelComponent levelComponent;


    public void lazyInitDescription() {
        outputComponent.lazyInitDescription();
        upgradeComponent.lazyInitDescription();
        
        updateModifiedValues();
    }

    public BaseConstruction(BaseIdleGame game, String id) {
        this.game = game;
        this.saveData = new ConstructionSaveData();
        this.id = id;

        game.getEventManager().registerListener(this);
    }

    public abstract void onClick();

    public abstract boolean canClickEffect();

    public String getButtonDescroption() {
        return descriptionPackage.getButtonDescroption();
    }

    //protected abstract long calculateModifiedUpgradeCost(long baseValue, int level);
    protected abstract long calculateModifiedOutput(long baseValue, int level);
    protected abstract long calculateModifiedOutputCost(long baseValue, int level);



    /**
     * 重新计算各个数值的加成后的结果
     */
    public void updateModifiedValues() {
        Gdx.app.log(this.name, "updateCurrentCache called");
        // --------------
        boolean reachMaxLevel = this.getSaveData().getLevel() == this.getMaxLevel();
        upgradeComponent.updateModifiedValues(reachMaxLevel);
        outputComponent.updateModifiedValues();

    }

    @Override
    public void onBuffChange() {
        updateModifiedValues();
    }


    protected void printDebugInfoAfterConstructed() {
        // default do nothing
    }

    protected boolean canOutput() {
        return outputComponent.canOutput();
    }


    protected boolean canUpgrade() {
        return upgradeComponent.canUpgrade();
    }

    public String getSaveDataKey() {
        return id;
    }


}
