package sam.sultan.newsapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sam.sultan.newsapp.R
import sam.sultan.newsapp.adapters.RvAdapter
import sam.sultan.newsapp.databinding.FragmentArchivesBinding
import sam.sultan.newsapp.models.database.NewsDataBase
import sam.sultan.newsapp.models.entities.Article
import sam.sultan.newsapp.models.repositories.NewsRepository
import sam.sultan.newsapp.models.viewModels.NewsViewModel
import sam.sultan.newsapp.models.viewModels.ViewModelFactory

class ArchivesFragment : Fragment() {

    lateinit var binding: FragmentArchivesBinding
    private lateinit var viewModel: NewsViewModel
    private val adapter = RvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArchivesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        binding.archiveRv.layoutManager = LinearLayoutManager(requireContext())
        binding.archiveRv.adapter = adapter
        adapter.clickToDetails = { detailsPage(it) }
        getAllFromDb()
    }

    private fun detailsPage(article: Article){
        var bundle = Bundle();
        bundle.putSerializable("article", article)
        findNavController().navigate(R.id.action_archivesFragment_to_detailsFragment, bundle)
    }

    private fun setUpViewModel(){
        val dao = NewsDataBase.getDatabase(requireContext()).newsDao()
        val repository = NewsRepository(dao)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(NewsViewModel::class.java)
    }

    private fun getAllFromDb(){
        viewModel.archivedNews.observe(viewLifecycleOwner, Observer { it ->
            adapter.setNewsList(it)
        })
    }

}