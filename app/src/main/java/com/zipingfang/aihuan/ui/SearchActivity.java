package com.zipingfang.aihuan.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.ui.index.SearchResultActivity;
import com.zipingfang.aihuan.utils.RecordSQLiteOpenHelper;
import com.zipingfang.aihuan.view.CustomListView;

/**
 * Created by zipingfang on 16/10/22.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_search;
    private TextView tv_cancel;
    private CustomListView lv_container;
    private Button btn_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private TextView tv_tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_search);
        initView();
    }

    private void initView() {
        tv_tip=(TextView)findViewById(R.id.tv_tip);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        lv_container = (CustomListView) findViewById(R.id.lv_container);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    String res = et_search.getText().toString().trim();
                    boolean hasData = hasData(res);
                    if (!hasData) {
                        insertData(res);
                        queryData("");
                    }
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("search",res);
                    startActivity(intent);
                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }
                String tempName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        lv_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.tv_item_search);
                String res = textView.getText().toString();
                et_search.setText(res);
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("search",res);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.btn_clear:
                deleteData();
                queryData("");
                break;
        }
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, R.layout.zpf_item_search, cursor, new String[] { "name" },
                new int[] { R.id.tv_item_search }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        lv_container.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }
}
