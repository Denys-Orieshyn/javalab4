import animals.Eagle;
import animals.Giraffe;
import animals.Lion;
import animals.Zebra;
import cages.AnimalNotFoundException;
import cages.BirdCage;
import cages.Cage;
import cages.CageFullException;
import cages.LionCage;
import cages.UngulateCage;
import zoo.Zoo;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LionCage lionCage = new LionCage(2);
        UngulateCage ungulateCage = new UngulateCage(4);
        BirdCage birdCage = new BirdCage(3);

        // --- Розміщення тварин ---
        Lion simba = new Lion("Сімба");
        lionCage.add(simba);
        lionCage.add(new Lion("Нала"));

        // addAll приймає List<Zebra>, хоча вольєр типу Ungulate — завдяки <? extends T>
        List<Zebra> zebras = List.of(new Zebra("Марті"), new Zebra("Смужка"));
        ungulateCage.addAll(zebras);
        ungulateCage.add(new Giraffe("Мелман"));

        birdCage.add(new Eagle("Беркут"));

        // Обмеження generics перевіряє компілятор — ці рядки не скомпілюються:
        // lionCage.add(new Zebra("Зайва"));      // у вольєр левів — тільки леви
        // birdCage.add(new Lion("Зайвий"));      // у вольєр птахів — тільки птахи
        // ungulateCage.add(new Eagle("Зайвий")); // у вольєр копитних — тільки копитні

        printCage(lionCage);
        printCage(ungulateCage);
        printCage(birdCage);

        // --- Винятки ---
        System.out.println("\n--- Перевірка винятків ---");
        try {
            lionCage.add(new Lion("Муфаса")); // місткість 2, вже 2 леви
        } catch (CageFullException e) {
            System.out.println("CageFullException: " + e.getMessage());
        }

        try {
            birdCage.remove(new Eagle("Сокіл")); // такого орла у вольєрі немає
        } catch (AnimalNotFoundException e) {
            System.out.println("AnimalNotFoundException: " + e.getMessage());
        }

        // --- Вилучення ---
        lionCage.remove(simba);
        System.out.println("\nПісля вилучення: " + lionCage);

        // --- Зоопарк ---
        Zoo zoo = new Zoo();
        zoo.addCage(lionCage);
        zoo.addCage(ungulateCage);
        zoo.addCage(birdCage);

        System.out.println("\nВольєрів у зоопарку: " + zoo.getCages().size());
        System.out.println("Тварин у зоопарку: " + zoo.getCountOfAnimals());
    }

    // Wildcard <?>: метод друкує вольєр будь-якого типу
    private static void printCage(Cage<?> cage) {
        System.out.println(cage.getTitle() + ": зайнято " + cage.getOccupiedCount()
                + " з " + cage.getCapacity() + " -> " + cage.getAnimals());
    }
}
