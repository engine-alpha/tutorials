package eatutorials.sound;

import ea.Game;
import ea.Scene;
import ea.input.KeyListener;
import ea.sound.Music;
import ea.sound.Sound;

import java.awt.event.KeyEvent;

/**
 * Diese Klasse demonstriert die Verwendung von Sound in der Engine.<br />
 *
 * Hierbei ist der <b>Unterschied zwischen den beiden Soundfile-Typen in der Engine</b> wichtig:
 * <ol>
 *     <li><code>Music</code>: Längere Tonstücke (wie Hintergrundmusik). Eigenschaften:
 *     <ul>
 *         <li>Können geloopt werden (Musik beginnt automatisch nach Ende von vorne)</li>
 *         <li>Werden selten manipuliert (z.B. pausieren, wiederaufnehmen etc.)</li>
 *         <li>Das selbe Music-Objekt kann <b>nur einmal gleichzeitig</b> abgespielt werden</li>
 *     </ul>
 *     </li>
 *     <li><code>Sound</code>: Kurze Soundeffekte (wie Geräusche für Schüsse, Schritte, Menü-Effekte, etc.).
 *     Eigenschaften:
 *     <ul>
 *         <li>Können nicht geloopt werden.</li>
 *         <li>Werden nicht </li>
 *         <li>Das selbe Sound-Objekt kann <b>mehrfach gleichzeitig</b> abgespielt werden (zum Beispiel für Geräusche
 *         von Schüssen, bei denen das Schussgeräusch länger anhält als bis der nächste Schuss kommt).</li>
 *     </ul>
 *     </li>
 * </ol>
 *
 *
 * <i>Vielen Dank an <a href="https://soundimage.org/">Eric Matyas</a> für die Hintergrundmusik!</i>
 * <i>Vielen Dank an <a href="https://freesound.org/people/uEffects/">uEffects</a> von freesound.org für die
 * Geräusche.</i>
 *
 *
 * <i>Erläuterungen für dieses Tutorial und weitere Tutorials sind auf
 * <a href="http://engine-alpha.org/tutorials">unserer Tutorial-Seite</a> verfügbar.</i>
 *
 * @author Michael Andonie
 */
public class SoundTutorial
extends Scene
implements KeyListener {

    /**
     * Die Hintergrundmusik für den Spielbetrieb.
     */
    private Music background_game;

    /**
     * Die Hintergrundmusik für das Pausenmenü.
     */
    private Music background_menu;

    /**
     * Ein Sound-Effekt
     */
    private Sound effect1;

    /**
     * Noch ein Sound-Effekt
     */
    private Sound effect2;



    public SoundTutorial() {
        //Einladen der Sound-Dateien:
        background_game = new Music("eatutorials/sound/background_game.wma");
        background_menu = new Music("eatutorials/sound/background_menu.wma");

        effect1 = new Sound("eatutorials/sound/bang.ogg");

        background_menu.play();

        addKeyListener(this);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_1:
                //
                break;
            case KeyEvent.VK_2:
                //
                break;
            case KeyEvent.VK_3:
                //
                break;
            case KeyEvent.VK_4:
                //
                break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent keyEvent) {
        //Ignore
    }

    public static void main(String[] args) {
        Scene scene = new SoundTutorial();
        Game.start(500,500,scene);
    }
}
