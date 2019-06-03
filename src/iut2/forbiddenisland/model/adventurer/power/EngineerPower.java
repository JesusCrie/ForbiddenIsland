package iut2.forbiddenisland.model.adventurer.power;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;

/**
 * Allow the current player to dry two cells while consuming one action.
 * This is implemented with the second cell to by dried costs nothing.
 */
public class EngineerPower implements Power {

    // if useAction = adventurer use one action
    private boolean canUseAction = false;

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.GAME_NEW_ROUND) {
            canUseAction = true;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_DRY && res.isOk()) {
            if (canUseAction) {
                res.setData(0);
                canUseAction = false;
            } else {
                canUseAction = true;
            }
        }
    }
}
