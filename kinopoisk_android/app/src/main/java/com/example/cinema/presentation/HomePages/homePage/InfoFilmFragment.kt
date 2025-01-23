package com.example.cinema.presentation.HomePages.homePage
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.InfoStaffDto
import com.example.cinema.data.SimilarFilmDto
import com.example.cinema.data.StaffJobFilmDto
import com.example.cinema.data.dataBases.CollectionWithFilms
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.FragmentInfoFilmBinding
import com.example.cinema.presentation.BottonSheetCollectionAdapter
import com.example.cinema.presentation.ConnectivityChecker
import com.example.cinema.presentation.HomePages.homePage.ActorPageFragment.Companion.STAFF_ID
import com.example.cinema.presentation.State
import com.example.cinema.presentation.filmGallery.GalleryFragment.Companion.GALLERY
import com.example.cinema.presentation.filmGallery.FilmGalleryAdapter
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_ALL_ACTOR_ID
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_ALL_RELATED_FILM
import com.example.cinema.presentation.staff.SimilarFilmAdapter
import com.example.cinema.presentation.staff.StaffAdapter
import com.example.cinema.presentation.staff.StaffJobFilmAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class InfoFilmFragment : Fragment() {
    companion object {
        const val ID = "id"
        private const val LIST_STATE_ADD = "LIST_STATE_ADD"
        private const val LIST_STATE_REMOVE = "LIST_STATE_REMOVE"
        const val CLICK_SERIES_FILMS = "click_series_films"
    }

    private val viewModel: HomePageViewModel by viewModels()
    private var _binding: FragmentInfoFilmBinding? = null
    private val binding get() = _binding!!
    private val connectivityChecker = ConnectivityChecker()

    private val staffAdapter = StaffAdapter { pageDto -> onClickStaff(pageDto) }
    private val galleryAdapter = FilmGalleryAdapter()
    private val staffJobFilmAdapter = StaffJobFilmAdapter { pageDto -> onClickJob(pageDto) }
    private val similarFilmAdapter = SimilarFilmAdapter { pageDto -> onClickRelated(pageDto) }
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n", "CutPasteId", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (connectivityChecker.isInternetAvailable(requireContext())) {
                loadingVisible()
                val idBundle = arguments?.getInt(ID)!!
                val bundleGalleryId = Bundle()
                bundleGalleryId.putInt(GALLERY, idBundle)
                val clickSeriesFilmsAll = arguments?.getBoolean(CLICK_SERIES_FILMS)
                val collectionButtonSheetAdapter = BottonSheetCollectionAdapter { state, element ->
                    onClickCheckbox(element, idBundle, state)
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    val webUrl = viewModel.apiInfo(idBundle).webUrl
                    val minutes = viewModel.apiInfo(idBundle).filmLength
                    val hours = minutes?.div(60).toString()
                    val minutesRemainder = minutes?.rem(60).toString()
                    if (clickSeriesFilmsAll == true) {
                        with(binding) {
                            containerSerial.visibility = View.VISIBLE
                            seasons.text =
                                getString(viewModel.getEpisodes(idBundle).size, R.string.season)
                            series.text =
                                viewModel.getEpisodes(idBundle).firstOrNull()?.episodes?.size?.let {
                                    getString(
                                        it, R.string.series
                                    )
                                }
                            val bundle = Bundle()
                            bundle.putInt(ID, idBundle)
                            allSeries.setOnClickListener {
                                findNavController().navigate(
                                    R.id.action_infoFilmFragment_to_episodesFragment,
                                    bundle
                                )
                            }
                        }
                    } else {
                        binding.containerSerial.visibility = View.INVISIBLE
                    }
                    Glide.with(binding.infoFilmImage)
                        .load(viewModel.apiInfo(idBundle).posterUrl)
                        .centerCrop()
                        .into(binding.infoFilmImage)

                    with(binding) {
                        textDescr.text = viewModel.apiInfo(idBundle).description
                        textDescr.ellipsize = TextUtils.TruncateAt.END
                        textDescr.maxLines = 3
                        textDescr.setOnClickListener {
                            if (textDescr.text.isNotEmpty()) {
                                textDescr.maxLines = Int.MAX_VALUE
                                textDescr.ellipsize = TextUtils.TruncateAt.MARQUEE
                                textDescr.movementMethod = ScrollingMovementMethod()
                            } else {
                                textDescr.maxLines = 3
                                textDescr.text
                                textDescr.ellipsize = TextUtils.TruncateAt.END
                                textDescr.movementMethod = null
                            }
                        }
                        someId.text = viewModel.apiInfo(idBundle).ratingKinopoisk.toString()
                        textName.text = viewModel.apiInfo(idBundle).nameRu
                        infoYear.text = viewModel.apiInfo(idBundle).year
                        infoGenre.text =
                            viewModel.apiInfo(idBundle).genres.firstOrNull()?.genre.toString()
                        infoCountry.text =
                            viewModel.apiInfo(idBundle).countries.firstOrNull()?.country.toString()
                        infoLong.text =
                            hours + getString(R.string.hour) + " " + minutesRemainder + getString(R.string.minutes)
                        infoAge.text = viewModel.apiInfo(idBundle).ratingAgeLimits
                    }
                    binding.share.setOnClickListener {
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, webUrl)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, "Share with")
                        startActivity(shareIntent)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    binding.allEpisode.text = viewModel.staff(idBundle).size.toString()
                    staffAdapter.setData(viewModel.staff(idBundle))
                    binding.textView4.text = viewModel.staffJobFilm(idBundle).size.toString()
                    staffJobFilmAdapter.setData(viewModel.staffJobFilm(idBundle))
                    binding.textView5.text = viewModel.gallery(idBundle).size.toString()
                    galleryAdapter.setData(viewModel.gallery(idBundle))
                    binding.textViewSimilar.text = viewModel.similarFilm(idBundle).size.toString()
                    similarFilmAdapter.setData(viewModel.similarFilm(idBundle))
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    val like = FilmEntity(
                        id = idBundle,
                        posterUrl = viewModel.apiInfo(idBundle).posterUrl,
                        nameRu = viewModel.apiInfo(idBundle).nameRu,
                    )
                    viewModel.addFilm(like)
                    binding.like.setOnClickListener {
                        when {
                            binding.like.isChecked -> {
                                viewModel.addFilmForCollection(1, idBundle)
                            }

                            !binding.like.isChecked -> {
                                viewModel.deleteFilmForCollection(1, idBundle)
                            }
                        }
                    }
                    binding.bookmark.setOnClickListener {
                        when {
                            binding.bookmark.isChecked -> {
                                viewModel.addFilmForCollection(2, idBundle)
                            }

                            !binding.bookmark.isChecked -> {
                                viewModel.deleteFilmForCollection(2, idBundle)
                            }
                        }
                    }
                    binding.hide.setOnClickListener {
                        when {
                            binding.hide.isChecked -> {
                                viewModel.addFilmForCollection(3, idBundle)
                            }

                            !binding.hide.isChecked -> {
                                viewModel.deleteFilmForCollection(3, idBundle)
                            }
                        }
                    }
                    binding.threeDot.setOnClickListener {
                        val dialogLayout = layoutInflater.inflate(R.layout.sheet_layout_one, null)
                        val builder = BottomSheetDialog(requireContext())
                        val closeButton = dialogLayout.findViewById<ImageView>(R.id.exit)
                        with(builder) {
                            setContentView(dialogLayout)
                            show()
                        }
                        viewLifecycleOwner.lifecycleScope.launch {
                            with(dialogLayout) {

                                findViewById<TextView>(R.id.text_name_sheet).text =
                                    viewModel.apiInfo(idBundle).nameRu
                                findViewById<TextView>(R.id.text_year_sheet).text =
                                    viewModel.apiInfo(idBundle).year
                                findViewById<TextView>(R.id.text_genre_sheet).text =
                                    viewModel.apiInfo(idBundle).genres.firstOrNull()?.genre.toString()
                                Glide.with(this@InfoFilmFragment)
                                    .load(viewModel.apiInfo(idBundle).posterUrl)
                                    .into(findViewById(R.id.image_sheet))
                            }
                        }
                        closeButton.setOnClickListener {
                            builder.dismiss()
                        }
                        recyclerView = dialogLayout.findViewById(R.id.recycler_add_collection)
                        recyclerView.adapter = collectionButtonSheetAdapter
                        recyclerView.layoutManager = LinearLayoutManager(
                            requireContext(), LinearLayoutManager.VERTICAL, false
                        )

                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.allCollection.collect { item ->
                                collectionButtonSheetAdapter.setData(item)
                            }
                        }
                        dialogLayout.findViewById<ImageView>(R.id.addPlus).setOnClickListener {
                            val dialog = DialogScreen().getDialog(
                                requireActivity(),
                                DialogScreen.ADD_COLLECTION_DIALOG,
                                viewModel
                            )
                            dialog?.show()
                        }
                    }
                    viewModel.allCollection.collect { collect ->
                        collect.elementAtOrNull(0)?.films?.forEach {
                            if (it.id == idBundle) {
                                binding.like.isChecked = true
                            }
                        }
                        collect.elementAtOrNull(1)?.films?.forEach {
                            if (it.id == idBundle) {
                                binding.bookmark.isChecked = true
                            }
                        }
                        collect.elementAtOrNull(2)?.films?.forEach {
                            if (it.id == idBundle) {
                                binding.hide.isChecked = true
                            }
                        }
                    }
                }
                binding.recyclerStaff.adapter = staffAdapter
                binding.recyclerStaff.layoutManager = GridLayoutManager(
                    requireContext(), 4, LinearLayoutManager.HORIZONTAL, false
                )

                binding.recyclerFilmJob.adapter = staffJobFilmAdapter
                binding.recyclerFilmJob.layoutManager = GridLayoutManager(
                    requireContext(), 2, LinearLayoutManager.HORIZONTAL, false
                )
                binding.recyclerGallery.adapter = galleryAdapter

                binding.recyclerGallery.layoutManager = GridLayoutManager(
                    requireContext(), 1, GridLayoutManager.HORIZONTAL, false
                )

                binding.recyclerSimilar.adapter = similarFilmAdapter
                binding.recyclerSimilar.layoutManager = GridLayoutManager(
                    requireContext(), 1, LinearLayoutManager.HORIZONTAL, false
                )

                binding.textView5.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_infoFilmFragment_to_galleryFragment,
                        bundleGalleryId
                    )
                }
                binding.allActor.setOnClickListener {
                    val onClick = true
                    val bundle = Bundle()
                    bundle.putInt(ID, idBundle)
                    bundle.putBoolean(CLICK_ALL_ACTOR_ID, onClick)
                    findNavController().navigate(
                        R.id.action_infoFilmFragment_to_profileAllFilmsFragment,
                        bundle
                    )
                }
                binding.allJobIlm.setOnClickListener {
                    val onClick = true
                    val bundle = Bundle()
                    bundle.putInt(ID, idBundle)
                    bundle.putBoolean(CLICK_ALL_ACTOR_ID, onClick)
                    findNavController().navigate(
                        R.id.action_infoFilmFragment_to_profileAllFilmsFragment,
                        bundle
                    )
                }
                binding.allRelated.setOnClickListener {
                    val onClick = true
                    val bundle = Bundle()
                    bundle.putInt(ID, idBundle)
                    bundle.putBoolean(CLICK_ALL_RELATED_FILM, onClick)
                    findNavController().navigate(
                        R.id.action_infoFilmFragment_to_profileAllFilmsFragment,
                        bundle
                    )
                }
                binding.outlineInt.setOnClickListener {
                    findNavController().navigate(R.id.action_infoFilmFragment_to_homePageFragment)
                }
            } else {
//                // Интернет недоступен
                Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Обработка ошибки
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



    private fun onClickStaff(dataItem: InfoStaffDto){
        val id = dataItem.staffId
        val bundle = Bundle()
        bundle.putInt(STAFF_ID, id!!)
        findNavController().navigate(R.id.action_infoFilmFragment_to_actorPageFragment, bundle)
    }
    private fun onClickJob(dataItem: StaffJobFilmDto){
        val id = dataItem.staffId
        val bundle = Bundle()
        bundle.putInt(STAFF_ID, id!!)
        findNavController().navigate(R.id.action_infoFilmFragment_to_actorPageFragment, bundle)
    }
    private fun onClickRelated(dataItem: SimilarFilmDto){
        val id = dataItem.filmId
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(ID, id)
        }
        findNavController().navigate(R.id.action_infoFilmFragment_self, bundle)
    }
    private fun onClickCheckbox(item: CollectionWithFilms, id: Int, state: String ) {
        if (state == LIST_STATE_ADD ){
            item.collection.collectionId?.let { viewModel.addFilmForCollection(it, id)
            }
        }else if (state == LIST_STATE_REMOVE){
            item.collection.collectionId?.let { viewModel.deleteFilmForCollection(it, id) }
        }
    }
    private fun loadingVisible() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{state ->
                when(state){
                    State.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.scrollView.isVisible = false
                    }
                    State.Success -> {
                        binding.progressBar.isVisible = false
                        binding.scrollView.isVisible = true
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


