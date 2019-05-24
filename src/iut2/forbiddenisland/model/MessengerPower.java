package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.RequestType;
import iut2.forbiddenisland.controller.Response;

public class MessengerPower implements Power {

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_SEND) {
            res.setData(true);
        }
    }
}
