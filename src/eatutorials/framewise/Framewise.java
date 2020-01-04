package eatutorials.framewise;

import ea.*;
import ea.actor.Circle;
import ea.input.KeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Diese Klasse demonstriert die Verwendung frameweiser Updates in der Engine.
 *
 * <i>Erläuterungen für dieses Tutorial und weitere Tutorials sind auf
 * <a href="http://engine-alpha.org/tutorials">unserer Tutorial-Seite</a> verfügbar.</i>
 *
 * @author Michael Andonie
 */
public class Framewise
extends Scene
implements KeyListener {

    /**
     * Konstruktor der Tutorial-Klasse. Erstellt eine Demo.
     */
    public Framewise() {
        //addKeyListener(this); //Füge dich selbst als KeyListener für deine Scene hinzu.
        //Game.setDebug(true);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_1: //Shoot Bullet Type 1
                Bullet bullet1 = new Bullet(this, new Vector(0.5f, 0.04f), 1200);
                bullet1.position.set(-300, -300);
                bullet1.setColor(Color.RED);
                add(bullet1);
                break;
            case KeyEvent.VK_2: //Shoot Bullet Type 2
                Bullet bullet2 = new Bullet(this, new Vector(0.0f, 1f), 500);
                bullet2.position.set(0, -370);
                bullet2.setColor(Color.BLUE);
                add(bullet2);
                break;
            case KeyEvent.VK_3: //Shoot Bullet Type 3
                Bullet bullet3 = new Bullet(this, new Vector(-0.5f, -0.5f), 800);
                bullet3.position.set(250, 200);
                bullet3.setColor(Color.CYAN);
                add(bullet3);
                break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent keyEvent) {
        //Ignorieren.

    }

    /* ~~~~~~~~~~~~~~~~~~~~ KeyListener-Methoden ~~~~~~~~~~~~~~~~~~~~ */




    /**
     * Ein Geschoss. Bewegt sich schnell und jeden Frame mit fester Geschwindigkeit, bis seine Lebenszeit abgelaufen
     * ist.
     */
    public class Bullet
    extends Circle
    implements FrameUpdateListener {

        /**
         * Die Strecke, die dieses Geschoss *jede MILLISEKUNDE* zurücklegt.
         */
        private Vector velocityPerMS;

        /**
         * Die Lebenszeit dieses Geschosses in Millisekunden. Nach ablauf dieser Zeit wird es entfernt.
         */
        private int lifetimeInMS;

        public Bullet(Scene scene, Vector velocityPerMS, int lifetimeInMS) {
            super(scene, 10);
            this.velocityPerMS = velocityPerMS;
            this.lifetimeInMS = lifetimeInMS;
        }

        /**
         * Diese Methode wird <b>jeden Frame aufgerufen</b>.
         * @param ms    Die Zeit (in Millisekunden), die seit dem letzten Frame-Update vergangen ist.
         */
        @Override
        public void onFrameUpdate(int ms) {
            //1. Bewege das Geschoss entlang seiner Geschwindigkeit.
            this.position.move(velocityPerMS.multiply(ms)); //[zurückgelegte Strecke] = [Geschw. pro MS] * [vergangene MS]

            //2. Ziehe die vergangene Zeit von der Lebenszeit des Geschosses ab.
            this.lifetimeInMS = this.lifetimeInMS - ms;

            //3. Teste, ob Lebenszeit abgelaufen ist
            if(this.lifetimeInMS < 0) {
                //Lebenszeit ist abgelaufen: Entferne das Objekt von der Scene, die mich erstellt hat.
                Framewise.this.remove(this);
            }
        }
    }

    /**
     * Main-Methode startet die Demo.
     * @param args  Command-Line-Args. Irrelevant.
     */
    public static void main(String[] args) {
        Game.start(400, 300, new Scene());
    }
}
