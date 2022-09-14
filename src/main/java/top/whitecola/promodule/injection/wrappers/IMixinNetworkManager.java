package top.whitecola.promodule.injection.wrappers;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Packet;

public interface IMixinNetworkManager {
    void addToSendQueueWithoutEvent(Packet packet);

    void addToReceiveQueue(Packet packet);

    void addToReceiveQueueWithoutEvent(Packet packet);
}
