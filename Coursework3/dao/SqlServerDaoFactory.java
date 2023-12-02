package dao;

public class SqlServerDaoFactory implements DaoFactory {
    @Override
    public Dao createDao() {
        return new Dao(new SqlServerComputerDao(), new SqlServerStaffDao());
    }
}
