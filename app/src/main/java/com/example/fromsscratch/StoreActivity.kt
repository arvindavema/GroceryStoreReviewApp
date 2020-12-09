package com.example.fromsscratch

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import org.json.JSONObject

class StoreActivity : AppCompatActivity() {

    val restaurantList = ArrayList<Store>()
    val resList:String="      {\n" +
            "                \"debug_log\" : {\n" +
            "                \"line\" : []\n" +
            "                },\n" +
            "                \"html_attributions\" : [],\n" +
            "                \"logging_info\" : {\n" +
            "                \"experiment_id\" : [],\n" +
            "                \"query_geographic_location\" : \"AU\"\n" +
            "                },\n" +
            "                \"results\" : [\n" +
            "                {\n" +
            "                    \"geometry\" : {\n" +
            "                        \"location\" : {\n" +
            "                            \"lat\" : -33.86879,\n" +
            "                            \"lng\" : 151.194217\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "                    \"id\" : \"21a0b251c9b8392186142c798263e289fe45b4aa\",\n" +
            "                    \"name\" : \"Rhythmboat Cruises\",\n" +
            "                    \"opening_hours\" : {\n" +
            "                        \"open_now\" : false,\n" +
            "                        \"weekday_text\" : []\n" +
            "                    },\n" +
            "                    \"photos\" : [\n" +
            "                        {\n" +
            "                            \"height\" : 426,\n" +
            "                            \"html_attributions\" : [\n" +
            "                            \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/104066891898402903288\\\"\\u003eRhythmboat Cruises\\u003c/a\\u003e\"\n" +
            "                            ],\n" +
            "                            \"photo_reference\" : \"CmRdAAAA-YL_I_Gk02blOX6S0nKHry8PFu9pDyp3Y9AnqISsa3Eq8mkbdD5mXuu1Fax60s0nSy3iiX-h5j-ztyLHcc1-782MsLQsgLLa4t3ZgDmCMll-a8ABapZGnZwDKByk67LFEhBgedv_u_eYFsEo9ay8jxJjGhTUHKPJ4G82vBJqSNliuv7UlAtclw\",\n" +
            "                            \"width\" : 640\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"place_id\" : \"ChIJyWEHuEmuEmsRm9hTkapTCrk\",\n" +
            "                    \"reference\" : \"CnRmAAAAvQlMKw-XtxEY4vWFCvudF7CEMQGI5ycNbfVgGl9rAF75fdiPOiLJw1k9NL2v8ZIJsOJuRS3Lm9Dw1vga4ajycAs7PlxN1MVnnYT9la0pBvEvSQNlyvszKANS1R4P7Mvk_jhqswMggqCUtwJ13LN2hRIQOiAkLTWUi3DOjVVOw7J5IRoUb_cJyJaJNqKdmkDM2f0OjQjh9F0\",\n" +
            "                    \"scope\" : \"GOOGLE\",\n" +
            "                    \"types\" : [ \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "                    \"vicinity\" : \"Pyrmont Bay Wharf (Near Australia Maritime Museum), Pyrmont, NSW 2009\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"geometry\" : {\n" +
            "                        \"location\" : {\n" +
            "                            \"lat\" : -33.867591,\n" +
            "                            \"lng\" : 151.201196\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "                    \"id\" : \"a97f9fb468bcd26b68a23072a55af82d4b325e0d\",\n" +
            "                    \"name\" : \"Australian Cruise Group\",\n" +
            "                    \"opening_hours\" : {\n" +
            "                        \"open_now\" : false,\n" +
            "                        \"weekday_text\" : []\n" +
            "                    },\n" +
            "                    \"photos\" : [\n" +
            "                        {\n" +
            "                            \"height\" : 1331,\n" +
            "                            \"html_attributions\" : [\n" +
            "                            \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/110751364053842618118\\\"\\u003eAustralian Cruise Group\\u003c/a\\u003e\"\n" +
            "                            ],\n" +
            "                            \"photo_reference\" : \"CmRdAAAAtEoj29FJcNBccrsu6bHt0xgwVGhYlciCY1fe6gTr_d5_KkeP3LITnOwnpNRJWnX39B04-aIBOKXKJH6ltx948T5vWIYBoah1yZDXsWngWZ5kMsK7xyCB5P_q_xBIBxxUEhAYWeB4PiOm_Jy093fB-j0iGhSYmIs9xB2aa6u-RH8V8lZEk-Q5ig\",\n" +
            "                            \"width\" : 2000\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"place_id\" : \"ChIJrTLr-GyuEmsRBfy61i59si0\",\n" +
            "                    \"reference\" : \"CnRqAAAAFbukrZvRNsc05TreHUCrPEya5NcN9v0fFLLaK-D1fSyxFTuQlUDhDstU3qwXKw_fADX4W6guUkexax1nufgiYIuGCKoZPEnup1r-LhGGNz9dn1uf9Of5iOtZ1XgCeDjJaYvGbSB3C0pAXL8r9kOsmhIQbx2Sia2DAWTjtSZwuh5aehoUhM6upqBCDLhGruZAGGsCOwAiIq8\",\n" +
            "                    \"scope\" : \"GOOGLE\",\n" +
            "                    \"types\" : [\n" +
            "                        \"restaurant\",\n" +
            "                        \"travel_agency\",\n" +
            "                        \"food\",\n" +
            "                        \"point_of_interest\",\n" +
            "                        \"establishment\"\n" +
            "                    ],\n" +
            "                    \"vicinity\" : \"32 The Promenade, King Street Wharf 5, Sydney\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"geometry\" : {\n" +
            "                        \"location\" : {\n" +
            "                            \"lat\" : -33.870943,\n" +
            "                            \"lng\" : 151.190311\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/bar-71.png\",\n" +
            "                    \"id\" : \"e644f7f34cf875b9919c6548f1b721947362850a\",\n" +
            "                    \"name\" : \"Lunch Cruise with Jazz on Sydney Harbour\",\n" +
            "                    \"opening_hours\" : {\n" +
            "                        \"open_now\" : false,\n" +
            "                        \"weekday_text\" : []\n" +
            "                    },\n" +
            "                    \"photos\" : [\n" +
            "                        {\n" +
            "                            \"height\" : 292,\n" +
            "                            \"html_attributions\" : [\n" +
            "                            \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/105423912060796272053\\\"\\u003eFrom a Google User\\u003c/a\\u003e\"\n" +
            "                            ],\n" +
            "                            \"photo_reference\" : \"CmRdAAAAR4bqFTKYWdBwZwdBdON_JRD7V_joTwwIPwRUpZIZWkSSd8GQ3P2O-_aQbUJdL2RhoAyzCUIF0f--DI4oXFneTpj5zZfFq-iFiT7i_x0tjnDveIY8tJv-6o0uWSSjYqabEhCKqQWZqrKAoddjDcc64N48GhQZ2T1_ntPzNKCooHpZzlYQ7AxFOA\",\n" +
            "                            \"width\" : 438\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"place_id\" : \"ChIJLfySpTOuEmsRPCRKrzl8ZEY\",\n" +
            "                    \"reference\" : \"CoQBewAAAFd2fO_YWGTiT4RzXWb5tsOuOt7YyV_ScQOwm0tqJSrAyACCczeOzV-P_mgZLro1oKP_34Nt0nVC_1OEKAQUcd7cUm7xmAMSX-EkbSWiD0kOWGgGgKuDRtb0t_8qsxBGU_izugWCyK7SRWezTxELYNdkS0OEiSWPnvhxvXuQktBBEhAAtEe7fagW2kUR14T1QpVsGhQBO7YpIyYSPvo4zUJuL_bX30nJZw\",\n" +
            "                    \"scope\" : \"GOOGLE\",\n" +
            "                    \"types\" : [ \"bar\", \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "                    \"vicinity\" : \"37 Bank St, Pyrmont\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                    \"status\" : \"OK\"\n" +
            "            }";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        val list: ArrayList<String> = ArrayList()

        var obj = JSONObject(resList);
        var res=obj.getJSONArray("results");
        (0..(res.length()-1)).forEach { i ->
            var item = res.getJSONObject(i).get("name")
            list.add(item.toString())

            val r = Store(
                res.getJSONObject(i).get("name").toString(),
                res.getJSONObject(i).get("id") .toString(),
                res.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lat") .toString(),
                res.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lng").toString(),
                res.getJSONObject(i).get("vicinity").toString()
            )

            restaurantList.add(r)
        }
        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
            "Virat Kohli", "Rohit Sharma", "Steve Smith",
            "Kane Williamson", "Ross Taylor"
        )

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.recipe_list_view)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, list)
        mListView.adapter = arrayAdapter

        mListView.onItemClickListener = AdapterView.OnItemClickListener() {
                parent, view, position, id ->
            parent.getItemAtPosition(position)
            val r:Store=restaurantList.get(position)
            val intent = Intent(applicationContext, MapsActivity::class.java)
            intent.putExtra("lat",r.latitude)
            intent.putExtra("lon",r.longitude)
            intent.putExtra("id",r.id.toString())
            intent.putExtra("name",r.name)
            intent.putExtra("add",r.address)
            startActivity(intent)
        }
    }
}