package StaticFactory;

public abstract class StaticFactory {
    static public ITStaff createITManager() {
        return new ITManager();
    }
    static public ITStaff createDeveloper() {
        return new Developer();
    }
    static public ITStaff createTester() {
        return new Tester();
    }
}
