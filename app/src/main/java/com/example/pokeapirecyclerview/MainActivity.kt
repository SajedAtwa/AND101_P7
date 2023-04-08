package com.example.pokeapirecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

data class Pokemon(val pokeImageUrl: String, val pokeName: String, val pokeType: String)

class MainActivity : AppCompatActivity() {
    private lateinit var pokeList: MutableList<Pokemon>
    private lateinit var rvPoke: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPoke = findViewById(R.id.poke_list)
        pokeList = mutableListOf()

        getPokemonImageURL()
    }

    var pokemonID = Random.nextInt(500)

    private fun getPokemonImageURL() {
        val client = AsyncHttpClient()
        for(i in 0 until 40) {
            pokemonID = Random.nextInt(500)
            client["https://pokeapi.co/api/v2/pokemon/$pokemonID", object :
                JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {

                    val pokeImageURL =
                        json.jsonObject.getJSONObject("sprites").getString("back_default")
                    val pokeName = json.jsonObject.getJSONObject("species").getString("name")
                    val pokeType =
                        json.jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type")
                            .getString("name")
                    val pokeData = Pokemon(pokeImageURL, pokeName, pokeType)
                    pokeList.add(pokeData)
                    val adapter = PokeAdapter(pokeList)
                    if (pokeList.size == 40) {
                        rvPoke.adapter = adapter
                        rvPoke.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("poke Error", errorResponse)
                }
            }]
        }
    }
}