package iut2.forbiddenisland.view;

import java.io.File;

public enum CellName {
    HELIPORT("Héliport", "/tuiles/Heliport.png", "/cartes/Heliport.png"),
    SHADOW_CAVERN("La Caverne des Ombres"),
    FIRE_CAVERN("La Caverne du Brasier"),
    PURPLE_FOREST("La Forêt Pourpre"),
    SILVER_DOOR("La Porte d'Argent"),
    BRONZE_DOOR("La Porte de Bronze"),
    COPPER_DOOR("La Porte de Cuivre"),
    IRON_DOOR("La Porte de Fer"),
    GOLDEN_DOOR("La Porte d'Or"),
    WATCHTOWER("La Tour de Guet"),
    SCREAMINGS_GARDEN("Le Jardin des Hurlements"),
    WISPERING_GARDEN("Le Jardin des Murmures"),
    LOST_LAGOON("Le Lagon Perdu"),
    FOGGY_SWAMP("Le Marais Brumeux"),
    CORAL_PALACE("Le Palais de Corail"),
    TIDAL_PALACE("Le Palais des Marées"),
    ABYSS_BRIDGE("Le Pont des Abimes"),
    GHOST_ROCK("Le Rochet Fantôme"),
    ILLUSION_DUNE("Les Dunes de l'Illusion"),
    CLIFFS_OBLIVION("Les Falaises de l'Oubli"),
    MOON_TEMPLE("Le Temple de la Lune"),
    SUN_TEMPLE("Le Temple du Soleil"),
    TWILIGHT_VAL("Le Val du Crépuscule"),
    OBSERVATORY("L'Observatoire");

    private final String name;
    private final File gridFile;
    private final File cardFile;

    CellName(final String name, final String gridFilename, final String cardFilename) {
        this.name = name;
        this.gridFile = new File(gridFilename);
        this.cardFile = new File(cardFilename);
    }
}
