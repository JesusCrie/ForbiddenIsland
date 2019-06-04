package iut2.forbiddenisland.model.adventurer.power;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.Adventurer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This power allow the current player to send cards to any player.
 */
public class MessengerPower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_SEND
                && req.getCurrentPlayer().equals(req.getData(Request.DATA_PLAYER))
                && !req.getData(Request.DATA_PLAYER).equals(req.getData(Request.DATA_PLAYER_EXTRA))) {
            req.bypassChecks();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.PLAYERS_SENDABLE) {
            final Response<List<Adventurer>> castedRes = (Response<List<Adventurer>>) res;

            final List<Adventurer> players = board.getCells().values().stream()
                    .flatMap(c -> c.getAdventurers().stream())
                    .filter(pl -> !pl.equals(res.getOriginRequest().getCurrentPlayer()))
                    .collect(Collectors.toList());

            castedRes.setData(players);
        }
    }
}
