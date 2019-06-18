package iut2.forbiddenisland.view;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public enum AdventurerGraphicalMetadata {

    DIVER("Plongeur", "/personnages/plongeur.png", "/pions/pionNoir.png"),
    ENGINEER("Ingénieur", "/personnages/ingenieur.png", "/pions/pionRouge.png"),
    EXPLORER("Explorateur", "/personnages/explorateur.png", "/pions/pionVert.png"),
    MESSENGER("Messager", "/personnages/messager.png", "/pions/pionGris.png"),
    NAVIGATOR("Navigateur", "/personnages/navigateur.png", "/pions/pionJaune.png"),
    PILOT("Pilote", "/personnages/pilote.png", "/pions/pionBleu.png");

    private final String name;
    private final String cardFile;
    private final String pieceFile;

    private Image cacheCardImage;
    private Image cachePieceImage;

    AdventurerGraphicalMetadata(final String name, final String cardFile, final String pieceFile) {
        this.name = name;
        this.cardFile = cardFile;
        this.pieceFile = pieceFile;
    }

    public String getName() {
        return name;
    }

    public Image getCardImage() {
        if (cacheCardImage == null) {
            try {
                // Lazy load image
                cacheCardImage = ImageIO.read(getClass().getResourceAsStream(cardFile));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load card image");
            }
        }

        return cacheCardImage;
    }

    public Image getPieceImage() {
        if (cachePieceImage == null) {
            try {
                // Lazy load image
                cachePieceImage = ImageIO.read(getClass().getResourceAsStream(pieceFile));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load piece image");
            }
        }

        return cachePieceImage;
    }
}
