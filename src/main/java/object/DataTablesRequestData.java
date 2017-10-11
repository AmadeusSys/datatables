package object;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TY on 2017/8/12.
 */
@Component
public class DataTablesRequestData<T> implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

//    DictionaryItemService dictionaryItemService;

    public DataTablesResponseData toDataTablesResponseData(){

        DataTablesResponseData responseData = new DataTablesResponseData();

        responseData.setDraw(this.getDraw());

//        if (this.getDictionaryName() != null){
//            dictionaryItemService = (DictionaryItemService)applicationContext.getBean("dictionaryItemService");
//            responseData.setDictionaryList(dictionaryItemService.listDictionaryByKeys(this.getDictionaryName()));
//        }

        return responseData;

    }

    /**
     * 生成orderBy数据
     */
    public void generationOrderByData(){
        for (OrderData orderData : this.getOrder()) {

            Integer column = orderData.getColumn();

            String name = this.getFieldName(column);

            String realName = EntityTool.getTableColumnName(queryModel.getClass(),name);

            if (StringUtils.isNotEmpty(realName)){
                RealOrderData realOrderData = new RealOrderData();
                realOrderData.setColumnName(realName);
                realOrderData.setDir(orderData.getDir());
                this.realOrder.add(realOrderData);
            }

        }
    }

    private String getFieldName(Integer columnNumber){

        if (this.getColumns().size() > columnNumber){

            ColumnsData columnsData = this.getColumns().get(columnNumber);

            return columnsData.getData();

        }

        return null;

    }

    @NotNull
    @Min(0)
    @ApiParam(value = "请求批次，用来解决由于网络的阻塞，导致请求批次错误的问题",required = true,defaultValue = "1")
    private Integer draw;

    @NotNull
    @Min(0)
    @ApiParam(value = "请求数据开始条数",required = true,defaultValue = "0")
    private Integer start = 0;

    @NotNull
    @Min(-1)
    @ApiParam(value = "请求数据结束条数",required = true,defaultValue = "25")
    private Integer length = 25;

    @NotNull
    @ApiModelProperty(value = "检索数据",required = false)
    private SearchData search = new SearchData();

    @NotEmpty
    @ApiModelProperty(value = "排序数据",required = false)
    private List<OrderData> order = new ArrayList();

    @NotEmpty
    @ApiModelProperty(value = "数据字段，与排序数据共同使用",required = false)
    private List<ColumnsData> columns = new ArrayList();

    /**
     * 真实排序条件,需要手动生成
     */
    @ApiModelProperty(hidden=true)
    private List<RealOrderData> realOrder = new ArrayList();

    @ApiModelProperty(hidden=true)
    private T queryModel;

    @ApiModelProperty(value = "字典列表",required = false)
    private String[] dictionaryName;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public SearchData getSearch() {
        return search;
    }

    public void setSearch(SearchData search) {
        this.search = search;
    }

    public List<ColumnsData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnsData> columns) {
        this.columns = columns;
    }

    public List<OrderData> getOrder() {
        return order;
    }

    public void setOrder(List<OrderData> order) {
        this.order = order;
    }

    public T getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(T queryModel) {
        this.queryModel = queryModel;
    }

    public String[] getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String[] dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}



