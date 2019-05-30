package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.Power;

public class EngineerPower implements Power {

    // if useAction = adventurer use one action
    // if not useAction = adventurer don't use action
    private boolean useAction = false;
    private boolean lock = false;

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.GAME_NEW_ROUND) {
            useAction = false;
            lock = false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_DRY) {
            if (useAction && !lock) {
                res.setData(0);
                lock = true;
            } else {
                useAction = true;
            }
        }
    }
}
