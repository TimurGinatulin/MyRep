package sample.controllers.listener;

import javafx.event.Event;
import javafx.event.EventHandler;

public class MyEventHandler implements EventHandler {

    @Override
    public void handle(Event event) {

        System.out.println(event.getSource().getClass().getName());
    }
}
