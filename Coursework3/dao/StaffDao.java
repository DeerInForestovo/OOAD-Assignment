package dao;

import bean.Staff;

public interface StaffDao {
    public int insertStaff(Staff staff);

    public int deleteStaff(int id);

    public int updateStaff(int id);
}
