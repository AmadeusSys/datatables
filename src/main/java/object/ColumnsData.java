package object;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by TY on 2017/8/12.
 */
public class ColumnsData {

    @ApiModelProperty(value = "数据字段的字段名",required = false)
    private String data;

    @ApiModelProperty(value = "暂时闲置字段",required = false)
    private String name;

    @ApiModelProperty(value = "暂时闲置字段",required = false)
    private String searchable;

    @ApiModelProperty(value = "暂时闲置字段",required = false)
    private String orderable;

    @ApiModelProperty(value = "暂时闲置字段",required = false)
    private SearchData search;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchable() {
        return searchable;
    }

    public void setSearchable(String searchable) {
        this.searchable = searchable;
    }

    public String getOrderable() {
        return orderable;
    }

    public void setOrderable(String orderable) {
        this.orderable = orderable;
    }

    public SearchData getSearch() {
        return search;
    }

    public void setSearch(SearchData search) {
        this.search = search;
    }

}
