package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.MessengerPower;

/**
 * Represent the character "messenger".
 */
public class Messenger extends Adventurer {

    public Messenger() {
        super("Messager",
                new MessengerPower()
        );
    }
}
