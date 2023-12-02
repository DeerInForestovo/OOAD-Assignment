package dao;

public class Dao {
    ComputerDao computerDao;
    StaffDao staffDao;
    public Dao(ComputerDao computerDao, StaffDao staffDao) {
        this.computerDao = computerDao;
        this.staffDao = staffDao;
    }

    public ComputerDao getComputerDao() {
        return computerDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }
}
