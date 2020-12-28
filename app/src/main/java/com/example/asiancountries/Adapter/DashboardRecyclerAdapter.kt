package com.example.asiancountries.Adapter

import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.android.volley.toolbox.ImageLoader
import com.bumptech.glide.Glide
import com.example.asiancountries.MainActivity
import com.example.asiancountries.R
import com.example.asiancountries.model.Countries
import com.example.asiancountries.model.CountryEntity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(private val context: Context,
                               var itemList: ArrayList<CountryEntity>):
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.ctrName) as TextView
        val capital=itemView.findViewById(R.id.capital) as TextView
        val region=itemView.findViewById(R.id.region) as TextView
        val subregion=itemView.findViewById(R.id.subregion) as TextView
        val population=itemView.findViewById(R.id.population) as TextView
        val borders=itemView.findViewById(R.id.borders) as TextView
        val languages=itemView.findViewById(R.id.languages) as TextView
        val imgflag = itemView.findViewById(R.id.imgflag) as ImageView
        //val borders=itemView.findViewById(R.id.borders)

        companion object {
            //var userId: Int? = 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_dashboard_single_row,
            parent,
            false
        )
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val resObject = itemList.get(position)
        //holder.imgflag.clipToOutline = true
        holder.name.text = resObject.name
        holder.capital.text = resObject.capital
        holder.region.text=resObject.region
        holder.subregion.text=resObject.subregion
        holder.population.text=resObject.population
        holder.borders.text=resObject.borders
        holder.languages.text=resObject.languages
        //Picasso.get().load(resObject.imgFlag).error(R.drawable.ic_error).into(holder.imgflag)
       // Picasso.get().load("https://restcountries.eu/data/col.svg").error(R.drawable.ic_error).into(holder.imgflag)
        //Glide.with(context as MainActivity).load(resObject.imgFlag).into(holder.imgflag)
        GlideToVectorYou.init().with(context).load(Uri.parse(resObject.imgFlag),holder.imgflag);
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun setData(record: ArrayList<CountryEntity>) {
        this.itemList = record
        notifyDataSetChanged()

    }

}