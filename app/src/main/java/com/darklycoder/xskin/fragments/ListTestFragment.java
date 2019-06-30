package com.darklycoder.xskin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.darklycoder.xskin.R;
import com.darklycoder.xskin.core.base.SkinFragment;

public class ListTestFragment extends SkinFragment {

    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_test_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = view.findViewById(R.id.list_view);

        mListView.setAdapter(new MyAdapter(getActivity()));
    }

    private class MyAdapter extends BaseAdapter {

        private Context mContext;

        MyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return 200;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (null == convertView) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_test, parent, false);
                vh.mTvTitle = convertView.findViewById(R.id.tv_title);
                convertView.setTag(vh);

            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            vh.mTvTitle.setText("Test" + (position + 1));

            return convertView;
        }

    }

    static class ViewHolder {
        TextView mTvTitle;
    }

}
