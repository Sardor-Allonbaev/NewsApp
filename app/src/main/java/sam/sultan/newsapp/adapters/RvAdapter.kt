package sam.sultan.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sam.sultan.newsapp.R
import sam.sultan.newsapp.databinding.RvItemBinding
import sam.sultan.newsapp.models.entities.Article

class RvAdapter: RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    private var newsList: List<Article> = emptyList();

    var clickToDetails: ((Article)->Unit)? = null


    inner class RvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var binding = RvItemBinding.bind(itemView)
        fun bind(article: Article) = with(binding){
            Glide.with(binding.image).load(article.urlToImage).into(binding.image)
            newsTitle.text = article.title
            description.text = article.description
            author.text = article.author
            publishedDate.text = article.publishedAt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return RvViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(newsList[position])
        holder.binding.newsTitle.setOnClickListener { clickToDetails?.invoke(newsList[position]) }
    }

    fun setNewsList(newList: List<Article>){
        val diffUtil = DiffUtils(newsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        newsList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}