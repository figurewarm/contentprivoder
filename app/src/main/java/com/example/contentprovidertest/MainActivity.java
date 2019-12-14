package com.example.contentprovidertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String newId;
    private String ENG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addWord = (Button) findViewById(R.id.addWord);
        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    EditText eng = (EditText) findViewById(R.id.eng);
                    EditText chn = (EditText) findViewById(R.id.chn);
                    //String ENG = eng.getText().toString();
                    ENG = eng.getText().toString();
                    String CHN = chn.getText().toString();
                    Uri uri = Uri.parse("content://com.example.vocabularybook.provider/word");
                    ContentValues values = new ContentValues();
                    values.put("ENG", ENG);
                    values.put("CHN", CHN);
                    Uri newUri = getContentResolver().insert(uri, values);
                    newId = newUri.getPathSegments().get(1);
                    Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button queryWord = (Button) findViewById(R.id.queryWord);
        queryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View view1 = layoutInflater.inflate(R.layout.word, null);
                    builder.setView(view1);
                    builder.setTitle("添加的单词");
                    TextView ch = view1.findViewById(R.id.chn);
                    TextView en = view1.findViewById(R.id.eng);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    Uri uri = Uri.parse("content://com.example.vocabularybook.provider/word/" + ENG);
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    String ENG = cursor.getString(cursor.getColumnIndex("ENG"));
                    String CHN = cursor.getString(cursor.getColumnIndex("CHN"));
                    ch.setText("explain:    " + CHN);
                    en.setText("word:   " + ENG);
//                    Toast.makeText(MainActivity.this,ENG+"  "+CHN,Toast.LENGTH_SHORT).show();
                    cursor.close();
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button updateWord = (Button) findViewById(R.id.updateWord);
        updateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //EditText eng = (EditText) findViewById(R.id.eng);
                    EditText chn = findViewById(R.id.chn);
                    //String ENG = eng.getText().toString();
                    String CHN = chn.getText().toString();
                    Uri uri = Uri.parse("content://com.example.vocabularybook.provider/word/" + ENG);
                    ContentValues values = new ContentValues();
                    values.put("ENG", ENG);
                    values.put("CHN", CHN);
                    getContentResolver().update(uri, values, null, null);
                    Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "ERROR" + newId, Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button deleteWord = (Button) findViewById(R.id.deleteWord);
        deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View view1 = layoutInflater.inflate(R.layout.word, null);
                    builder.setView(view1);
                    builder.setTitle("确定删除？");
                    TextView ch = view1.findViewById(R.id.chn);
                    TextView en = view1.findViewById(R.id.eng);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Uri uri = Uri.parse("content://com.example.vocabularybook.provider/word/" + ENG);
                            getContentResolver().delete(uri, null, null);
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    Uri uri = Uri.parse("content://com.example.vocabularybook.provider/word/" + ENG);
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    String ENG = cursor.getString(cursor.getColumnIndex("ENG"));
                    String CHN = cursor.getString(cursor.getColumnIndex("CHN"));
                    ch.setText("explain:    " + CHN);
                    en.setText("word:   " + ENG);
                    cursor.close();
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
