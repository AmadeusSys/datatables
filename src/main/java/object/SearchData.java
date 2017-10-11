package object;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by TY on 2017/8/12.
 */
public class SearchData {

    @ApiModelProperty(value = "检索数据内容",required = false)
    private String value;

    @ApiModelProperty(value = "检索数据是否使用了正则表达式",required = false)
    private Boolean regex;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRegex() {
        return regex;
    }

    public void setRegex(Boolean regex) {
        this.regex = regex;
    }

}
