package top.whitecola.promodule.events.impls.event;

public abstract class AbstractEvent {
    private boolean canceled;
    public void setCancel(){
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
