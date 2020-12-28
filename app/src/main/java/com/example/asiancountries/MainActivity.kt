package com.example.asiancountries

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import coil.util.CoilUtils.clear
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.asiancountries.Adapter.DashboardRecyclerAdapter
import com.example.asiancountries.database.CountryDatabase
import com.example.asiancountries.database.UserViewModel
import com.example.asiancountries.model.Countries
import com.example.asiancountries.model.CountryEntity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.util.HashSet

class MainActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: UserViewModel
    lateinit var recyclerDashboard: RecyclerView
    private lateinit var dashboardRecyclerAdapter: DashboardRecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    /*lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar*/


    var sharedPreferences: SharedPreferences? = null
    var countries = arrayListOf<CountryEntity>()
    var arrayAdapter: ArrayAdapter<*>? = null

    companion object {
        @SuppressLint("StaticFieldLeak")



        var userFirst: String? = ""
        var userLast: String? = ""
        var userEmail: String? = ""
        var userImage: String? = ""
    }

    lateinit var country:CountryEntity
    lateinit var recordUser: CountryEntity

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    val bookInfoList = arrayListOf<CountryEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerDashboard=findViewById(R.id.recyclerDashboard)
        // val relativeDashboard = view?.findViewById(R.id.relativeDashboard) as RelativeLayout
       mUserViewModel= ViewModelProvider(this).get(UserViewModel::class.java)
       // mUserViewModel= ViewModelProviders.of(this).get(UserViewModel::class.java)
       /*mUserViewModel.getAllCountries.observe( lifecycle, Observer { record->
            recyclerAdapter.setData(country as ArrayList<CountryEntity>)
        })*/
        mUserViewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel::class.java)
        //for an activity, it is automatically created by onCreateOptionsMenu

        layoutManager=
            LinearLayoutManager(this)//since we're working inside the fragment, we need to use activity instead of this.

        //progressLayout=findViewById(R.id.progressLayout)
        //progressBar=findViewById(R.id.progressBar)


        //progressLayout.visibility= View.VISIBLE//makes the layout visible when the fragment is being loaded

        sharedPreferences =
            this.getSharedPreferences(
                "com.example.payo.activity.fragment",
                Context.MODE_PRIVATE
            )
        // cardRecords=view.findViewById(R.id.cardRecords)
        val recyclerView: RecyclerView? = findViewById<RecyclerView>(R.id.recyclerDashboard)
        val set = sharedPreferences!!.getStringSet("records", null) as HashSet<String>?

        arrayAdapter=
            ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)

      delete.setOnClickListener {


          val builder = AlertDialog.Builder(this)
          builder.setPositiveButton("Yes") { _, _ ->
              mUserViewModel.deleteAllUsers()
              Toast.makeText(this,
                  "Successfully removed everything",
                  Toast.LENGTH_SHORT
              ).show()
          }
          builder.setNegativeButton("No") { _, _ -> }
          builder.setTitle("Delete everything?")
          builder.setMessage("Are you sure you want to delete everything?")
          builder.create().show()
      }

        recyclerAdapter= DashboardRecyclerAdapter(this, countries)//typecasting application context as context will always succeed

        //now, we need to set them up with the RecyclerView
        val url="https://restcountries.eu/rest/v2/region/asia"
        recyclerDashboard.adapter=recyclerAdapter
        recyclerDashboard.layoutManager=layoutManager

        val queue= Volley.newRequestQueue(this)
        val jsonArrayObject=object:
            JsonArrayRequest(Method.GET, url, null, Response.Listener<JSONArray> {
                //rlLoading.visibility = View.GONE
                try {


                    for(i in 0 until 50)
                    {
                        val ctr=it.getJSONObject(i)
                       val data=ctr.getJSONArray("borders")
                        var bordersList:String=""

                        for(j in 0 until data.length())
                       {
                             val bordersObj=data.getString(j)
                        /*   val borderctr= "https://restcountries.eu/rest/v2/alpha/${bordersObj}"
                           val jsonObject=object:JsonObjectRequest(Method.GET,borderctr,null,Response.Listener {r->
                               val country=r.getString("name")
                               bordersList+="${country},"
                           },Response.ErrorListener {

                           }){
                               override fun getHeaders(): MutableMap<String, String> {
                                   val headers1 = HashMap<String, String>()
                                   headers["Content-type"] = "application/json"
                                   return headers1
                               }
                           }
                           queue.add(jsonObject)*/


                        bordersList+="${bordersObj},"

                        }
                        var border="Borders:"
                        if(bordersList!=null)
                        border+=bordersList
                        else
                        border+="Null"
                        val flags=ctr.getString("flag")
                        val language=ctr.getJSONArray("languages")
                        var langs=""
                        for(k in 0 until language.length())
                        {
                            val languageArray=language.getJSONObject(k)
                            val lang=languageArray.getString("name")
                            langs+="${lang},"
                        }
                        val ctrobject=CountryEntity(
                            ctr.getString("name").toString(),
                            ctr.getString("capital"),
                            ctr.getString("region"),
                            ctr.getString("subregion"),
                            ctr.getString("population"),
                           border,
                            langs,
                            flags


                        )
                        countries.add(ctrobject)

                            dashboardRecyclerAdapter= DashboardRecyclerAdapter(this,countries)
                            val mLayoutManager = LinearLayoutManager(this)
                            recyclerDashboard.layoutManager = mLayoutManager
                            recyclerDashboard.itemAnimator = DefaultItemAnimator()
                            recyclerDashboard.adapter = dashboardRecyclerAdapter
                            recyclerDashboard.setHasFixedSize(true)

                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }
            },Response.ErrorListener {
                Toast.makeText(this, "No response", Toast.LENGTH_SHORT).show()
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                return headers
            }
        }
        queue.add(jsonArrayObject)

    }


}