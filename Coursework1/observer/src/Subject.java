public interface Subject<Observer> {
    void notifyObservers(char key);
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void clearObservers();
}