package iut2.forbiddenisland.view;

import iut2.forbiddenisland.model.Treasure;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public enum TreasureCardGraphicalMetadata {

    TREASURE_SACRED_STONE("La Pierre Sacrée", "/cartes/Pierre.png"),
    TREASURE_ZEPHYR_STATUE("La Statue du Zéphyr", "/cartes/Zephyr.png"),
    TREASURE_ARDENT_CRYSTAL("Le Cristal Ardent", "/cartes/Cristal.png"),
    TREASURE_WAVE_CHALICE("Le Calice de l'Onde", "/cartes/Calice.png"),
    SPECIAL_RISING_WATERS("Montée des Eaux", "/cartes/MonteeDesEaux.png"),
    SPECIAL_HELICOPTER("Hélicoptère", "/cartes/Helicoptere.png"),
    SPECIAL_SAND_BAG("Sacs de Sable", "/cartes/SacsDeSable.png"),
    EMPTY_CARD("Carte vide", "/cartes/FondRouge.png");

    private final String name;
    private final String imageFile;

    private Image cacheImage;

    TreasureCardGraphicalMetadata(final String name, final String imageFilename) {
        this.name = name;
        this.imageFile = imageFilename;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        if (cacheImage == null) {
            try {
                cacheImage = ImageIO.read(getClass().getResourceAsStream(imageFile));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load image");
            }
        }

        return cacheImage;
    }

    public static TreasureCardGraphicalMetadata from(final Treasure treasure) {
        if (treasure.getName().equals(TREASURE_SACRED_STONE.getName())) {
            return TREASURE_SACRED_STONE;
        } else if (treasure.getName().equals(TREASURE_ZEPHYR_STATUE.getName())) {
            return TREASURE_ZEPHYR_STATUE;
        } else if (treasure.getName().equals(TREASURE_ARDENT_CRYSTAL.getName())) {
            return TREASURE_ARDENT_CRYSTAL;
        } else if (treasure.getName().equals(TREASURE_WAVE_CHALICE.getName())) {
            return TREASURE_WAVE_CHALICE;
        } else if (treasure.getName().equals((EMPTY_CARD.getName()))){
            return EMPTY_CARD;
        }

        return null;
    }
}
