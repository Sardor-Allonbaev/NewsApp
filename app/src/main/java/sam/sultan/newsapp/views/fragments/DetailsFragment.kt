package sam.sultan.newsapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import sam.sultan.newsapp.databinding.FragmentDetailsBinding
import sam.sultan.newsapp.models.database.NewsDataBase
import sam.sultan.newsapp.models.entities.Article
import sam.sultan.newsapp.models.repositories.NewsRepository
import sam.sultan.newsapp.models.viewModels.NewsViewModel

class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        var article = arguments?.getSerializable("article") as Article
        if(article == null){
            Toast.makeText(requireContext(), "Can't load", Toast.LENGTH_SHORT).show()
        }else{
            binding.webContent.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url!!)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveBtn.setOnClickListener {
            viewModel.saveArticle(article)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpViewModel(){
        val dao = NewsDataBase.getDatabase(requireContext()).newsDao()
        val repository = NewsRepository(dao)
        viewModel = NewsViewModel(repository)
    }

}