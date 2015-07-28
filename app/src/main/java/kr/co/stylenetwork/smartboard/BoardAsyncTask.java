/*
*   게시판의 모든 요청을 하나의 AsyncTask 로 처리해보자!!
* */
package kr.co.stylenetwork.smartboard;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

public class BoardAsyncTask extends AsyncTask<String, Void, Object>{
    String commandType;
    MainActivity mainActivity;

    public BoardAsyncTask(String commandType, MainActivity mainActivity) {
        this.commandType = commandType;
        this.mainActivity=mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Object doInBackground(String... params) {
        HttpClientManager manager = new HttpClientManager();
        return manager.request(params[0], params[1]);
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(Object obj) {
        switch (commandType){
            case "list":      listView(obj);break;
            case "detail":  detailView(obj);break;
            case "write":   writeView(obj);break;
            case "edit":     editView(obj);break;
            case "delete": deleteView(obj);break;
        }
    }

    public void listView(Object obj){
        /*ListView가 의존하고 있는 MyListAdapter의 ArrayList를
        * 넘겨받은 obj로 변경하고, 리스트 어댑터 갱신!!
        */
        ListFragment listFragment=(ListFragment)mainActivity.adapter.getItem(0);
        listFragment.adapter.list=(ArrayList)obj;

        listFragment.adapter.notifyDataSetChanged();/*갱신*/
    }
    public void detailView(Object obj){
    }
    public void writeView(Object obj){
        int result=(Integer)obj;


        if(result !=0){/*성공한 경우*/
            WriteActivity.writeActivity.finish();

            /*웹서버와의 통신을 통해 새로운 데이터 가져와야 함*/
            BoardAsyncTask task = new BoardAsyncTask("list", mainActivity);
            task.execute("http://192.168.0.142:8080/android/board/list",null);
        }else{/*실패한 경우*/
            Toast.makeText(WriteActivity.writeActivity, "등록실패", Toast.LENGTH_SHORT ).show();
        }
    }
    public void editView(Object obj){
    }
    public void deleteView(Object obj){
    }
}









