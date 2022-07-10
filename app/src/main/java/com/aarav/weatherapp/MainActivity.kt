package com.aarav.weatherapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    button_id.setOnClickListener {
        if (city.text.toString().isEmpty()) {
            Toast.makeText(this, "Provide a City/Country name", Toast.LENGTH_SHORT).show()
        }
        else {
            val city_name = city.text
            Toast.makeText(this, "Getting Information", Toast.LENGTH_SHORT).show()
            getinfo(city_name.toString())
        }
    }
    }
    fun getinfo(city: String){
        var q = Volley.newRequestQueue(this)
        var url = "https://wttr.in/$city?format=j1"
        val rq = StringRequest(Request.Method.GET, url, { response ->
            val current = JSONObject(response.toString()).get("current_condition").toString()
            val jobj = JSONObject(JSONArray(current).get(0).toString())
            val feel = jobj.get("FeelsLikeC").toString().plus("°C")
            val humidityz = jobj.get("humidity").toString().plus("%")
            val temp = jobj.get("temp_C").toString().plus("°C")
            val wind = jobj.get("windspeedKmph").toString().plus("Km/Hr")
            val uv = jobj.get("uvIndex").toString()
            val weth = JSONObject(JSONArray(jobj.get("weatherDesc").toString()).get(0).toString()).get("value").toString()
            updated_city.setText(city.toString())
            feelslike.setText(feel)
            humidity.setText(humidityz)
            temperature.setText(temp)
            windspeed.setText(wind)
            description.setText(weth)
            airquality.setText(uv)
        }, { Toast.makeText(this, "Invalid Location, Try Again", Toast.LENGTH_SHORT).show() })
        q.add(rq)
    }
}