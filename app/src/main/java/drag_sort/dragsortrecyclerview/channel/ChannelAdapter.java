package drag_sort.dragsortrecyclerview.channel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import drag_sort.dragsortrecyclerview.R;


/**
 * 类描述：
 * 创建人：livesun
 * 创建时间：2017/8/31
 * 修改人：
 * 修改时间：
 * github：https://github.com/livesun
 */

public class ChannelAdapter extends RecyclerView.Adapter<ViewHolder> implements IItemHelper {
    //标题
    public static final int CHANNEL_TITLE=1;
    //我的频道
    public static final int MY_CHANNEL=2;
    //其他频道
    public static final int OTHER_CHANNEL=3;
    private final Context mContext;
    private List<ChannelBean> mDatas=new ArrayList<>();
    //我的频道集合。
    List<ChannelBean> mMyChannel=new ArrayList<>();
    //其他频道集合
    List<ChannelBean> mOtherChannel=new ArrayList<>();

    private final LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    //是否编辑模式
    private boolean isEdit;

    public ChannelAdapter(Context context, List<ChannelBean> datas,RecyclerView recyclerView){
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
        for(int i=0;i<mDatas.size();i++){
            if(mDatas.get(i).getTypeView()==2){
                mMyChannel.add(mDatas.get(i));
            }else if(mDatas.get(i).getTypeView()==3){
                mOtherChannel.add(mDatas.get(i));
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view=null;
        if(viewType==CHANNEL_TITLE){
            view = mInflater.inflate(R.layout.channel_title, parent, false);
        }else {
            view = mInflater.inflate(R.layout.channel_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       //是否展示编辑图标
        if(isEdit&&position<=mMyChannel.size()){
            ImageView imageEdit=holder.getView(R.id.img_edit);
            if(imageEdit!=null){
                imageEdit.setVisibility(View.VISIBLE);
            }
        }else {
            ImageView imageEdit=holder.getView(R.id.img_edit);
            if(imageEdit!=null){
                imageEdit.setVisibility(View.GONE);
            }
        }
        TextView channel = holder.getView(R.id.src);
        if(channel!=null){
            channel.setText(mDatas.get(position).getSrc());
        }
        TextView title = holder.getView(R.id.title);
        if(title!=null){
            title.setText(mDatas.get(position).getSrc());
        }

        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mDatas.get(holder.getAdapterPosition()).getTypeView()==MY_CHANNEL){
                    //我的频道执行方法
                    if(isEdit){
                        move(holder.getAdapterPosition());
                    }else{
                        ToastUtil.showShort(mContext,mDatas.get(holder.getAdapterPosition()).getSrc());
                    }

                }else if(mDatas.get(holder.getAdapterPosition()).getTypeView()==OTHER_CHANNEL){
                    //其他频道执行方法
                    if(isEdit){
                        move(holder.getAdapterPosition());
                    }else{
                        ToastUtil.showShort(mContext,mDatas.get(holder.getAdapterPosition()).getSrc());
                    }
                }
            }
        });


        if(mDatas.get(holder.getAdapterPosition()).getTypeView()==MY_CHANNEL){
            //长按事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int childCount = mRecyclerView.getChildCount();
                    childCount=childCount-1-mOtherChannel.size();
                    if(!isEdit){
                        //可看的
                        for (int i = 0; i < childCount; i++) {
                            View view = mRecyclerView.getChildAt(i);
                            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
                            if (imgEdit != null) {
                                imgEdit.setVisibility(View.VISIBLE);
                            }
                        }
                        isEdit=true;

//                        开启拖动
                        if(onStartDragListener!=null){
                            onStartDragListener.startDrag(holder);
                        }

                    }else{
                        for (int i = 0; i < childCount; i++) {
                            View view = mRecyclerView.getChildAt(i);
                            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
                            if (imgEdit != null) {
                                imgEdit.setVisibility(View.GONE);
                            }
                        }
                        isEdit=false;
                    }
                    return true;
                }
            });
            //编辑状态下 排序
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(isEdit){
                        switch (event.getAction()){

                            case MotionEvent.ACTION_MOVE:
                                if(onStartDragListener!=null){
                                    onStartDragListener.startDrag(holder);
                                }
                                break;
                        }
                    }
                    return false;
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mDatas.get(position).getTypeView()==1){
            return CHANNEL_TITLE;
        }else if(mDatas.get(position).getTypeView()==2){
            return MY_CHANNEL;
        }else{
            return OTHER_CHANNEL;
        }
    }

    /**
     * 条目移动
     * @param position
     */
    private void move(int position) {

        if(position>mMyChannel.size()+1){
            int i = position - 2 - mMyChannel.size();
            //其他
            ChannelBean item = mOtherChannel.get(position-2-mMyChannel.size());
            mOtherChannel.remove(item);
            item.setTypeView(2);
            mMyChannel.add(item);
            //这里实现的不择手段了，，之后再改改
            notifyData();
            notifyItemMoved(position, mMyChannel.size());

        }else if(position>0&&position<=mMyChannel.size()){
            //我的
            ChannelBean item = mMyChannel.get(position-1);
            mMyChannel.remove(item);
            item.setTypeView(3);
            mOtherChannel.add(0, item);

            notifyData();
            notifyItemMoved(position, mMyChannel.size() + 2);
        }
    }


    @Override
    public void itemMoved(int oldPosition, int newPosition) {
        ChannelBean channelBean = mMyChannel.get(oldPosition - 1);
        mMyChannel.remove(oldPosition - 1);
        mMyChannel.add(newPosition-1,channelBean);
        notifyData();
        notifyItemMoved(oldPosition, newPosition);
    }

    private void notifyData(){
        //这里实现的不择手段了，，之后再改改
        mDatas.clear();
        mDatas.add(new ChannelBean("我的频道",1));
        mDatas.addAll(mMyChannel);
        mDatas.add(new ChannelBean("其他频道",1));
        mDatas.addAll(mOtherChannel);
    }

    @Override
    public void itemDismiss(int position) {
    }

    private OnStartDragListener onStartDragListener;

    public interface OnStartDragListener{
        void startDrag(RecyclerView.ViewHolder holder);
    }

    public void setOnStartDragListener(OnStartDragListener onStartDragListener){

        this.onStartDragListener = onStartDragListener;
    }
}
