package object.mapper;


import object.DataTablesRequestData;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface QueryDataTablesMapper<T> {

    @SelectProvider(type = QueryDataTablesProvider.class, method = "dynamicSQL")
    List<T> listDataTables(DataTablesRequestData<T> DataTablesRequestData);

    @SelectProvider(type = QueryDataTablesProvider.class, method = "dynamicSQL")
    Integer countDataTables(DataTablesRequestData<T> DataTablesRequestData);

    @SelectProvider(type = QueryDataTablesProvider.class, method = "dynamicSQL")
    Integer countNoDelete();
}
