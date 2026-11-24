package cages;

import animals.Lion;

// Вольєр для левів: тип зафіксовано як Lion.
// lionCage.add(new Zebra("...")) — помилка КОМПІЛЯЦІЇ, а не виняток під час роботи.
public class LionCage extends MammalCage<Lion> {
    public LionCage(int capacity) {
        super(capacity);
    }

    @Override
    public String getTitle() {
        return "Вольєр для левів";
    }
}
