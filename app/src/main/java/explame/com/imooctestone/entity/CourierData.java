package explame.com.imooctestone.entity;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.entity
 *      时间           2017/5/6.
 *      创建者：    qzhuorui
 *      描述：        TODO
 */
public class CourierData {

    //时间
    private String datetime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
