package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val share:Button=findViewById(R.id.share)
        val next:Button=findViewById(R.id.nextButton)
        val meme:ImageView=findViewById(R.id.memeImageView)
        val Progress_bar:ProgressBar=findViewById(R.id.progressBar)
        var memeUrl:String?=null
         fun loadMeme()
        {
            //initiate the request queue
            Progress_bar.visibility= View.VISIBLE
            val queue= Volley.newRequestQueue(this)
            val url="https://meme-api.herokuapp.com/gimme"
            //request a string response from the provided URL
            val jsonObject=JsonObjectRequest(Request.Method.GET,url,null,
                    Response.Listener{ response->
                        memeUrl=response.getString("url")
                        Glide.with(this).load(memeUrl).listener(object:RequestListener<Drawable>{
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                Progress_bar.visibility=View.GONE
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                 Progress_bar.visibility=View.GONE
                                 return false
                            }
                        }).into(meme)
                    },Response.ErrorListener{
                        Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show()
            })
            queue.add(jsonObject)
        }
        loadMeme()
        share.setOnClickListener {
            val intent=Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"hey checkout this cool meme ${memeUrl}")
            val chooser=Intent.createChooser(intent,"share this meme using:")
            startActivity(chooser)
        }
        next.setOnClickListener {  loadMeme()}
    }
}