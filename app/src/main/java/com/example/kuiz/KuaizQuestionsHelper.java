package com.example.kuiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class KuaizQuestionsHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "Kuizp.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_version = 3;
    //Table name
    private static final String TABLE_NAME = "Qstions";
    //Table Fields
    //Id of question
    private static final String QID = "ID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTIONA = "OPTIONA";
    //Option B
    private static final String OPTIONB = "OPTIONB";
    //Option C
    private static final String OPTIONC = "OPTIONC";
    //Option D
    private static final String OPTIOND = "OPTIOND";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + QID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(500), " + OPTIONA + " VARCHAR(255), " + OPTIONB + " VARCHAR(255), " + OPTIONC + " VARCHAR(255), " + OPTIOND + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    KuaizQuestionsHelper(Context context){
        super(context,DB_NAME,null,DB_version);
        this.context=context;
    }
    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


    void allQuestion() {
        ArrayList<KuaizQuestions> arraylist = new ArrayList<>();

        arraylist.add(new KuaizQuestions("Which of the following is called address operator?", "*", "&", "_", "%", "&"));


        arraylist.add(new KuaizQuestions("From which function the execution of a C++ program starts?", "start() function", "main() function", "new() function", "end() function", "main() function"));

        arraylist.add(new KuaizQuestions(" What is the index number of the last element of an array with 9 elements?", "9", "Programmer-defined", "8", "0", "8"));

        arraylist.add(new KuaizQuestions("Which of the following accesses the seventh element stored in array?", "array[6];", "array[7];", "array(7);", "array;", "array[6];"));

        arraylist.add(new KuaizQuestions("What is the size of a boolean variable in C++?", "1 bit", "1 byte", "4 bytes", "2 bytes", "1 bit"));

        arraylist.add(new KuaizQuestions("Which keyword is used to represent a friend function?", "friend", " Friend", " friend_func", "Friend_func", "friend"));

        arraylist.add(new KuaizQuestions("How many specifiers are present in access specifiers in class?", "1", "2", "3", "4", "3"));

        arraylist.add(new KuaizQuestions(" Which of the following is a valid class declaration?", "class A { int x; };", "class B { }", "public class A { }", "object A { int x; };", "class A { int x; };"));

        arraylist.add(new KuaizQuestions("Which specifier makes all the data members and functions of base class inaccessible by the derived class?", "private", "protected", "public", "both private and protected", "private"));

        arraylist.add(new KuaizQuestions("What can be passed by non-type template parameters during compile time?", "int", "float", "constant expression", "string", "constant expression"));

        arraylist.add(new KuaizQuestions("Where is the derived class is derived from?", "derived", "base", "both derived & base", "class", "base"));

        arraylist.add(new KuaizQuestions("Which of the following can derived class inherit?", "members", "functions", "both members & functions", "classes", "both members & functions"));

        arraylist.add(new KuaizQuestions("Which operator is used to declare the destructor?", "#", "~", "@", "$", "~"));


        arraylist.add(new KuaizQuestions("How many ways of passing a parameter are there in c++?", "1", "2", "3", "4", "3"));

        arraylist.add(new KuaizQuestions("By default how the value are passed in c++?", "call by value", "call by reference", "call by pointer", "call by object", "call by value"));

        this.addAllQuestions(arraylist);

    }

    private void addAllQuestions(ArrayList<KuaizQuestions> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (KuaizQuestions question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTIONA, question.getOptionA());
                values.put(OPTIONB, question.getOptionB());
                values.put(OPTIONC, question.getOptionC());
                values.put(OPTIOND, question.getOptionD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    List<KuaizQuestions> getAllOfTheQuestions() {

        List<KuaizQuestions> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {QID, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            KuaizQuestions question = new KuaizQuestions();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptionA(cursor.getString(2));
            question.setOptionB(cursor.getString(3));
            question.setOptionC(cursor.getString(4));
            question.setOptionD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }



}
