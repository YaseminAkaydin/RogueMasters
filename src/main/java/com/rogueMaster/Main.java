package com.rogueMaster;

import java.util.List;

/**
 * Halter Klasse bis Projekt aufgebaut wird.
 */
public class Main {

    /**
     * Erzeugt das Startmenü und gibt dies in der Console aus. Eingabe wird auch von der Methode übernommen.
     * Vorbedingung: Spiel wurde gestartet.
     * Nachbedingung: Menü wird angezeigt und Spieler kann Option auswählen.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showStartMenu() {
        return 0;
    }

    /**
     * Erstellt eine, neues Spiel (Runde) Rogue Masters.
     * Vorbedingung: StartMenü wurde geöffnet
     * Nachbedingung: Menü wird angezeigt und Spieler kann Option auswählen.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int startNewGame(){
        return 0;
    }


    /**
     * Der Spieler tritt einem bereits aktiven Spiel über die Eingabe eines Codes bei.
     * Vorbedingung: Spiel mit der GameID existiert und läuft noch.
     * Nachbedigung: Spieler tritt dem Spiel bei.
     * @param GameID - Unique ID als Identifier des Spiels
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int joinGame(int GameID){
    return 0;
    }

    /**
     * Wird genutzt, um das Spiel erfolgreich zu beenden.
     * Vorbedingung: Spieler ist in einem Spiel und dsa Spiel läuft noch.
     * Nachbedingung: Spieler hat das Spiel verlassen.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int exitGame(){
        return 0;
    }

    /**
     * Nachdem ein Spiel initialisiert wurde, wird dieser "lobbyScreen" angezeigt in dem andere Spieler beitreten können oder ggf. andere Einstellungen vorgenommen werden können.
     * Vorbedingung: Spiel vom Lobby Screen existiert.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showLobbyScreen(){
        return 0;
    }


    /**
     * Gibt ein Link zum Tutorial aus in der Konsole in welchem man das Tutorial/Dokumentation sehen kann.
     */
    public void showTutorial(){
    }

    /**
     * Wird genutzt, um den Zug zu beenden.
     * Vorbedingung: Du bist in einem Spiel und hast einen Zug gemacht.
     * Nachbedingung: Server Antwortet.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int waitForDungeon(){
        return 0;
    }

    /**
     * Zeigt das Leaderboard an, in dem die Top 10 Spieler angezeigt werden.
     * If showPersonalScore==true; Zeigt das persönliche Ranking des Spielers im Vergleich zu dem Leaderboard an.
     * Vorbedingung: Spieler befindet sich im Hauptmenü
     * Nachbedingung: Server antwortet.
     * @param showPersonalScore showPersonalScore
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showLeaderboard(boolean showPersonalScore){
        return 0;
    }


    /**
     * Neues Spiel wird erzeugt (gameID, map, monster, etc.).
     * Vorbedingung: Spiele Anfrage wurde von einem Spieler getätigt.
     * @return GameObject wird zurückgegeben
     */
    public Game generateDungeon(){
        return null;
    }

    /**
     * Der Raum, in dem sich der aufrufende Spieler befindet, wird angezeigt. (z.B. wenn ein neuer Raum betreten wird oder wenn ein Menü geschlossen wird)
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showRoom(){
        return 0;
    }

    /**
     * Gewünschter Spieler, Monster oder andere Character wird detailliert angezeigt.
     * @param character der Spieler der inspiziert werden soll. Kann der aufrufende Spieler oder ein anderer sein. Ggf. erweiterbar auf NPCs.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showCharacter(BaseCharacter character){
        return 0;
    }

    /**
     * TODO: wie/wo genau werden die stats angezeigt? Gewünschte stats als List?
     * Gegebene stats werden angezeigt.
     * @param character dessen stats angezeigt werden sollen.
     * @param stats die angezeigt werden sollen
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showSmallStats(BaseCharacter character, List<String> stats){
        return 0;
    }

    /**
     * Inventar des aufrufenden Spielers wird angezeigt.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showInventory(){
        return 0;
    }

    /**
     * Menü mit auswahlmöglichkeiten werden angezeigt. (State abhängig)
     * Vorbedingung: Spieler befindet sich im Dungeon.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int openMenu(){
        return 0;
    }

    /**
     * Menü wird wieder geschlossen.
     * Vorbedingung: openMenu() wurde aufgerufen und das menü wird momentan angezeigt.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int closeMenu(){
        return 0;
    }

    /**
     * Item wird aus dem Dungeon entfernt und ins Inventar des Spielers bewegt, sofern dieser an das Item kommen kann und genug Platz hat.
     * @param item welches eingesammelt werden soll
     * @return True, falls Gegenstand eingesammelt werden konnte. False, falls Gegenstand nicht aufgesammelt werden konnte (dann sollte ein Grund ausgegeben werden)
     */
    public boolean pickupItem(Item item){
        return false;
    }


    /**
     * Wenn der Charakter angreifbar ist, wird ihm Schaden zugefügt und eine Nachricht ausgegeben
     * Vorbedingung: Ein angreifbarer Charakter befindet sich im selben Raum wie der Spieler der angreift
     * @param gegenerischerCharacter Der Character den man Angreifen will.
     * @return True, wenn der Charakter angegriffen wurde sonst false
     */
    public boolean attack(BaseCharacter gegenerischerCharacter){
        return true;
    }


    /**
     * Der Spieler wird zurück ins Hauptmenü "bewegt". Spiel wird ggf. Terminiert.
     * Vorbedingung: Spieler befindet sich nicht im Hauptmenü
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int returnMainMenu(){
        return 0;
    }

    /**
     * Der erreichte Score wird angezeigt, nachdem das Spiel beendet wurde (absichtlich oder durchs Sterben). Ruft calcScore() auf.
     * Vorbedingung: Spieler hat das Spiel beendet und befindet sich nicht mehr im Dungeon
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public int showScore(){
        return 0;
    }

    /**
     * Der erreichte Score wird berechnet. Übergangsweise erstmal erreichtes Level -> score.
     * Vorbedingung: Spieler ist gestorben, geht ins Hauptmenü oder beendet das Spiel
     * @return Gibt den erreichten Score als integer zurück.
     */
    public int calcScore(){
        return 0;
    }

    /**
     * n Anzahl Items werden an der Position des Spielers fallen gelassen, Itemanzahl wird im Inventar aktualisiert
     * Vorbedingung: Spieler befindet sich im Inventar, Item + Anzahl wird ausgewählt, Items wird gedroppt
     * @param item Item welche fallen gelassen werden.
     * @param n anzahl items
     * @return True bei drop des items sonst false
     */
    public boolean dropItem(Item item, int n){
        return true;
    }

    /**
     * Item benutzen, Anzahl der Items im Inventar um 1 reduzieren, bei 0 aus dem Inventar entfernen
     * Vorbedingung: Spieler möchte ein Item aus dem Inventar benutzen.
     * @param item  Item was genutzt wird.
     * @return Gibt einen Statuscode aus der Bestimmt, ob die Aktion erfolgreich war. Bei einer Zahl kleiner gleich null liegt ein Fehler vor.
     */
    public boolean useItem(Item item){
        return true;
    }

}