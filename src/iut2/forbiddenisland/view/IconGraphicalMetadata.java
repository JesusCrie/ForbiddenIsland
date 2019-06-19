package iut2.forbiddenisland.view;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public enum IconGraphicalMetadata {

    ICON_DRY("/icones/iconDry.png"),
    ICON_GIVE("/icones/iconGive.png"),
    ICON_MOVE("/icones/iconMove.png"),
    ICON_SHIFT("/icones/iconShift.png"),
    ICON_GET("/icones/iconGet.png");

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
