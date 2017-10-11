package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TY on 2017/8/12.
 */
public class DataTablesResponseData {


    /**
     * 唯一标识符
     */
    private Integer draw;

    /**
     * 总数据条数不参与模糊查询
     */
    private Integer recordsTotal = 0;

    /**
     * 模糊查询后的总数据条数
     */
    private Integer recordsFiltered = 0;

    /**
     * 数据正体
     */
    private Object data = new ArrayList();

    private Map dictionaryList = new HashMap();

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(Map dictionaryList) {
        this.dictionaryList = dictionaryList;
    }
}
