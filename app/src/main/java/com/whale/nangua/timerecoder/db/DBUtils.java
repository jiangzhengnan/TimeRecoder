package com.whale.nangua.timerecoder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public boolean insertBooks(String title,String summary,String author,String image,String max,String catalog) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("summary",summary);
        values.put("author", author);
        values.put("image", image);
        values.put("max", max);
        values.put("catalog", catalog);
        long result = db.insert("Books", null, values);
        return true ? false : result != -1;
    }

    ;

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
                bookinfos.add( bookInfo);
            } while (cursor.moveToNext());
        }
        return bookinfos;
    }

    public void clearBooks() {
          db.execSQL("DELETE FROM BOOKS");
    }
}
