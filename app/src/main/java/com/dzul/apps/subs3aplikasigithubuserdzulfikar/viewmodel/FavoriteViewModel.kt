package com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity.FavoriteEntity
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.repository.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    val allFavoriteUsers: LiveData<List<FavoriteEntity>> = repository.allFavUsers.asLiveData()

    fun insert(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        repository.insert(favoriteEntity)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteUser(userLogin: String) = viewModelScope.launch {
        repository.delete(userLogin)
    }

    fun getUser(userLogin: String) : Array<FavoriteEntity> {
        return repository.getSelectUser(userLogin)
    }


}
class FavoriteViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

