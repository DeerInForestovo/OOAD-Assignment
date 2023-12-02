package singleton;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import dao.*;

public class DaoFactoryImpl implements DaoFactory {
    private static final DaoFactoryImpl uniqueFactoryImpl = new DaoFactoryImpl();

    private DaoFactory daoFactory = null;

    public static DaoFactoryImpl getInstance() {
        return uniqueFactoryImpl;
    }

    @Override
    public Dao createDao() {
        return daoFactory.createDao();
    }

    private DaoFactoryImpl() {
        Properties prop = new Properties();
        try {
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream("src/resource.properties"));
            prop.load(inputStream);
            try {
                String factoryName = "dao." + prop.getProperty("database") + "DaoFactory";
                Class clz = Class.forName(factoryName);
                daoFactory = (DaoFactory) clz.getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
