package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.adventurer.Power;

public class EngineerPower implements Power {

    // if useAction = adventurer use one action
    // if not useAction = adventurer don't use action
    private boolean useAction = false;

    @Override
    public void alterRequest(final Request req) {
        if (req.getType() == RequestType.GAME_NEW_ROUND) {
            useAction = false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_DRY && res.isOk()) {
            if (useAction ) {
                res.setData(0);
                useAction = false;
            } else {
                useAction = true;
            }
        }
    }
}
