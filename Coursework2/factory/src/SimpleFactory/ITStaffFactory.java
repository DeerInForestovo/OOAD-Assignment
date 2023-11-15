package SimpleFactory;

public class ITStaffFactory {
    public ITStaff createITStaff(int tag) {
        if (tag == 1) return new ITManager();
        else if (tag == 2) return new Developer();
        else if (tag == 3) return new Tester();
        else return null;
    }
}
