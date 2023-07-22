package hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.OutputComponent;

public class EmptyOutputComponent extends OutputComponent {

    public EmptyOutputComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public void onSubLogicFrame() {

    }

    @Override
    public long calculateModifiedOutputGain(long baseValue, int level, int proficiency) {
        return baseValue;
    }

    @Override
    public long calculateModifiedOutputCost(long baseValue, int level, int proficiency) {
        return baseValue;
    }
}
