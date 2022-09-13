package com.myproject.anasnewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.myproject.anasnewsapp.R
import com.myproject.anasnewsapp.ui.NewsActivity
import com.myproject.anasnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticalFragment : Fragment() {

    lateinit var newsViewModel :NewsViewModel
val args : ArticalFragmentArgs by  navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel= (activity as NewsActivity).newsViewModel
         val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }

        fab.setOnClickListener {
                newsViewModel.saveArticle(article)
            Snackbar.make(view,"Article saved successfuly",Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article,container,false)
    }
}