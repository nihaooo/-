package explame.com.imooctestone.entity;

/*
 *      项目名：    ImoocTestOne
 *      包名：       explame.com.imooctestone.entity
 *      时间           2017/5/8.
 *      创建者：    qzhuorui
 *      描述：        微信精选的数据类
 */
public class WeChatData {

    //标题
    private String title;
    //出处
    private String source;
    //图片url
    private String imgUrl;
    //新闻地址
    private String newsUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
