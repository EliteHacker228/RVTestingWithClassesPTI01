package com.example.max.recyclerviewtesting;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    SharedPreferences sharedPreferences;

    public Elements content;

    public ArrayList<NewsElement> newsList = new ArrayList<>();
    public ArrayList<NewsElement> savedNewsList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new NewThread().execute();
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyRecyclerViewAdapter(this, newsList);
        recyclerView.setAdapter(adapter);
    }


    public class NewThread extends AsyncTask<String, Void, String> {

        String cleaner(String a){
            String source = a;
            String result = "";
            ArrayList<String> arr = new ArrayList<>();
            arr.add(source);

            for(String retrival: source.split(" ")){
                arr.add(retrival);

            }
            arr.remove(0);
            Iterator<String> iterator = arr.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                if (string.equals("Россия") || string.equals("Екатеринбург")) {
                    iterator.remove();
                }else{
                    result += string+" ";
                }
            }
            return result;
        }

        String url_interpretator(String src){
            String result="";
            String interval = "";

            char[] morph = src.toCharArray();
            morph[0]=' ';
            morph[1]=' ';

            for (char a : morph){
                if(a==' '){
                    continue;
                }else{
                    interval=interval+a;
                }
            }

            result="https://"+interval;

            return result;
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected  String doInBackground(String... arg) {
            Document doc;
            String  newsText,  newsDate,  newsTime,  newsPicURL,  newsLink;

            try{
                doc= Jsoup.connect("https://www.znak.com/?&%D0%B5%D0%BA%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%BD%D0%B1%D1%83%D1%80%D0%B3%20%D0%BC%D1%83%D0%B7%D0%B5").get();
                content = doc.select(".pub");

                int link_counter = 0;

                newsList.clear();
                for(Element contents: content){


                    String linkID = doc.getElementsByClass("pub").get(link_counter).attr("href");

                    String region = doc.getElementsByClass("region").get(link_counter).text();

                    String time = doc.getElementsByTag("time").get(link_counter).attr("datetime");

                    String sourceYear;



                    DateAndTime dat = new DateAndTime(time);
                    newsText=cleaner(contents.text());
                    newsDate=" "+dat.getYear()+" ";
                    newsTime=dat.getTime();


                     Document doc2 = null;
                    try {
                        doc2 = Jsoup.connect("https://www.znak.com" + linkID).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String pic_url = url_interpretator(doc2.getElementsByTag("img").get(1).attr("src"));
                    String readyURL = pic_url;
                    Log.d("picture", contents.text() + " " + pic_url);
                    newsPicURL=readyURL;

                    sourceYear=dat.getSourceYear();
                    newsLink="https://znak.com"+linkID;
                    NewsElement newsElement = new NewsElement( newsText,  newsDate,  newsTime,  newsPicURL,  newsLink, sourceYear);
                    newsList.add(newsElement);

                    Comparator<NewsElement> comparator = new Comparator<NewsElement>() {
                        @Override
                        public int compare(NewsElement o1, NewsElement o2) {
                            Log.d("Comparator", o1.getNewsDate());
                            return -(o1.getSourceYear().compareTo(o2.getSourceYear()));
                        }
                    };

                    newsList.sort(comparator);


                    publishProgress();
                    link_counter++;
                }

            }catch (IOException e){

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            adapter.notifyDataSetChanged();
        }
    }

    private class DateAndTime{


        private String date;
        private String sourceYear;
        private String year;
        private String time="";
        private char[] timeArr;

        DateAndTime(String date){
            this.date=date;
        }

        public String getSourceYear() {
            return sourceYear;
        }

        String getYear(){


            String[] dateAndTime = date.split("T");
            String[] dateArr = dateAndTime[0].split("-");

            timeArr = dateAndTime[1].toCharArray();
            year = dateArr[2]+"."+dateArr[1]+"."+dateArr[0];
            sourceYear = dateArr[0]+"."+dateArr[1]+"."+dateArr[2];

            return year;

        }


        String getTime(){

            for(int i = 0; i<timeArr.length-4; i++){
                time=time+timeArr[i];
            }

            String intTime = String.valueOf(timeArr[0])+String.valueOf(timeArr[1]);
            int timeValue = Integer.valueOf(intTime)+5;

            if(timeValue>23){
                timeValue=timeValue-24;
                String time = "0"+String.valueOf(timeValue)+timeArr[2]+timeArr[3]+timeArr[4];
                return time;
            }else{
                String time = String.valueOf(timeValue)+timeArr[2]+timeArr[3]+timeArr[4];
                return time;
            }

        }
    }

    void saveData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.commit();

    }

    void loadData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);

    }

}