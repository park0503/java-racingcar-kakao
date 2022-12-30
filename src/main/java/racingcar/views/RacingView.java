package racingcar.views;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import racingcar.utils.InputParser;
import racingcar.models.Car;

public class RacingView {
    private final Console console;

    public RacingView(Console console) {
        this.console = console;
    }

    public List<Car> getCars() {
        return requestUntilSuccess(getCarsSupplier());
    }

    public int getCount() {
        return requestUntilSuccess(getCountSupplier());
    }

    public void showCurrentStatus(List<Car> cars) {
        console.printOutput("\n");
        cars.forEach((car) -> console.printOutput(car.getCurrentPosition()));
    }

    public void showResult(List<Car> winners) {
        console.printOutput(String.join(", ", winners.stream().map(Car::getName) + "(이)가 최종 우승했습니다."));
    }

    private Supplier<List<Car>> getCarsSupplier() {
        return () -> {
            String input = console.input("경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).");
            String[] carNames = InputParser.splitByComma(input);
            return Arrays.stream(carNames).map(Car::new).collect(Collectors.toList());
        };
    }

    private Supplier<Integer> getCountSupplier() {
        return () -> {
            String countInput = console.input("시도할 회수는 몇회인가요?");
            return InputParser.parseStringToPositiveInt(countInput);
        };
    }

    private <T> T requestUntilSuccess(Supplier<T> getT) {
        Optional<T> result = wrapTryCatch(getT);
        while (result.isEmpty()) {
            result = wrapTryCatch(getT);
        }
        return result.get();
    }

    private <T> Optional<T> wrapTryCatch(Supplier<T> getT) {
        try {
            return Optional.of(getT.get());
        } catch (RuntimeException e) {
            console.printError(e.getMessage());
            return Optional.empty();
        }
    }
}