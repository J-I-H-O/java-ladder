package controller;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import domain.BooleanGenerator;
import domain.Height;
import domain.ladder.Ladder;
import domain.player.PlayerName;
import domain.player.PlayerNames;
import java.util.List;
import view.InputView;
import view.OutputView;

public class LadderGame {
    private final BooleanGenerator booleanGenerator;

    public LadderGame(BooleanGenerator booleanGenerator) {
        this.booleanGenerator = booleanGenerator;
    }

    public void run() {
        try {
            PlayerNames playerNames = generatePlayers();
            Height height = generateHeight();
            Ladder ladder = generateLadder(playerNames, height);

            OutputView.printPlayerNames(playerNames);
            OutputView.printLadder(playerNames.findMaxNameLength(), ladder);
        } catch (Exception e) {
            OutputView.printErrorMessage(e);
        }
    }

    private PlayerNames generatePlayers() {
        List<String> names = InputView.inputPlayerNames();
        return names.stream()
                .map(PlayerName::new)
                .collect(collectingAndThen(toList(), PlayerNames::new));
    }

    private Height generateHeight() {
        int height = InputView.inputHeight();
        return new Height(height);
    }

    private Ladder generateLadder(PlayerNames playerNames, Height height) {
        return Ladder.create(height, playerNames.getPlayerCount(), booleanGenerator);
    }
}
