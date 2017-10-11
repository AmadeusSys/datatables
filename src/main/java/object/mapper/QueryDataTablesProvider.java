package object.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * Created by TY on 2017/7/28.
 */
public class QueryDataTablesProvider extends MapperTemplate {

    public QueryDataTablesProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 获取过滤数据
     * @param ms
     * @return
     */
    public String listDataTables(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(DataTablesSqlHelper.dataTables_selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(DataTablesSqlHelper.dataTables_whereAllIfColumns(entityClass, this.isNotEmpty()));
        sql.append(DataTablesSqlHelper.dataTables_orderBy());
        sql.append(DataTablesSqlHelper.dataTables_limit());
        System.out.println(sql);
        return sql.toString();
    }

    /**
     * 获取条件过滤的数据条数
     * @param ms
     * @return
     */
    public String countDataTables(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(DataTablesSqlHelper.dataTables_whereAllIfColumns(entityClass, this.isNotEmpty()));
        return sql.toString();
    }

    /**
     * 获取未被删除的数据条数
     * @param ms
     * @return
     */
    public String countNoDelete(MappedStatement ms){
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(DataTablesSqlHelper.dataTables_whereNoDelete(entityClass));
        System.out.println(sql);
        return sql.toString();
    }

}
