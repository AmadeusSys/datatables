package object;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrderData {

  /**
   * Column to which ordering should be applied. This is an index reference to the columns array of
   * information that is also submitted to the server.
   */
  @NotNull
  @Min(0)
  @ApiModelProperty(value = "字段数据的下标",required = false)
  private Integer column;

  /**
   * Ordering direction for this column. It will be asc or desc to indicate ascending ordering or
   * descending ordering, respectively.
   */
  @NotNull
  @ApiModelProperty(value = "排序方式desc|asc",required = false)
  @Pattern(regexp = "(desc|asc)")
  private String dir;

  public String getDir() {
    return dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public Integer getColumn() {
    return column;
  }

  public void setColumn(Integer column) {
    this.column = column;
  }
}
