package zoo;

import animals.Animal;
import cages.Cage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Zoo {
    // Wildcard <? extends Animal>: в одному списку зберігаються вольєри різних типів —
    // LionCage (Cage<Lion>), UngulateCage (Cage<Ungulate>), BirdCage (Cage<Bird>).
    // List<Cage<Animal>> так не зміг би: Cage<Lion> НЕ є нащадком Cage<Animal>.
    private final List<Cage<? extends Animal>> cages = new ArrayList<>();

    public void addCage(Cage<? extends Animal> cage) {
        if (cage == null) {
            throw new IllegalArgumentException("Вольєр не може бути null");
        }
        cages.add(cage);
    }

    // Кількість тварин у всіх вольєрах зоопарку
    public int getCountOfAnimals() {
        int count = 0;
        for (Cage<? extends Animal> cage : cages) {
            count += cage.getOccupiedCount();
        }
        return count;
    }

    public List<Cage<? extends Animal>> getCages() {
        return Collections.unmodifiableList(cages);
    }
}
