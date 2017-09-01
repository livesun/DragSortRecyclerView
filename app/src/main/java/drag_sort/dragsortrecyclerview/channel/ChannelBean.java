package drag_sort.dragsortrecyclerview.channel;

/**
 * 类描述：
 * 创建人：livesun
 * 创建时间：2017/8/31
 * 修改人：
 * 修改时间：
 * github：https://github.com/livesun
 */

public class ChannelBean {

    String src;
    int typeView;

    public ChannelBean(String src, int typeView) {
        this.src = src;
        this.typeView = typeView;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getTypeView() {
        return typeView;
    }

    public void setTypeView(int typeView) {
        this.typeView = typeView;
    }
}
