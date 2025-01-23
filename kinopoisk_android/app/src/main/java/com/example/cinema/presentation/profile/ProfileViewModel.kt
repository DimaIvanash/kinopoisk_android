package com.example.cinema.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.dataBases.Dao
import com.example.cinema.domain.GetPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: GetPageUseCase, private val collectionDao: Dao): ViewModel(){
    private val allCollection = this.collectionDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = emptyList()  //пустой список
        )

//    fun buttonAdd(save: CollectionEntity){
//
//        viewModelScope.launch {
//            collectionDao.insert(
//                CollectionEntity(
//                    save.id,
//                    save.favorite,
//                    show = false,
//                    viewed = false
//
//                )
//            )
//
//        }
//    }
//    fun buttonClear(){
//        viewModelScope.launch {
//            allCollection.value.lastOrNull()?.let { collectionDao.delete(it) }
//        }
//    }


}