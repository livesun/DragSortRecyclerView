package drag_sort.dragsortrecyclerview.channel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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

public class ChannelActivity extends AppCompatActivity implements ChannelAdapter.OnStartDragListener {

    private RecyclerView channelRecyler;
    private ChannelAdapter mAdapter;
    private ItemTouchHelper mTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_activity);
        channelRecyler = (RecyclerView) findViewById(R.id.channl_recyler);
        mAdapter = new ChannelAdapter(this,getDatas(),channelRecyler);
        GridLayoutManager manager=new GridLayoutManager(this,4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                if(itemViewType==mAdapter.CHANNEL_TITLE){
                    return 4;
                }
                return 1;
            }
        });
        channelRecyler.setLayoutManager(manager);
        channelRecyler.setAdapter(mAdapter);
        //创建ItemTouchHelper
        mTouchHelper = new ItemTouchHelper(new ToucheCallBcak(mAdapter));
        //attach到RecyclerView中
        mTouchHelper.attachToRecyclerView(channelRecyler);
        mAdapter.setOnStartDragListener(this);
    }

    private List<ChannelBean> getDatas(){
        List<ChannelBean> beanList=new ArrayList<>();
        beanList.add(new ChannelBean("我的频道",1));

        for(int i=0;i<10;i++){
            beanList.add(new ChannelBean("频道"+i,2));
        }

        beanList.add(new ChannelBean("其他频道",1));

        for(int i=0;i<10;i++){
            beanList.add(new ChannelBean("其他"+i,3));
        }

        return beanList;

    }

    @Override
    public void startDrag(RecyclerView.ViewHolder holder) {
        mTouchHelper.startDrag(holder);
    }
}
