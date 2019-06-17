package iut2.forbiddenisland.view;

import java.io.File;

public enum CellName {
    HELIPORT("Héliport", "/tuiles/Heliport.png", "/cartes/Heliport.png"),
    SHADOW_CAVERN("La Caverne des Ombres", "/tuiles/LaCarverneDesOmbres.png", "/cartes/LaCaverneDesOmbres.png"),
    FIRE_CAVERN("La Caverne du Brasier", "/tuiles/LaCarverneDuBrasier.png", "/cartes/CaverneDuBrasier.png"),
    PURPLE_FOREST("La Forêt Pourpre", "/tuiles/LaForetPourpre.png", "/cartes/LaForetPoupre.png"),
    SILVER_DOOR("La Porte d'Argent", "/tuiles/LaPortedArgent.png", "/cartes/LaPortedArgent.png"),
    BRONZE_DOOR("La Porte de Bronze", "/tuiles/LaPorteDeBronze.png", "/cartes/LaPorteDeBronze.png"),
    COPPER_DOOR("La Porte de Cuivre", "/tuiles/LaPorteDeCuivre.png", "/cartes/LaPorteDeCuivre.png"),
    IRON_DOOR("La Porte de Fer","/tuiles/LaPorteDeFer.png", "/cartes/LaPorteDeFer.png"),
    GOLDEN_DOOR("La Porte d'Or", "/tuiles/LaPortedOr.png", "/cartes/LaPorteDOr.png"),
    WATCHTOWER("La Tour de Guet", "/tuiles/LaTourDuGuet.png", "/cartes/LaTourDeGuet.png"),
    SCREAMINGS_GARDEN("Le Jardin des Hurlements", "/tuiles/LeJardinDesHurlements.png", "/cartes/LeJardinDesHurlements.png"),
    WISPERING_GARDEN("Le Jardin des Murmures", "/tuiles/LeJardinDesMurmures.png", "/cartes/LeJardinDesMurmures.png"),
    LOST_LAGOON("Le Lagon Perdu", "/tuiles/LeLagonPerdu.png", "/cartes/LeLagonPerdu.png"),
    FOGGY_SWAMP("Le Marais Brumeux", "/tuiles/LeMaraisBrumeux.png", "/cartes/LeMaraisBrumeux.png"),
    CORAL_PALACE("Le Palais de Corail", "/tuiles/LePalaisDeCorail.png", "/cartes/LePalaisDeCorail.png"),
    TIDAL_PALACE("Le Palais des Marées", "/tuiles/LePalaisDesMarees.png", "/cartes/LePalaisDesMarees.png"),
    ABYSS_BRIDGE("Le Pont des Abimes", "/tuiles/LePontDesAbimes.png","/cartes/LePontDesAbimes.png"),
    GHOST_ROCK("Le Rochet Fantôme", "/tuiles/LeRocherFantome.png", "/cartes/LeRocherFantome.png"),
    ILLUSION_DUNE("Les Dunes de l'Illusion", "/tuiles/LesDunesDeLIllusion.png", "/cartes/LesDunesDeLIllusion.png"),
    CLIFFS_OBLIVION("Les Falaises de l'Oubli", "/tuiles/LesFalaisesDeLOubli.png","/cartes/LesFalaisesDeLOubli.png"),
    MOON_TEMPLE("Le Temple de la Lune", "/tuiles/LeTempleDeLaLune.png", "/cartes/LeTempleDeLaLune.png"),
    SUN_TEMPLE("Le Temple du Soleil", "/tuiles/LeTempleDuSoleil.png", "/cartes/LeTempleDuSoleil.png"),
    TWILIGHT_VAL("Le Val du Crépuscule", "/tuiles/LeValDuCrepuscule.png", "/cartes/LeValDuCrecupuscule.png"),
    OBSERVATORY("L'Observatoire", "/tuiles/Observatoire.png", "/cartes/Observatoire.png");

    private final String name;
    private final File gridFile;
    private final File cardFile;

    CellName(final String name, final String gridFilename, final String cardFilename) {
        this.name = name;
        this.gridFile = new File(gridFilename);
        this.cardFile = new File(cardFilename);
    }
}
