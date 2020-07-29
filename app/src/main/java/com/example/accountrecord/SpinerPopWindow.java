package com.example.accountrecord;

import java.util.List;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * 自定义PopupWindow  主要用来显示ListView
 *
 * @param <T>
 * @param <T>
 * @author Ansen
 * @create time 2015-11-3
 */
public class SpinerPopWindow<T> extends PopupWindow {
    private static final String TAG = "SpinerPopWindow";
    private LayoutInflater inflater;
    private ListView mListView;
    private List<T> list;
    private MyAdapter mAdapter;
    private Context context;
    private RemoveUserinfoListner removeUserinfoListner;

    public SpinerPopWindow(Context context, List<T> list, OnItemClickListener
            clickListener, RemoveUserinfoListner removeUserinfoListner) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.removeUserinfoListner = removeUserinfoListner;
        init(clickListener);
    }

    private void init(OnItemClickListener clickListener) {
        //View view = inflater.inflate(R.layout.select_phonedialog, null);
        View view = inflater.inflate(ResourceUtil.getLayoutId(context, "select_phonedialog"), null);
        setContentView(view);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        //mListView =  view.findViewById(R.id.select_listview);
        mListView = view.findViewById(ResourceUtil.getId(context, "select_listview"));
        mAdapter = new MyAdapter();
        if (list != null) {
            mListView.setAdapter(mAdapter);
        }
        mListView.setOnItemClickListener(clickListener);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final SelectPhone selectPhone = (SelectPhone) list.get(position);
            ViewHodler hodler = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(ResourceUtil.getLayoutId(context,
                        "selectphone_item"), parent, false);
                hodler = new ViewHodler();
                hodler.phonetextview = convertView.findViewById(ResourceUtil.getId(context, "account_textview"));//account_textview   //account_image
                hodler.imageview = convertView.findViewById(ResourceUtil.getId(context, "account_image"));
                hodler.deleterl = convertView.findViewById(ResourceUtil.getId(context, "delete_account_rl"));
                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler) convertView.getTag();
            }
            hodler.phonetextview.setText(selectPhone.getName());
            //操作删除存在对象里面的数据刷新listview
            hodler.deleterl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // data.remove(position);
                    DeleteaccountDialog deleteaccountDialog = new DeleteaccountDialog(context, selectPhone.getName(), new Deleteaccountlistener() {
                        @Override
                        public void deleteaccountsuccess() {
                            notifyDataSetChanged();
                            removeUserinfoListner.removeuserinfosuccess(position,
                                    (List<SelectPhone>) list);
                        }
                    });
                    deleteaccountDialog.show();

                }
            });
            return convertView;
        }

        public class ViewHodler {
            TextView phonetextview;
            ImageView imageview;
            RelativeLayout deleterl;


        }

    }

}
