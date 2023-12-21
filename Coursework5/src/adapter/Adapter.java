package adapter;

import original.FileOperateInterfaceV1;
import original.FileOperateInterfaceV2;
import original.StaffModel;

import java.util.Comparator;
import java.util.List;

public class Adapter implements FileOperateInterfaceV2 {
    private FileOperateInterfaceV1 adaptee;

    public Adapter(FileOperateInterfaceV1 adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public List<StaffModel> readAllStaff() {
        return adaptee.readStaffFile();
    }

    @Override
    public void listAllStaff(List<StaffModel> list) {
        adaptee.printStaffFile(list);
    }

    @Override
    public void writeByName(List<StaffModel> list) {
        list.sort(Comparator.comparingInt(a -> a.getName().charAt(0)));
        adaptee.writeStaffFile(list);
    }

    @Override
    public void writeByRoom(List<StaffModel> list) {
        list.sort(Comparator.comparingInt(a -> a.getRoom().charAt(0)));
        adaptee.writeStaffFile(list);
    }
}
