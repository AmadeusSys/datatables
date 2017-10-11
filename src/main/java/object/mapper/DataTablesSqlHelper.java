package object.mapper;


import object.EntityTool;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Iterator;
import java.util.Set;

public class DataTablesSqlHelper extends SqlHelper{


    public static String dataTables_selectAllColumns(Class<?> entityClass){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(EntityTool.yt_getSelectFieldByMethods(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    public static String dataTables_whereAllIfColumns(Class<?> entityClass, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        sql.append("<if test=\"queryModel != null\" >");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var4 = columnList.iterator();

        while(var4.hasNext()) {
            EntityColumn column = (EntityColumn)var4.next();
            sql.append(getIfNotNull("queryModel", column, " AND " + column.getColumnEqualsHolder("queryModel"), empty));
        }

        sql.append("</if>");
        sql.append("<if test=\"search != null\" >");
        String likeField = EntityTool.yt_getLikeFieldName(entityClass);
        String likeConditions = String.format("and %s LIKE #{search.value,javaType=java.lang.String}",likeField);
        sql.append(dataTables_getLike("search", "value", likeConditions, empty));
        sql.append("</if>");
        sql.append("</where>");
        return sql.toString();
    }

    public static String dataTables_getLike(String entityName, String column, String contents, boolean empty){

        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }

        sql.append(column).append(" != null");

        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();

    }

    public static String dataTables_orderBy() {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"realOrder != null\">");
        sql.append("<foreach collection=\"realOrder\" item=\"orderItem\" open=\" order by \" separator=\",\" close=\"  \" >");
        sql.append("${orderItem.columnName} ${orderItem.dir}");
        sql.append("</foreach> ");
        sql.append("</if>");
        return sql.toString();
    }

    public static String dataTables_limit(){
        return "LIMIT #{start},#{length} ";
    }

    public static String dataTables_whereNoDelete(Class<?> entityClass){
        String deleteField = EntityTool.yt_getDeleteFieldName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        sql.append(deleteField);
        sql.append(" = 0");
        sql.append("</where>");
        return sql.toString();
    }

}
