package iut2.forbiddenisland.view;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public enum IconGraphicalMetadata {

    ICON_DRY("/icones/iconDry.png"),
    ICON_SEND("/icones/iconGive.png"),
    ICON_MOVE("/icones/iconMove.png"),
    ICON_DONE("/icones/iconDone.png"),
    ICON_CLAIM("/icones/iconGet.png"),
    ICON_WATER_LEVEL("/Niveau.png"),
    ICON_WATER_LEVEL_CURSOR("/stick.png");

    private final String image;
    private Image cacheImage;

    IconGraphicalMetadata(final String image) {
        this.image = image;
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
