package cages;

// Виняток: спроба вилучити тварину, якої немає у вольєрі.
public class AnimalNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AnimalNotFoundException(String message) {
        super(message);
    }
}
