package cages;

import animals.Mammal;

// Вольєр для ссавців. Звужує обмеження: T тепер має бути ссавцем.
// Сам клас абстрактний — у зоопарку є тільки конкретні вольєри для левів і для копитних.
public abstract class MammalCage<T extends Mammal> extends Cage<T> {
    protected MammalCage(int capacity) {
        super(capacity);
    }
}
