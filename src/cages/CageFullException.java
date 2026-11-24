package cages;

// Виняток: спроба додати тварину у вольєр, де всі місця зайняті.
// Unchecked (RuntimeException) — це порушення правила, яке викликаючий код може перевірити заздалегідь через isFull().
public class CageFullException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CageFullException(String message) {
        super(message);
    }
}
