package dao;

public class MysqlDaoFactory implements DaoFactory {
    @Override
    public Dao createDao() {
        return new Dao(new MysqlComputerDao(), new MysqlStaffDao());
    }
}
