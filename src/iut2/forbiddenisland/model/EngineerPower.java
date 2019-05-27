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
    public void alterRequest(Request req) {
        if (req.getType() == RequestType.PLAYER_MOVE_AMOUNT) {
            useAction = false;
        }
    }

    @Override
    public void alterResponse(Response res) {
        if (res.getOriginRequest().getType() == RequestType.CELL_DRY) {
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
