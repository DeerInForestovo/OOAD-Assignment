package mutiAdapter;
import original.*;

import java.util.List;

public class MultiAdapter implements FileOperateInterfaceV2_1 {
    private FileOperateInterfaceV1 fileOperateAdaptee;
    private ManageStaffInterface manageStaffAdaptee;

    public MultiAdapter(FileOperateInterfaceV1 fileOperateAdaptee, ManageStaffInterface manageStaffAdaptee) {
        this.fileOperateAdaptee = fileOperateAdaptee;
        this.manageStaffAdaptee = manageStaffAdaptee;
    }

    @Override
    public List<StaffModel> readAllStaff() {
        return fileOperateAdaptee.readStaffFile();
    }

    @Override
    public void listAllStaff(List<StaffModel> list) {
        fileOperateAdaptee.printStaffFile(list);
    }

    @Override
    public void writeByName(List<StaffModel> list) {
        fileOperateAdaptee.writeStaffFile(list);
    }

    @Override
    public void writeByRoom(List<StaffModel> list) {
        fileOperateAdaptee.writeStaffFile(list);
    }

    @Override
    public void addNewStaff(List<StaffModel> list) {
        manageStaffAdaptee.addingStaff(list);
    }

    @Override
    public void removeStaffByName(List<StaffModel> list) {
        manageStaffAdaptee.removeStaff(list);
    }
}