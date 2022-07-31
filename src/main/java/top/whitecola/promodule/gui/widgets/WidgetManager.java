package top.whitecola.promodule.gui.widgets;

import java.util.Vector;

public class WidgetManager {
    protected Vector<AbstractWidget> widgets = new Vector<AbstractWidget>();


    public WidgetManager(){

    }


    public void drawWidgets() {
        for (AbstractWidget widget : widgets) {
            widget.drawWidget();
        }
    }

    public Vector<AbstractWidget> getWidgets() {
        return widgets;
    }

    public void addWidget(AbstractWidget widget){
        widgets.add(widget);
    }

    public void removeWidget(AbstractWidget widget){
        widgets.remove(widget);
    }


    public AbstractWidget getWidgetByName(String name){
        for(AbstractWidget widget : widgets){
            if(widget.getWidgetName().equalsIgnoreCase(name)){
                return widget;
            }
        }
        return null;
    }

    public void removeWidgetByName(String name){
        for(AbstractWidget widget : widgets){
            if(widget.getWidgetName().equalsIgnoreCase(name)){
                widgets.remove(widget);
            }
        }
    }



}
