package de.roguemaster.player.CommandLineInterface;

public class CommandLineWriter {
    private String ausgabeString;

    /**
     * Schreibt AusgabeString in die Konsole
      */
    public void write () {
        System.out.println(ausgabeString);
    }

    /**
     * Liest die eingabe vom Spieler
     */
    public void read() {
        //
    }

    CommandLineWriter(CLIBuilder builder) {
        ausgabeString = builder.ausgabeString;
    }

    public static class CLIBuilder {
        private String ausgabeString;

        public CLIBuilder() {
            ausgabeString = "";
        }

        /**
         * TODO: Ändere String raum zu Raum raum
         * Fügt der Ausgabe eine Raum hinzu.
         * @param raum Raum der dargestellt werden soll.
         * @return Builder
         */
        public CLIBuilder erstelleRaum(String raum) {
            ausgabeString += raum + "\n";
            return this;
        }

        /**
         * Fügt Optionen hinzu.
         * @param optionen mehrere Optionen.
         * @return Builder
         */
        public CLIBuilder option(String... optionen) {
            for(String option: optionen){
                ausgabeString += option + "\n";
            }
            return this;
        }

        /**
         * Erstellt eine CommandLineWriter für die entsprechenden Ausgaben.
         * @return CommandLineWriter der die Ausgaben.
         */
        public CommandLineWriter build () {
            return new CommandLineWriter(this);
        }
    }

    public static void main(String[] args) {
        CLIBuilder com = new CommandLineWriter.CLIBuilder().option("Angreifen?", "Verteidigen?", "Flüchten?");
    }

    // CommandLineWriter com = new CommandLineWriter.CLIBuilder.fight().Option("Angreifen", "Verteidigen", "Flüchten").build();
}