package eatutorials.helloworld;

import ea.Scene;
import ea.Game;

import ea.actor.Circle;
import ea.actor.Rectangle;
import ea.actor.Text;
import eatutorials.util.Util;

import java.awt.Color;

public class HelloWorld_v2
        extends Scene{

    public HelloWorld_v2() {
        Text helloworld = new Text("Hello World", 2);
        helloworld.setCenter(0,1);
        this.add(helloworld);
        //Game.setDebug(true);

        helloworld.setColor(Color.BLACK);

        Rectangle background = new Rectangle(10, 3);
        background.setColor(Color.PINK);
        background.setCenter(0, 1);
        //background.setLayerPosition(-1);

        Circle circle = new Circle(5);
        circle.setColor(Color.GRAY);
        circle.setCenter(0, 1);
        //circle.setLayerPosition(-2);

        this.add(background, circle);
        //getCamera().setZoom(0.5f);
    }

    public static void main(String[] args) {
        Scene helloWorld = new HelloWorld_v2();
        Game.start(400, 300, helloWorld);
        Util.makeScreenshot("nolayer");
    }
}
