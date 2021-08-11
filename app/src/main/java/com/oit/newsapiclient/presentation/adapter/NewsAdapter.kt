package com.oit.newsapiclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter(
) : RecyclerView.Adapter<NewsViewHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsListItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

class NewsViewHolder(
    private val binding: NewsListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.tvTitle.text = article.title
        binding.tvDescription.text = article.description
        binding.tvPublishedAt.text = article.publishedAt
        binding.tvSource.text = article.source.name

        val urlToImage: String = article.urlToImage
        Glide.with(binding.ivArticleImage.context)
            .load(urlToImage)
            .into(binding.ivArticleImage)
    }

}