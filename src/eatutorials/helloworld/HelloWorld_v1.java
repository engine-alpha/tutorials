package eatutorials.helloworld;

import ea.Scene;
import ea.Game;

import ea.actor.Text;
import eatutorials.util.Util;

public class HelloWorld_v1
        extends Scene{

    public HelloWorld_v1() {
        Text helloworld = new Text("Hello World", 2);
        helloworld.setCenter(0,1);
        this.add(helloworld);
        Game.setDebug(true);
    }

    public static void main(String[] args) {
        Scene helloWorld = new HelloWorld_v1();
        Game.start(400, 300, helloWorld);
        Util.makeScreenshot("debug");
    }
}
