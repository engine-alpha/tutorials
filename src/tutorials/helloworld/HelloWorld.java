package tutorials.helloworld;

import ea.Game;
import ea.Scene;
import ea.actor.Text;

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
        Text helloworld = new Text("Hello World");

        //Füge das Text-Objekt dieser Szene hinzu.
        //-> Erst nachdem das Objekt der Szene hinzugefügt wurde, ist es auch sichtbar.
        this.add(helloworld);
    }


    /**
     * Die Main-Methode. Wird zum Starten dieses Beispiels ausgeführt.
     * @param args Command-Line-Parameter. Vollkommen irrelevant.
     */
    public static void main(String[] args) {
        //Starte einfach das Spiel:
        startGame();
    }

    public static void startGame() {
        //Erstelle die Szene, die angezeigt werden soll (~ unser HelloWorld)
        Scene helloWorld = new HelloWorld();

        //Startet das "Spiel" und macht damit die Szene sichtbar, die wir erstellt haben.
        //Dabei setzen wir auch die Fenstergröße: 812 px breit, 502 px hoch.
        //Der letzte Parameter ist die Szene, mit der das Spiel starten soll (~ unser HelloWorld)
        Game.start(812, 592, helloWorld);
    }
}
