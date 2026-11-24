package animals;

// Базовий клас для всіх тварин зоопарку.
// abstract — бо "просто тварину" створити не можна, тільки конкретний вид.
public abstract class Animal {
    private final String name;

    protected Animal(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ім'я тварини не може бути порожнім");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Назва виду ("Лев", "Зебра"...) — кожен конкретний клас повертає свою
    public abstract String getSpecies();

    @Override
    public String toString() {
        return getSpecies() + " " + name;
    }
}
