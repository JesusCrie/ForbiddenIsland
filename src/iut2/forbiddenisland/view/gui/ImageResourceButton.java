package iut2.forbiddenisland.view.gui;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class ImageResourceButton extends AutoResizeImageButton {

    public ImageResourceButton(final InputStream imageStream) throws IOException {
        super(ImageIO.read(imageStream));
    }
}
