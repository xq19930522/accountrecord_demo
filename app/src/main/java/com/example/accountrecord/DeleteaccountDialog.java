package com.example.accountrecord;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 类说明：删除账号是否确认弹窗
 * 创建人：xuqing
 * 创建时间：2019-04-29
 */
public class DeleteaccountDialog extends Dialog {
    private Context context;
    private RelativeLayout cancel, confirm;
    private TextView accounttext;
    private Deleteaccountlistener deleteaccountlistener;
    private String account;


    public DeleteaccountDialog(Context context, String account, Deleteaccountlistener
            deleteaccountlistener) {
        super(context);
        this.context = context;
        this.account = account;
        this.deleteaccountlistener = deleteaccountlistener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(ResourceUtil.getLayoutId(context,
                "deleteaccount_dialog"), null);
        setContentView(layout);
        initview();
    }

    private void initview() {
        cancel = findViewById(ResourceUtil.getId(context, "deleteaccount_cancel"));
        confirm = findViewById(ResourceUtil.getId(context, "deleteaccount_confirm"));
        accounttext = findViewById(ResourceUtil.getId(context, "deleteaccount_text"));
        accounttext.setText("确定删除账号[" + account + "]");
        //删除账号取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteaccountDialog.this.dismiss();
            }
        });
        //确定删除账号
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteaccountDialog.this.dismiss();
                deleteaccountlistener.deleteaccountsuccess();
            }
        });
    }


}
