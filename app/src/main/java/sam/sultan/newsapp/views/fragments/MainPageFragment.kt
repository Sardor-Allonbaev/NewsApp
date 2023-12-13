package sam.sultan.newsapp.views.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sam.sultan.newsapp.R
import sam.sultan.newsapp.adapters.RvAdapter
import sam.sultan.newsapp.databinding.FragmentMainPageBinding
import sam.sultan.newsapp.models.database.NewsDataBase
import sam.sultan.newsapp.models.entities.Article
import sam.sultan.newsapp.models.repositories.NewsRepository
import sam.sultan.newsapp.models.viewModels.NewsViewModel
import sam.sultan.newsapp.models.viewModels.ViewModelFactory
import sam.sultan.newsapp.utils.Resource


class MainPageFragment : Fragment() {

    lateinit var binding: FragmentMainPageBinding
    var adapter = RvAdapter()
    private lateinit var viewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelSetUp()
        if(isNetworkConnected(requireContext())){
            viewModel.getAllNews()
        }else{
            hideProgressBar()
            Toast.makeText(requireContext(), "Please, connect to the Internet", Toast.LENGTH_LONG).show()
        }

        getNews()
        binding.mainRv.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRv.adapter = adapter
        adapter.clickToDetails = { detailsPage(it) }
        binding.refreshBtn.setOnClickListener {
            viewModel.getAllNews()
        }
    }

    private fun detailsPage(article: Article){
        var bundle = Bundle();
        bundle.putSerializable("article", article)
        findNavController().navigate(R.id.action_mainPageFragment_to_detailsFragment, bundle)
    }



    private fun getNews(){
        viewModel.news.observe(viewLifecycleOwner, Observer {
            showProgressBar()
            if(it is Resource.Success){
                hideProgressBar()
                it.data?.articles?.let { it1 -> adapter.setNewsList(it1) }
            }else if(it is Resource.Error){
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun viewModelSetUp(){
        val dao = NewsDataBase.getDatabase(requireContext()).newsDao()
        val repository = NewsRepository(dao)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(NewsViewModel::class.java)
    }

    private fun showProgressBar(){
        binding.mainRv.visibility = View.GONE
        binding.progressBar3.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.mainRv.visibility = View.VISIBLE
        binding.progressBar3.visibility = View.GONE
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}