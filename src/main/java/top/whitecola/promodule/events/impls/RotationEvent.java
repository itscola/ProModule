package top.whitecola.promodule.events.impls;

import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import static top.whitecola.promodule.utils.MCWrapper.*;


public class RotationEvent extends EventAdapter {
    public RotationEvent() {
        super(RotationEvent.class.getSimpleName());
    }


    @Override
    public void onPreMotion(PreMotionEvent e) {
        if(e.getYaw()!=mc.thePlayer.prevRotationYaw){
//            System.out.println("not same!!! "+e.getYaw()+" "+mc.thePlayer.prevRotationYaw);

        }

//        System.out.println("same "+e.getYaw()+" "+mc.thePlayer.rotationYawHead);



        super.onPreMotion(e);
    }
}
