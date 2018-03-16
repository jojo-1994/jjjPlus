package com.sz.jjj.xml;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sz.jjj.R;
import com.sz.jjj.xml.model.Books;
import com.sz.jjj.xml.utils.DomBookParser;
import com.sz.jjj.xml.utils.SaxBookParser;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2017/8/23.
 *
 * @description:
 */

public class XmlParseActivity extends AppCompatActivity {
    private static final String TAG = "XML";
    @BindView(R.id.saxBtn)
    Button saxBtn;
    @BindView(R.id.domBtn)
    Button domBtn;
    @BindView(R.id.pullBtn)
    Button pullBtn;
    @BindView(R.id.writeBtn)
    Button writeBtn;

    private BookParser parser;
    private List<Books> books;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parse_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.saxBtn, R.id.domBtn, R.id.pullBtn, R.id.writeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.saxBtn:
               parsexml(1);
                break;
            case R.id.domBtn:
                parsexml(2);
                break;
            case R.id.pullBtn:
                parsexml(3);
                break;
            case R.id.writeBtn:
                try {
                    String xml = parser.serialize(books);  //序列化
                    FileOutputStream fos = openFileOutput("books.xml", Context.MODE_PRIVATE);
                    fos.write(xml.getBytes("UTF-8"));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                break;
        }
    }

    private void parsexml(int i) {
        try {
            InputStream is = getAssets().open("books.xml");
            switch (i){
                case 1:
                    parser = new SaxBookParser();
                    break;
                case 2:
                    parser = new SaxBookParser();
                    break;
                case 3:
                    parser = new DomBookParser();
                    break;
            }
            books = parser.parse(is);
            for (Books book : books) {
                Log.i(TAG, book.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

}
