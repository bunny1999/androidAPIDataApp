package in.irotech.apihandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button button;
//    TextView textView;
    RequestQueue requestQueue;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
//        requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue=VollySingleton.getInstance(this).getRequestQueue();
        layout=findViewById(R.id.layoutAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestApi();
            }
        });

    }
    public void requestApi(){
        String url="https://api.myjson.com/bins/19lqde";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("students");
                    LayoutInflater inflater=getLayoutInflater();
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject=array.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        String cc=jsonObject.getString("course_count");
                        String email=jsonObject.getString("email");

                        View view=inflater.inflate(R.layout.list_tile,null);
                        TextView na=view.findViewById(R.id.name);
                        TextView em=view.findViewById(R.id.email);
                        TextView c=view.findViewById(R.id.cc);
                        na.setText("Name: "+name);
                        em.setText("Email: "+email);
                        c.setText("Course Count: "+cc);
                        layout.addView(view);

                        View view1=new LinearLayout(MainActivity.this);
                        view1.setMinimumHeight(10);
                        layout.addView(view1);

                        //                        textView.append("Name: "+name+"\nEmail: "+email+"\nCourse Count: "+cc+"\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.create();
                builder.setTitle("ERROR");
                builder.setCancelable(true);
                builder.setMessage("Unable to Fetch API");
                builder.show();
            }
        });
        requestQueue.add(request);
    }
}
