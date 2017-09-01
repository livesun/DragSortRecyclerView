package drag_sort.dragsortrecyclerview.channel;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by 29028 on 2017/8/19.
 */

public class ToucheCallBcak extends ItemTouchHelper.Callback {

    private IItemHelper itemHelper;

    public ToucheCallBcak(IItemHelper itemHelper){

        this.itemHelper = itemHelper;
    }

    //声明不同转台下的移动方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

   // 拖动的条目从旧位置--到新位置时调用
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 不同Type之间不可移动
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        itemHelper.itemMoved(viewHolder.getLayoutPosition(),target.getLayoutPosition());
        return false;
    }

    // 滑动到消失调用
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemHelper.itemDismiss(viewHolder.getLayoutPosition());
    }

    /**
     * true --开启长按
     * false --关闭长按拖动
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 关闭侧滑
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {

        return false;
    }
    //状态改变时调用 比如正在滑动，正在拖动
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //不是空闲状态--背景加深
//        if(actionState!= ItemTouchHelper.ACTION_STATE_IDLE){
//            viewHolder.itemView.setBackgroundColor(Color.GRAY);
//        }
    }

    //滑动完，拖动完调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
//        viewHolder.itemView.setBackgroundColor(0);
    }

//重写这个方法修改你的UI响应
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //正在拖动
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
          /*  final float alpha = 80 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);*/

        }else if(actionState==ItemTouchHelper.ACTION_STATE_IDLE){
             //正在滑动
         }else if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
         }
    }
}
