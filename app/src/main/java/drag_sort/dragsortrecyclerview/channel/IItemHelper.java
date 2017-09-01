package drag_sort.dragsortrecyclerview.channel;

/**
 * Created by 29028 on 2017/8/19.
 */

public interface IItemHelper {

    void itemMoved(int oldPosition, int newPosition);
    void itemDismiss(int position);
}
