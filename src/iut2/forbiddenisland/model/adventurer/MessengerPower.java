package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.adventurer.Power;

public class MessengerPower implements Power {

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_SEND) {
            res.setData(true);
        }
    }
}
