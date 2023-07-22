package hundun.gdxgame.idledemo.logic.construction;

import hundun.gdxgame.idleshare.gamelib.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.idleshare.gamelib.framework.model.construction.starter.BaseAutoOutputComponent;

public class IdleForestOutputComponent extends BaseAutoOutputComponent {

    public IdleForestOutputComponent(BaseConstruction construction) {
        super(construction);
    }

    @Override
    public long calculateModifiedOutputGain(long baseValue, int level, int proficiency) {
        return (long)((baseValue * level) * (0.5 + proficiency / construction.getProficiencyComponent().maxProficiency * 0.5));
    }

    @Override
    public long calculateModifiedOutputCost(long baseValue, int level, int proficiency) {
        return (long)((baseValue * level) * (0.5 + proficiency / construction.getProficiencyComponent().maxProficiency * 0.5));
    }
}
