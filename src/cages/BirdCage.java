package cages;

import animals.Bird;

// Вольєр для птахів: приймає будь-якого птаха (зараз у зоопарку це орли).
public class BirdCage extends Cage<Bird> {
    public BirdCage(int capacity) {
        super(capacity);
    }

    @Override
    public String getTitle() {
        return "Вольєр для птахів";
    }
}
