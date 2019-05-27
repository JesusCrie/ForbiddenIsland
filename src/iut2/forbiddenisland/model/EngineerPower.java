package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.adventurer.Power;

public class EngineerPower implements Power {

    // if useAction = adventurer use one action
    // if not useAction = adventurer don't use action
    private boolean useAction;

    @Override
    public void alterRequest(final Request req) {
        if (req.getType() == RequestType.GAME_NEW_ROUND) {
            useAction = false;
        } else if (req.getType() == RequestType.PLAYER_DRY) {
            if (useAction) {
                req.bypassChecks();
            }
        }
    }

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_DRY) {
            if (!useAction) {
                useAction = true;
                //Next drying will use action
            } else if (useAction) {
                useAction = false;
                //Action has been used
            }


        }
    }
}
