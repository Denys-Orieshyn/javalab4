package zoo;

import animals.Eagle;
import animals.Giraffe;
import animals.Lion;
import animals.Zebra;
import cages.BirdCage;
import cages.LionCage;
import cages.UngulateCage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZooTest {

    private Zoo zoo;
    private LionCage lionCage;
    private UngulateCage ungulateCage;
    private BirdCage birdCage;

    @BeforeEach
    void setUp() {
        zoo = new Zoo();
        lionCage = new LionCage(3);
        ungulateCage = new UngulateCage(5);
        birdCage = new BirdCage(4);
    }

    // Порожній зоопарк — 0 тварин.
    @Test
    @DisplayName("Порожній зоопарк -> 0 тварин")
    void emptyZoo() {
        assertEquals(0, zoo.getCountOfAnimals());
        assertTrue(zoo.getCages().isEmpty());
    }

    // Вольєри без тварин теж дають 0.
    @Test
    @DisplayName("Зоопарк з порожніми вольєрами -> 0 тварин")
    void zooWithEmptyCages() {
        zoo.addCage(lionCage);
        zoo.addCage(birdCage);

        assertEquals(2, zoo.getCages().size());
        assertEquals(0, zoo.getCountOfAnimals());
    }

    // Вольєри різних типів (Cage<Lion>, Cage<Ungulate>, Cage<Bird>) зберігаються в одному зоопарку
    // завдяки wildcard, і тварини рахуються в усіх вольєрах разом.
    @Test
    @DisplayName("Підрахунок тварин у вольєрах різних типів")
    void countAnimalsInDifferentCages() {
        lionCage.add(new Lion("Сімба"));
        lionCage.add(new Lion("Нала"));
        ungulateCage.add(new Zebra("Марті"));
        ungulateCage.add(new Giraffe("Мелман"));
        ungulateCage.add(new Zebra("Смужка"));
        birdCage.add(new Eagle("Беркут"));

        zoo.addCage(lionCage);
        zoo.addCage(ungulateCage);
        zoo.addCage(birdCage);

        assertEquals(6, zoo.getCountOfAnimals());
    }

    // Зоопарк зберігає посилання на вольєри, тому зміни у вольєрі відразу видно в підрахунку.
    @Test
    @DisplayName("Підрахунок оновлюється після додавання і вилучення тварин")
    void countReflectsChanges() {
        zoo.addCage(lionCage);
        Lion simba = new Lion("Сімба");

        lionCage.add(simba);
        assertEquals(1, zoo.getCountOfAnimals());

        lionCage.remove(simba);
        assertEquals(0, zoo.getCountOfAnimals());
    }

    // null замість вольєра додавати не можна.
    @Test
    @DisplayName("addCage(null) -> IllegalArgumentException")
    void addNullCageThrows() {
        assertThrows(IllegalArgumentException.class, () -> zoo.addCage(null));
    }
}
