package top.whitecola.promodule.events.impls;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import top.whitecola.promodule.events.EventAdapter;

public class EventToInvokeNotification extends EventAdapter {
    public EventToInvokeNotification() {
        super(MainMenuEvent.class.getSimpleName());
    }

    @Override
    public void onRender(TickEvent.RenderTickEvent event) {
        super.onRender(event);
    }
}
