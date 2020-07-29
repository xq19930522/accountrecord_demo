package com.example.accountrecord;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText account_ed ,password_ed;
    private String  account, password;
    private  Boolean flag=false;
    private Button  loginbtn;
    List<SelectPhone> getdata=null;
    private Context mContext=LoginActivity.this;
    private TextView  textView;
    private RelativeLayout select_loginaccount_rl;
    private String  cacheaccount ,cachepsw;
    private SpinerPopWindow<SelectPhone> mSpinerPopWindow;
    List<SelectPhone> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();

    }

    private void initview() {
        account_ed=findViewById(R.id.login_account_ed);
        password_ed=findViewById(R.id.account_login_psw);
        loginbtn=findViewById(R.id.promptly_login);
        loginbtn.setOnClickListener(this);
        textView=findViewById(R.id.login_account_textview);
        select_loginaccount_rl=findViewById(R.id.select_loginaccount_rl);
        select_loginaccount_rl.setOnClickListener(this);
        getdata = SharedPreferencesUtils.getSelectBean(mContext, "selectphone");
        if (getdata != null && getdata.size() > 0) {
            SelectPhone phone = getdata.get(0);
            cacheaccount = phone.getName();
            cachepsw=phone.getPassword();

            if (!TextUtils.isEmpty(cacheaccount)) {
                account_ed.setText(cacheaccount);
                password_ed.setText(cachepsw);
            } else {
                account_ed.setText(null);
                password_ed.setText(null);
            }
        }

        mSpinerPopWindow = new SpinerPopWindow<SelectPhone>(LoginActivity.this,
                getdata, itemClickListener, removeUserinfoListner);
        mSpinerPopWindow.setOnDismissListener(dismissListener);



    }

    //删除用户缓存信息
    private RemoveUserinfoListner removeUserinfoListner = new RemoveUserinfoListner() {
        @Override
        public void removeuserinfosuccess(int position, List<SelectPhone> data) {
            if (data != null && data.size() > 0) {
                data.remove(position);
                SharedPreferencesUtils.putSelectBean(mContext, data, "selectphone");
                flag = false;
                List<SelectPhone> getdata = SharedPreferencesUtils.
                        getSelectBean(LoginActivity.this, "selectphone");
                if (getdata != null && getdata.size() > 0) {
                    SelectPhone selectPhone = getdata.get(0);
                    account_ed.setText(selectPhone.getName());
                    password_ed.setText(selectPhone.getPassword());
                } else {
                    account_ed.setText(null);
                    password_ed.setText(null);

                }
            } else {
                Toast.makeText(mContext,"缓存数据为空",Toast.LENGTH_LONG).show();
            }
        }

    };

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            flag = false;

        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            flag = false;
            SelectPhone selectPhone = getdata.get(position);
            String getusername = selectPhone.getName();
            String psw = selectPhone.getPassword();
            account_ed.setText(getusername);
            password_ed.setText(psw);

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.promptly_login:
                login();
                break;
            case R.id.select_loginaccount_rl:
                if (flag) {
                    flag = false;
                    if (mSpinerPopWindow != null) {
                        mSpinerPopWindow.dismiss();
                    } else {

                    }
                } else {
                    flag = true;
                    if (mSpinerPopWindow != null) {
                        mSpinerPopWindow.showAsDropDown(textView);
                    } else {
                    }

                }
                break;
          default:
              break;
        }
    }

    private  void  login(){
        account=account_ed.getText().toString();
        password=password_ed.getText().toString();
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            Toast.makeText(mContext,"账号或者密码输入不能为空",Toast.LENGTH_LONG).show();
        }
        SelectPhone selectPhone=new SelectPhone();
        selectPhone.setName(account);
        selectPhone.setPassword(password);
        List<SelectPhone> getdata = SharedPreferencesUtils.getSelectBean(LoginActivity.this, "selectphone");
        if (getdata != null && getdata.size() > 0) {
            getdata.add(0,selectPhone);
            SharedPreferencesUtils.putSelectBean(LoginActivity.this, getdata, "selectphone");
        } else {
            data.add(selectPhone);
            SharedPreferencesUtils.putSelectBean(LoginActivity.this, data, "selectphone");
        }
        Toast.makeText(mContext,"登录成功数据缓存成功",Toast.LENGTH_LONG).show();
        finish();


    }
}
