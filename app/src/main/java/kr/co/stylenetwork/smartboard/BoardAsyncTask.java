/*
*   게시판의 모든 요청을 하나의 AsyncTask 로 처리해보자!!
* */
package kr.co.stylenetwork.smartboard;

import android.os.AsyncTask;

public class BoardAsyncTask extends AsyncTask<String, Void, Object>{
    String commandType;

    public BoardAsyncTask(String commandType) {
        this.commandType = commandType;
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
    }
    public void detailView(Object obj){
    }
    public void writeView(Object obj){
    }
    public void editView(Object obj){
    }
    public void deleteView(Object obj){
    }
}









