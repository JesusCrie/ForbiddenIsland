package iut2.forbiddenisland.view;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public enum CellGraphicalMetadata {
    HELIPORT("Héliport", "/tuiles/Heliport.png", "/tuiles/Heliport_Inonde.png", "/cartes/Heliport.png"),
    SHADOW_CAVERN("La Caverne des Ombres", "/tuiles/LaCarverneDesOmbres.png", "/tuiles/LaCarverneDesOmbres_Inonde.png", "/cartes/LaCaverneDesOmbres.png"),
    FIRE_CAVERN("La Caverne du Brasier", "/tuiles/LaCarverneDuBrasier.png", "/tuiles/LaCarverneDuBrasier_Inonde.png", "/cartes/CaverneDuBrasier.png"),
    PURPLE_FOREST("La Forêt Pourpre", "/tuiles/LaForetPourpre.png", "/tuiles/LaForetPourpre_Inonde.png", "/cartes/LaForetPoupre.png"),
    SILVER_DOOR("La Porte d'Argent", "/tuiles/LaPortedArgent.png", "/tuiles/LaPortedArgent_Inonde.png", "/cartes/LaPortedArgent.png"),
    BRONZE_DOOR("La Porte de Bronze", "/tuiles/LaPorteDeBronze.png", "/tuiles/LaPorteDeBronze_Inonde.png", "/cartes/LaPorteDeBronze.png"),
    COPPER_DOOR("La Porte de Cuivre", "/tuiles/LaPorteDeCuivre.png", "/tuiles/LaPorteDeCuivre_Inonde.png", "/cartes/LaPorteDeCuivre.png"),
    IRON_DOOR("La Porte de Fer", "/tuiles/LaPorteDeFer.png", "/tuiles/LaPorteDeFer_Inonde.png", "/cartes/LaPorteDeFer.png"),
    GOLDEN_DOOR("La Porte d'Or", "/tuiles/LaPortedOr.png", "/tuiles/LaPortedOr_Inonde.png", "/cartes/LaPorteDOr.png"),
    WATCHTOWER("La Tour de Guet", "/tuiles/LaTourDuGuet.png", "/tuiles/LaTourDuGuet_Inonde.png", "/cartes/LaTourDeGuet.png"),
    SCREAMINGS_GARDEN("Le Jardin des Hurlements", "/tuiles/LeJardinDesHurlements.png", "/tuiles/LeJardinDesHurlements_Inonde.png", "/cartes/LeJardinDesHurlements.png"),
    WHISPERS_GARDEN("Le Jardin des Murmures", "/tuiles/LeJardinDesMurmures.png", "/tuiles/LeJardinDesMurmures_Inonde.png", "/cartes/LeJardinDesMurmures.png"),
    LOST_LAGOON("Le Lagon Perdu", "/tuiles/LeLagonPerdu.png", "/tuiles/LeLagonPerdu_Inonde.png", "/cartes/LeLagonPerdu.png"),
    FOGGY_SWAMP("Le Marais Brumeux", "/tuiles/LeMaraisBrumeux.png", "/tuiles/LeMaraisBrumeux_Inonde.png", "/cartes/LeMaraisBrumeux.png"),
    CORAL_PALACE("Le Palais de Corail", "/tuiles/LePalaisDeCorail.png", "/tuiles/LePalaisDeCorail_Inonde.png", "/cartes/LePalaisDeCorail.png"),
    TIDAL_PALACE("Le Palais des Marées", "/tuiles/LePalaisDesMarees.png", "/tuiles/LePalaisDesMarees_Inonde.png", "/cartes/LePalaisDesMarees.png"),
    ABYSS_BRIDGE("Le Pont des Abimes", "/tuiles/LePontDesAbimes.png", "/tuiles/LePontDesAbimes_Inonde.png", "/cartes/LePontDesAbimes.png"),
    GHOST_ROCK("Le Rochet Fantôme", "/tuiles/LeRocherFantome.png", "/tuiles/LeRocherFantome_Inonde.png", "/cartes/LeRocherFantome.png"),
    ILLUSION_DUNE("Les Dunes de l'Illusion", "/tuiles/LesDunesDeLIllusion.png", "/tuiles/LesDunesDeLIllusion_Inonde.png", "/cartes/LesDunesDeLIllusion.png"),
    CLIFFS_OBLIVION("Les Falaises de l'Oubli", "/tuiles/LesFalaisesDeLOubli.png", "/tuiles/LesFalaisesDeLOubli_Inonde.png", "/cartes/LesFalaisesDeLOubli.png"),
    MOON_TEMPLE("Le Temple de la Lune", "/tuiles/LeTempleDeLaLune.png", "/tuiles/LeTempleDeLaLune_Inonde.png", "/cartes/LeTempleDeLaLune.png"),
    SUN_TEMPLE("Le Temple du Soleil", "/tuiles/LeTempleDuSoleil.png", "/tuiles/LeTempleDuSoleil_Inonde.png", "/cartes/LeTempleDuSoleil.png"),
    TWILIGHT_VAL("Le Val du Crépuscule", "/tuiles/LeValDuCrepuscule.png", "/tuiles/LeValDuCrepuscule_Inonde.png", "/cartes/LeValDuCrecupuscule.png"),
    OBSERVATORY("L'Observatoire", "/tuiles/Observatoire.png", "/tuiles/Observatoire_Inonde.png", "/cartes/Observatoire.png");

    private final String name;
    private final String gridDryFile;
    private final String gridWetFile;
    private final String cardFile;

    private Image cacheGridDryImage;
    private Image cacheGridWetImage;
    private Image cacheCardImage;

    CellGraphicalMetadata(final String name, final String gridDryFilename, final String gridWetFilename, final String cardFilename) {
        this.name = name;
        this.gridDryFile = gridDryFilename;
        this.gridWetFile = gridWetFilename;
        this.cardFile = cardFilename;
    }

    public String getName() {
        return name;
    }

    public Image getGridDryImage() {
        if (cacheGridDryImage == null) {
            try {
                cacheGridDryImage = ImageIO.read(getClass().getResourceAsStream(gridDryFile));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load grid image");
            }
        }

        return cacheGridDryImage;
    }

    public Image getGridWetImage() {
        if (cacheGridWetImage == null) {
            try {
                cacheGridWetImage = ImageIO.read(getClass().getResourceAsStream(gridWetFile));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load grid image");
            }
        }

        return cacheGridWetImage;
    }

    public Image getCardImage() {
        if (cacheCardImage == null) {
            try {
                cacheCardImage = ImageIO.read(getClass().getResourceAsStream(cardFile));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load card image");
            }
        }

        return cacheCardImage;
    }

    public static CellGraphicalMetadata fromName(final String name) {
        for (CellGraphicalMetadata value : values()) {
            if (value.getName().equals(name))
                return value;
        }

        return null;
    }
}
