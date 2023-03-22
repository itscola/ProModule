package top.whitecola.promodule.events.impls.event;

public abstract class AbstractEvent {
    private boolean cancelled;
    public void setCancel(){
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
