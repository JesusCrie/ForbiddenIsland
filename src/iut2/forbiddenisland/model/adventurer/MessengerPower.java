package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.Power;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.List;

public class MessengerPower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_SEND) {
            req.bypassChecks();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.PLAYERS_SENDABLE) {
            final Response<List<Adventurer>> castedRes = (Response<List<Adventurer>>) res;
            castedRes.getData().add(null /* TODO add player cells */);
        }
    }
}
