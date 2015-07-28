/*
* 글작성용 액티비티
*
* 웹서버에 데이터를 요청하기 위해서는 HttpClient, HttpURLConnection
* 두가지 객체가 지원되는데, HttpClient 객체는  Apache 에서 지원하는 객체이고
* HttpURLConnection 은 표준자바에서 지원하는 객체이다.
* 앞으로는 구글에서 HttpURLConnection 을 사용하라고 했다..
* */
package kr.co.stylenetwork.smartboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WriteActivity extends Activity {
    static WriteActivity writeActivity;
    String TAG;
    EditText writer,title,content;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        TAG=this.getClass().getName();
        writeActivity=this;

        writer=(EditText)findViewById(R.id.writer);
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
    }

    public void regist(){
        String p1=writer.getText().toString();
        String p2=title.getText().toString();
        String p3=content.getText().toString();

        BoardAsyncTask boardAsyncTask = new BoardAsyncTask("write", MainActivity.mainActivity );
        boardAsyncTask.execute("http://192.168.0.142:8080/android/board/write"
                , "writer="+p1+"&title="+p2+"&content="+p3);
    }

    public void close(){
        this.finish();/*현재 액티비티 종료*/
    }
    public void btnClick(View view){
        switch(view.getId()){
            case R.id.bt_regist:regist();break;
            case R.id.bt_close:close();break;
        }

    }
}








