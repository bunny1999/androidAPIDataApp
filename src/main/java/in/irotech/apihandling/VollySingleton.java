package in.irotech.apihandling;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
//This is for incrsing the perfomance
//Like if there are other therads which are calling Volly.requestQueue which is prebuild then it will difficult here, so for that kind of stuff we are using Singleton(NOT CLEAR)
public class VollySingleton {
    public static VollySingleton mVollySingleton;
    public RequestQueue mRequestQueue;

    public VollySingleton(Context context){
        mRequestQueue= Volley.newRequestQueue(context.getApplicationContext());

    }

    public static synchronized VollySingleton getInstance(Context context){
        if(mVollySingleton==null){
            mVollySingleton=new VollySingleton(context);
        }
        return mVollySingleton;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}