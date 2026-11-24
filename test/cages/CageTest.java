package cages;

import animals.Eagle;
import animals.Giraffe;
import animals.Lion;
import animals.Ungulate;
import animals.Zebra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CageTest {

    private LionCage lionCage;
    private UngulateCage ungulateCage;
    private BirdCage birdCage;

    // Перед кожним тестом — нові порожні вольєри
    @BeforeEach
    void setUp() {
        lionCage = new LionCage(2);
        ungulateCage = new UngulateCage(3);
        birdCage = new BirdCage(2);
    }

    // ---------- місткість і зайняті місця ----------

    // Новий вольєр порожній, а максимальна кількість місць — та, що передали в конструктор.
    @Test
    @DisplayName("Новий вольєр: місткість задана, зайнятих місць 0")
    void newCageIsEmpty() {
        assertEquals(2, lionCage.getCapacity());
        assertEquals(0, lionCage.getOccupiedCount());
        assertFalse(lionCage.isFull());
    }

    // Місткість 0 або від'ємна не має сенсу — конструктор має кинути виняток.
    @ParameterizedTest(name = "місткість {0}")
    @ValueSource(ints = {0, -1, -10})
    @DisplayName("Некоректна місткість -> IllegalArgumentException")
    void invalidCapacity(int capacity) {
        assertThrows(IllegalArgumentException.class, () -> new LionCage(capacity));
    }

    // ---------- наповнення ----------

    // Кожне додавання збільшує кількість зайнятих місць на 1, тварини зберігаються у вольєрі.
    @Test
    @DisplayName("Наповнення вольєра левів")
    void fillLionCage() {
        Lion simba = new Lion("Сімба");
        Lion nala = new Lion("Нала");

        lionCage.add(simba);
        assertEquals(1, lionCage.getOccupiedCount());

        lionCage.add(nala);
        assertEquals(2, lionCage.getOccupiedCount());
        assertTrue(lionCage.isFull());
        assertEquals(List.of(simba, nala), lionCage.getAnimals());
    }

    // У вольєр копитних можна помістити і зебр, і жирафів — обидва є Ungulate.
    @Test
    @DisplayName("Вольєр копитних приймає зебр і жирафів")
    void ungulateCageAcceptsZebraAndGiraffe() {
        ungulateCage.add(new Zebra("Марті"));
        ungulateCage.add(new Giraffe("Мелман"));

        assertEquals(2, ungulateCage.getOccupiedCount());
    }

    // Вольєр для птахів приймає орлів.
    @Test
    @DisplayName("Вольєр птахів приймає орлів")
    void birdCageAcceptsEagles() {
        birdCage.add(new Eagle("Беркут"));
        birdCage.add(new Eagle("Сокіл"));

        assertEquals(2, birdCage.getOccupiedCount());
    }

    // addAll(Collection<? extends T>): для вольєра типу Ungulate можна передати List<Zebra>.
    @Test
    @DisplayName("addAll приймає List<Zebra> для вольєра копитних (wildcard)")
    void addAllWithSubtypeList() {
        List<Zebra> zebras = List.of(new Zebra("Марті"), new Zebra("Смужка"));
        ungulateCage.addAll(zebras);

        assertEquals(2, ungulateCage.getOccupiedCount());
    }

    // Коли всі місця зайняті, додавання ще однієї тварини має кинути CageFullException,
    // а кількість тварин не повинна змінитись.
    @Test
    @DisplayName("Повний вольєр -> CageFullException")
    void addToFullCageThrows() {
        lionCage.add(new Lion("Сімба"));
        lionCage.add(new Lion("Нала"));

        assertThrows(CageFullException.class, () -> lionCage.add(new Lion("Муфаса")));
        assertEquals(2, lionCage.getOccupiedCount());
    }

    // Якщо addAll переповнює вольєр, виняток кидається на тій тварині, якій не вистачило місця.
    @Test
    @DisplayName("addAll понад місткість -> CageFullException")
    void addAllOverCapacityThrows() {
        List<Ungulate> herd = List.of(
                new Zebra("1"), new Zebra("2"), new Giraffe("3"), new Giraffe("4"));

        assertThrows(CageFullException.class, () -> ungulateCage.addAll(herd));
        assertEquals(3, ungulateCage.getOccupiedCount());
    }

    // null замість тварини додавати не можна.
    @Test
    @DisplayName("add(null) -> IllegalArgumentException")
    void addNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> lionCage.add(null));
    }

    // Одну й ту саму тварину не можна посадити у вольєр двічі.
    @Test
    @DisplayName("Повторне додавання тієї ж тварини -> IllegalArgumentException")
    void addSameAnimalTwiceThrows() {
        Lion simba = new Lion("Сімба");
        lionCage.add(simba);

        assertThrows(IllegalArgumentException.class, () -> lionCage.add(simba));
        assertEquals(1, lionCage.getOccupiedCount());
    }

    // ---------- вилучення ----------

    // Після вилучення тварини місце звільняється, і її більше немає у списку.
    @Test
    @DisplayName("Вилучення тварини звільняє місце")
    void removeAnimal() {
        Lion simba = new Lion("Сімба");
        lionCage.add(simba);
        lionCage.add(new Lion("Нала"));

        lionCage.remove(simba);

        assertEquals(1, lionCage.getOccupiedCount());
        assertFalse(lionCage.getAnimals().contains(simba));
        assertFalse(lionCage.isFull());
    }

    // Вилучення тварини, якої немає у вольєрі, має кинути AnimalNotFoundException.
    @Test
    @DisplayName("Вилучення відсутньої тварини -> AnimalNotFoundException")
    void removeMissingAnimalThrows() {
        lionCage.add(new Lion("Сімба"));

        assertThrows(AnimalNotFoundException.class, () -> lionCage.remove(new Lion("Муфаса")));
        assertEquals(1, lionCage.getOccupiedCount());
    }

    // Тварини з однаковим ім'ям — різні об'єкти. Вилучити можна тільки ту, що справді сидить у вольєрі.
    @Test
    @DisplayName("Лев з таким самим ім'ям, але інший об'єкт — не знаходиться")
    void removeAnimalWithSameNameButDifferentObject() {
        lionCage.add(new Lion("Сімба"));

        assertThrows(AnimalNotFoundException.class, () -> lionCage.remove(new Lion("Сімба")));
    }

    // ---------- захист внутрішнього списку ----------

    // getAnimals() повертає незмінний список: обійти перевірку місткості через нього не можна.
    @Test
    @DisplayName("getAnimals() не дозволяє змінити вольєр ззовні")
    void getAnimalsIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class,
                () -> lionCage.getAnimals().add(new Lion("Шрам")));
    }
}
