package racingcar.models;

import java.util.Objects;

public class Car {
    private final String name;
    private int position = 1;

    private final static int MAX_NAME_LENGTH = 5;

    public Car(String name, int position) {
        this.name = checkName(name);
        this.position = checkPosition(position);
    }

    public Car(String name) {
        this(name, 1);
    }

    public String getName() {
        return name;
    }
    public int getPosition() {
        return position;
    }

    private String checkName(String name) {
        if (Objects.isNull(name) || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new RuntimeException("잘못된 자동차 이름입니다.");
        }
        return name;
    }

    private int checkPosition(int position) {
        if (position < 1) {
            throw new RuntimeException("잘못된 위치입니다.");
        }
        return position;
    }

    public void moveWithPower(int power) {
        if (power > 3) {
            this.position++;
        }
    }

    public String getCurrentPosition() {
        return this.name + " : "
                + "-".repeat(Math.max(0, this.position));
    }

    public boolean throughSpecificPoint(int point) {
        return position >= point;
    }
}
