package com.example.mirko.tutorial1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mirko on 27/10/15.
 */
public class MyDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "db.prova";

    private static final DBStrategy DB11 = new DBStrategy() {
        @Override
        public void upgrade(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE people (name VARCHAR(32), surname VARCHAR(32), age INT);");
        }

        @Override
        public void downgrade(SQLiteDatabase db) {
            db.execSQL("DROP TABLE people");
        }
    };

    private static final DBStrategy DB12 = new DBStrategy() {
        @Override
        public void upgrade(SQLiteDatabase db) {
            final ContentValues cv = new ContentValues();
            cv.put("name", "Napoleone");
            cv.put("surname", "Bonaparte");
            cv.put("age", 10);
            db.insert("people", "name", cv);
        }

        @Override
        public void downgrade(SQLiteDatabase db) {
            db.execSQL("DELETE FROM people WHERE name='Napoleone' AND surname='Bonaparte'");
        }
    };

    private static final DBStrategy DB2 = new DBStrategy() {
        @Override
        public void upgrade(SQLiteDatabase db) {
            final ContentValues cv = new ContentValues();
            cv.put("name", "Topo");
            cv.put("surname", "Gigio");
            cv.put("age", 3);
            db.insert("people", "name", cv);
        }

        @Override
        public void downgrade(SQLiteDatabase db) {
            db.execSQL("DELETE FROM people WHERE name='Topo' AND surname='Gigio'");
        }
    };

    private static final DBStrategy[] STRATEGIES = {
        new CompositeDBStrategy(DB11, DB12),
            DB2
    };

    private static final int VERSION = STRATEGIES.length;

    public static SQLiteOpenHelper instance(Context context) {
        return new MyDatabase(context.getApplicationContext());
    }

    private MyDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (DBStrategy s : STRATEGIES) {
            s.upgrade(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion + 1; i <= newVersion; i++) {
            STRATEGIES[i - 1].upgrade(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i > newVersion; i--) {
            STRATEGIES[i - 1].downgrade(db);
        }
    }

    private interface DBStrategy {
        void upgrade(SQLiteDatabase db);
        void downgrade(SQLiteDatabase db);
    }

    private static class CompositeDBStrategy implements DBStrategy {
        private final DBStrategy[] strategies;

        public CompositeDBStrategy(DBStrategy ... strategies) {
            this.strategies = strategies;
        }
        public void upgrade(SQLiteDatabase db) {
            for (DBStrategy s : this.strategies) {
                s.upgrade(db);
            }
        }

        public void downgrade(SQLiteDatabase db) {
            for (DBStrategy s : this.strategies) {
                s.downgrade(db);
            }
        }
    }
}
