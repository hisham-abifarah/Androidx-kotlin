package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.movies_popular

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hishamabifarah.androidx_kotlin.R
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.POSTER_BASE_URL
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.Movie
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.NetworkState
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.single_movie_details.SingleMovie
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

/**
 * Created by Hisham Abi Farah on 12/January/2020

 */

//todo notes implement PagedListAdapter
// PagedListAdapter class extends PagedListAdapter<> and initialize MovieDiffCallBack()
// have to create a class that extends DiffUtil
// and implement its members areItemsTheSame and areContentsTheSame

class MoviePagedListAdapter (public val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallBack()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if(viewType == MOVIE_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.movie_list_item,parent,false)
            return MovieItemViewHolder(view)
        }else{
            view = layoutInflater.inflate(R.layout.network_state_item,parent,false)
            return NetworkStateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>(){

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //todo notes : Recyclerview has 2 types of views, one to show the movie items and another to show netwotkstate item
    // create these 2 viewholder classes

    class MovieItemViewHolder (view : View) : RecyclerView.ViewHolder(view){
        //Movie? : means movie variable can accept null values

        fun bind(movie: Movie?, context: Context){
            //movie? : now safe calls, even if movie is null app won't throw a nullpointer exception

            itemView.cv_movie_title.text = movie?.title
            itemView.cv_movie_release_date.text = movie?.releaseDate

//            Log.d("Poster Image", movie?.posterPath.toString())

            if (movie?.posterPath == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.img_not_available)
                    .into(itemView.cv_iv_movie_poster)
            } else {
                val moviePoster = POSTER_BASE_URL + movie.posterPath
                Glide.with(itemView.context)
                    .load(moviePoster)
                    .into(itemView.cv_iv_movie_poster)
            }
            itemView.setOnClickListener {
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }
    }

//    class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
////        fun bind(networkState: NetworkState?) {
////            if (networkState != null && networkState == NetworkState.LOADING) {
////                itemView.progress_bar_item.visibility = View.VISIBLE
////            } else {
////                itemView.progress_bar_item.visibility = View.GONE
////            }
////
////            if (networkState != null && networkState == NetworkState.ERROR) {
////                itemView.error_msg_item.visibility = View.VISIBLE
////                itemView.error_msg_item.text = networkState.message
////            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
////                itemView.error_msg_item.visibility = View.VISIBLE
////                itemView.error_msg_item.text = networkState.message
////            } else {
////                itemView.progress_bar_item.visibility = View.GONE
////            }
////        }
////    }

    class NetworkStateViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {

            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.message;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.message;
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        Log.d("hisham- hadExtraRow" , hadExtraRow.toString())
        Log.d("hisham- hasExtraRow" , hasExtraRow.toString())

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }
}