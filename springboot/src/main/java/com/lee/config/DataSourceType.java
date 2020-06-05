package com.lee.config;

import org.apache.tomcat.jdbc.pool.DataSource;

public class DataSourceType {

    //内部枚举类，用于选择特定的数据类型
    public enum DataBaseType {

        Ds1, Ds2, Ds3;
        private String name;

        private DataSource dataSource;

        DataBaseType() {
        }

        DataBaseType(String name, DataSource dataSource) {
            this.name = name;
            this.dataSource = dataSource;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DataSource getDataSource() {
            return dataSource;
        }

        public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    }


    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>();

    // 往当前线程里设置数据源类型
    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataBaseType);
    }

    // 获取数据源类型
    public static DataBaseType getDataBaseType() {
        DataBaseType dataBaseType = TYPE.get() == null ? DataBaseType.Ds1 : TYPE.get();
        return dataBaseType;
    }

    // 清空数据类型
    public static void clearDataBaseType() {
        TYPE.remove();
    }


}
