package hundun.gdxgame.idleframe.listener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/02/08
 */
public interface IOneFrameResourceChangeListener {
    void onResourceChange(Map<String, Long> changeMap);
}
