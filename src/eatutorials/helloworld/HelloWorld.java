package eatutorials.helloworld;

import ea.EngineAlpha;
import ea.Game;
import ea.Scene;
import ea.actor.Circle;
import ea.actor.Rectangle;
import ea.actor.Text;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Minimales "Hello World"-Beispiel; demonstriert die grundlegendsten Schritte zum Verwenden der Engine. Das sind:
 * <ul>
 *     <li>Erstellen einer Szene.</li>
 *     <li>Erstellen eines Actors (~grafischen Objekts) und das <b>Einfügen in die Szene</b>
 *     (notwendig, damit das Objekt sichtbar wird).</li>
 *     <li>Einstellen des Spielfensters und der Szene in das Spielfenster.</li>
 * </ul>
 *
 * <i>Erläuterungen für dieses Tutorial und weitere Tutorials sind auf
 * <a href="http://engine-alpha.org/tutorials">unserer Tutorial-Seite</a> verfügbar.</i>
 *
 * @author Michael Andonie
 */
public class HelloWorld
extends Scene{

    /**
     * Konstruktor. Erstellt die eine Szene, in der einfach unser "Hello World" angezeigt wird.
     */
    public HelloWorld() {
        //Erstelle ein Text-Objekt (mit Inhalt "Hello World"), das angezeigt werden soll.
        Text helloworld = new Text(this, "Hello World");
        helloworld.position.setCenter(0,0); //Zentriere den Text im Bild

        //Füge das Text-Objekt dieser Szene hinzu.
        //-> Erst nachdem ein Objekt der Szene hinzugefügt wurde, ist es auch sichtbar.
        this.add(helloworld);

        addMoreActors(); //<- Einkommentieren, um noch mehr grafische Objekte einzuführen.
        //Game.setDebug(true); //<- Einkommentieren, um den Debug-Modus (und damit ein Raster) zu aktiveren
    }

    /**
     * Fügt zwei weitere Actors (~ "sichtbare Objekte") hinzu: Ein Rechteck und einen Kreis.<br />
     * Neben geometrischen Objekten lassen sich viele weitere grafische Objekte einer Szene hinzufügen.
     */
    public void addMoreActors() {
        //Erstelle ein rotes Rechteck (Breite 120, Höhe 50) an Position (-100|-70)
        Rectangle rectangle = new Rectangle(this, 120, 50);
        rectangle.position.set(-100,-70);
        rectangle.setColor(Color.RED);

        //Erstelle einen türkisen Kreis (Durchmesser 150) an Position (0|0)
        Circle circle = new Circle(this, 150);
        circle.position.set(0,0);
        circle.setColor(Color.WHITE);

        //Füge das Rechteck und den Kreis der Szene hinzu
        //-> Erst nachdem ein Objekt der Szene hinzugefügt wurde, ist es auch sichtbar.
        add(rectangle, circle);
    }

    /*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ STARTING THE GAME ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  */

    /**
     * Die Main-Methode. Wird zum Starten dieses Beispiels ausgeführt.
     * @param args Command-Line-Parameter. Vollkommen irrelevant.
     */
    public static void main(String[] args) {
        //Starte zum Programmbeginn einfach das Spiel:
        startGame();
    }

    /**
     * Hierin wird das Spiel gestartet. Hierfür werden zwei Arbeitsschritte ausgeführt:
     *
     * <ol>
     *     <li>Eine HelloWorld-Scene wird erstellt.</li>
     *     <li>Das "Spiel" wird gestartet mit der HelloWorld-Szene in einem 812x592px-Fenster.</li>
     * </ol>
     */
    public static void startGame() {
        //Erstelle die Szene, die angezeigt werden soll (~ unser HelloWorld)
        Scene helloWorld = new HelloWorld();

        //Startet das "Spiel" und macht damit die Szene sichtbar, die wir erstellt haben.
        //Dabei setzen wir auch die Fenstergröße: 812 px breit, 502 px hoch.
        //Der letzte Parameter ist die Szene, mit der das Spiel starten soll (~ unser HelloWorld)
        Game.start(400, 300, helloWorld);
    }
}
