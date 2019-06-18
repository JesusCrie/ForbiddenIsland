package iut2.forbiddenisland.view;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public enum TreasureGraphicalMetadata {

    SACRED_STONE("La pierre sacrée", "/tresors/pierre.png"),
    ZEPHYR_STATUE("La Statue du Zéphyr", "/tresors/zephyr.png"),
    ARDENT_CRYSTAL("Le Cristal Ardent", "/tresors/cristal.png"),
    WAVE_CHALICE("Le Calice de l'Onde", "/tresors/calice.png");

    private final String name;
    private final String image;

    private Image cacheImage;

    TreasureGraphicalMetadata(final String name, final String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        if (cacheImage == null) {
            try {
                cacheImage = ImageIO.read(getClass().getResourceAsStream(image));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load image");
            }
        }

        return cacheImage;
    }
}
