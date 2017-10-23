package com.mayinews.g.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.g.R;
import com.mayinews.g.home.bean.SearchDataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 搜索结果的listVIew适配器
 */

public class ListSearchAdapter extends BaseAdapter {
    private Context context;
    private List<SearchDataBean> data;
    private LayoutInflater inflater;


    public ListSearchAdapter(Context context, List<SearchDataBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SearchDataBean bean = data.get(position);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_search_item, parent, false);
            holder = new ViewHolder(convertView);
           convertView.setTag(holder);
        }else{

             holder= (ViewHolder) convertView.getTag();

        }

        //进行数据绑定
        if(bean.getAvatar()==null){
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+bean.getCover())).into(holder.image);
        }else{
            Glide.with(context).load(buildGlideUrl("http://static.mayinews.com"+bean.getAvatar())).into(holder.image);
        }

        holder.tvNickname.setText(bean.getNickname());
        if(bean.getType().equals("actor")){

        }else{
            holder.tvType.setText("专辑");

        }



        return convertView;
    }




    class ViewHolder {
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_type)
        TextView tvType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://m.mayinews.com").build());
        }
    }


}
