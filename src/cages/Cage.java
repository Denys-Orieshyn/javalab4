package cages;

import animals.Animal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Узагальнений вольєр. T — тип тварин, яких можна в нього помістити.
// Обмеження <T extends Animal>: у вольєр можна покласти тільки тварину (не String, не Integer).
// Конкретні нащадки звужують T: LionCage — тільки Lion, BirdCage — тільки Bird і т.д.
public abstract class Cage<T extends Animal> {
    private final int capacity;
    private final List<T> animals = new ArrayList<>();

    protected Cage(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість вольєра має бути більше 0, отримано: " + capacity);
        }
        this.capacity = capacity;
    }

    // Максимальна кількість місць
    public int getCapacity() {
        return capacity;
    }

    // Кількість зайнятих місць
    public int getOccupiedCount() {
        return animals.size();
    }

    public boolean isFull() {
        return animals.size() >= capacity;
    }

    // Розміщення тварини у вольєрі. Якщо місць немає — CageFullException.
    public void add(T animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Не можна додати null замість тварини");
        }
        if (animals.contains(animal)) {
            throw new IllegalArgumentException(animal + " вже знаходиться у цьому вольєрі");
        }
        if (isFull()) {
            throw new CageFullException("Вольєр заповнений (" + capacity + " з " + capacity
                    + "), неможливо додати: " + animal);
        }
        animals.add(animal);
    }

    // Додавання кількох тварин одразу.
    // Wildcard <? extends T>: у вольєр копитних (T = Ungulate) можна передати і List<Zebra>,
    // і List<Giraffe>, і List<Ungulate>. Без wildcard підійшов би тільки List<Ungulate>.
    public void addAll(Collection<? extends T> newAnimals) {
        for (T animal : newAnimals) {
            add(animal);
        }
    }

    // Вилучення тварини з вольєра. Якщо такої тварини тут немає — AnimalNotFoundException.
    public void remove(T animal) {
        boolean removed = animals.remove(animal); // true — тварину видалено, false — її не було у списку
        if (removed == false) {
            throw new AnimalNotFoundException(animal + " не знаходиться у цьому вольєрі");
        }
    }

    // Повертаємо незмінний "вид" на список, щоб ззовні не можна було обійти перевірку місткості
    public List<T> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    // Назва вольєра для виводу — кожен нащадок задає свою
    public abstract String getTitle();

    @Override
    public String toString() {
        return getTitle() + " [" + getOccupiedCount() + "/" + capacity + "]: " + animals;
    }
}
