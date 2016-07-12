package com.whale.nangua.timerecoder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whale.nangua.timerecoder.bean.BookInfo;

import java.util.ArrayList;

/**
 * Created by nangua on 2016/5/19.
 */
public class DBUtils {
    //数据库版本号
    final static int VERSION = 1;
    //声明数据库
    private SQLiteDatabase db;

    private static DBUtils DBinstance;

    private DBUtils(Context context) {
        DbHelper helper = new DbHelper(context, "Book", null, 1);
        if (helper!=null) {
            //获得数据库
            db = helper.getWritableDatabase();
        }

    }

    public static DBUtils getInstance(Context context) {
        if (DBinstance == null) {
            DBinstance = new DBUtils(context);
        }
        return DBinstance;
    }

    /**
     * private String title; //标题
     * private String author; //作者
     * private String image; //图片url
     * private String max;//最大页数
     * private String catalog; //章节
     *Books
     * @return
     */
    public boolean insertBooks(String id,String title,String summary,String author,String image,String max,String catalog,String price,String alt) {
        ContentValues values = new ContentValues();
        values.put("bookid",id);
        values.put("title", title);
        values.put("summary",summary);
        values.put("author", author);
        values.put("image", image);
        values.put("max", max);
        values.put("catalog", catalog);
        values.put("price",price);
        values.put("alt",alt);
        long result = db.insert("Books", null, values);
        return true ? false : result != -1;
    }



    public ArrayList<BookInfo> queryBooks() {
        ArrayList<BookInfo> bookinfos = new ArrayList<>();
        //查询Device表中所有的数据
        Cursor cursor = db.query("Books", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                bookInfo.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                bookInfo.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                bookInfo.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bookInfo.setMax(cursor.getString(cursor.getColumnIndex("max")));
                bookInfo.setCatalog(cursor.getString(cursor.getColumnIndex("catalog")));
                bookInfo.setAlt(cursor.getString(cursor.getColumnIndex("alt")));
                bookInfo.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                bookInfo.setId(cursor.getString(cursor.getColumnIndex("bookid")));
                bookinfos.add( bookInfo);
            } while (cursor.moveToNext());
        }
        return bookinfos;
    }

    public void clearBooks() {
          db.execSQL("DELETE FROM BOOKS");
    }

    public String queryPages(String title) {
        Cursor cursor = db.query("Books", null, "title" + "=?", new String[]{String.valueOf(title)}, null, null, null);
        String nowpage = "";
        if(cursor != null && cursor.moveToFirst()){
            nowpage = cursor.getString(cursor.getColumnIndex("nowpage"));
        }
        return nowpage;
    }

    /**
     * 插入页码到数据库
     * @param id
     * @param page
     * @return
     * UPDATE table_name
    SET column1 = value1, column2 = value2...., columnN = valueN
    WHERE [condition];
     */
    public void updatePage(String bookid, String page) {
        Log.d("xiaojingyu", "updatePage：传入的page：" + page);
        ContentValues values = new ContentValues();
        values.put("nowpage", page );//key为字段名，value为值
        db.update("Books", values, "bookid=?", new String[]{bookid});
    }

}
